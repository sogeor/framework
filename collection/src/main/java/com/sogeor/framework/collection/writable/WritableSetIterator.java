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

package com.sogeor.framework.collection.writable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.SetIterator;
import com.sogeor.framework.collection.readable.ReadableCollection;

/**
 * Представляет собой итератор элементов (1) записываемого множества.
 *
 * @param <T> тип [1].
 *
 * @since 1.0.0-RC1
 */
public interface WritableSetIterator<T> extends SetIterator<T>, WritableIterator<T> {

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
    WritableSetIterator<T> start();

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
    WritableSetIterator<T> previous();

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
    WritableSetIterator<T> next();

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
    WritableSetIterator<T> end();

    /**
     * {@inheritDoc}
     *
     * @param value элемент.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> this")
    @NonNull
    WritableSetIterator<T> element(final @Nullable T value);

    /**
     * {@inheritDoc}
     *
     * @param value элемент.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> this")
    @NonNull
    WritableSetIterator<T> insert(final @Nullable T value);

    /**
     * {@inheritDoc}
     *
     * @param values элементы.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @SuppressWarnings("unchecked")
    @Contract("!null -> this")
    @NonNull
    WritableSetIterator<T> insert(final @Nullable T @NonNull ... values);

    /**
     * {@inheritDoc}
     *
     * @param values элементы.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this")
    @NonNull
    WritableSetIterator<T> insert(final @NonNull ReadableCollection<T> values);

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
    WritableSetIterator<T> remove();

}
