// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.anotherWorld.util.ChunkRandom;
import org.terasology.anotherWorld.util.PDist;
import org.terasology.engine.math.ChunkMath;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.utilities.random.Random;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.chunks.CoreChunk;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.gestalt.naming.Name;
import org.terasology.math.TeraMath;

import java.util.LinkedList;
import java.util.List;

public class DefaultLayersDefinition implements LayersDefinition {
    private final List<LayerDefinition> layerDefinitions = new LinkedList<>();
    private final int seaLevel;
    private final Name biomeId;

    public DefaultLayersDefinition(int seaLevel, Name biomeId) {
        this.seaLevel = seaLevel;
        this.biomeId = biomeId;
    }

    @Override
    public Name getBiomeId() {
        return biomeId;
    }

    public void addLayerDefinition(PDist thickness, Block block, boolean generateUnderSee) {
        layerDefinitions.add(new LayerDefinition(thickness, block, generateUnderSee));
    }

    @Override
    public void generateInChunk(long seed, CoreChunk chunk, Region region, int x, int z,
                                LayeringConfig layeringConfig) {
        Random random = ChunkRandom.getChunkRandom(seed, chunk.getPosition(), 349 * (31 * x + z));
        int groundLevel = TeraMath.floorToInt(region.getFacet(SurfaceHeightFacet.class).getWorld(x, z));
        boolean underSea = groundLevel < seaLevel;

        Region3i chunkRegion = chunk.getRegion();
        if (underSea) {
            int seaBottom = Math.max(groundLevel + 1, chunkRegion.minY());
            int seaTop = Math.min(seaLevel, chunkRegion.maxY());
            for (int level = seaBottom; level <= seaTop; level++) {
//                if (chunkRegion.encompasses(x, level, z)) {
                chunk.setBlock(ChunkMath.calcRelativeBlockPos(x, level, z), layeringConfig.getSeaBlock());
//                }
            }
        }

        int level = groundLevel;
        for (LayerDefinition layerDefinition : layerDefinitions) {
            if (!underSea || layerDefinition.generateUnderSee) {
                int layerHeight = layerDefinition.thickness.getIntValue(random);
                for (int i = 0; i < layerHeight; i++) {
                    if (level - i > 0) {
                        if (chunkRegion.encompasses(x, level - i, z)) {
                            chunk.setBlock(ChunkMath.calcRelativeBlockPos(x, level - i, z), layerDefinition.block);
                        }
                    }
                }
                level -= layerHeight;
                if (level < 1) {
                    break;
                }
            }
        }

        for (int i = level; i > 0; i--) {
            if (chunkRegion.encompasses(x, i, z)) {

                chunk.setBlock(ChunkMath.calcRelativeBlockPos(x, i, z), layeringConfig.getMainBlock());
            }
        }


        if (chunkRegion.encompasses(x, 0, z)) {
            chunk.setBlock(ChunkMath.calcRelativeBlockPos(x, 0, z), layeringConfig.getBottomBlock());
        }
    }

    private static final class LayerDefinition {
        private final PDist thickness;
        private final Block block;
        private final boolean generateUnderSee;

        private LayerDefinition(PDist thickness, Block block, boolean generateUnderSee) {
            this.thickness = thickness;
            this.block = block;
            this.generateUnderSee = generateUnderSee;
        }
    }
}
