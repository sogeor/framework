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
 * Представляет собой итерируемую упорядоченную коллекцию элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface IterableSequencedCollection<T> extends IterableCollection, SequencedCollection {

    /**
     * {@inheritDoc}
     *
     * @return Новый итератор этой коллекции в неопределённом состоянии.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    Iterator iterator();

    /**
     * Представляет собой итератор итерируемой упорядоченной коллекции.
     *
     * @see IterableSequencedCollection
     * @since 1.0.0-RC1
     */
    interface Iterator extends IterableCollection.Iterator {

        /**
         * Если {@code index == index()}, то есть этот итератор имеет текущий элемент, который есть в коллекции этого
         * итератора и доступен по {@code index}, то возвращает {@code true}.
         * <p>
         * Если {@code exists(index)}, то пытается перейти к элементу коллекции этого итератора по {@code index} и, если
         * удалось, возвращает {@code true}, иначе — {@code false}.
         *
         * @param index индекс.
         *
         * @return Если этот итератор имеет текущий элемент, который есть в коллекции этого итератора и доступен по
         * {@code index}, или удалось перейти к какому-то элементу коллекции этого итератора, то {@code true}, иначе
         * {@code false}.
         *
         * @see #exists(long)
         * @see #index()
         * @since 1.0.0-RC1
         */
        @Contract("? -> value")
        boolean move(final long index);

        /**
         * @param index индекс.
         *
         * @return Если в коллекции этого итератора есть элемент по {@code index}, то есть этот итератор может перейти к
         * нему, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean exists(final long index);

        /**
         * @return Если этот итератор имеет текущий элемент, который есть в коллекции этого итератора, то его индекс,
         * иначе {@code -1}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        long index();

    }

}
