// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.climateConditions.ConditionsBaseField;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseFacet3D;

/**
 * Created by Marcin on 2014-10-20.
 */
public class TemperatureFacet extends BaseFacet3D {
    private final ConditionsBaseField temperatureBaseField;

    public TemperatureFacet(Region3i targetRegion, Border3D border, ConditionsBaseField temperatureBaseField) {
        super(targetRegion, border);
        this.temperatureBaseField = temperatureBaseField;
    }

    public float get(int x, int y, int z) {
        return temperatureBaseField.get(x, y, z);
    }
}
