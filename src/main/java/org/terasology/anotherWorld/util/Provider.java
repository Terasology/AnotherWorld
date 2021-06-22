// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.anotherWorld.util;

public interface Provider<T> {
    T provide(float randomValue);
}
