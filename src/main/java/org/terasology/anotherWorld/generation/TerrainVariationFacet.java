// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.generation;

import org.terasology.anotherWorld.util.alpha.IdentityAlphaFunction;
import org.terasology.anotherWorld.util.alpha.UniformNoiseAlpha;
import org.terasology.engine.math.Region3i;
import org.terasology.engine.utilities.procedural.Noise3D;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseFacet3D;

public class TerrainVariationFacet extends BaseFacet3D {
    private final UniformNoiseAlpha alpha = new UniformNoiseAlpha(IdentityAlphaFunction.singleton());
    private final Noise3D noise;

    public TerrainVariationFacet(Region3i targetRegion, Border3D border, Noise3D noise) {
        super(targetRegion, border);
        this.noise = noise;
    }

    public float get(int x, int y, int z) {
        return alpha.apply((1 + noise.noise(x * 0.01f, y * 0.01f, z * 0.01f)) / 2f);
    }
}
