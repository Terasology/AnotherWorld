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
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.anotherWorld.util.ChunkRandom;
import org.terasology.anotherWorld.util.PDist;
import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.TeraMath;
import org.terasology.naming.Name;
import org.terasology.utilities.random.Random;
import org.terasology.world.block.Block;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.facets.ElevationFacet;

import java.util.LinkedList;
import java.util.List;

public class DefaultLayersDefinition implements LayersDefinition {
    private List<LayerDefinition> layerDefinitions = new LinkedList<>();
    private int seaLevel;
    private Name biomeId;

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
    public void generateInChunk(long seed, CoreChunk chunk, Region region, int x, int z, LayeringConfig layeringConfig) {
        Random random = ChunkRandom.getChunkRandom(seed, chunk.getPosition(), 349 * (31 * x + z));
        // The ElevationFacet isn't really appropriate here, but the algorithm isn't very compatible with using the DensityFacet instead.
        int groundLevel = TeraMath.floorToInt(region.getFacet(ElevationFacet.class).getWorld(x, z));
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
        private PDist thickness;
        private Block block;
        private boolean generateUnderSee;

        private LayerDefinition(PDist thickness, Block block, boolean generateUnderSee) {
            this.thickness = thickness;
            this.block = block;
            this.generateUnderSee = generateUnderSee;
        }
    }
}
