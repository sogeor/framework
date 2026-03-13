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
 * Представляет собой абстрактную итерируемую коллекцию элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractIterableCollection extends AbstractCollection implements IterableCollection {

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractIterableCollection() {}

    /**
     * {@inheritDoc}
     *
     * @return Новый итератор этой коллекции в неопределённом состоянии.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator iterator();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterableCollection clone();

    /**
     * Представляет собой абстрактный итератор абстрактной итерируемой коллекции.
     *
     * @see AbstractIterableCollection
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator implements Iterator {

        /**
         * Создаёт экземпляр по умолчанию.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractIterator() {}

        /**
         * {@inheritDoc}
         *
         * @return Хеш-код на основе коллекции, состояния и текущего элемента этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public abstract int hashCode();

        /**
         * {@inheritDoc}
         *
         * @param object объект.
         *
         * @return Если {@code object} эквивалентен этому итератору, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("null -> false; !null -> value")
        public abstract boolean equals(final @Nullable Object object);

        /**
         * {@inheritDoc}
         *
         * @return Строка, представляющая состояние и текущий элемент этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public abstract @NonNull String toString();

    }

}
