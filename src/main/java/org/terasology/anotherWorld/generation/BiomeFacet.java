// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseObjectFacet2D;

public class BiomeFacet extends BaseObjectFacet2D<AnotherWorldBiome> {
    public BiomeFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border, AnotherWorldBiome.class);
    }
}
