// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.structure;

import org.joml.Vector3ic;
import org.terasology.engine.world.block.BlockRegionc;

import java.util.Collection;

public interface StructureDefinition {
    Collection<Structure> generateStructures(Vector3ic chunkSize, long seed, BlockRegionc region);
}
