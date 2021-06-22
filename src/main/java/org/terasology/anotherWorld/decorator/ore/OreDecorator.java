// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.ore;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.decorator.structure.Structure;
import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockRegionc;
import org.terasology.engine.world.chunks.Chunk;
import org.terasology.engine.world.chunks.Chunks;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OreDecorator implements ChunkDecorator {
    private Map<String, StructureDefinition> oreDefinitions = new LinkedHashMap<>();
    private long seed;
    private Predicate<Block> blockFilter;

    public OreDecorator(long seed, Predicate<Block> blockFilter) {
        this.seed = seed;
        this.blockFilter = blockFilter;
        loadOres();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void generateChunk(Chunk chunk, Region chunkRegion) {
        Structure.StructureCallback callback = new StructureCallbackImpl(chunk);

        for (StructureDefinition structureDefinition : oreDefinitions.values()) {
            Collection<Structure> structures = structureDefinition.generateStructures(Chunks.CHUNK_SIZE, seed, chunkRegion.getRegion());
            for (Structure structure : structures) {
                structure.generateStructure(callback);
            }
        }
    }

    private void loadOres() {
        WorldGeneratorPluginLibrary pluginLibrary = CoreRegistry.get(WorldGeneratorPluginLibrary.class);
        List<OreDefinition> loadedOreDefinitions = pluginLibrary.instantiateAllOfType(OreDefinition.class);
        for (OreDefinition oreDefinition : loadedOreDefinitions) {
            String oreId = oreDefinition.getOreId();
            oreDefinitions.put(oreId, oreDefinition);
        }
    }

    private final class StructureCallbackImpl implements Structure.StructureCallback {
        private Chunk chunk;
        private BlockRegionc region;

        private StructureCallbackImpl(Chunk chunk) {
            this.chunk = chunk;
            this.region = chunk.getRegion();
        }

        @Override
        public boolean canReplace(int x, int y, int z) {
            return region.contains(x, y, z) && blockFilter.apply(chunk.getBlock(x - region.minX(), y - region.minY(), z - region.minZ()));
        }

        @Override
        public void replaceBlock(int x, int y, int z, float force, Block block) {
            chunk.setBlock(x - region.minX(), y - region.minY(), z - region.minZ(), block);
        }
    }
}
