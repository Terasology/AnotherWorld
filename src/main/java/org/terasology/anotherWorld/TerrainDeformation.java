// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import com.google.common.base.Function;
import org.terasology.engine.utilities.procedural.Noise2D;
import org.terasology.engine.utilities.procedural.SimplexNoise;
import org.terasology.math.TeraMath;

public class TerrainDeformation {
    private static final float MIN_MULTIPLIER = 0.0005f;
    private static final float MAX_MULTIPLIER = 0.01f;

    private final Noise2D hillynessNoise;

    private final float noiseMultiplier;
    private final Function<Float, Float> terrainFunction;

    public TerrainDeformation(long worldSeed, float terrainDiversity, Function<Float, Float> terrainFunction) {
        this.terrainFunction = terrainFunction;
        hillynessNoise = new SimplexNoise(worldSeed + 872364);
        noiseMultiplier = MIN_MULTIPLIER + (MAX_MULTIPLIER - MIN_MULTIPLIER) * terrainDiversity;
    }

    public float getHillyness(int x, int z) {
        double result = hillynessNoise.noise(x * noiseMultiplier, z * noiseMultiplier);
        return terrainFunction.apply((float) TeraMath.clamp((result + 1.0f) / 2.0f));
    }
}
