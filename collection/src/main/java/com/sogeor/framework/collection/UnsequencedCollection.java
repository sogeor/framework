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
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой неупорядоченную коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface UnsequencedCollection<T> extends Collection<T> {

    /**
     * @return Новый итератор элементов этой неупорядоченной коллекции.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этой коллекции.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * Если {@code empty()}, то возвращает {@code 0}, иначе вычисляет хеш-код этой коллекции на основе её элементов и
     * возвращает его.
     *
     * @return Хеш-код этой коллекции.
     *
     * @implSpec При переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * var result = 0;
     * for (final @NonNull var it = iterator(); it.after(); it.next()) {
     *     result += Objects.hashCode(it.current());
     * }
     * return result;
     * }
     * </pre>
     * @implNote Требуемая стандартная реализация обладает оценкой временной сложности {@code Θ(n)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * Если {@code this} эквивалентно {@code object}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @implSpec При переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * if (this == object) return true;
     * if (!(object instanceof ReadableUnsequencedCollection<?> that) || size() != that.size()) return false;
     *
     * // Элементы этой и переданной коллекций эквивалентны, но вряд ли располагаются в одном и том же порядке.
     * return contains(that);
     * }
     * </pre>
     * @implNote Требуемая стандартная реализация обладает оценкой временной сложности {@code O(n²)}.
     * <p>
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    boolean equals(final @Nullable Object object);

    /**
     * Представляет собой итератор элементов неупорядоченной коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see UnsequencedCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends Collection.Iterator<T> {

        /**
         * Если {@code !first()}, то переходит к первому элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> start();

        /**
         * Если {@code before()}, то переходит к элементу перед текущим.
         *
         * @return {@code this}.
         *
         * @see #before()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> previous();

        /**
         * Если {@code after()}, то переходит к элементу после текущего.
         *
         * @return {@code this}.
         *
         * @see #after()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> next();

        /**
         * Если {@code !last()}, то переходит к последнему элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> this")
        @NonNull
        Iterator<T> end();

    }

}
