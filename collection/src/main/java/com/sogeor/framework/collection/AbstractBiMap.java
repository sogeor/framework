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

import java.util.Objects;

/**
 * Представляет собой абстрактный двунаправленный ассоциативный массив элементов.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 *
 * @see AbstractEntry
 * @since 1.0.0-RC1
 */
public abstract class AbstractBiMap<K, V> extends AbstractCollection implements BiMap<K, V> {

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractBiMap() {}

    /**
     * @return Мультимножество элементов этого двунаправленного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    public abstract @NonNull Set<? extends AbstractEntry<K, V>> entries();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractBiMap<K, V> clone();

    /**
     * Представляет собой абстрактный элемент двунаправленного ассоциативного массива.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see AbstractBiMap
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractEntry<K, V> implements Entry<K, V> {

        /**
         * Создаёт экземпляр по умолчанию.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractEntry() {}

        /**
         * @return {@code Objects.hashCode(key()) ^ Objects.hashCode(value())}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public int hashCode() {
            return Objects.hashCode(key()) ^ Objects.hashCode(value());
        }

        /**
         * {@inheritDoc}
         *
         * @param object объект.
         *
         * @return Если {@code object} эквивалентен этому элементу, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("!null -> value; null -> false")
        public boolean equals(final @Nullable Object object) {
            return object == this || object instanceof Entry<?, ?> that && Objects.equals(key(), that.key()) &&
                                     Objects.equals(value(), that.value());
        }

        /**
         * @return {@code '{' + String.valueOf(key()) + ", " + value() + '}'}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public @NonNull String toString() {
            return '{' + String.valueOf(key()) + ", " + value() + '}';
        }

    }

}
