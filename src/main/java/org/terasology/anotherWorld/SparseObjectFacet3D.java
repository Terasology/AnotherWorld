// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseFacet3D;
import org.terasology.math.geom.Vector3i;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcin on 2014-10-21.
 */
public class SparseObjectFacet3D<T> extends BaseFacet3D {
    private final Map<Vector3i, T> positions = new HashMap<>();

    public SparseObjectFacet3D(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }

    public void setFlag(Vector3i position, T value) {
        positions.put(position, value);
    }

    public Map<Vector3i, T> getFlaggedPositions() {
        return Collections.unmodifiableMap(positions);
    }
}
