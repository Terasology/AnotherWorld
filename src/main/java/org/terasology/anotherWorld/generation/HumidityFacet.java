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
public class HumidityFacet extends BaseFacet3D {
    private final ConditionsBaseField humidityBaseField;

    public HumidityFacet(Region3i targetRegion, Border3D border, ConditionsBaseField humidityBaseField) {
        super(targetRegion, border);
        this.humidityBaseField = humidityBaseField;
    }

    public float get(int x, int y, int z) {
        return humidityBaseField.get(x, y, z);
    }
}
