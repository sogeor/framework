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
 * Представляет собой итератор элементов мультимножества.
 *
 * @param <T> тип элементов.
 *
 * @see Multiset
 * @since 1.0.0-RC1
 */
public interface MultisetIterator<T> extends Iterator<T> {

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
    @NonNull
    MultisetIterator<T> start();

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
    @NonNull
    MultisetIterator<T> previous();

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
    @NonNull
    MultisetIterator<T> next();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @see #last()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    @NonNull
    MultisetIterator<T> end();

    /**
     * @return Особенности итератора.
     *
     * @see StandardIteratorFeature
     * @see StandardMultisetIteratorFeature
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    ImmutableSet<@NonNull IteratorFeature> features();

}
