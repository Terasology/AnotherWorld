// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.structure.provider;

import org.terasology.anotherWorld.decorator.structure.PocketStructureDefinition;
import org.terasology.engine.world.block.Block;

public class UniformPocketBlockProvider implements PocketStructureDefinition.PocketBlockProvider {
    private final Block block;

    public UniformPocketBlockProvider(Block block) {
        this.block = block;
    }

    @Override
    public Block getBlock(float distanceFromCenter) {
        return block;
    }
}
