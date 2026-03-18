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

package com.sogeor.framework.collection.mutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.collection.Collection;

/**
 * Представляет собой изменяемую коллекцию элементов.
 * <p>
 * Чтобы повысить эффективность работы с памятью, можно вручную менять вместимость коллекции с помощью следующих
 * методов: {@link #shrink()} и {@link #expand(long)}.
 *
 * @since 1.0.0-RC1
 */
public interface MutableCollection extends Collection {

    /**
     * Если {@code capacity() > size()}, то сокращает вместимость этой коллекции до {@link #size()} и возвращает
     * {@code true}, иначе возвращает {@code false}.
     *
     * @return Если вместимость этой коллекции была сокращена, то {@code true}, иначе {@code false}.
     *
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
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    boolean expand(final long capacity);

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    MutableCollection clone();

}
