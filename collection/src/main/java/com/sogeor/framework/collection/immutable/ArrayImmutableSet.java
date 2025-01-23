
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
import com.sogeor.framework.function.Predicate;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой неизменяемое множество элементов на основе массива.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public class ArrayImmutableSet<T> extends AbstractImmutableSet<T> {

    /**
     * Содержит элементы.
     *
     * @since 1.0.0-RC1
     */
    protected final @Nullable T @NonNull [] elements;

    /**
     * Создаёт экземпляр на основе {@code elements}.
     *
     * @param elements элементы.
     *
     * @since 1.0.0-RC1
     */
    @SafeVarargs
    public ArrayImmutableSet(final @Nullable T @NonNull ... elements) {
        this.elements = elements;
    }

    /**
     * {@inheritDoc}
     *
     * @param consumer потребитель элементов.
     *
     * @return {@code this}.
     *
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @throws F неудачное потребление элемента с помощью {@code consumer}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull ArrayImmutableSet<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws NullValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        for (final @Nullable var element : elements) consumer.consume(element);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param predicate предикат элементов.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> value; null -> fault")
    public <F extends Throwable> boolean all(final @NonNull Predicate<? super T, F> predicate) throws
                                                                                               NullValidationFault, F {
        Validator.nonNull(predicate, "The passed predicate");
        for (final @Nullable var element : elements) if (!predicate.evaluate(element)) return false;
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param predicate предикат элементов.
     *
     * @return {@code true} или {@code false}.
     *
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> value; null -> fault")
    public <F extends Throwable> boolean any(final @NonNull Predicate<? super T, F> predicate) throws
                                                                                               NullValidationFault, F {
        Validator.nonNull(predicate, "The passed predicate");
        for (final @Nullable var element : elements) if (predicate.evaluate(element)) return true;
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param value элемент.
     *
     * @return Если {@code value} один из {2}, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    public boolean contains(final @Nullable T value) {
        for (final @Nullable var element : elements) if (value == element) return true;
        return false;
    }

    /**
     * @return Итератор элементов.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public @NonNull Iterator<T> iterator() {
        return new Iterator<>(this);
    }

    /**
     * @return Размер коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public long size() {
        return elements.length;
    }

    /**
     * Представляет собой итератор элементов неизменяемого множества на основе массива.
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
     * @see ArrayImmutableSet
     * @since 1.0.0-RC1
     */
    public static class Iterator<T> extends AbstractIterator<T> {

        /**
         * Содержит неизменяемое множество элементов на основе массива.
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
         * @since 1.0.0-RC1
         */
        protected Iterator(final @NonNull ArrayImmutableSet<T> set) {
            this.set = set;
        }

        /**
         * Если {@linkplain #first() текущий элемент не первый}, то переходит к первому элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #end()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> start() {
            index = 0;
            return this;
        }

        /**
         * Если {@linkplain #before() перед текущим элементом существует другой}, то переходит к нему.
         *
         * @return {@code this}.
         *
         * @see #previous()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> previous() {
            if (before()) --index;
            return this;
        }

        /**
         * Если {@linkplain #after() после текущего элемента существует другой}, то переходит к нему.
         *
         * @return {@code this}.
         *
         * @see #previous()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> next() {
            if (after()) ++index;
            return this;
        }

        /**
         * Если {@linkplain #last() текущий элемент не последний}, то переходит к последнему элементу, если он
         * существует.
         *
         * @return {@code this}.
         *
         * @see #start()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> end() {
            index = set.elements.length - 1;
            return this;
        }

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
         *
         * @see #last()
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
         * @see #after()
         * @see #current()
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
         * @see #before()
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean after() {
            return index < set.elements.length - 1;
        }

        /**
         * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean last() {
            return index == set.elements.length - 1;
        }

        /**
         * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
         *
         * @see #after()
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> true")
        public boolean current() {
            return true;
        }

        /**
         * @return Если итератор способен переходить к первому элементу, то {@code true}, иначе {@code false}.
         *
         * @see #canPrevious()
         * @see #canNext()
         * @see #canEnd()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> true")
        public boolean canStart() {
            return true;
        }

        /**
         * @return Если итератор способен переходить к элементу, расположенному перед текущим, то {@code true}, иначе
         * {@code false}.
         *
         * @see #canStart()
         * @see #canNext()
         * @see #canEnd()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> true")
        public boolean canPrevious() {
            return true;
        }

        /**
         * @return Если итератор способен переходить к элементу, расположенному после текущего, то {@code true}, иначе
         * {@code false}.
         *
         * @see #canStart()
         * @see #canPrevious()
         * @see #canEnd()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> true")
        public boolean canNext() {
            return true;
        }

        /**
         * @return Если итератор способен переходить к последнему элементу, то {@code true}, иначе {@code false}.
         *
         * @see #canStart()
         * @see #canPrevious()
         * @see #canNext()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> true")
        public boolean canEnd() {
            return true;
        }

        /**
         * @return Если {@linkplain #current()}, то текущий элемент, иначе {@code null}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public @Nullable T element() {
            return index < set.elements.length ? set.elements[(int) index] : null;
        }

    }

}
