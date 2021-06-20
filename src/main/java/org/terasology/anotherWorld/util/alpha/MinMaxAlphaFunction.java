// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.util.alpha;

import com.google.common.base.Function;

public class MinMaxAlphaFunction implements Function<Float, Float> {
    private Function<Float, Float> delegate;
    private float min;
    private float max;

    public MinMaxAlphaFunction(Function<Float, Float> delegate, float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum cannot be higher than maximum");
        }
        this.delegate = delegate;
        this.min = min;
        this.max = max;
    }

    @Override
    public Float apply(Float input) {
        float result = delegate.apply(input);
        return min + (max - min) * result;
    }
}
