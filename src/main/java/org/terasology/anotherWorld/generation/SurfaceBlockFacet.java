// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.anotherWorld.SparseObjectFacet3D;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockRegion;
import org.terasology.engine.world.generation.Border3D;

public class SurfaceBlockFacet extends SparseObjectFacet3D<Block> {
    public SurfaceBlockFacet(BlockRegion targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}
