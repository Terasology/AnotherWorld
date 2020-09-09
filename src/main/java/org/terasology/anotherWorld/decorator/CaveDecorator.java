// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.decorator.structure.Structure;
import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.anotherWorld.decorator.structure.VeinsStructureDefinition;
import org.terasology.anotherWorld.util.PDist;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockManager;
import org.terasology.engine.world.chunks.ChunkConstants;
import org.terasology.engine.world.chunks.CoreChunk;
import org.terasology.engine.world.generation.Region;

import java.util.Collection;

public class CaveDecorator implements ChunkDecorator {
    private final long seed;
    private final Predicate<Block> blockFilter;
    private final StructureDefinition caveDefinition;

    public CaveDecorator(long seed,
                         Predicate<Block> blockFilter, PDist caveFrequency, PDist mainCaveRadius, PDist mainCaveYLevel,
                         PDist tunnelLength, PDist tunnelRadius, BlockManager blockManager) {
        this.seed = seed;
        this.blockFilter = blockFilter;

        caveDefinition = new VeinsStructureDefinition(caveFrequency,
                new VeinsStructureDefinition.VeinsBlockProvider() {
                    @Override
                    public Block getClusterBlock(float distanceFromCenter) {
                        return blockManager.getBlock(BlockManager.AIR_ID);
                    }

                    @Override
                    public Block getBranchBlock() {
                        return blockManager.getBlock(BlockManager.AIR_ID);
                    }
                }, mainCaveRadius, mainCaveYLevel, new PDist(4f, 1f), new PDist(0f, 0.1f), tunnelLength,
                new PDist(1000f, 0f), new PDist(0f, 0f), new PDist(0.25f, 0f), new PDist(5f, 0f), new PDist(0.5f, 0.5f),
                tunnelRadius, new PDist(1f, 0f), new PDist(1f, 0f));
    }

    @Override
    public void initialize() {
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        Structure.StructureCallback callback = new StructureCallbackImpl(chunk);

        Collection<Structure> structures = caveDefinition.generateStructures(ChunkConstants.CHUNK_SIZE, seed,
                chunkRegion.getRegion());
        for (Structure structure : structures) {
            structure.generateStructure(callback);
        }
    }

    private final class StructureCallbackImpl implements Structure.StructureCallback {
        private final CoreChunk chunk;
        private final Region3i region;

        private StructureCallbackImpl(CoreChunk chunk) {
            this.chunk = chunk;
            this.region = chunk.getRegion();
        }

        @Override
        public boolean canReplace(int x, int y, int z) {
            return region.encompasses(x, y, z) && blockFilter.apply(chunk.getBlock(x - region.minX(),
                    y - region.minY(), z - region.minZ()));
        }

        @Override
        public void replaceBlock(int x, int y, int z, float force, Block block) {
            chunk.setBlock(x - region.minX(), y - region.minY(), z - region.minZ(), block);
        }
    }
}
