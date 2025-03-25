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
     * @return Новый итератор элементов этой коллекции.
     *
     * @implSpec Возвращаемый итератор должен находится в неопределённом состоянии.
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
     * @see Iterator
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

    /**
     * @return {@code super.toString() + '{' + size() + '}'}.
     *
     * @see #capacity()
     * @see #size()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public @NonNull String toString() {
        return super.toString() + "{capacity=" + capacity() + ",size=" + size() + '}';
    }

    /**
     * Представляет собой абстрактный итератор элементов абстрактной коллекции.
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
         * @return {@code super.hashCode()}.
         *
         * @see Object#hashCode()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public int hashCode() {
            return super.hashCode();
        }

        /**
         * @param object объект.
         *
         * @return {@code this == object}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("$? -> $value")
        public boolean equals(final @Nullable Object object) {
            return this == object;
        }

        /**
         * @return {@code super.toString()}.
         *
         * @see Object#toString()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public @NonNull String toString() {
            return super.toString();
        }

    }

}
