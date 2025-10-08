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

package com.sogeor.framework.collection.writable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.Collection;
import com.sogeor.framework.collection.readable.ReadableCollection;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;

/**
 * Представляет собой записываемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface WritableCollection<T> extends Collection<T> {

    /**
     * Если возможно, то добавляет {@code element} в эту коллекцию и возвращает {@code true}, иначе — {@code false}.
     *
     * @param element элемент.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code element} не должен быть {@code null}.
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code O(n)}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    boolean add(final @Nullable T element) throws ValidationFault;

    /**
     * Если возможно, то добавляет {@code elements} в эту коллекцию и возвращает {@code true}, иначе — {@code false}.
     *
     * @param elements элементы.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code O(n²)}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("!null -> ?; null -> fault")
    boolean add(final @Nullable T @NonNull ... elements) throws ValidationFault;

    /**
     * Если возможно, то добавляет {@code elements} в эту коллекцию и возвращает {@code true}, иначе — {@code false}.
     *
     * @param elements элементы.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @implNote Ожидаемая реализация обладает оценкой временной сложности {@code O(n²)}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    boolean add(final @NonNull ReadableCollection<T> elements) throws ValidationFault;

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
     * Представляет собой итератор элементов записываемой коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see WritableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends Collection.Iterator<T> {

        /**
         * Если {@code determined()}, то заменяет текущий элемент на {@code element}.
         *
         * @param element элемент.
         *
         * @return {@code this}.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code element} не должен быть {@code null}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @Contract("? -> this")
        @NonNull
        Iterator<T> replace(final @Nullable T element) throws ValidationFault;

        /**
         * Если {@code determined()}, то заменяет текущий элемент на {@code elements}.
         *
         * @param elements элементы.
         *
         * @return {@code this}.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code elements} не должны быть {@code null}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @SuppressWarnings("unchecked")
        @Contract("!null -> this; null -> fault")
        @NonNull
        Iterator<T> replace(final @Nullable T @NonNull ... elements) throws ValidationFault;

        /**
         * Если {@code determined()}, то заменяет текущий элемент на {@code elements}.
         *
         * @param elements элементы.
         *
         * @return {@code this}.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code elements} не должны быть {@code null}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @Contract("!null -> this; null -> fault")
        @NonNull
        Iterator<T> replace(final @NonNull ReadableCollection<T> elements) throws ValidationFault;

        /**
         * Если {@code determined()}, то вставляет {@code element} после текущего элемента, иначе переходит в
         * определённое состояние и вставляет {@code element} первым.
         *
         * @param element элемент.
         *
         * @return {@code this}.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code element} не должен быть {@code null}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @Contract("? -> this")
        @NonNull
        Iterator<T> insert(final @Nullable T element) throws ValidationFault;

        /**
         * Если {@code determined()}, то вставляет {@code elements} после текущего элемента, иначе переходит в
         * определённое состояние и вставляет {@code elements} первыми.
         *
         * @param elements элементы.
         *
         * @return {@code this}.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code elements} не должны быть {@code null}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @SuppressWarnings("unchecked")
        @Contract("!null -> this; null -> fault")
        @NonNull
        Iterator<T> insert(final @Nullable T @NonNull ... elements) throws ValidationFault;

        /**
         * Если {@code determined()}, то вставляет {@code elements} после текущего элемента, иначе переходит в
         * определённое состояние и вставляет {@code elements} первыми.
         *
         * @param elements элементы.
         *
         * @return {@code this}.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code elements} не должны быть {@code null}.
         * @see #determined()
         * @since 1.0.0-RC1
         */
        @Contract("!null -> this; null -> fault")
        @NonNull
        Iterator<T> insert(final @NonNull ReadableCollection<T> elements) throws ValidationFault;

        /**
         * Если {@code current()}, то удаляет текущий элемент.
         *
         * @return {@code this}.
         *
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> remove();

    }

}
