// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.engine.world.block.Block;

public interface LayerDefinition {
    Block getBlock(boolean underSea);

    int getThickness(int x, int z);
}
