// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.decorator.layering;

import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.anotherWorld.ChunkDecorator;
import org.terasology.anotherWorld.generation.BiomeFacet;
import org.terasology.biomesAPI.BiomeRegistry;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.world.block.BlockRegionc;
import org.terasology.engine.world.chunks.Chunk;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generator.plugin.WorldGeneratorPluginLibrary;
import org.terasology.gestalt.naming.Name;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayeringDecorator implements ChunkDecorator {
    private Map<Name, LayersDefinition> biomeLayers = new HashMap<>();
    private LayeringConfig layeringConfig;
    private long seed;

    public LayeringDecorator(LayeringConfig layeringConfig, long seed) {
        this.layeringConfig = layeringConfig;
        this.seed = seed;
        loadLayers();
    }

    public void addBiomeLayers(LayersDefinition layersDefinition) {
        biomeLayers.put(layersDefinition.getBiomeId(), layersDefinition);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void generateChunk(Chunk chunk, Region region) {
        BiomeFacet biomeFacet = region.getFacet(BiomeFacet.class);
        BiomeRegistry biomeRegistry = CoreRegistry.get(BiomeRegistry.class);

        BlockRegionc chunkRegion = chunk.getRegion();
        for (int x = chunkRegion.minX(); x <= chunkRegion.maxX(); x++) {
            for (int z = chunkRegion.minZ(); z <= chunkRegion.maxZ(); z++) {
                AnotherWorldBiome biome = biomeFacet.getWorld(x, z);
                LayersDefinition matchingLayers = findMatchingLayers(biomeRegistry, biome);
                if (matchingLayers != null) {
                    matchingLayers.generateInChunk(seed, chunk, region, x, z, layeringConfig);
                }
            }
        }
    }

    private LayersDefinition findMatchingLayers(BiomeRegistry biomeRegistry, AnotherWorldBiome biome) {
        LayersDefinition layersDefinition = biomeLayers.get(biome.getId());
        if (layersDefinition != null) {
            return layersDefinition;
        }
        // commented out- biomes module doesn't support biome lookup by string id yet
//        String biomeParentId = biome.getBiomeParent();
//        if (biomeParentId != null) {
//            AnotherWorldBiome parentBiome = biomeRegistry.getBiomeById(biomeParentId, AnotherWorldBiome.class);
//            if (parentBiome != null) {
//                return findMatchingLayers(biomeRegistry, parentBiome);
//            }
//        }
        return null;
    }

    private void loadLayers() {
        WorldGeneratorPluginLibrary pluginLibrary = CoreRegistry.get(WorldGeneratorPluginLibrary.class);
        List<LayersDefinition> loadedLayersDefinitions = pluginLibrary.instantiateAllOfType(LayersDefinition.class);
        for (LayersDefinition layersDefinition : loadedLayersDefinitions) {
            addBiomeLayers(layersDefinition);
        }
    }
}
