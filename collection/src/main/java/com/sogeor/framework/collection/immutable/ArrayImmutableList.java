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
 * Представляет собой неизменяемый список элементов на основе  массива.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public class ArrayImmutableList<T> extends AbstractImmutableList<T> {

    /**
     * Содержит элементы в виде пустого  массива.
     *
     * @since 1.0.0-RC1
     */
    protected static final @Nullable Object @NonNull [] EMPTY_ELEMENTS = new Object[0];

    /**
     * Содержит элементы в виде  массива.
     *
     * @since 1.0.0-RC1
     */
    protected final @Nullable T @NonNull [] elements;

    /**
     * Создаёт экземпляр на основе {@code elements}.
     *
     * @param elements элементы в виде  массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public ArrayImmutableList(final @Nullable T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        this.elements = elements.length == 0 ? (T[]) EMPTY_ELEMENTS : elements;
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
    public <F extends Throwable> @NonNull ArrayImmutableList<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws ValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        for (final @Nullable T element : elements) consumer.consume(element);
        return this;
    }

    /**
     * @return Новый итератор элементов этого списка.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этого списка.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public @NonNull ArrayImmutableList.Iterator<T> iterator() {
        return new Iterator<>(this);
    }

    /**
     * Представляет собой итератор элементов неизменяемого списка.
     *
     * @param <T> тип элементов.
     *
     * @see ArrayImmutableList
     * @since 1.0.0-RC1
     */
    public static class Iterator<T> extends AbstractIterator<T> {

        /**
         * Содержит неизменяемый список элементов на основе  массива.
         *
         * @since 1.0.0-RC1
         */
        protected final @NonNull ArrayImmutableList<T> list;

        /**
         * Содержит индекс текущего элемента.
         *
         * @since 1.0.0-RC1
         */
        protected int index;

        /**
         * Создаёт экземпляр на основе {@code list}.
         *
         * @param list неизменяемый список элементов на основе  массива.
         *
         * @throws ValidationFault неудачная валидация.
         * @throws NullValidationFault {@code list} не должен быть {@code null}.
         * @since 1.0.0-RC1
         */
        protected Iterator(final @NonNull ArrayImmutableList<T> list) throws ValidationFault {
            this.list = Validator.nonNull(list, "The passed list");
            index = list.empty() ? -1 : 0;
        }

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
        public @NonNull Iterator<T> start() {
            return this;
        }

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
        public @NonNull Iterator<T> previous() {
            return this;
        }

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
        public @NonNull Iterator<T> next() {
            return this;
        }

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
        public @NonNull Iterator<T> end() {
            return this;
        }

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean first() {
            return false;
        }

        /**
         * @return Если перед текущим элементом существует другой, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean before() {
            return false;
        }

        /**
         * @return Если после текущего элемента существует другой, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean after() {
            return false;
        }

        /**
         * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean last() {
            return false;
        }

        /**
         * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean current() {
            return determined();
        }

        /**
         * @return Если этот итератор находится в определённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean determined() {
            return index >= 0;
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
            return current() ? list.elements[index] : null;
        }

        /**
         * {@inheritDoc}
         *
         * @param index индекс элемента.
         *
         * @return {@code this}.
         *
         * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(1)}.
         * @see #exists(long)
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("value -> this")
        public @NonNull Iterator<T> move(final long index) {
            if (exists(index)) this.index = (int) index;
            return this;
        }

        /**
         * @return Если {@code current()}, то индекс текущего элемента, иначе {@code -1}.
         *
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        @Override
        public long index() {
            return index;
        }

    }

}
