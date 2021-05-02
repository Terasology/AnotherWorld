/*
 * Copyright 2014 MovingBlocks
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

import org.terasology.gestalt.naming.Name;

/**
 * Standard implementation class for basic another world biomes.
 */
class AnotherWorldBiomeImpl implements AnotherWorldBiome {

    private final Name id;

    private final String name;

    private final float rarity;


    private final SweetSpot sweetSpot;

    AnotherWorldBiomeImpl(String id, String name, float rarity, SweetSpot sweetSpot) {
        this.id = new Name(id);
        this.name = name;
        this.rarity = rarity;
        this.sweetSpot = sweetSpot;
    }

    @Override
    public Name getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getBiomeParent() {
        return null;
    }

    @Override
    public float getRarity() {
        return rarity;
    }

    @Override
    public SweetSpot getSweetSpot() {
        return sweetSpot;
    }
}
