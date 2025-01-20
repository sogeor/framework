
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

package com.sogeor.framework.collection.immutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.CollectionFeature;

/**
 * Представляет собой неизменяемое множество (1) элементов (2) на основе массива.
 *
 * @param <T> тип [2].
 *
 * @see AbstractImmutableSetIterator
 * @since 1.0.0-RC1
 */
public class ArrayImmutableSet<T> extends AbstractImmutableSet<T> {

    /**
     * Содержит стандартные особенности {1}.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull ImmutableSet<@NonNull CollectionFeature> STANDARD_FEATURES = new ArrayImmutableSet<>();

    /**
     * Содержит стандартные {2}.
     *
     * @since 1.0.0-RC1
     */
    public static final @Nullable Object @NonNull [] STANDARD_ELEMENTS = new Object[0];

    /**
     * Содержит {2}.
     *
     * @since 1.0.0-RC1
     */
    protected final @Nullable T @NonNull [] elements;

    /**
     * Создаёт экземпляр на основе {@linkplain #STANDARD_FEATURES стандартных особенностей {1}} и
     * {@linkplain #STANDARD_ELEMENTS {2}}.
     *
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    public ArrayImmutableSet() {
        this(STANDARD_FEATURES, (T) STANDARD_ELEMENTS);
    }

    /**
     * Создаёт экземпляр на основе {@code features}, но без {3}.
     *
     * @param features особенности {1}.
     *
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    protected ArrayImmutableSet(final @NonNull ImmutableSet<@NonNull CollectionFeature> features) {
        this(features, (T) new Object[0]);
    }

    /**
     * Создаёт экземпляр на основе {@linkplain #STANDARD_FEATURES стандартных особенностей} и {@code elements}.
     *
     * @param elements {3}.
     *
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    public ArrayImmutableSet(final @Nullable T @NonNull ... elements) {
        this(STANDARD_FEATURES, elements);
    }

    /**
     * Создаёт экземпляр на основе {@code features} и {@code elements}.
     *
     * @param features особенности {1}.
     * @param elements {3}.
     *
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    protected ArrayImmutableSet(final @NonNull ImmutableSet<@NonNull CollectionFeature> features,
                                final @Nullable T @NonNull ... elements) {
        super(features);
        this.elements = elements;
//        this.elements = elements.length == 0 ? elements;
    }

    @SafeVarargs
    private static <T> @Nullable T @NonNull [] elements(final @Nullable T @NonNull ... elements) {
        final @Nullable T @NonNull [] result = (T[]) new Object[elements.length];
        return result;
    }

    /**
     * @param value элемент.
     *
     * @return Если {@code value} один из {2}, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    public boolean contains(final @Nullable T value) {
        for (final @Nullable var element : elements) if (value == element) return true;
        return false;
    }

    /**
     * @return Итератор {1}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public @NonNull ImmutableSetIterator<T> iterator() {
        return new ArrayImmutableSetIterator<>(this);
    }

    /**
     * @return Размер {1}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public long size() {
        return elements.length;
    }

    /**
     * @return Если {2} не существуют, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public boolean empty() {
        return elements.length > 0;
    }

}
