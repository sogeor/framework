
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
 * Представляет собой пустое неизменяемое множество элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public class EmptyImmutableSet<T> {

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
     * @return Новый итератор элементов этого абстрактного неизменяемого множества.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public @NonNull Iterator<T> iterator() {
        return new Iterator<>();
    }

    /**
     * @return {@code 0}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public long size() {
        return 0;
    }

    /**
     * @return {@code true}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> true")
    public boolean empty() {
        return true;
    }

    /**
     * Представляет собой итератор элементов пустого неизменяемого множества.
     *
     * @param <T> тип элементов.
     *
     * @see EmptyImmutableSet
     * @since 1.0.0-RC1
     */
    public static class Iterator<T> {

        /**
         * Создаёт экземпляр.
         *
         * @since 1.0.0-RC1
         */
        public Iterator() {}

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
         * @see #exists()
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
         * @see #exists()
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
        public boolean exists() {
            return false;
        }

        /**
         * @return {@code true}.
         *
         * @implSpec Если
         * {@linkplain #canNext() итератор способен переходить к элементу, расположенному после текущего}, то возвращает
         * {@code true}.
         * @see #reversible()
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
        public boolean reversible() {
            return true;
        }

        /**
         * @return {@code true}.
         *
         * @see #canStart()
         * @see #reversible()
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
         * @see #reversible()
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
