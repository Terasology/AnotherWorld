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

import com.google.common.collect.Maps;
import org.joml.Vector2ic;
import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.anotherWorld.decorator.layering.LayerDefinition;
import org.terasology.anotherWorld.decorator.layering.LayeringConfig;
import org.terasology.biomesAPI.Biome;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.Requires;
import org.terasology.gestalt.naming.Name;

import java.util.List;
import java.util.Map;

@Produces(BlockLayersFacet.class)
@Requires(value = {@Facet(BiomeFacet.class)})
public class BlockLayersProvider implements FacetProvider {
    private LayeringConfig layeringConfig;
    private long seed;

    private Map<Name, List<LayerDefinition>> biomeLayerDefinitions = Maps.newHashMap();

    public BlockLayersProvider(LayeringConfig layeringConfig) {
        this.layeringConfig = layeringConfig;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void addBiomeLayerDefinitions(Biome biome, List<LayerDefinition> layerDefinitions) {
        biomeLayerDefinitions.put(biome.getId(), layerDefinitions);
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(BlockLayersFacet.class);
        // Need to fool Java with the double-casting
        BlockLayersFacet result = new BlockLayersFacet(region.getRegion(), border, (Class<List<LayerDefinition>>) (Class<?>) List.class);
        BiomeFacet biomeFacet = region.getRegionFacet(BiomeFacet.class);

        for (Vector2ic pos : result.getRelativeArea()) {
            AnotherWorldBiome biome = biomeFacet.get(pos);
            result.set(pos, biomeLayerDefinitions.get(biome.getId()));
        }

        region.setRegionFacet(BlockLayersFacet.class, result);
    }
}
