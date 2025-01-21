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

package com.sogeor.framework.collection;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.collection.immutable.ImmutableSet;

/**
 * Представляет собой абстрактный итератор элементов коллекции.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractCollection
 * @since 1.0.0-RC1
 */
public abstract class AbstractIterator<T> implements Iterator<T> {

    /**
     * Содержит особенности итератора.
     *
     * @since 1.0.0-RC1
     */
    protected final @NonNull ImmutableSet<@NonNull IteratorFeature> features;

    /**
     * Создаёт экземпляр на основе {@code features}.
     *
     * @param features особенности итератора.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractIterator(final @NonNull ImmutableSet<@NonNull IteratorFeature> features) {
        this.features = features;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @see #end()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    public abstract @NonNull Iterator<T> start();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @see #next()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    public abstract @NonNull Iterator<T> previous();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @see #previous()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    public abstract @NonNull Iterator<T> next();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @see #start()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    public abstract @NonNull Iterator<T> end();

    /**
     * @return Особенности итератора.
     *
     * @see StandardIteratorFeature
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    public @NonNull ImmutableSet<@NonNull IteratorFeature> features() {
        return features;
    }

}
