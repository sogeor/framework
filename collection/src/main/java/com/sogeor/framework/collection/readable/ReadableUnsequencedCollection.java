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
import com.sogeor.framework.collection.UnsequencedCollection;

/**
 * Представляет собой читаемую неупорядоченную коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface ReadableUnsequencedCollection<T> extends ReadableCollection<T>, UnsequencedCollection<T> {

    /**
     * @return Новый итератор элементов этой коллекции.
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
     * @implNote Требуемая стандартная реализация обладает оценкой временной сложности {@code O(n²)}.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    boolean equals(final @Nullable Object object);

    /**
     * Представляет собой итератор элементов читаемой неупорядоченной коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see ReadableUnsequencedCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends ReadableCollection.Iterator<T>, UnsequencedCollection.Iterator<T> {}

}
