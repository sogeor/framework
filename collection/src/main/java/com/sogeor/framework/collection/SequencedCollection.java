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
     * @implSpec Возвращаемый итератор должен находится в неопределённом состоянии.
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
     * @see Iterator
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
     * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Ω(1)} и {@code O(n)}.
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
         * @implNote Стандартная реализация обладает оценками временной сложности {@code Ω(1)} и {@code O(n²)}.
         * @see #exists(long)
         * @since 1.0.0-RC1
         */
        @SuppressWarnings("StatementWithEmptyBody")
        @Contract("? -> value")
        default boolean move(final long index) {
            if (!exists(index)) return false;
            while (index > index() && next()) ;
            while (index < index() && previous()) ;
            return true;
        }

        /**
         * Если элемент по {@code index} существует, то возвращает {@code true}, иначе — {@code false}.
         *
         * @param index индекс элемента.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Ω(1)} и {@code O(n)}.
         * @since 1.0.0-RC1
         */
        @Contract("? -> value")
        boolean exists(final long index);

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
