// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator;

import com.google.common.base.Predicate;
import org.terasology.engine.world.block.Block;

import java.util.Collection;
import java.util.Collections;

public class BlockCollectionPredicate implements Predicate<Block> {
    private final Collection<Block> blocks;

    public BlockCollectionPredicate(Block block) {
        this(Collections.singleton(block));
    }

    public BlockCollectionPredicate(Collection<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public boolean apply(Block block) {
        return blocks.contains(block);
    }
}
