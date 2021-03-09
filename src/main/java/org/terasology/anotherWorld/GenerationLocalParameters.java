// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import org.joml.Vector3i;
import org.terasology.anotherWorld.generation.HumidityFacet;
import org.terasology.anotherWorld.generation.TemperatureFacet;
import org.terasology.engine.world.generation.Region;

public class GenerationLocalParameters implements LocalParameters {
    private Vector3i location;
    private TemperatureFacet surfaceTemperatureFacet;
    private HumidityFacet surfaceHumidityFacet;


    public GenerationLocalParameters(Region chunkRegion, Vector3i location) {
        this.location = location;
        surfaceTemperatureFacet = chunkRegion.getFacet(TemperatureFacet.class);
        surfaceHumidityFacet = chunkRegion.getFacet(HumidityFacet.class);
    }

    @Override
    public float getTemperature() {
        return surfaceTemperatureFacet.get(location.x, location.y, location.z);
    }

    @Override
    public float getHumidity() {
        return surfaceHumidityFacet.get(location.x, location.y, location.z);
    }
}
