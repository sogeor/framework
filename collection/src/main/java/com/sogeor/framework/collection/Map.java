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
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой ассоциативный массив элементов — пар, каждая из которых состоит из ключа и соответствующего ему
 * значения.
 *
 * @see Entry
 * @since 1.0.0-RC1
 */
public interface Map extends Collection {

    /**
     * @return Множество ключей этого ассоциативного массива.
     *
     * @implSpec Возвращаемое множество ключей должно быть тесно связано с этим ассоциативным массивом, так как оно
     * предоставляет содержащиеся в нём ключи. К примеру, размер множества ключей должен совпадать с размером
     * ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    @NonNull
    Set keys();

    /**
     * @return Мультимножество значений этого ассоциативного массива.
     *
     * @implSpec Возвращаемое мультимножество значений должно быть тесно связано с этим ассоциативным массивом, так как
     * оно предоставляет содержащиеся в нём значения. К примеру, размер мультимножества значений должен совпадать с
     * размером ассоциативного массива.
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    @NonNull
    Multiset values();

    /**
     * @return Мультимножество элементов этого ассоциативного массива.
     *
     * @implSpec Возвращаемое мультимножество элементов должно быть тесно связано с этим ассоциативным массивом, так как
     * оно предоставляет содержащиеся в нём элементы. К примеру, размер мультимножества элементов должен совпадать с
     * размером ассоциативного массива.
     * @apiNote По сути возвращаемое мультимножество элементов — это этот ассоциативный массив, но в другом виде.
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    @NonNull
    Set entries();

    /**
     * Представляет собой элемент ассоциативного массива — пару, состоящую из ключа и соответствующего ему значения.
     *
     * @see Map
     * @since 1.0.0-RC1
     */
    interface Entry {

        /**
         * Вычисляет хеш-код на основе ключа и соответствующего ему значения этого элемента и возвращает его.
         *
         * @return Хеш-код на основе ключа и соответствующего ему значения этого элемента.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        int hashCode();

        /**
         * Если {@code object} является элементом ассоциативного массива, то сравнивает его с этим элементом, а именно
         * убеждается, что у обоих элементов один и тот же ключ, а также одно и то же соответствующее ключу значение.
         * Если все условия истинны, то есть {@code object} эквивалентен этому элементу, то возвращает {@code true},
         * иначе — {@code false}.
         *
         * @param object объект.
         *
         * @return Если {@code object} эквивалентен этому элементу, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("null -> false; !null -> value")
        boolean equals(final @Nullable Object object);

        /**
         * Представляет ключ и соответствующее ему значение этого элемента в виде строки и возвращает её.
         *
         * @return Строка, представляющая ключ и соответствующее ему значение этого элемента.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        @NonNull
        String toString();

    }

}
