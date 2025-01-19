/*
 * Copyright 2024 Sogeor
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

package com.sogeor.framework.collection.readable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.Iterator;

/**
 * Представляет собой итератор элементов (1) читаемой коллекции.
 *
 * @param <T> тип [1].
 *
 * @since 1.0.0-RC1
 */
public interface ReadableIterator<T> extends Iterator<T> {

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    @NonNull
    ReadableIterator<T> start();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    @NonNull
    ReadableIterator<T> previous();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    @NonNull
    ReadableIterator<T> next();

    /**
     * {@inheritDoc}
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> this")
    @NonNull
    ReadableIterator<T> end();

    /**
     * @return Если {1} обладает {@linkplain StandardReadableIteratorFeature#ELEMENT_OPERATION} и
     * {@linkplain #current()}, то текущий элемент, иначе {@code null}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    @Nullable
    T element();

}
