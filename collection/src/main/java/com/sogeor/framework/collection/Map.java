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
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой ассоциативный массив элементов — пар, каждая из которых состоит из ключа и соответствующего ему
 * значения.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 * @param <T> тип элементов.
 *
 * @see Entry
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface Map<K, V, T extends Map.Entry<K, V>> extends Set<T> {

    /**
     * @return Новый итератор элементов этого ассоциативного массива.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этого ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    Iterator<K, V, T> iterator();

    /**
     * Представляет собой элемент ассоциативного массива — пару, состоящую из ключа и соответствующего ему значения.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see Map
     * @see Iterator
     * @since 1.0.0-RC1
     */
    interface Entry<K, V> {

        /**
         * Вычисляет хеш-код этого элемента и возвращает его.
         *
         * @return Хеш-код этого элемента.
         *
         * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Ω(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        int hashCode();

        /**
         * Если {@code this} эквивалентно {@code object}, то возвращает {@code true}, иначе — {@code false}.
         *
         * @param object объект.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Ω(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("? -> value")
        boolean equals(final @Nullable Object object);

        /**
         * @return {@code super.toString()}.
         *
         * @see Object#toString()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        @NonNull
        String toString();

    }

    /**
     * Представляет собой итератор элементов ассоциативного массива — пар, каждая из которых состоит из ключа и
     * соответствующего ему значения.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     * @param <T> тип элементов.
     *
     * @see Map
     * @see Entry
     * @since 1.0.0-RC1
     */
    interface Iterator<K, V, T extends Entry<K, V>> extends Set.Iterator<T> {}

}
