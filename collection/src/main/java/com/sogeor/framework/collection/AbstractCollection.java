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
 * @since 1.0.0-RC1
 */
public abstract class AbstractCollection implements Collection {

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractCollection() {}

    /**
     * {@inheritDoc}
     *
     * @return Хеш-код на основе элементов этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public abstract int hashCode();

    /**
     * {@inheritDoc}
     *
     * @param object объект.
     *
     * @return Если {@code object} эквивалентен этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("null -> false; !null -> value")
    public abstract boolean equals(final @Nullable Object object);

    /**
     * {@inheritDoc}
     *
     * @return Строка, представляющая элементы этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public abstract @NonNull String toString();

}
