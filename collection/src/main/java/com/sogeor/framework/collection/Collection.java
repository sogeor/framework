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

package com.sogeor.framework.collection;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface Collection<T> {

    /**
     * @return Новый итератор элементов этой коллекции.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этой коллекции.
     * @since 1.0.0-RC1
     */
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
    @Contract("-> value")
    default long size() {
        var result = 0L;
        for (final @NonNull var it = iterator(); it.after(); it.next()) ++result;
        return result;
    }

    /**
     * @return {@code size() == 0}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    default boolean empty() {
        return size() == 0;
    }

    /**
     * Если {@code empty()}, то возвращает, например, {@code 0} или {@code 1}, иначе вычисляет хеш-код этой коллекции на
     * основе её элементов и возвращает его.
     *
     * @return Хеш-код этой коллекции.
     *
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Ω(1)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * Если {@code this} эквивалентно {@code object}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Ω(1)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    boolean equals(final @Nullable Object object);

    /**
     * @return {@code super.toString()}.
     *
     * @see Object#toString()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    @NonNull
    String toString();

    /**
     * Представляет собой итератор элементов коллекции.
     * <p>
     * Каждый итератор позволяет перебирать элементы коллекции в нужном порядке, а некоторые итераторы могут даже
     * получать информацию об их существовании, изменять, добавлять или удалять их.
     * <p>
     * Каждый итератор элементов коллекции может находиться в определённом или неопределённом состоянии. Если размер
     * коллекции равен нулю, то итератор находится в неопределённом состоянии, а его методы ничего не делают, кроме
     * методов вставки элементов, в ином случае итератор находится в определённом состоянии, то есть сохраняет
     * информацию о текущем элементе коллекции.
     * <p>
     * Итератор предназначен для использования исключительно в одном потоке. Если же необходимо иное, требуется
     * синхронизация поверх вызовов его методов.
     *
     * @param <T> тип элементов.
     *
     * @see Collection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> {

        /**
         * Если {@code !first()}, то переходит к первому элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> start();

        /**
         * Если {@code before()}, то переходит к элементу перед текущим.
         *
         * @return {@code this}.
         *
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> previous();

        /**
         * Если {@code after()}, то переходит к элементу после текущего.
         *
         * @return {@code this}.
         *
         * @see #after()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> next();

        /**
         * Если {@code !last()}, то переходит к последнему элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> end();

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
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
         * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
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
         * @return Если этот итератор находится в определённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean determined();

        /**
         * @return Если этот итератор находится в неопределённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        default boolean undetermined() {
            return !determined();
        }

        /**
         * @return {@code super.hashCode()}.
         *
         * @see Object#hashCode()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        int hashCode();

        /**
         * @param object объект.
         *
         * @return {@code this == object}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("$? -> $value")
        boolean equals(final @Nullable Object object);

        /**
         * @return {@code super.toString()}.
         *
         * @see Object#toString()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        @NonNull
        String toString();

    }

}
