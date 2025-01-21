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
import com.sogeor.framework.collection.immutable.ImmutableSet;

/**
 * Представляет собой коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface Collection<T> {

    /**
     * @return Итератор элементов.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * @return Размер коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    long size();

    /**
     * @return Если элементы не существуют, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean empty();

    /**
     * @return Особенности коллекции.
     *
     * @see StandardCollectionFeature
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    @NonNull
    ImmutableSet<@NonNull CollectionFeature> features();

}
