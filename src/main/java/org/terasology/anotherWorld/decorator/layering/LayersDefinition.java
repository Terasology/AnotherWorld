// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.engine.world.chunks.CoreChunk;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generator.plugin.WorldGeneratorPlugin;
import org.terasology.gestalt.naming.Name;

public interface LayersDefinition extends WorldGeneratorPlugin {
    Name getBiomeId();

    void generateInChunk(long seed, CoreChunk chunk, Region chunkRegion, int x, int y, LayeringConfig layeringConfig);
}
