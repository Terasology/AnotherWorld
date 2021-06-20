// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.util.alpha;

import com.google.common.base.Function;

public class PowerAlphaFunction implements Function<Float, Float> {
    private Function<Float, Float> delegate;
    private float power;

    public PowerAlphaFunction(Function<Float, Float> delegate, float power) {
        this.delegate = delegate;
        this.power = power;
    }

    @Override
    public Float apply(Float input) {
        return (float) Math.pow(delegate.apply(input), power);
    }
}
