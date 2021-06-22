// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.structure;

import org.terasology.engine.world.block.Block;

public interface Structure {
    void generateStructure(StructureCallback callback);

    /**
     * These positions are in world coordinates
     */
    public interface StructureCallback {
        void replaceBlock(int x, int y, int z, float force, Block block);

        boolean canReplace(int x, int y, int z);
    }
}
