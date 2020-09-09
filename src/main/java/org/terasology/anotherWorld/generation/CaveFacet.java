// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import com.google.common.base.Predicate;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseBooleanFieldFacet3D;

public class CaveFacet extends BaseBooleanFieldFacet3D {
    private final Predicate<Block> replacePredicate;

    public CaveFacet(Region3i targetRegion, Border3D border, Predicate<Block> replacePredicate) {
        super(targetRegion, border);
        this.replacePredicate = replacePredicate;
    }

    public Predicate<Block> getReplacePredicate() {
        return replacePredicate;
    }
}
