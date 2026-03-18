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

package com.sogeor.framework.collection;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;

/**
 * Представляет собой абстрактную очередь элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractQueue<T> extends AbstractSequencedIterableCollection<T> implements Queue<T> {

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractQueue() {}

    /**
     * {@inheritDoc}
     *
     * @return Новый итератор этой коллекции в неопределённом состоянии.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractQueue<T> clone();

    /**
     * Представляет собой абстрактный итератор абстрактной очереди.
     *
     * @param <T> тип элементов.
     *
     * @see AbstractQueue
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> extends
                                                     AbstractSequencedIterableCollection.AbstractIterator<T> implements
                                                                                                             Queue.Iterator<T> {

        /**
         * Создаёт экземпляр по умолчанию.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractIterator() {}

    }

}
