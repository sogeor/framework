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
     * Перебирает все элементы и потребляет каждый из них с помощью {@code consumer}.
     *
     * @param consumer потребитель элементов.
     *
     * @return {@code this}.
     *
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @throws F неудачное потребление элемента с помощью {@code consumer}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> this; null -> fault")
    default <F extends Throwable> @NonNull ReadableCollection<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws NullValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        final @NonNull var it = iterator();
        if (it.canNext()) for (it.start(); it.after(); it.next()) consumer.consume(it.element());
        else for (it.end(); it.before(); it.previous()) consumer.consume(it.element());
        return this;
    }

    /**
     * Перебирает все элементы и оценивает каждый из них с помощью {@code predicate}. Если оценки всех элементов равны
     * {@code true}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param predicate предикат элементов.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> value; null -> fail")
    default <F extends Throwable> boolean all(final @NonNull Predicate<? super T, F> predicate) throws
                                                                                                NullValidationFault, F {
        Validator.nonNull(predicate, "The passed predicate");
        final @NonNull var it = iterator();
        if (it.canNext()) {
            for (it.start(); it.after(); it.next()) if (!predicate.evaluate(it.element())) return false;
        } else for (it.end(); it.before(); it.previous()) if (!predicate.evaluate(it.element())) return false;
        return true;
    }

    /**
     * Перебирает все элементы и оценивает каждый из них с помощью {@code predicate}. Если оценка хотя бы одного
     * элемента равна {@code true}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param predicate предикат элементов.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> value; null -> fail")
    default <F extends Throwable> boolean any(final @NonNull Predicate<? super T, F> predicate) throws
                                                                                                NullValidationFault, F {
        Validator.nonNull(predicate, "The passed predicate");
        final @NonNull var it = iterator();
        if (it.canNext()) {
            for (it.start(); it.after(); it.next()) if (predicate.evaluate(it.element())) return true;
        } else for (it.end(); it.before(); it.previous()) if (predicate.evaluate(it.element())) return true;
        return false;
    }

    /**
     * Если {@code element} один из элементов этой коллекции, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param element элемент.
     *
     * @return {@code true} или {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    default boolean contains(final @Nullable T element) {
        return any(e -> Objects.equals(e, element));
    }

    /**
     * Если {@code elements} одни из элементов этой коллекции, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param elements элементы.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("? -> value")
    default boolean contains(final @Nullable T @NonNull ... elements) throws NullValidationFault {
        Validator.nonNull(elements, "The passed elements");
        for (final @Nullable var element : elements) if (!contains(element)) return false;
        return true;
    }

    /**
     * Если {@code elements} одни из элементов этой коллекции, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param elements элементы.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    default boolean contains(final @NonNull ReadableCollection<T> elements) throws NullValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements.all(this::contains);
    }

    /**
     * @return Итератор элементов.
     *
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
     * @implSpec Каждый итератор должен быть способен переходить к элементу, расположенному либо перед текущим, либо
     * после него, либо к обоим из них.
     * <p>
     * Если итератор способен переходить к элементу, расположенному перед текущим, то он должен быть также способен
     * переходить к последнему. И наоборот, если итератор способен переходить к элементу, расположенному после текущего,
     * то он должен быть также способен переходить к первому. Это необходимо для корректной итерации, например:
     * <pre>
     * {@code
     * void example(final @NonNull Iterator<?> it) {
     *     if (it.canNext()) { // it.canStart() == true
     *         for (it.start(); it.after(); it.next()) {
     *             // ...
     *         }
     *     } else { // it.canPrevious() == true && it.canEnd() == true
     *         for (it.end(); it.before(); it.previous()) {
     *             // ...
     *         }
     *     }
     * }
     * }
     * </pre>
     * @see ReadableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends Collection.Iterator<T> {

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @see #end()
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
         * @see #next()
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
         * @see #previous()
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
         * @see #start()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> end();

        /**
         * Если {@linkplain #current() текущий элемент существует}, то возвращает его, иначе — {@code null}.
         *
         * @return Текущий элемент или {@code null}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        @Nullable
        T element();

    }

}
