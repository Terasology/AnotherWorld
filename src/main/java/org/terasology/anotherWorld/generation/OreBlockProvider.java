// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import com.google.common.base.Predicate;
import org.terasology.anotherWorld.decorator.ore.OreDefinition;
import org.terasology.anotherWorld.decorator.structure.Structure;
import org.terasology.anotherWorld.decorator.structure.StructureDefinition;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockRegion;
import org.terasology.world.chunks.Chunks;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Produces(OreBlockFacet.class)
public class OreBlockProvider implements FacetProvider {
    private Predicate<Block> blockFilter;
    private Map<String, StructureDefinition> oreDefinitions = new LinkedHashMap<>();
    private long seed;

    public OreBlockProvider(Predicate<Block> blockFilter) {
        this.blockFilter = blockFilter;
        loadOres();
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public void process(GeneratingRegion region) {
        OreBlockFacet oreBlockFacet = new OreBlockFacet(region.getRegion(), region.getBorderForFacet(OreBlockFacet.class), Block.class, blockFilter);

        Structure.StructureCallback callback = new StructureCallbackImpl(oreBlockFacet.getWorldRegion(), oreBlockFacet);

        for (StructureDefinition structureDefinition : oreDefinitions.values()) {
            Collection<Structure> structures = structureDefinition.generateStructures(Chunks.CHUNK_SIZE, seed, oreBlockFacet.getWorldRegion());
            for (Structure structure : structures) {
                structure.generateStructure(callback);
            }
        }

        region.setRegionFacet(OreBlockFacet.class, oreBlockFacet);
    }

    private void loadOres() {
        WorldGeneratorPluginLibrary pluginLibrary = CoreRegistry.get(WorldGeneratorPluginLibrary.class);
        List<OreDefinition> loadedOreDefinitions = pluginLibrary.instantiateAllOfType(OreDefinition.class);
        for (OreDefinition oreDefinition : loadedOreDefinitions) {
            String oreId = oreDefinition.getOreId();
            oreDefinitions.put(oreId, oreDefinition);
        }
    }

    private static final class StructureCallbackImpl implements Structure.StructureCallback {
        private BlockRegion region;
        private OreBlockFacet oreBlockFacet;

        private StructureCallbackImpl(BlockRegion region, OreBlockFacet oreBlockFacet) {
            this.region = region;
            this.oreBlockFacet = oreBlockFacet;
        }

        @Override
        public boolean canReplace(int x, int y, int z) {
            return region.contains(x, y, z);
        }

        @Override
        public void replaceBlock(int x, int y, int z, float force, Block block) {
            oreBlockFacet.setWorld(x, y, z, block);
        }
    }
}
