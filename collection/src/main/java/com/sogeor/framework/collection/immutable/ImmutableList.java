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
import com.sogeor.framework.collection.readable.ReadableList;

/**
 * Представляет собой неизменяемый список элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface ImmutableList<T> extends ImmutableSequencedCollection<T>, ReadableList<T> {

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @NonNull
    ImmutableList<?> EMPTY = new ArrayImmutableList<>();

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("-> $!null")
    static <T> @NonNull ImmutableList<T> of() {
        return (ImmutableList<T>) EMPTY;
    }

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> $!null")
    static <T> @NonNull ImmutableList<T> of(final @Nullable T element) {
        return new ArrayImmutableList<>(element);
    }

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    @Contract("? -> $!null")
    static <T> @NonNull ImmutableList<T> of(final @Nullable T @NonNull ... elements) {
        return new ArrayImmutableList<>(elements);
    }

    /**
     * @return Новый итератор элементов этого списка.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этого списка.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * Представляет собой итератор элементов неизменяемого списка.
     *
     * @param <T> тип элементов.
     *
     * @see ImmutableList
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends ImmutableSequencedCollection.Iterator<T>, ReadableList.Iterator<T> {}

}
