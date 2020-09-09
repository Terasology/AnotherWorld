// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.TerrainVariationFacet;
import org.terasology.anotherWorld.util.Provider;
import org.terasology.engine.math.ChunkMath;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.chunks.CoreChunk;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3i;

public class BeachDecorator implements ChunkDecorator {
    private final Predicate<Block> blockFilter;
    private final Provider<Block> beachBlockProvider;
    private final int fromLevel;
    private final int toLevel;

    public BeachDecorator(Predicate<Block> blockFilter, final Block beachBlock, int fromLevel, int toLevel) {
        this(blockFilter, new Provider<Block>() {
            @Override
            public Block provide(float randomValue) {
                return beachBlock;
            }
        }, fromLevel, toLevel);
    }

    public BeachDecorator(Predicate<Block> blockFilter, Provider<Block> beachBlockProvider, int fromLevel,
                          int toLevel) {
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
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        TerrainVariationFacet terrainVariationFacet = chunkRegion.getFacet(TerrainVariationFacet.class);
        for (Vector3i position : chunk.getRegion()) {
            int groundLevel = TeraMath.floorToInt(surfaceHeightFacet.getWorld(position.x, position.z));
            if (groundLevel <= toLevel && groundLevel >= fromLevel) {
                for (int y = fromLevel; y <= toLevel; y++) {
                    if (blockFilter.apply(chunk.getBlock(ChunkMath.calcRelativeBlockPos(position)))) {
                        chunk.setBlock(ChunkMath.calcRelativeBlockPos(position),
                                beachBlockProvider.provide(terrainVariationFacet.get(position.x, position.y,
                                        position.z)));
                    }
                }
            }
        }
    }
}
