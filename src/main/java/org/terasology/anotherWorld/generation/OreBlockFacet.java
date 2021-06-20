// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import com.google.common.base.Predicate;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockRegion;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseObjectFacet3D;

public class OreBlockFacet extends BaseObjectFacet3D<Block> {
    private Predicate<Block> replacePredicate;

    public OreBlockFacet(BlockRegion targetRegion, Border3D border, Class<Block> objectType, Predicate<Block> replacePredicate) {
        super(targetRegion, border, objectType);
        this.replacePredicate = replacePredicate;
    }

    public Predicate<Block> getReplacePredicate() {
        return replacePredicate;
    }
}
