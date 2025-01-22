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
 * Представляет собой абстрактное мультимножество элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractMultiset<T> extends AbstractCollection<T> implements Multiset<T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractMultiset() {}

    /**
     * Представляет собой абстрактный итератор элементов мультимножества.
     *
     * @param <T> тип элементов.
     *
     * @implSpec Каждый итератор должен быть способен переходить к элементу, расположенному либо перед текущим, либо
     * после него, либо к обоим из них.
     * <p>
     * Если итератор способен переходить к элементу, расположенному перед текущим, то он должен быть также способен
     * переходить к последнему. И наоборот, если итератор способен переходить к элементу, расположенному после текущего,
     * то он должен быть также способен переходить к первому. Это необходимо для корректной итерации, например:
     * <pre>
     * {@code
     * void example(final @NonNull Iterator<?> it) {
     *     if (it.canNext()) { // it.canStart() == true
     *         for (it.start(); it.after(); it.next()) {
     *             // ...
     *         }
     *     } else { // it.canPrevious() == true && it.canEnd() == true
     *         for (it.end(); it.before(); it.previous()) {
     *             // ...
     *         }
     *     }
     * }
     * }
     * </pre>
     * @see AbstractMultiset
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> extends AbstractCollection.AbstractIterator<T> implements Multiset.Iterator<T> {

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
         * @see #end()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public abstract @NonNull AbstractIterator<T> start();

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
        public abstract @NonNull AbstractIterator<T> previous();

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
        public abstract @NonNull AbstractIterator<T> next();

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
        public abstract @NonNull AbstractIterator<T> end();

    }

}
