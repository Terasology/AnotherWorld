// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator;

import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.BiomeFacet;
import org.terasology.biomesAPI.BiomeRegistry;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.world.chunks.ChunkConstants;
import org.terasology.engine.world.chunks.CoreChunk;
import org.terasology.engine.world.generation.Region;

/**
 * Rasterizes the Biome Facet into the Chunk.
 */
public class BiomeDecorator implements ChunkDecorator {

    private BiomeRegistry biomeRegistry;

    @Override
    public void initialize() {
        biomeRegistry = CoreRegistry.get(BiomeRegistry.class);
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        BiomeFacet biomeFacet = chunkRegion.getFacet(BiomeFacet.class);

        if (biomeFacet == null) {
            throw new IllegalStateException("World generator does not provide a biome facet.");
        }

        for (int x = 0; x < ChunkConstants.CHUNK_SIZE.x; x++) {
            for (int z = 0; z < ChunkConstants.CHUNK_SIZE.z; z++) {
                // Biome is the same for each value of y and depends only on x and z
                AnotherWorldBiome biome = biomeFacet.get(x, z);
                for (int y = 0; y < ChunkConstants.CHUNK_SIZE.y; y++) {
                    biomeRegistry.setBiome(biome, chunk, x, y, z);
                }
            }
        }
    }

}
