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

package com.sogeor.framework.collection.readable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.collection.AbstractCollection;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;

/**
 * Представляет собой абстрактную читаемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractReadableCollection<T> extends AbstractCollection<T> implements ReadableCollection<T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractReadableCollection() {}

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
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull AbstractReadableCollection<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws ValidationFault, F {
        ReadableCollection.super.iterate(consumer);
        return this;
    }

    /**
     * @return Абстрактный итератор элементов этой абстрактной читаемой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

    /**
     * {@inheritDoc}
     *
     * @return Строковое представление этой читаемой коллекции.
     *
     * @implNote Стандартная реализация представляет эту коллекцию и все её элементы в виде строки, указывая строковое
     * представление каждого из них.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public String toString() {
        final @NonNull var result = new StringBuilder(getClass().getSimpleName()).append('@')
                                                                                 .append(Integer.toHexString(
                                                                                         hashCode()))
                                                                                 .append('{');
        final @NonNull var it = iterator();
        if (it.canNext()) {
            for (it.start(); it.after(); it.next()) result.append(it.current()).append(", ");
            result.deleteCharAt(result.length() - 1).setCharAt(result.length() - 1, '}');
        } else {
            final int index = result.length();
            for (it.end(); it.before(); it.previous()) result.insert(index, it.current()).insert(index, ", ");
            result.delete(index, index + ", ".length()).append('}');
        }
        return result.toString();
    }

    /**
     * Представляет собой абстрактный итератор элементов читаемой коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see AbstractReadableCollection
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> extends AbstractCollection.AbstractIterator<T> implements ReadableCollection.Iterator<T> {

        /**
         * Создаёт экземпляр.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractIterator() {}

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
        public abstract @NonNull AbstractIterator<T> start();

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
        public abstract @NonNull AbstractIterator<T> previous();

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
        public abstract @NonNull AbstractIterator<T> next();

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
        public abstract @NonNull AbstractIterator<T> end();

    }

}
