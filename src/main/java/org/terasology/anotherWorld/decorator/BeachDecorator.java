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
package org.terasology.anotherWorld.decorator;

import com.google.common.base.Predicate;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.TerrainVariationFacet;
import org.terasology.anotherWorld.util.Provider;
import org.terasology.world.block.Block;
import org.terasology.world.chunks.Chunks;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.facets.SurfacesFacet;

public class BeachDecorator implements ChunkDecorator {
    private Predicate<Block> blockFilter;
    private Provider<Block> beachBlockProvider;
    private int fromLevel;
    private int toLevel;

    public BeachDecorator(Predicate<Block> blockFilter, final Block beachBlock, int fromLevel, int toLevel) {
        this(blockFilter, new Provider<Block>() {
            @Override
            public Block provide(float randomValue) {
                return beachBlock;
            }
        }, fromLevel, toLevel);
    }

    public BeachDecorator(Predicate<Block> blockFilter, Provider<Block> beachBlockProvider, int fromLevel, int toLevel) {
        this.blockFilter = blockFilter;
        this.beachBlockProvider = beachBlockProvider;
        this.fromLevel = fromLevel;
        this.toLevel = toLevel;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SurfacesFacet surfacesFacet = chunkRegion.getFacet(SurfacesFacet.class);
        TerrainVariationFacet terrainVariationFacet = chunkRegion.getFacet(TerrainVariationFacet.class);
        for (Vector3ic position : chunk.getRegion()) {
            for (int groundLevel : surfacesFacet.getWorldColumn(position.x(), position.z())) {
                if (groundLevel <= toLevel && groundLevel >= fromLevel) {
                    for (int y = fromLevel; y <= toLevel; y++) {
                        if (blockFilter.apply(chunk.getBlock(Chunks.toRelative(position, new Vector3i())))) {
                            chunk.setBlock(Chunks.toRelative(position, new Vector3i()), beachBlockProvider.provide(terrainVariationFacet.get(position.x(), position.y(), position.z())));
                        }
                    }
                }
            }
        }
    }
}
