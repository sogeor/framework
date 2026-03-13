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
 * Представляет собой упорядоченный двунаправленный ассоциативный массив элементов.
 *
 * @see Entry
 * @since 1.0.0-RC1
 */
public interface SequencedBiMap extends SequencedCollection, BiMap {

    /**
     * @return Обратный этому двунаправленный ассоциативный массив.
     *
     * @implSpec Возвращаемый двунаправленный ассоциативный массив должен быть тесно связано с этим, так как он
     * предоставляет содержащиеся в нём ключи в виде значений и соответствующие значения в виде ключей.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedBiMap inverse();

    /**
     * @return Множество ключей этого двунаправленного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet keys();

    /**
     * @return Мультимножество значений этого двунаправленного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet values();

    /**
     * @return Мультимножество элементов этого двунаправленного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet entries();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    SequencedBiMap clone();

    /**
     * Представляет собой элемент упорядоченного двунаправленного ассоциативного массива.
     *
     * @see SequencedBiMap
     * @since 1.0.0-RC1
     */
    interface Entry extends Map.Entry {}

}
