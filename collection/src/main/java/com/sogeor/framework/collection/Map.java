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
import com.sogeor.framework.collection.immutable.ImmutableSet;

/**
 * Представляет собой ассоциативный массив элементов — пар, каждая из которых состоит из ключа и соответствующего ему
 * значения.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 * @param <T> тип элементов.
 *
 * @see MapEntry
 * @see MapIterator
 * @since 1.0.0-RC1
 */
public interface Map<K, V, T extends MapEntry<K, V>> extends Collection<T> {

    /**
     * @return Итератор элементов.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    MapIterator<K, V, T> iterator();

    /**
     * @return Особенности ассоциативного массива.
     *
     * @see StandardCollectionFeature
     * @see StandardMapFeature
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    ImmutableSet<@NonNull CollectionFeature> features();

}
