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

/**
 * Представляет собой упорядоченный многозначный ассоциативный массив элементов — пар, каждая из которых состоит из
 * ключа и соответствующей ему итерируемой коллекции значений.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 *
 * @see Entry
 * @since 1.0.0-RC1
 */
public interface SequencedMultimap<K, V> extends Multimap<K, V>, SequencedCollection {

    /**
     * @return Множество ключей этого многозначного ассоциативного массива.
     *
     * @implSpec Возвращаемое множество ключей должно быть тесно связано с этим многозначным ассоциативным массивом, так
     * как оно предоставляет содержащиеся в нём ключи. К примеру, размер множества ключей должен совпадать с размером
     * многозначного ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet<K> keys();

    /**
     * @return Мультимножество итерируемых коллекций значений этого многозначного ассоциативного массива.
     *
     * @implSpec Возвращаемое мультимножество итерируемых коллекций значений должно быть тесно связано с этим
     * многозначным ассоциативным массивом, так как оно предоставляет содержащиеся в нём итерируемые коллекции значений.
     * К примеру, размер мультимножества итерируемых коллекций значений должен совпадать с размером многозначного
     * ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedMultiset<? extends IterableCollection<V>> values();

    /**
     * @return Мультимножество значений этого многозначного ассоциативного массива.
     *
     * @implSpec Возвращаемое мультимножество значений должно быть тесно связано с этим многозначным ассоциативным
     * массивом, так как оно предоставляет содержащиеся в нём значения. К примеру, размер мультимножества значений
     * должен совпадать с суммой размеров итерируемых коллекций значений многозначного ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedMultiset<V> flattenedValues();

    /**
     * @return Мультимножество элементов этого многозначного ассоциативного массива.
     *
     * @implSpec Возвращаемое мультимножество элементов должно быть тесно связано с этим многозначным ассоциативным
     * массивом, так как оно предоставляет содержащиеся в нём элементы. К примеру, размер мультимножества элементов
     * должен совпадать с размером многозначного ассоциативного массива.
     * @apiNote По сути возвращаемое мультимножество элементов — это этот многозначный ассоциативный массив, но в другом
     * виде.
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
    SequencedMultimap<K, V> clone();

    /**
     * Представляет собой элемент упорядоченного многозначного ассоциативного массива.
     *
     * @param <K> тип ключей.
     * @param <V> тип значений.
     *
     * @see SequencedMultimap
     * @since 1.0.0-RC1
     */
    interface Entry<K, V> extends Multimap.Entry<K, V> {

        /**
         * @return Итерируемая коллекция значений этого элемента.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        @NonNull
        SequencedIterableCollection<V> values();

    }

}
