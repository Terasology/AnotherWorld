// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.engine.world.block.Block;

public class LayeringConfig {
    private final Block bottomBlock;
    private final Block mainBlock;
    private final Block seaBlock;

    public LayeringConfig(Block bottomBlock, Block mainBlock, Block seaBlock) {
        this.bottomBlock = bottomBlock;
        this.mainBlock = mainBlock;
        this.seaBlock = seaBlock;
    }

    public Block getBottomBlock() {
        return bottomBlock;
    }

    public Block getMainBlock() {
        return mainBlock;
    }

    public Block getSeaBlock() {
        return seaBlock;
    }
}
