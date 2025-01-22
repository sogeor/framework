
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
import com.sogeor.framework.collection.readable.ReadableCollection;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.function.Predicate;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой неизменяемое множество без элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public class EmptyImmutableSet<T> extends AbstractImmutableSet<T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    public EmptyImmutableSet() {}

    /**
     * {@inheritDoc}
     *
     * @param consumer потребитель элементов.
     *
     * @return {@code this}.
     *
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull EmptyImmutableSet<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws NullValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param predicate предикат элементов.
     *
     * @return {@code false}.
     *
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> false; null -> fault")
    public <F extends Throwable> boolean all(final @NonNull Predicate<? super T, F> predicate) throws
                                                                                               NullValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param predicate предикат элементов.
     *
     * @return {@code false}.
     *
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> false; null -> fault")
    public <F extends Throwable> boolean any(final @NonNull Predicate<? super T, F> predicate) throws
                                                                                               NullValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param element элемент.
     *
     * @return {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> false")
    public boolean contains(final @Nullable T element) {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param elements элементы.
     *
     * @return {@code false}.
     *
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Override
    @SuppressWarnings("unchecked")
    @Contract("? -> false")
    public boolean contains(final @Nullable T @NonNull ... elements) throws NullValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements.length == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @param elements элементы.
     *
     * @return {@code false}.
     *
     * @throws NullValidationFault {@code elements} не должны быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    public boolean contains(final @NonNull ReadableCollection<T> elements) throws NullValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements == this || elements.empty();
    }

    /**
     * @return Итератор элементов.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public @NonNull Iterator<T> iterator() {
        return new Iterator<>();
    }

    /**
     * @return Размер коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public long size() {
        return 0;
    }

    /**
     * @return Если элементы не существуют, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> true")
    public boolean empty() {
        return true;
    }

    /**
     * Представляет собой итератор несуществующих элементов неизменяемого множества.
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
     * @see EmptyImmutableSet
     * @since 1.0.0-RC1
     */
    public static class Iterator<T> extends AbstractIterator<T> {

        /**
         * Создаёт экземпляр.
         *
         * @since 1.0.0-RC1
         */
        protected Iterator() {}

        /**
         * @return {@code this}.
         *
         * @see #end()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> start() {
            return this;
        }

        /**
         * @return {@code this}.
         *
         * @see #next()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> previous() {
            return this;
        }

        /**
         * @return {@code this}.
         *
         * @see #previous()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> next() {
            return this;
        }

        /**
         * @return {@code this}.
         *
         * @see #start()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        public @NonNull Iterator<T> end() {
            return this;
        }

        /**
         * @return {@code false}.
         *
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> false")
        public boolean first() {
            return false;
        }

        /**
         * @return {@code false}.
         *
         * @see #after()
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> false")
        public boolean before() {
            return false;
        }

        /**
         * @return {@code false}.
         *
         * @see #before()
         * @see #current()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> false")
        public boolean after() {
            return false;
        }

        /**
         * @return {@code false}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> false")
        public boolean last() {
            return false;
        }

        /**
         * @return {@code false}.
         *
         * @see #after()
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> false")
        public boolean current() {
            return false;
        }

        /**
         * @return {@code true}.
         *
         * @implSpec Если
         * {@linkplain #canNext() итератор способен переходить к элементу, расположенному после текущего}, то возвращает
         * {@code true}.
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
         * @return {@code true}.
         *
         * @implSpec Если
         * {@linkplain #canNext() итератор не способен переходить к элементу, расположенному после текущего}, то
         * возвращает {@code true}.
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
         * @return {@code true}.
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
         * @return {@code true}.
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
         * @return {@code null}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> null")
        public @Nullable T element() {
            return null;
        }

    }

}
