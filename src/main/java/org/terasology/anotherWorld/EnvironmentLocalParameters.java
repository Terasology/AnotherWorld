// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import org.joml.Vector3i;
import org.terasology.climateConditions.ClimateConditionsSystem;

public class EnvironmentLocalParameters implements LocalParameters {
    private ClimateConditionsSystem environmentSystem;
    private Vector3i location;

    public EnvironmentLocalParameters(ClimateConditionsSystem environmentSystem, Vector3i location) {
        this.environmentSystem = environmentSystem;
        this.location = location;
    }

    @Override
    public float getTemperature() {
        return environmentSystem.getTemperature(location.x, location.y, location.z);
    }

    @Override
    public float getHumidity() {
        return environmentSystem.getHumidity(location.x, location.y, location.z);
    }
}
