// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

import com.google.common.base.Function;
import org.terasology.anotherWorld.generation.BiomeProvider;
import org.terasology.anotherWorld.generation.HillynessProvider;
import org.terasology.anotherWorld.generation.HumidityProvider;
import org.terasology.anotherWorld.generation.PerlinSurfaceHeightProvider;
import org.terasology.anotherWorld.generation.TemperatureProvider;
import org.terasology.anotherWorld.generation.TerrainVariationProvider;
import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.climateConditions.ClimateConditionsSystem;
import org.terasology.climateConditions.ConditionsBaseField;
import org.terasology.coreworlds.generator.facetProviders.SeaLevelProvider;
import org.terasology.coreworlds.generator.facetProviders.SurfaceToDensityProvider;
import org.terasology.engine.core.SimpleUri;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.world.chunks.CoreChunk;
import org.terasology.engine.world.generation.EntityBuffer;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.World;
import org.terasology.engine.world.generation.WorldBuilder;
import org.terasology.engine.world.generator.WorldGenerator;
import org.terasology.engine.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.util.LinkedList;
import java.util.List;

public abstract class PluggableWorldGenerator implements WorldGenerator {
    private final List<ChunkDecorator> chunkDecorators = new LinkedList<>();
    private final List<FeatureGenerator> featureGenerators = new LinkedList<>();
    private final List<FacetProvider> facetProviders = new LinkedList<>();
    private final SimpleUri uri;
    private World world;
    private int seaLevel = 32;
    private int maxLevel = 220;
    private float biomeDiversity = 0.5f;
    private String worldSeed;

    private Function<Float, Float> temperatureFunction = IdentityAlphaFunction.singleton();
    private Function<Float, Float> humidityFunction = IdentityAlphaFunction.singleton();

    private PerlinSurfaceHeightProvider surfaceHeightProvider;

    public PluggableWorldGenerator(SimpleUri uri) {
        this.uri = uri;
    }

    public void addChunkDecorator(ChunkDecorator chunkGenerator) {
        chunkDecorators.add(chunkGenerator);
    }

    public void addFeatureGenerator(FeatureGenerator featureGenerator) {
        featureGenerators.add(featureGenerator);
    }

    public void addFacetProvider(FacetProvider facetProvider) {
        facetProviders.add(facetProvider);
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * 0=changing slowly, 1=changing frequently
     *
     * @param biomeDiversity
     */
    public void setBiomeDiversity(float biomeDiversity) {
        this.biomeDiversity = biomeDiversity;
    }

    public void setTemperatureFunction(Function<Float, Float> temperatureFunction) {
        this.temperatureFunction = temperatureFunction;
    }

    public void setHumidityFunction(Function<Float, Float> humidityFunction) {
        this.humidityFunction = humidityFunction;
    }

    public void setLandscapeOptions(float seaFrequency, float terrainDiversity,
                                    Function<Float, Float> generalTerrainFunction,
                                    Function<Float, Float> heightBelowSeaLevelFunction,
                                    Function<Float, Float> heightAboveSeaLevelFunction,
                                    float hillinessDiversity, Function<Float, Float> hillynessFunction) {
        surfaceHeightProvider = new PerlinSurfaceHeightProvider(seaFrequency, terrainDiversity, generalTerrainFunction,
                heightBelowSeaLevelFunction,
                heightAboveSeaLevelFunction,
                hillinessDiversity, hillynessFunction, seaLevel, maxLevel);
    }

    @Override
    public void initialize() {
        setupGenerator();

        ClimateConditionsSystem environmentSystem = CoreRegistry.get(ClimateConditionsSystem.class);
        environmentSystem.configureHumidity(seaLevel, maxLevel, biomeDiversity, humidityFunction, 0, 1);
        environmentSystem.configureTemperature(seaLevel, maxLevel, biomeDiversity, temperatureFunction, -20, 40);

        ConditionsBaseField temperatureBaseField = environmentSystem.getTemperatureBaseField();
        ConditionsBaseField humidityBaseField = environmentSystem.getHumidityBaseField();

        WorldBuilder worldBuilder = new WorldBuilder(CoreRegistry.get(WorldGeneratorPluginLibrary.class));
        worldBuilder.setSeed(getSeed());
        worldBuilder.addProvider(new BiomeProvider());
        worldBuilder.addProvider(new HillynessProvider());
        worldBuilder.addProvider(surfaceHeightProvider);
        worldBuilder.addProvider(new SurfaceToDensityProvider());
        worldBuilder.addProvider(new HumidityProvider(humidityBaseField));
        worldBuilder.addProvider(new TemperatureProvider(temperatureBaseField));
        worldBuilder.addProvider(new TerrainVariationProvider());
        worldBuilder.addProvider(new SeaLevelProvider(seaLevel));

        for (FacetProvider facetProvider : facetProviders) {
            worldBuilder.addProvider(facetProvider);
        }

        for (ChunkDecorator chunkDecorator : chunkDecorators) {
            worldBuilder.addRasterizer(chunkDecorator);
        }
        for (FeatureGenerator featureGenerator : featureGenerators) {
            worldBuilder.addRasterizer(featureGenerator);
        }

        world = worldBuilder.build();
        world.initialize();
    }

    @Override
    public String getWorldSeed() {
        return worldSeed;
    }

    @Override
    public void setWorldSeed(String seedString) {
        worldSeed = seedString;
    }

    public long getSeed() {
        return worldSeed.hashCode();
    }

    protected abstract void setupGenerator();


    @Override
    public void createChunk(CoreChunk chunk, EntityBuffer buffer) {
        world.rasterizeChunk(chunk, buffer);
    }

    @Override
    public SimpleUri getUri() {
        return uri;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
