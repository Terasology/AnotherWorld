// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.structure;

import org.terasology.engine.math.Region3i;
import org.terasology.math.geom.Vector3i;

import java.util.Collection;

public interface StructureDefinition {
    Collection<Structure> generateStructures(Vector3i chunkSize, long seed, Region3i region);
}
