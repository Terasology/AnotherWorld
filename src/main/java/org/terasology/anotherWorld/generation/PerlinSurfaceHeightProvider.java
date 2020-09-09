// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import com.google.common.base.Function;
import org.terasology.anotherWorld.TerrainDeformation;
import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.engine.utilities.procedural.BrownianNoise2D;
import org.terasology.engine.utilities.procedural.Noise2D;
import org.terasology.engine.utilities.procedural.SimplexNoise;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.BaseVector2i;

@Produces(SurfaceHeightFacet.class)
public class PerlinSurfaceHeightProvider implements FacetProvider {
    private static final float MIN_MULTIPLIER = 0.00005f;
    private static final float MAX_MULTIPLIER = 0.001f;
    private final float seaFrequency;
    private final int seaLevel;
    private final int maxLevel;
    private final float terrainNoiseMultiplier;
    private final Function<Float, Float> generalHeightFunction;
    private final Function<Float, Float> heightBelowSeaLevelFunction;
    private final Function<Float, Float> heightAboveSeaLevelFunction;
    private final float hillynessDiversity;
    private final Function<Float, Float> hillynessFunction;
    private Noise2D noise;
    private double noiseScale;
    private TerrainDeformation terrainDeformation;

    /**
     * @deprecated Use the full constructor instead.
     */
    @Deprecated
    public PerlinSurfaceHeightProvider(float seaFrequency, Function<Float, Float> heightAboveSeaLevelFunction,
                                       float hillynessDiversity, Function<Float, Float> hillynessFunction,
                                       int seaLevel, int maxLevel) {
        this(seaFrequency, 0.4216f, IdentityAlphaFunction.singleton(), IdentityAlphaFunction.singleton(),
                heightAboveSeaLevelFunction, hillynessDiversity, hillynessFunction, seaLevel, maxLevel);
    }

    public PerlinSurfaceHeightProvider(float seaFrequency, float terrainDiversity,
                                       Function<Float, Float> generalTerrainFunction,
                                       Function<Float, Float> heightBelowSeaLevelFunction,
                                       Function<Float, Float> heightAboveSeaLevelFunction,
                                       float hillinessDiversity, Function<Float, Float> hillynessFunction,
                                       int seaLevel, int maxLevel) {
        this.seaFrequency = seaFrequency;
        this.seaLevel = seaLevel;
        this.maxLevel = maxLevel;
        this.terrainNoiseMultiplier = MIN_MULTIPLIER + terrainDiversity * (MAX_MULTIPLIER - MIN_MULTIPLIER);
        this.generalHeightFunction = generalTerrainFunction;
        this.heightBelowSeaLevelFunction = heightBelowSeaLevelFunction;
        this.heightAboveSeaLevelFunction = heightAboveSeaLevelFunction;
        this.hillynessDiversity = hillinessDiversity;
        this.hillynessFunction = hillynessFunction;
    }

    @Override
    public void setSeed(long seed) {
        BrownianNoise2D brownianNoise = new BrownianNoise2D(new SimplexNoise(seed), 6);
        noise = brownianNoise;
        noiseScale = brownianNoise.getScale();
        terrainDeformation = new TerrainDeformation(seed, hillynessDiversity, hillynessFunction);
    }

    private float getNoiseInWorld(int worldX, int worldZ) {
        float hillyness = terrainDeformation.getHillyness(worldX, worldZ);

        double noiseValue = 0;
        int scanArea = (int) ((1 - hillyness) * 30);
        int divider = 0;
        int sampleReduction = Math.max(1, scanArea / 4);
        // Scan and average heights in the circle of blocks with diameter of "scanArea" (based on hillyness)
        for (int x = worldX - scanArea; x <= worldX + scanArea; x++) {
            if (x % sampleReduction == 0) {
                int zScan = (int) Math.sqrt(scanArea * scanArea - (x - worldX) * (x - worldX));
                for (int z = worldZ - zScan; z <= worldZ + zScan; z++) {
                    if (z % sampleReduction == 0) {
                        noiseValue += noise.noise(terrainNoiseMultiplier * x, terrainNoiseMultiplier * z) / noiseScale;
                        divider++;
                    }
                }
            }
        }
        return generalHeightFunction.apply((float) TeraMath.clamp((noiseValue / divider + 1.0) / 2));
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(SurfaceHeightFacet.class);
        SurfaceHeightFacet facet = new SurfaceHeightFacet(region.getRegion(), border);

        for (BaseVector2i position : facet.getWorldRegion().contents()) {
            float noiseValue = getNoiseInWorld(position.x(), position.y());
            if (noiseValue < seaFrequency) {
                // Number in range 0<=alpha<1
                float alphaBelowSeaLevel = (noiseValue / seaFrequency);
                float resultAlpha = heightBelowSeaLevelFunction.apply(alphaBelowSeaLevel);
                facet.setWorld(position, (seaLevel * resultAlpha));
            } else {
                // Number in range 0<=alpha<1
                float alphaAboveSeaLevel = (noiseValue - seaFrequency) / (1 - seaFrequency);
                float resultAlpha = heightAboveSeaLevelFunction.apply(alphaAboveSeaLevel);
                facet.setWorld(position, (seaLevel + resultAlpha * (maxLevel - seaLevel)));
            }
        }

        region.setRegionFacet(SurfaceHeightFacet.class, facet);
    }
}
