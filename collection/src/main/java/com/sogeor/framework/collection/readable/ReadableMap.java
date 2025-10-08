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

package com.sogeor.framework.collection.readable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.Experimental;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.Map;

import java.util.Objects;

/**
 * Представляет собой ассоциативный массив элементов — пар, каждая из которых состоит из ключа и соответствующего ему
 * значения.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 *
 * @see Entry
 * @since 1.0.0-RC1
 */
@Experimental
public interface ReadableMap<K, V> extends ReadableCollection, Map<K, V> {

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    ReadableSet<K> keys();

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    ReadableMultiset<V> values();

    /**
     * TODO
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    ReadableSet<? extends Entry<K, V>> entries();

    /**
     * Представляет собой элемент ассоциативного массива — пару, состоящую из ключа и соответствующего ему значения.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see ReadableMap
     * @since 1.0.0-RC1
     */
    interface Entry<K, V> extends Map.Entry<K, V> {

        /**
         * TODO
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> null")
        @Nullable
        K key();

        /**
         * TODO
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> null")
        @Nullable
        V value();

        /**
         * @return {@code Objects.hashCode(key()) ^ Objects.hashCode(value())}.
         *
         * @implNote Требуемая реализация обладает оценкой временной сложности {@code Ω(1)}.
         * @see Objects#hashCode(Object)
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        int hashCode();

        /**
         * Если {@code !(object instanceof ReadableMap.Entry<?, ?> that)}, то возвращает {@code false}.
         * <p>
         * Если {@code Objects.equals(key(), that.key()) && Objects.equals(value(), that.value())}, то возвращает
         * {@code true}, иначе — {@code false}.
         *
         * @param object объект.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Требуемая реализация обладает оценкой временной сложности {@code Ω(1)}.
         * @see Objects#equals(Object, Object)
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("? -> value")
        boolean equals(final @Nullable Object object);

        /**
         * @return {@code super.toString()}.
         *
         * @implNote Требуемая реализация обладает оценкой временной сложности {@code Ω(1)}.
         * @see Object#toString()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        @NonNull
        String toString();

    }

}
