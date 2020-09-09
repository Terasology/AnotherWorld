// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import com.google.common.collect.Maps;
import org.terasology.anotherWorld.AnotherWorldBiome;
import org.terasology.anotherWorld.decorator.layering.LayerDefinition;
import org.terasology.anotherWorld.decorator.layering.LayeringConfig;
import org.terasology.biomesAPI.Biome;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.Requires;
import org.terasology.gestalt.naming.Name;
import org.terasology.math.geom.BaseVector2i;

import java.util.List;
import java.util.Map;

@Produces(BlockLayersFacet.class)
@Requires(value = {@Facet(BiomeFacet.class)})
public class BlockLayersProvider implements FacetProvider {
    private final LayeringConfig layeringConfig;
    private final Map<Name, List<LayerDefinition>> biomeLayerDefinitions = Maps.newHashMap();
    private long seed;

    public BlockLayersProvider(LayeringConfig layeringConfig) {
        this.layeringConfig = layeringConfig;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void addBiomeLayerDefinitions(Biome biome, List<LayerDefinition> layerDefinitions) {
        biomeLayerDefinitions.put(biome.getId(), layerDefinitions);
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(BlockLayersFacet.class);
        // Need to fool Java with the double-casting
        BlockLayersFacet result = new BlockLayersFacet(region.getRegion(), border,
                (Class<List<LayerDefinition>>) (Class<?>) List.class);
        BiomeFacet biomeFacet = region.getRegionFacet(BiomeFacet.class);

        for (BaseVector2i pos : result.getRelativeRegion().contents()) {
            AnotherWorldBiome biome = biomeFacet.get(pos);
            result.set(pos, biomeLayerDefinitions.get(biome.getId()));
        }

        region.setRegionFacet(BlockLayersFacet.class, result);
    }
}
