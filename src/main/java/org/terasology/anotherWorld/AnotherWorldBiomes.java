// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import org.terasology.biomesAPI.BiomeRegistry;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.registry.In;

@RegisterSystem
public class AnotherWorldBiomes extends BaseComponentSystem {
    public static final AnotherWorldBiome DESERT = new AnotherWorldBiomeImpl(
            "AnotherWorld:Desert",
            "Desert",
            0.4f,
            // Hot and dry, and preferably flat
            new DefaultSweetSpot(0f, 0.4f, 1f, 0.4f, 0f, 0.2f, 0f, 0f)
    );
    public static final AnotherWorldBiome FOREST = new AnotherWorldBiomeImpl(
            "AnotherWorld:Forest",
            "Forest",
            1f,
            // Reasonably humid, and average temperature
            new DefaultSweetSpot(0.7f, 0.5f, 0.5f, 0.5f, 0f, 0f, 0f, 0f)
    );
    public static final AnotherWorldBiome PLAINS = new AnotherWorldBiomeImpl(
            "AnotherWorld:Plains",
            "Plains",
            1f,
            // Rather dry and average temperature, flat preferred
            new DefaultSweetSpot(0.3f, 0.3f, 0.5f, 0.3f, 0f, 0.4f, 0f, 0f)
    );
    public static final AnotherWorldBiome TUNDRA = new AnotherWorldBiomeImpl(
            "AnotherWorld:Tundra",
            "Tundra",
            0.8f,
            // Cold and dry
            new DefaultSweetSpot(0f, 0.2f, 0f, 0.2f, 0f, 0f, 0.6f, 0.6f)
    );
    public static final AnotherWorldBiome TAIGA = new AnotherWorldBiomeImpl(
            "AnotherWorld:Taiga",
            "Taiga",
            0.8f,
            // Cold, but reasonably humid, usually on high levels
            new DefaultSweetSpot(0.5f, 0.2f, 0f, 0.2f, 0f, 0f, 0.6f, 0.6f)
    );
    public static final AnotherWorldBiome ALPINE = new AnotherWorldBiomeImpl(
            "AnotherWorld:Alpine",
            "Alpine",
            0.9f,
            // Occurs in very high Y-level, and cold
            new DefaultSweetSpot(0f, 0f, 0f, 0f, 0f, 0f, 1f, 1f)
    );
    public static final AnotherWorldBiome CLIFF = new AnotherWorldBiomeImpl(
            "AnotherWorld:Cliff",
            "Cliff",
            0.8f,
            // Cold, but reasonably humid, usually on high levels
            new DefaultSweetSpot(0f, 0f, 0f, 0f, 1f, 1f, 0f, 0f)
    );
    @In
    private BiomeRegistry registry;

    @Override
    public void preBegin() {
        registry.registerBiome(DESERT);
        registry.registerBiome(FOREST);
        registry.registerBiome(PLAINS);
        registry.registerBiome(TUNDRA);
        registry.registerBiome(TAIGA);
        registry.registerBiome(ALPINE);
        registry.registerBiome(CLIFF);
    }
}
