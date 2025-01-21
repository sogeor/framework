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
 * Представляет собой итератор элементов коллекции.
 *
 * @param <T> тип элементов.
 *
 * @see Collection
 * @since 1.0.0-RC1
 */
public interface Iterator<T> {

    /**
     * Если итератор не обладает {@linkplain StandardIteratorFeature#START_OPERATION}, то возвращает {@code this}.
     * <p>
     * Если {@linkplain #first() текущий элемент не первый}, то переходит к первому элементу, если он существует.
     *
     * @return {@code this}.
     *
     * @see #end()
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> start();

    /**
     * Если итератор не обладает {@linkplain StandardIteratorFeature#PREVIOUS_OPERATION}, то возвращает {@code this}.
     * <p>
     * Если {@linkplain #before() перед текущим элементом существует другой}, то переходит к нему.
     *
     * @return {@code this}.
     *
     * @see #next()
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> previous();

    /**
     * Если итератор не обладает {@linkplain StandardIteratorFeature#NEXT_OPERATION}, то возвращает {@code this}.
     * <p>
     * Если {@linkplain #after() после текущего элемента существует другой}, то переходит к нему.
     *
     * @return {@code this}.
     *
     * @see #previous()
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> next();

    /**
     * Если итератор не обладает {@linkplain StandardIteratorFeature#END_OPERATION}, то возвращает {@code this}.
     * <p>
     * Если {@linkplain #last() текущий элемент не последний}, то переходит к последнему элементу, если он существует.
     *
     * @return {@code this}.
     *
     * @see #start()
     * @since 1.0.0-RC1
     */
    @Contract("-> this")
    @NonNull
    Iterator<T> end();

    /**
     * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
     *
     * @see #last()
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
     * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
     *
     * @see #first()
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
     * @return Особенности итератора.
     *
     * @see StandardIteratorFeature
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    @NonNull
    ImmutableSet<@NonNull IteratorFeature> features();

}
