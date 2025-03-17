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

package com.sogeor.framework.collection.immutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой неизменяемое множество элементов на основе двумерного массива.
 * <p>
 * Данное множество не способно содержать максимальное теоретическое количество элементов — {@code 9223372036854775807},
 * — однако способно содержать {@code 4611686018427387903} элементов. Так как даже для хранения такого количества
 * элементов размером {@code 1} байт потребуется {@code 4} эксбибайт, то данное ограничение не создаст проблем в
 * ближайшем будущем.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public class ArrayImmutableSet<T> extends AbstractImmutableSet<T> {

    /**
     * Содержит элементы в виде пустого двумерного массива.
     *
     * @since 1.0.0-RC1
     */
    protected static final @Nullable Object @NonNull [] @NonNull [] EMPTY_ELEMENTS = new Object[0][];

    /**
     * Содержит элементы в виде двумерного массива.
     *
     * @since 1.0.0-RC1
     */
    protected final @Nullable T @NonNull [] @NonNull [] elements;

    /**
     * Создаёт экземпляр на основе {@code elements}.
     *
     * @param elements элементы в виде одномерного массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public ArrayImmutableSet(final @Nullable T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        if (elements.length == 0) {
            this.elements = (T[][]) EMPTY_ELEMENTS;
            return;
        }
        this.elements = (T[][]) new Object[1][];
        this.elements[0] = elements;
    }

    /**
     * Создаёт экземпляр на основе {@code elements}.
     *
     * @param elements элементы в виде двумерного массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null} и не должны содержать равные
     * {@code null} одномерные массивы элементов.
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    @SuppressWarnings("ConstantValue")
    public ArrayImmutableSet(final @Nullable T @NonNull [] @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        var i = 0;
        while (i < elements.length && elements[i] != null) ++i;
        if (i < elements.length && (i != 0 || elements[i] == null))
            Validator.nonNull(elements[i], "The subarray of the passed elements with index %s".formatted(i));
        this.elements = elements;
    }

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
    public <F extends Throwable> @NonNull ArrayImmutableSet<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws ValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        if (empty()) return this;
        for (final @Nullable T @NonNull [] _elements : elements)
            for (final @Nullable T element : _elements) consumer.consume(element);
        return this;
    }

    /**
     * @return Новый итератор элементов этого множества.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этого множества.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public @NonNull Iterator<T> iterator() {
        return new Iterator<>(this);
    }

    /**
     * Представляет собой итератор элементов неизменяемого множества на основе двумерного массива.
     *
     * @param <T> тип элементов.
     *
     * @see ArrayImmutableSet
     * @since 1.0.0-RC1
     */
    public static class Iterator<T> extends AbstractIterator<T> {

        /**
         * Содержит неизменяемое множество элементов на основе двумерного массива.
         *
         * @since 1.0.0-RC1
         */
        protected final @NonNull ArrayImmutableSet<T> set;

        /**
         * Содержит индекс текущего элемента.
         *
         * @since 1.0.0-RC1
         */
        protected long index;

        /**
         * Создаёт экземпляр на основе {@code set}.
         *
         * @param set неизменяемое множество элементов на основе массива.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code set} не должно быть {@code null}.
         * @since 1.0.0-RC1
         */
        protected Iterator(final @NonNull ArrayImmutableSet<T> set) throws ValidationFault {
            this.set = Validator.nonNull(set, "The passed set");
            index = set.empty() ? -1 : 0;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> start() {
            if (index > 0) index = 0;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> previous() {
            if (before()) --index;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @see #after()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> next() {
            if (after()) ++index;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@code this}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> end() {
            if (index != -1) index = set.size() - 1;
            return this;
        }

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean first() {
            return index == 0;
        }

        /**
         * @return Если перед текущим элементом существует другой, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean before() {
            return index > 0;
        }

        /**
         * @return Если после текущего элемента существует другой, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean after() {
            return index < set.size() - 1;
        }

        /**
         * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean last() {
            return index == set.size() - 1;
        }

        /**
         * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public boolean current() {
            return index >= 0;
        }

        /**
         * @return Если этот итератор находится в определённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public boolean determined() {
            return index != -1;
        }

        /**
         * @return Если этот итератор находится в неопределённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @implSpec Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public boolean undetermined() {
            return index == -1;
        }

        /**
         * {@inheritDoc}
         *
         * @return Текущий элемент или {@code null}.
         *
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public @Nullable T element() {
            if (!current()) return null;
            var t = 0L;
            for (var i = 0; i < set.elements.length; ++i)
                if (index >= t - 1 && index <= t + set.elements[i].length - 1)
                    return set.elements[i][(int) (index - t)];
            return null;
        }

    }

}
