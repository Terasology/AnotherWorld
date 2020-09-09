// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.climateConditions.ConditionsBaseField;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.FacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;

/**
 * Created by Marcin on 2014-10-20.
 */
@Produces(HumidityFacet.class)
public class HumidityProvider implements FacetProvider {
    private final ConditionsBaseField conditionsBaseField;

    public HumidityProvider(ConditionsBaseField conditionsBaseField) {
        this.conditionsBaseField = conditionsBaseField;
    }

    @Override
    public void setSeed(long seed) {
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(HumidityFacet.class);

        HumidityFacet facet = new HumidityFacet(region.getRegion(), border, conditionsBaseField);
        region.setRegionFacet(HumidityFacet.class, facet);
    }
}
