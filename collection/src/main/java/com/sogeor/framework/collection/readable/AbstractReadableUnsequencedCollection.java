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
import com.sogeor.framework.collection.AbstractUnsequencedCollection;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;

/**
 * Представляет собой абстрактную читаемую неупорядоченную коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractReadableUnsequencedCollection<T> extends AbstractUnsequencedCollection<T> implements ReadableUnsequencedCollection<T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractReadableUnsequencedCollection() {}

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
    public <F extends Throwable> @NonNull AbstractReadableUnsequencedCollection<T> iterate(
            final @NonNull Consumer<? super T, F> consumer) throws ValidationFault, F {
        ReadableUnsequencedCollection.super.iterate(consumer);
        return this;
    }

    /**
     * @return Абстрактный итератор элементов этой абстрактной читаемой неупорядоченной коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

    /**
     * Представляет собой абстрактный итератор элементов читаемой неупорядоченной коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see AbstractReadableUnsequencedCollection
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> extends AbstractUnsequencedCollection.AbstractIterator<T> implements ReadableUnsequencedCollection.Iterator<T> {

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
