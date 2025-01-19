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
 * Представляет собой итератор (1) элементов (2) коллекции.
 *
 * @param <T> тип [2].
 *
 * @since 1.0.0-RC1
 */
public interface Iterator<T> {

    /**
     * Если {1} обладает {@linkplain StandardIteratorFeature#START_OPERATION} и не {@linkplain #first()}, то переходит к
     * первому элементу.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> start();

    /**
     * Если {1} обладает {@linkplain StandardIteratorFeature#PREVIOUS_OPERATION} и {@linkplain #before()}, то переходит
     * к предыдущему элементу.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> previous();

    /**
     * Если {1} обладает {@linkplain StandardIteratorFeature#NEXT_OPERATION} и {@linkplain #after()}, то переходит к
     * следующему элементу.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> next();

    /**
     * Если {1} обладает {@linkplain StandardIteratorFeature#END_OPERATION} и не {@linkplain #last()}, то переходит к
     * последнему элементу.
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> end();

    /**
     * @return Если {@linkplain #current()} и текущий элемент первый, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean first();

    /**
     * @return Если перед текущим элементом существует другой, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean before();

    /**
     * @return Если после текущего элемента существует другой, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean after();

    /**
     * @return Если {@linkplain #current()} и текущий элемент последний, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean last();

    /**
     * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    boolean current();

    /**
     * @return Особенности {1}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    @NonNull
    ImmutableSet<@NonNull IteratorFeature> features();

}
