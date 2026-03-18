/*
 * Copyright 2026 Sogeor
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

/**
 * Представляет собой абстрактный упорядоченный двунаправленный ассоциативный массив элементов.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 *
 * @see AbstractEntry
 * @since 1.0.0-RC1
 */
public abstract class AbstractSequencedBiMap<K, V> extends AbstractBiMap<K, V> implements SequencedBiMap<K, V> {

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractSequencedBiMap() {}

    /**
     * @return Мультимножество элементов этого двунаправленного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    public abstract @NonNull SequencedSet<? extends AbstractEntry<K, V>> entries();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractSequencedBiMap<K, V> clone();

    /**
     * Представляет собой абстрактный элемент упорядоченного двунаправленного ассоциативного массива.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see AbstractSequencedBiMap
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractEntry<K, V> extends AbstractBiMap.AbstractEntry<K, V> implements
                                                                                               SequencedBiMap.Entry<K, V> {

        /**
         * Создаёт экземпляр по умолчанию.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractEntry() {}

    }

}
