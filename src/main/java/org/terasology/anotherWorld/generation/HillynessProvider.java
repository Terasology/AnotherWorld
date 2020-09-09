// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.FacetBorder;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.Requires;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.math.TeraMath;

@Produces(HillynessFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = HillynessProvider.RANGE)))
public class HillynessProvider implements FacetProvider {
    public static final int RANGE = 5;

    public HillynessProvider() {
    }

    @Override
    public void setSeed(long seed) {

    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(HillynessFacet.class);
        HillynessFacet facet = new HillynessFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        Region3i worldRegion = region.getRegion();

        for (int x = worldRegion.minX(); x <= worldRegion.maxX(); x++) {
            for (int z = worldRegion.minZ(); z <= worldRegion.maxZ(); z++) {
                float baseHeight = surfaceHeightFacet.getWorld(x, z);
                int count = 0;
                int diffSum = 0;
                for (int testX = x - RANGE; testX < x + RANGE; testX++) {
                    int zRange = (int) Math.sqrt(RANGE * RANGE - (testX - x) * (testX - x));
                    for (int testZ = z - zRange; testZ < z + zRange; testZ++) {
                        count++;
                        diffSum += Math.abs(surfaceHeightFacet.getWorld(testX, testZ) - baseHeight);
                    }
                }
                float diffAverage = 1f * diffSum / count;

                facet.setWorld(x, z, TeraMath.clamp(diffAverage / 2));
            }
        }

        region.setRegionFacet(HillynessFacet.class, facet);
    }
}
