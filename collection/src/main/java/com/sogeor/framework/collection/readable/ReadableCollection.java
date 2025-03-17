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

package com.sogeor.framework.collection.readable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.Collection;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.function.Predicate;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;

/**
 * Представляет собой читаемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface ReadableCollection<T> extends Collection<T> {

    /**
     * Потребляет каждый из элементов этой коллекции с помощью {@code consumer}, пока не возникнет программный сбой или
     * неисправность.
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
    @Contract("!null -> this; null -> fault")
    default <F extends Throwable> @NonNull ReadableCollection<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws ValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        if (empty()) return this;
        for (final @NonNull var it = iterator(); it.after(); it.next()) consumer.consume(it.element());
        return this;
    }

    /**
     * Оценивает каждый из элементов этой коллекции с помощью {@code predicate}, пока не возникнет программный сбой или
     * неисправность и все оценки равны {@code true}. Если хотя бы одна оценка равна {@code false}, то возвращает
     * {@code false}, иначе — {@code true}.
     *
     * @param predicate предикат элементов.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n)}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> value; null -> fault")
    default <F extends Throwable> boolean all(final @NonNull Predicate<? super T, F> predicate) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(predicate, "The passed predicate");
        if (empty()) return true;
        for (final @NonNull var it = iterator(); it.after(); it.next())
            if (!predicate.evaluate(it.element())) return false;
        return true;
    }

    /**
     * Оценивает каждый из элементов этой коллекции с помощью {@code predicate}, пока не возникнет программный сбой или
     * неисправность и все оценки равны {@code false}. Если хотя бы одна оценка равна {@code true}, то возвращает
     * {@code true}, иначе — {@code false}.
     *
     * @param predicate предикат элементов.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n)}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> value; null -> fault")
    default <F extends Throwable> boolean any(final @NonNull Predicate<? super T, F> predicate) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(predicate, "The passed predicate");
        if (empty()) return false;
        for (final @NonNull var it = iterator(); it.after(); it.next())
            if (predicate.evaluate(it.element())) return true;
        return false;
    }

    /**
     * Если {@code element} является элементом этой коллекции, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param element элемент.
     *
     * @return {@code true} или {@code false}.
     *
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n)}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    default boolean contains(final @Nullable T element) {
        return !empty() && any(e -> Objects.equals(e, element));
    }

    /**
     * Если каждый из {@code elements} является элементом этой коллекции, то возвращает {@code true}, иначе —
     * {@code false}.
     *
     * @param elements элементы.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n²)}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("? -> value")
    default boolean contains(final @Nullable T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        if (elements.length > size()) return false;
        for (final @Nullable var element : elements) if (!contains(element)) return false;
        return true;
    }

    /**
     * Если каждый из {@code elements} является элементом этой коллекции, то возвращает {@code true}, иначе —
     * {@code false}.
     *
     * @param elements элементы.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n²)}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    default boolean contains(final @NonNull ReadableCollection<T> elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements == this || elements.size() <= size() && elements.all(this::contains);
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
     * Представляет собой итератор элементов читаемой коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see ReadableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends Collection.Iterator<T> {

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
         * Если {@code current()}, то возвращает текущий элемент, иначе — {@code null}.
         *
         * @return Текущий элемент или {@code null}.
         *
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        @Nullable
        T element();

    }

}
