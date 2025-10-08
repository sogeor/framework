/*
 * Copyright 2024 Sogeor
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
 * Представляет собой коллекцию элементов.
 * <p>
 * Если заранее известно хотя бы предполагаемое количество элементов коллекции после её изменения, то до этого изменения
 * с помощью методов {@code shrink()} и {@code expand(long)} можно произвести выделение дополнительной памяти сразу для
 * всех элементов, которым будет недостаточно уже выделенной, либо, наоборот, высвобождение излишней памяти, которая не
 * потребуется после изменения коллекции. Таким образом, можно повысить эффективность работы с памятью.
 *
 * @since 1.0.0-RC1
 */
public interface Collection {

    /**
     * @return Размер этой коллекции — количество её элементов.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    long size();

    /**
     * @return {@code size() == 0}.
     *
     * @see #size()
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    default boolean empty() {
        return size() == 0;
    }

    /**
     * @return Вместимость этой коллекции — максимальное количество элементов, которое она способна содержать.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    long capacity();

    /**
     * Если {@code capacity() > size()}, то сокращает вместимость этой коллекции до {@code size()} и возвращает
     * {@code true}, иначе возвращает {@code false}.
     *
     * @return Если вместимость этой коллекции была сокращена, то {@code true}, иначе {@code false}.
     *
     * @see #capacity()
     * @see #size()
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean shrink();

    /**
     * Если {@code capacity > capacity()}, то увеличивает вместимость этой коллекции до {@code capacity} и возвращает
     * {@code true}, иначе — {@code false}.
     *
     * @param capacity требуемая вместимость.
     *
     * @return Если вместимость этой коллекции была увеличена, то {@code true}, иначе {@code false}.
     *
     * @see #capacity()
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    boolean expand(final long capacity);

    /**
     * @return Хеш-код этой коллекции на основе её элементов.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * Если {@code this} эквивалентно {@code object}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("null -> false; !null -> value")
    boolean equals(final @Nullable Object object);

    /**
     * @return Строковое представление этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    @NonNull
    String toString();

}
