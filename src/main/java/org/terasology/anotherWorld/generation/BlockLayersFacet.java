// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.anotherWorld.decorator.layering.LayerDefinition;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseObjectFacet2D;

import java.util.List;

public class BlockLayersFacet extends BaseObjectFacet2D<List<LayerDefinition>> {
    public BlockLayersFacet(Region3i targetRegion, Border3D border, Class<List<LayerDefinition>> objectType) {
        super(targetRegion, border, objectType);
    }
}
