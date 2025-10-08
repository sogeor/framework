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
 * Представляет собой перебираемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface IterableCollection<T> extends Collection {

    /**
     * @return Новый итератор элементов этой коллекции.
     *
     * @implSpec Возвращаемый итератор должен находится в неопределённом состоянии.
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
     * @see Iterator
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * @return Размер этой коллекции — количество её элементов.
     *
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code Θ(n)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    default long size() {
        var result = 0L;
        for (final @NonNull var it = iterator(); it.next(); ) ++result;
        return result;
    }

    /**
     * Представляет собой итератор элементов перебираемой коллекции.
     * <p>
     * Каждый итератор элементов перебираемой коллекции может находиться в определённом или неопределённом состоянии.
     * Если размер коллекции равен нулю, то итератор точно находится в неопределённом состоянии и многие его методы
     * ничего не делают, в ином случае итератор может находиться в определённом состоянии, то есть сохранять информацию
     * о текущем элементе коллекции. Если итератор находится в неопределённом состоянии, но размер коллекции больше
     * нуля, то его состояние изменится на определённое при вызове метода {@linkplain #start()},
     * {@linkplain #previous()}, {@linkplain #next()} или {@linkplain #end()}, а текущий элемент будет выбран исходя из
     * особенностей вызываемого метода:
     * <ul>
     * <li>
     * После вызова метода {@linkplain #start()} текущим элементом станет первый элемент коллекции, если он существует.
     * </li>
     * <li>
     * После вызова метода {@linkplain #previous()} текущим элементом станет предыдущий элемент коллекции, если он
     * существует.
     * </li>
     * <li>
     * После вызова метода {@linkplain #next()} текущим элементом станет следующий элемент коллекции, если он существует.
     * </li>
     * <li>
     * После вызова метода {@linkplain #end()} текущим элементом станет последний элемент коллекции, если он существует.
     * </li>
     * </ul>
     * Если итератор находится в определённом состоянии, то его можно изменить на неопределённое с помощью вызова {@linkplain #reset()}.
     * <p>
     * Итератор предназначен для использования исключительно в одном потоке. Если же необходимо иное, требуется
     * синхронизация поверх вызовов его методов.
     *
     * @param <T> тип элементов.
     *
     * @see IterableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> {

        /**
         * Если {@code !first()}, то переходит к первому элементу, если он существует, и возвращает {@code true}, иначе
         * — {@code false}.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Ω(1)} и {@code O(n)}.
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean start();

        /**
         * Если {@code before()}, то переходит к элементу перед текущим и возвращает {@code true}, иначе —
         * {@code false}.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean previous();

        /**
         * Если {@code after()}, то переходит к элементу после текущего и возвращает {@code true}, иначе —
         * {@code false}.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @see #after()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean next();

        /**
         * Если {@code !last()}, то переходит к последнему элементу, если он существует, и возвращает {@code true},
         * иначе — {@code false}.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Ω(1)} и {@code O(n)}.
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean end();

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean first();

        /**
         * @return Если перед текущим элементом существует другой, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean before();

        /**
         * @return Если после текущего элемента существует другой, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean after();

        /**
         * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean last();

        /**
         * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean current();

        /**
         * @return Если этот итератор находится в определённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean determined();

        /**
         * @return Если этот итератор находится в неопределённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценками временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        default boolean undetermined() {
            return !determined();
        }

        /**
         * Если {@code determined()}, то изменяет состояние этого итератора на неопределённое и возвращает {@code true},
         * иначе — {@code false}.
         *
         * @return {@code true} или {@code false}.
         *
         * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean reset();

        /**
         * @return {@code super.hashCode()}.
         *
         * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
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
         * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("? -> $value")
        boolean equals(final @Nullable Object object);

        /**
         * @return Строковое представление этого итератора.
         *
         * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code Θ(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        @NonNull
        String toString();

    }

}
