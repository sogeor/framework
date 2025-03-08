/*
 * Copyright 2024 Sogeor
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

package com.sogeor.framework.collection.immutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.collection.readable.ReadableCollection;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;

/**
 * Представляет собой неизменяемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface ImmutableCollection<T> extends ReadableCollection<T> {

    /**
     * {@inheritDoc}
     *
     * @param consumer потребитель элементов.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @throws F неудачное потребление элемента с помощью {@code consumer}.
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    default <F extends Throwable> @NonNull ImmutableCollection<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws ValidationFault, F {
        ReadableCollection.super.iterate(consumer);
        return this;
    }

    /**
     * @return Новый итератор элементов этой коллекции.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этой коллекции.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * @return Размер этой коллекции — количество её элементов.
     *
     * @implNote Стандартная реализация с оценкой временной сложности {@code Θ(n)} неэффективна и должна быть
     * переопределена.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    default long size() {
        return ReadableCollection.super.size();
    }

    /**
     * @return {@code size() == 0}.
     *
     * @implNote Стандартная реализация с оценкой временной сложности {@code O(n)} неэффективна и должна быть
     * переопределена.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    default boolean empty() {
        return ReadableCollection.super.empty();
    }

    /**
     * @return Строковое представление этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    @NonNull
    String toString();

    /**
     * Представляет собой итератор элементов неизменяемой коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see ImmutableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends ReadableCollection.Iterator<T> {

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> start();

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> previous();

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #after()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> next();

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> end();

        /**
         * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        boolean current();

        /**
         * @return Если этот итератор находится в определённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        boolean determined();

        /**
         * @return Если этот итератор находится в неопределённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        default boolean undetermined() {
            return !determined();
        }

    }

}
