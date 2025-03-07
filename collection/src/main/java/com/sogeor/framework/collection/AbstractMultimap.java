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
 * Представляет собой абстрактный многозначный ассоциативный массив элементов — пар, каждая из которых состоит из ключа
 * и соответствующего ему значения.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 * @param <T> тип элементов.
 *
 * @see AbstractEntry
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractMultimap<K, V, T extends Multimap.Entry<K, V>> extends AbstractMultiset<T> implements Multimap<K, V, T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractMultimap() {}

    /**
     * @return Новый итератор элементов этого ассоциативного массива.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этого ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<K, V, T> iterator();

    /**
     * Представляет собой абстрактный элемент многозначного ассоциативного массива — абстрактную пару, состоящую из
     * ключа и соответствующего ему значения.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see AbstractMultimap
     * @see AbstractIterator
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractEntry<K, V> implements Entry<K, V> {

        /**
         * @return {@code super.toString()}.
         *
         * @see Object#toString()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public @NonNull String toString() {
            return super.toString();
        }

    }

    /**
     * Представляет собой абстрактный итератор элементов абстрактного многозначного ассоциативного массива — пар, каждая
     * из которых состоит из ключа и соответствующего ему значения.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     * @param <T> тип элементов.
     *
     * @see AbstractMultimap
     * @see AbstractEntry
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<K, V, T extends Entry<K, V>> extends AbstractMultiset.AbstractIterator<T> implements Multimap.Iterator<K, V, T> {

        /**
         * Создаёт экземпляр.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractIterator() {}

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public abstract @NonNull AbstractIterator<K, V, T> start();

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public abstract @NonNull AbstractIterator<K, V, T> previous();

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #after()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public abstract @NonNull AbstractIterator<K, V, T> next();

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
        public abstract @NonNull AbstractIterator<K, V, T> end();

    }

}
