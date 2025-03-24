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

/**
 * Представляет собой упорядоченную коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface SequencedCollection<T> extends Collection<T> {

    /**
     * @return Новый итератор элементов этой коллекции.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этой коллекции.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * Если {@code empty()}, то возвращает {@code 1}, иначе вычисляет хеш-код этой коллекции на основе её элементов и
     * возвращает его.
     *
     * @return Хеш-код этой коллекции.
     *
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Ω(1)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * Представляет собой итератор элементов упорядоченной коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see SequencedCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends Collection.Iterator<T> {

        /**
         * Если {@code exists(index)}, то переходит к элементу по {@code index} и возвращает {@code true}, иначе —
         * {@code false}.
         *
         * @param index индекс элемента.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n)}.
         * @see #exists(long)
         * @since 1.0.0-RC1
         */
        @Contract("? -> value")
        default boolean move(final long index) {
            if (!exists(index)) return false;
            while (index > index() && after()) next();
            while (index < index() && before()) previous();
            return index == index();
        }

        /**
         * Если элемент по {@code index} существует, то возвращает {@code true}, иначе — {@code false}.
         *
         * @param index индекс элемента.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n)}.
         * @since 1.0.0-RC1
         */
        @Contract("? -> value")
        default boolean exists(final long index) {
            if (index < 0 || undetermined()) return false;
            while (index > index() && after()) next();
            while (index < index() && before()) previous();
            return index == index();
        }

        /**
         * @return Если {@code current()}, то индекс текущего элемента, иначе {@code -1}.
         *
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        long index();

    }

}
