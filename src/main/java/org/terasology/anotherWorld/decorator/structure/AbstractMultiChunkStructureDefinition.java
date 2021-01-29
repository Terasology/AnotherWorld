// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.structure;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.terasology.anotherWorld.util.ChunkRandom;
import org.terasology.anotherWorld.util.PDist;
import org.terasology.utilities.random.Random;
import org.terasology.world.block.BlockRegionc;
import org.terasology.world.chunks.Chunks;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMultiChunkStructureDefinition implements StructureDefinition {
    private PDist frequency;

    protected AbstractMultiChunkStructureDefinition(PDist frequency) {
        this.frequency = frequency;
    }

    @Override
    public final Collection<Structure> generateStructures(Vector3ic chunkSize, long seed, BlockRegionc region) {
        List<Structure> result = new LinkedList<>();
        float maxRange = getMaxRange();

        int chunksRangeToEvaluateX = (int) Math.ceil(maxRange / chunkSize.x());
        int chunksRangeToEvaluateY = (int) Math.ceil(maxRange / chunkSize.y());
        int chunksRangeToEvaluateZ = (int) Math.ceil(maxRange / chunkSize.z());

        Vector3i minChunk = Chunks.toChunkPos(region.getMin(new Vector3i()));
        Vector3i maxChunk = Chunks.toChunkPos(region.getMax(new Vector3i()));

        for (int chunkX = minChunk.x - chunksRangeToEvaluateX; chunkX <= maxChunk.x + chunksRangeToEvaluateX; chunkX++) {
            for (int chunkY = minChunk.y - chunksRangeToEvaluateY; chunkY <= maxChunk.y + chunksRangeToEvaluateY; chunkY++) {
                for (int chunkZ = minChunk.z - chunksRangeToEvaluateZ; chunkZ <= maxChunk.z + chunksRangeToEvaluateZ; chunkZ++) {
                    generateStructuresForChunkWithFrequency(result, seed,
                            new Vector3i(chunkX, chunkY, chunkZ), chunkSize);
                }
            }
        }

        return result;
    }

    protected final void generateStructuresForChunkWithFrequency(List<Structure> result, long seed, Vector3ic chunkPosition, Vector3ic chunkSize) {
        Random random = ChunkRandom.getChunkRandom(seed, chunkPosition, getGeneratorSalt());

        float structuresInChunk = frequency.getValue(random);
        int structuresToGenerateInChunk = (int) structuresInChunk;

        // Check if we "hit" any leftover
        if (random.nextFloat() < structuresInChunk - structuresToGenerateInChunk) {
            structuresToGenerateInChunk++;
        }

        for (int i = 0; i < structuresToGenerateInChunk; i++) {
            generateStructuresForChunk(result, random, chunkPosition, chunkSize);
        }
    }

    protected abstract float getMaxRange();

    protected abstract int getGeneratorSalt();

    protected abstract void generateStructuresForChunk(List<Structure> result, Random random, Vector3ic chunkPosition, Vector3ic chunkSize);
}
