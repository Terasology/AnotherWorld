// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import org.joml.Vector3i;
import org.terasology.engine.world.block.BlockRegion;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseFacet3D;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SparseObjectFacet3D<T> extends BaseFacet3D {
    private Map<Vector3i, T> positions = new HashMap<>();

    public SparseObjectFacet3D(BlockRegion targetRegion, Border3D border) {
        super(targetRegion, border);
    }

    public void setFlag(Vector3i position, T value) {
        positions.put(position, value);
    }

    public Map<Vector3i, T> getFlaggedPositions() {
        return Collections.unmodifiableMap(positions);
    }
}
