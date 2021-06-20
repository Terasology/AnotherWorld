// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator;

import com.google.common.base.Predicate;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.TerrainVariationFacet;
import org.terasology.anotherWorld.util.Provider;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.chunks.Chunk;
import org.terasology.engine.world.chunks.Chunks;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generation.facets.SurfacesFacet;

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
    public void generateChunk(Chunk chunk, Region chunkRegion) {
        SurfacesFacet surfacesFacet = chunkRegion.getFacet(SurfacesFacet.class);
        TerrainVariationFacet terrainVariationFacet = chunkRegion.getFacet(TerrainVariationFacet.class);

        Vector3i relativePos = new Vector3i();
        for (Vector3ic position : chunk.getRegion()) {
            for (int groundLevel : surfacesFacet.getWorldColumn(position.x(), position.z())) {
                if (groundLevel <= toLevel && groundLevel >= fromLevel) {
                    for (int y = fromLevel; y <= toLevel; y++) {
                        Chunks.toRelative(position, relativePos);
                        if (blockFilter.apply(chunk.getBlock(relativePos))) {
                            chunk.setBlock(relativePos, beachBlockProvider.provide(terrainVariationFacet.get(position.x(), position.y(), position.z())));
                        }
                    }
                }
            }
        }
    }
}
