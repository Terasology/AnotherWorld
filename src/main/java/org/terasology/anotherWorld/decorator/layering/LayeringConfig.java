// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.engine.world.block.Block;

public class LayeringConfig {
    private Block bottomBlock;
    private Block mainBlock;
    private Block seaBlock;

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
