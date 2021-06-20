// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.util.alpha;

import com.google.common.base.Function;

public final class IdentityAlphaFunction implements Function<Float, Float> {
    private static IdentityAlphaFunction singleton = new IdentityAlphaFunction();

    private IdentityAlphaFunction() {
    }

    public static IdentityAlphaFunction singleton() {
        return singleton;
    }

    @Override
    public Float apply(Float input) {
        return input;
    }
}
