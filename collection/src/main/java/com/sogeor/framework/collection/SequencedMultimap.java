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
 * @see Entry
 * @since 1.0.0-RC1
 */
public interface SequencedMultimap extends SequencedCollection, Multimap {

    /**
     * @return Множество ключей этого многозначного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedSet keys();

    /**
     * @return Мультимножество итерируемых коллекций значений этого многозначного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedMultiset values();

    /**
     * @return Мультимножество значений этого многозначного ассоциативного массива.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $!null")
    @NonNull
    SequencedMultiset flattenedValues();

    /**
     * @return Мультимножество элементов этого многозначного ассоциативного массива.
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
    SequencedMultimap clone();

    /**
     * Представляет собой элемент упорядоченного многозначного ассоциативного массива.
     *
     * @see SequencedMultimap
     * @since 1.0.0-RC1
     */
    interface Entry extends Multimap.Entry {

        /**
         * @return Итерируемая коллекция значений этого элемента.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        @NonNull
        IterableSequencedCollection values();

    }

}
