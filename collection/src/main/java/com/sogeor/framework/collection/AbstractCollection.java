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
 * Представляет собой абстрактную коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractCollection<T> implements Collection<T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractCollection() {}

    /**
     * @return Абстрактный итератор элементов этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

    /**
     * {@inheritDoc}
     *
     * @return Строковое представление этой коллекции.
     *
     * @see #size()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public @NonNull String toString() {
        return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode()) + '{' + size() + '}';
    }

    /**
     * Представляет собой абстрактный итератор элементов коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see AbstractCollection
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> implements Iterator<T> {

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
         * @see #canStart()
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
         * @see #before()
         * @see #canPrevious()
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
         * @see #after()
         * @see #canNext()
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
         * @see #last()
         * @see #canEnd()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public abstract @NonNull AbstractIterator<T> end();

        /**
         * @param object объект.
         *
         * @return Если {@code this} равно {@code object}, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("$? -> $value")
        public abstract boolean equals(final @Nullable Object object);

        /**
         * @return Строковое представление этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public abstract @NonNull String toString();

    }

}
