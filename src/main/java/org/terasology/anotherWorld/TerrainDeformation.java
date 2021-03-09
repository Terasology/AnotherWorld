/*
 * Copyright 2015 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.anotherWorld;

import com.google.common.base.Function;
import org.terasology.engine.utilities.procedural.Noise2D;
import org.terasology.engine.utilities.procedural.SimplexNoise;
import org.terasology.math.TeraMath;

public class TerrainDeformation {
    private static final float MIN_MULTIPLIER = 0.0005f;
    private static final float MAX_MULTIPLIER = 0.01f;

    private final Noise2D hillynessNoise;

    private float noiseMultiplier;
    private Function<Float, Float> terrainFunction;

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
