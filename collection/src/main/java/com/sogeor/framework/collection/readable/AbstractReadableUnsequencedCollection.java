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
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.AbstractUnsequencedCollection;

import java.util.Objects;

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
     * @return Новый итератор элементов этой коллекции.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этой коллекции.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

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
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code Θ(n)}.
     * @see Objects#hashCode(Object)
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public int hashCode() {
        var result = 0;
        for (final @NonNull var it = iterator(); it.after(); it.next()) result += Objects.hashCode(it.current());
        return result;
    }

    /**
     * {@inheritDoc}
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
     * return contains((ReadableUnsequencedCollection<T>) that);
     * }
     * </pre>
     * @implNote Стандартная реализация обладает оценкой временной сложности {@code O(n²)}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Override
    @Contract("? -> value")
    public boolean equals(final @Nullable Object object) {
        if (this == object) return true;
        if (!(object instanceof ReadableUnsequencedCollection<?> that) || size() != that.size()) return false;
        return contains((ReadableUnsequencedCollection<T>) that);
    }

    /**
     * Представляет собой абстрактный итератор элементов абстрактной читаемой неупорядоченной коллекции.
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

    }

}
