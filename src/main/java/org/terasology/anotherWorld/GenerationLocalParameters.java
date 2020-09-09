// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import org.terasology.anotherWorld.generation.HumidityFacet;
import org.terasology.anotherWorld.generation.TemperatureFacet;
import org.terasology.engine.world.generation.Region;
import org.terasology.math.geom.Vector3i;

public class GenerationLocalParameters implements LocalParameters {
    private final Vector3i location;
    private final TemperatureFacet surfaceTemperatureFacet;
    private final HumidityFacet surfaceHumidityFacet;


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
