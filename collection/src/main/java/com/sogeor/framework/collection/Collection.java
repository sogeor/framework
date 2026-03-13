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
 * У каждой коллекции есть размер и вместимость. Размер — это количество элементов, которые содержатся в коллекции,
 * вместимость — это максимальный размер коллекции. Чтобы повысить эффективность работы с памятью, иногда следует
 * вручную поменять вместимость коллекции с помощью следующих методов: {@link #shrink()} и {@link #expand(long)}.
 * <p>
 * Коллекции бывают итерируемыми и неитерируемыми. Первые предоставляют итератор для итерации по их элементам, а вторые
 * нет. Итератор позволяет взаимодействовать с коллекцией на самом примитивном уровне.
 * <p>
 * Также коллекции бывают упорядоченными и неупорядоченными. Первые всегда гарантируют определённый порядок элементов.
 * Вторые не гарантируют порядок, но он всё равно может возникнуть.
 *
 * @since 1.0.0-RC1
 */
public interface Collection extends Cloneable {

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
     * @return Вместимость этой коллекции — её максимальный размер.
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
     * @see #size()
     * @see #capacity()
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
     * @return Если элементы этой коллекции могут быть {@code null}, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> $value")
    boolean nullable();

    /**
     * @return Если эта коллекция является упорядоченной, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> $value")
    default boolean sequenced() {
        return false;
    }

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Collection clone();

    /**
     * Если {@link #empty()}, то возвращает {@code 0} или {@code 1}, иначе вычисляет хеш-код на основе элементов этой
     * коллекции и возвращает его.
     *
     * @return Если {@link #empty()}, то {@code 0} или {@code 1}, иначе хеш-код на основе элементов этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * Если {@code object} является коллекцией, то сравнивает её элементы с элементами этой коллекции, а именно
     * убеждается, что обе коллекции содержат одни и те же элементы. Если {@code object} и эта коллекция являются
     * упорядоченными, то также проверяет, совпадает ли порядок их элементов. Если все условия истинны, то есть
     * {@code object} эквивалентен этой коллекции, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return Если {@code object} эквивалентен этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("null -> false; !null -> value")
    boolean equals(final @Nullable Object object);

    /**
     * Представляет элементы этой коллекции в виде строки и возвращает её.
     *
     * @return Строка, представляющая элементы этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    @NonNull
    String toString();

}
