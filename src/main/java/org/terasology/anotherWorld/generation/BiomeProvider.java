/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.anotherWorld.generation;

import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.biomesAPI.BiomeRegistry;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Vector2i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.facets.SeaLevelFacet;
import org.terasology.world.generation.facets.SurfaceHeightFacet;


@Produces(BiomeFacet.class)
@Requires({@Facet(TemperatureFacet.class),
        @Facet(HumidityFacet.class),
        @Facet(HillynessFacet.class),
        @Facet(SurfaceHeightFacet.class)})
public class BiomeProvider implements FacetProvider {

    @Override
    public void setSeed(long seed) {
    }

    @Override
    public void process(GeneratingRegion region) {
        BiomeFacet facet = new BiomeFacet(region.getRegion(), region.getBorderForFacet(BiomeFacet.class));
        TemperatureFacet temperatureFacet = region.getRegionFacet(TemperatureFacet.class);
        HumidityFacet surfaceHumidityFacet = region.getRegionFacet(HumidityFacet.class);
        HillynessFacet hillynessFacet = region.getRegionFacet(HillynessFacet.class);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        BiomeRegistry biomeRegistry = CoreRegistry.get(BiomeRegistry.class);

        for (BaseVector2i pos : facet.getWorldRegion().contents()) {
            int surfaceHeight = TeraMath.floorToInt(surfaceHeightFacet.getWorld(pos));
            float temp = temperatureFacet.get(pos.x(), surfaceHeight, pos.y());
            float hum = surfaceHumidityFacet.get(pos.x(), surfaceHeight, pos.y());
            float hillyness = hillynessFacet.getWorld(pos);

            AnotherWorldBiome bestBiome = getBestBiomeMatch(biomeRegistry, temp, hum, hillyness, surfaceHeight);
            facet.setWorld(pos, bestBiome);
        }

        region.setRegionFacet(BiomeFacet.class, facet);
    }


    private AnotherWorldBiome getBestBiomeMatch(BiomeRegistry biomeRegistry, float temp, float hum, float hillyness, float height) {
        AnotherWorldBiome chosenBiome = null;
        float maxPriority = 0;

        for (AnotherWorldBiome biome : biomeRegistry.getRegisteredBiomes(AnotherWorldBiome.class)) {
            final AnotherWorldBiome.SweetSpot sweetSpot = biome.getSweetSpot();
            float matchPriority = 0;

            matchPriority += sweetSpot.getAboveSeaLevelWeight() * (1 - Math.abs(sweetSpot.getAboveSeaLevel() - height));
            matchPriority += sweetSpot.getHumidityWeight() * (1 - Math.abs(sweetSpot.getHumidity() - hum));
            matchPriority += sweetSpot.getTemperatureWeight() * (1 - Math.abs(sweetSpot.getTemperature() - temp));
            matchPriority += sweetSpot.getTerrainWeight() * (1 - Math.abs(sweetSpot.getTerrain() - hillyness));

            matchPriority *= biome.getRarity();

            if (matchPriority > maxPriority) {
                chosenBiome = biome;
                maxPriority = matchPriority;
            }
        }
        return chosenBiome;
    }
}
