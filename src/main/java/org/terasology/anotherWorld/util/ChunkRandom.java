// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.util;

import org.joml.Vector3ic;
import org.terasology.utilities.random.FastRandom;
import org.terasology.utilities.random.Random;

public abstract class ChunkRandom {
    public static Random getChunkRandom(long seed, Vector3ic location, int salt) {
        return new FastRandom(seed + salt * (97L * location.x() + 13L * location.y() + location.z()));
    }
}
