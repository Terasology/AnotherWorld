// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import com.google.common.base.Predicate;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseObjectFacet3D;

public class OreBlockFacet extends BaseObjectFacet3D<Block> {
    private final Predicate<Block> replacePredicate;

    public OreBlockFacet(Region3i targetRegion, Border3D border, Class<Block> objectType,
                         Predicate<Block> replacePredicate) {
        super(targetRegion, border, objectType);
        this.replacePredicate = replacePredicate;
    }

    public Predicate<Block> getReplacePredicate() {
        return replacePredicate;
    }
}
