// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.Requires;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;

@Produces(SurfaceBlockFacet.class)
@Requires({@Facet(BlockLayersFacet.class), @Facet(SurfaceHeightFacet.class),
        @Facet(CaveFacet.class), @Facet(OreBlockFacet.class)})
public class SurfaceBlockFacetProvider implements FacetProvider {
    @Override
    public void setSeed(long seed) {

    }

    @Override
    public void process(GeneratingRegion region) {

    }
}
