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
 * Представляет собой абстрактный список элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractListIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractList<T> extends AbstractCollection<T> implements List<T> {

    /**
     * Создаёт экземпляр на основе {@code features}.
     *
     * @param features особенности списка.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractList(final @NonNull ImmutableSet<@NonNull CollectionFeature> features) {
        super(features);
    }

    /**
     * @return Особенности списка.
     *
     * @see StandardCollectionFeature
     * @see StandardListFeature
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    public @NonNull ImmutableSet<@NonNull CollectionFeature> features() {
        return super.features();
    }

}
