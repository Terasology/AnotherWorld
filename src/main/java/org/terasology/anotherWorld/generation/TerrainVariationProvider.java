// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.engine.utilities.procedural.Noise3D;
import org.terasology.engine.utilities.procedural.SimplexNoise;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;

@Produces(TerrainVariationFacet.class)
public class TerrainVariationProvider implements FacetProvider {
    private Noise3D noise;

    @Override
    public void setSeed(long seed) {
        noise = new SimplexNoise(seed + 2349873);
    }

    @Override
    public void process(GeneratingRegion region) {
        TerrainVariationFacet facet = new TerrainVariationFacet(region.getRegion(),
                region.getBorderForFacet(TerrainVariationFacet.class), noise);

        region.setRegionFacet(TerrainVariationFacet.class, facet);
    }
}
