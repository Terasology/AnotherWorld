// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.util;

import org.joml.Vector3i;
import org.terasology.utilities.random.FastRandom;
import org.terasology.utilities.random.Random;

public abstract class ChunkRandom {
    public static Random getChunkRandom(long seed, Vector3i location, int salt) {
        return new FastRandom(seed + salt * (97L * location.x + 13L * location.y + location.z));
    }
}
