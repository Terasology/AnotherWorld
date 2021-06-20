// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
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
