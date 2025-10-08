/*
 * Copyright 2025 Sogeor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sogeor.framework.gradle;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * @since 1.0.0-RC1
 */
public final class MultipartFormData {

    /**
     * @since 1.0.0-RC1
     */
    public static final int MINIMUM_BOUNDARY_LENGTH = 16;

    /**
     * @since 1.0.0-RC1
     */
    public static final int DEFAULT_BOUNDARY_LENGTH = 32;

    /**
     * @since 1.0.0-RC1
     */
    private final @NotNull String boundary;

    /**
     * @since 1.0.0-RC1
     */
    public MultipartFormData() {
        this(generateBoundary(DEFAULT_BOUNDARY_LENGTH));
    }

    /**
     * @since 1.0.0-RC1
     */
    public MultipartFormData(final @NotNull String boundary) {
        this.boundary = boundary;
    }

    /**
     * @since 1.0.0-RC1
     */
    public static @NotNull String generateBoundary(final int length) {
        return new Random().ints(Math.max(length, MINIMUM_BOUNDARY_LENGTH))
                           .mapToObj(i -> Integer.toHexString((byte) i))
                           .collect(Collectors.joining());
    }

    /**
     * @since 1.0.0-RC1
     */
    public @NotNull String getBoundary() {
        return boundary;
    }

}
