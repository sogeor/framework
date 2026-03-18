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
 * @param <K> тип ключей.
 * @param <V> тип значений.
 *
 * @see Entry
 * @since 1.0.0-RC1
 */
public interface SequencedBiMap<K, V> extends BiMap<K, V>, SequencedCollection {

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
    SequencedBiMap<K, V> inverse();

    /**
     * @return Множество ключей этого двунаправленного ассоциативного массива.
     *
     * @implSpec Возвращаемое множество ключей должно быть тесно связано с этим двунаправленным ассоциативным массивом,
     * так как оно предоставляет содержащиеся в нём ключи. К примеру, размер множества ключей должен совпадать с
     * размером двунаправленного ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet<K> keys();

    /**
     * @return Множество значений этого двунаправленного ассоциативного массива.
     *
     * @implSpec Возвращаемое множество значений должно быть тесно связано с этим двунаправленным ассоциативным
     * массивом, так как оно предоставляет содержащиеся в нём ключи. К примеру, размер множества значений должен
     * совпадать с размером двунаправленного ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet<V> values();

    /**
     * @return Мультимножество элементов этого двунаправленного ассоциативного массива.
     *
     * @implSpec Возвращаемое мультимножество элементов должно быть тесно связано с этим двунаправленным ассоциативным
     * массивом, так как оно предоставляет содержащиеся в нём элементы. К примеру, размер мультимножества элементов
     * должен совпадать с размером двунаправленного ассоциативного массива.
     * @apiNote По сути возвращаемое мультимножество элементов — это этот двунаправленный ассоциативный массив, но в
     * другом виде.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet<? extends Entry<K, V>> entries();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    SequencedBiMap<K, V> clone();

    /**
     * Представляет собой элемент упорядоченного двунаправленного ассоциативного массива.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see SequencedBiMap
     * @since 1.0.0-RC1
     */
    interface Entry<K, V> extends BiMap.Entry<K, V> {}

}
