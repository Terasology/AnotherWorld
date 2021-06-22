// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld;

class DefaultSweetSpot implements AnotherWorldBiome.SweetSpot {
    private float humidity;
    private float humidityWeight;
    private float temperature;
    private float temperatureWeight;
    private float terrain;
    private float terrainWeight;
    private float aboveSeaLevel;
    private float aboveSeaLevelWeight;

    public DefaultSweetSpot(float humidity, float humidityWeight, float temperature, float temperatureWeight,
                            float terrain, float terrainWeight, float aboveSeaLevel, float aboveSeaLevelWeight) {
        this.humidity = humidity;
        this.humidityWeight = humidityWeight;
        this.temperature = temperature;
        this.temperatureWeight = temperatureWeight;
        this.terrain = terrain;
        this.terrainWeight = terrainWeight;
        this.aboveSeaLevel = aboveSeaLevel;
        this.aboveSeaLevelWeight = aboveSeaLevelWeight;

        validate();
    }

    public float getHumidity() {
        return humidity;
    }

    public float getHumidityWeight() {
        return humidityWeight;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getTemperatureWeight() {
        return temperatureWeight;
    }

    public float getTerrain() {
        return terrain;
    }

    public float getTerrainWeight() {
        return terrainWeight;
    }

    public float getAboveSeaLevel() {
        return aboveSeaLevel;
    }

    public float getAboveSeaLevelWeight() {
        return aboveSeaLevelWeight;
    }

    private void validate() {
        validateValue(aboveSeaLevel);
        validateValue(aboveSeaLevelWeight);
        validateValue(humidity);
        validateValue(humidityWeight);
        validateValue(temperature);
        validateValue(temperatureWeight);
        validateValue(terrain);
        validateValue(terrainWeight);

        float weightTotal = aboveSeaLevelWeight + humidityWeight + temperatureWeight + terrainWeight;

        if (weightTotal > 1.0001 || weightTotal < 0.0009) {
            throw new IllegalArgumentException();
        }
    }

    private void validateValue(float value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException();
        }
    }
}
