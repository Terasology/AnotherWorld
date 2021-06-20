// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.ore;

import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.engine.world.generator.plugin.WorldGeneratorPlugin;

public interface OreDefinition extends StructureDefinition, WorldGeneratorPlugin {
    String getOreId();
}
