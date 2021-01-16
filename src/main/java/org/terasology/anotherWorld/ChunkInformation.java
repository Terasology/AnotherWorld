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

import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.HashMap;
import java.util.Map;

//TODO: this class seems to be unused - can it be removed?
public class ChunkInformation {
    private Map<Vector2ic, Integer> groundLevel = new HashMap<>();

    public void setPositionGroundLevel(int x, int z, int level) {
        groundLevel.put(new Vector2i(x, z), level);
    }

    public int getGroundLevel(int x, int z) {
        return groundLevel.get(new Vector2i(x, z));
    }
}
