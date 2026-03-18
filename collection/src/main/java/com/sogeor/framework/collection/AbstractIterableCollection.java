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

import java.util.Objects;

/**
 * Представляет собой абстрактную итерируемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractIterableCollection<T> extends AbstractCollection implements IterableCollection<T> {

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractIterableCollection() {}

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
    public abstract @NonNull AbstractIterableCollection<T> clone();

    /**
     * {@inheritDoc}
     *
     * @return Хеш-код этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public int hashCode() {
        if (sequenced()) {
            var result = 1;
            for (var it = iterator(); it.next(); ) {
                result = 31 * result + Objects.hashCode(it.element());
            }
            return result;
        }
        var result = 0;
        for (var it = iterator(); it.next(); ) {
            result += Objects.hashCode(it.element());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param object объект.
     *
     * @return Если {@code object} эквивалентен этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Override
    @Contract("!null -> value; null -> false")
    public boolean equals(final @Nullable Object object) {
        if (sequenced()) {
            if (object == this) return true;
            if (!(object instanceof IterableCollection<?> that) || size() != that.size()) return false;
            if (empty()) return true;
            if (!that.sequenced()) return containsAll((IterableCollection<T>) that);
            final @NonNull var it = iterator();
            final @NonNull var it_ = that.iterator();
            while (it.next() && it_.next()) {
                if (!Objects.equals(it.element(), it_.element())) {
                    return false;
                }
            }
            return true;
        }
        return object == this || object instanceof IterableCollection<?> that &&
                                 (empty() || size() != that.size() && containsAll((IterableCollection<T>) that));
    }

    /**
     * {@inheritDoc}
     *
     * @return Строка, представляющая элементы этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public @NonNull String toString() {
        final var builder = new StringBuilder().append('{');
        if (!empty()) for (final @NonNull var it = iterator(); it.next(); ) {
            builder.append(it.element());
            if (it.after()) builder.append(", ");
        }
        return builder.append('}').toString();
    }

    /**
     * Представляет собой абстрактный итератор абстрактной итерируемой коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see AbstractIterableCollection
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> implements Iterator<T> {

        /**
         * Создаёт экземпляр по умолчанию.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractIterator() {}

        /**
         * {@inheritDoc}
         *
         * @return Хеш-код на основе коллекции, состояния и текущего элемента этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public abstract int hashCode();

        /**
         * {@inheritDoc}
         *
         * @param object объект.
         *
         * @return Если {@code object} эквивалентен этому итератору, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("null -> false; !null -> value")
        public abstract boolean equals(final @Nullable Object object);

        /**
         * {@inheritDoc}
         *
         * @return Строка, представляющая состояние и текущий элемент этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public abstract @NonNull String toString();

    }

}
