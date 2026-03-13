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
import com.sogeor.framework.collection.IterableCollection;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.function.IntHandler;
import com.sogeor.framework.function.Predicate;
import com.sogeor.framework.validation.LessValidationFault;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;

/**
 * Представляет собой читаемую итерируемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface ReadableIterableCollection<T> extends ReadableCollection, IterableCollection {

    /**
     * Если {@code empty()}, то возвращает {@code false}, иначе потребляет каждый из элементов этой коллекции с помощью
     * {@code consumer}, пока не возникнет программный сбой или неисправность. Если удалось, то возвращает
     * {@code true}.
     *
     * @param consumer потребитель элементов.
     *
     * @return {@code !empty()}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @throws F неудачное потребление элемента с помощью {@code consumer}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> boolean iterate(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                  ValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        if (empty()) return false;
        final @NonNull var it = iterator();
        while (it.next()) consumer.consume(it.element());
        return true;
    }

    /**
     * Если {@code empty()}, то возвращает {@code true}, иначе оценивает каждый из элементов этой коллекции с помощью
     * {@code predicate}, пока не возникнет программный сбой или неисправность и все оценки равны {@code true}. Если
     * хотя бы одна оценка равна {@code false}, то возвращает {@code false}, иначе — {@code true}.
     *
     * @param predicate предикат элементов.
     *
     * @return Если {@code empty()}, то {@code true}, иначе {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> boolean all(final @NonNull Predicate<? super T, F> predicate) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(predicate, "The passed predicate");
        if (empty()) return true;
        final @NonNull var it = iterator();
        while (it.next()) if (!predicate.evaluate(it.element())) return false;
        return true;
    }

    /**
     * Если {@code empty()}, то возвращает {@code false}, иначе оценивает каждый из элементов этой коллекции с помощью
     * {@code predicate}, пока не возникнет программный сбой или неисправность и все оценки равны {@code false}. Если
     * хотя бы одна оценка равна {@code true}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param predicate предикат элементов.
     *
     * @return Если {@code empty()}, то {@code false}, иначе {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> boolean any(final @NonNull Predicate<? super T, F> predicate) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(predicate, "The passed predicate");
        if (empty()) return false;
        final @NonNull var it = iterator();
        while (it.next()) if (predicate.evaluate(it.element())) return true;
        return false;
    }

    /**
     * @param element элемент.
     *
     * @return Если {@code element} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code element} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> value; null -> ?")
    default boolean contains(final T element) throws ValidationFault {
        if (!nullable()) Validator.nonNull(element, "The passed element");
        return !empty() && any(e -> Objects.equals(e, element));
    }

    /**
     * @param elements элементы.
     *
     * @return Если каждый из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("!null -> ?; null -> fault")
    default boolean containsAll(final T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        if (elements.length > size()) return false;
        for (final var element : elements) if (!contains(element)) return false;
        return true;
    }

    /**
     * @param elements элементы.
     *
     * @return Если каждый из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default boolean containsAll(final @NonNull ReadableIterableCollection<T> elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements == this || elements.size() <= size() && elements.all(this::contains);
    }

    /**
     * @param elements элементы.
     *
     * @return Если любой из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("!null -> ?; null -> fault")
    default boolean containsAny(final T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        if (empty()) return false;
        for (final var element : elements) if (contains(element)) return true;
        return true;
    }

    /**
     * @param elements элементы.
     *
     * @return Если любой из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default boolean containsAny(final @NonNull ReadableIterableCollection<T> elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements == this || !empty() && elements.any(this::contains);
    }

    /**
     * Создаёт массив элементов, помещает в него элементы этой коллекции и возвращает его.
     *
     * @return Элементы этой коллекции в виде массива.
     *
     * @implNote Если {@code sequenced()}, то в возвращаемый массив помещаются элементы этой коллекции, начиная с
     * первого, иначе выбор элементов может быть случайным. Также возвращаемый массив может содержать не более
     * 2147483647 элемента этой коллекции.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("ConstantValue")
    @Contract("-> value")
    default Object @NonNull [] array() {
        final var array = new Object[(int) size()];
        if (array.length == 0) return array;
        final @NonNull var it = iterator();
        for (var i = 0; i < array.length && Validator.isTrue(it.next()); ++i) array[i] = it.element();
        return array;
    }

    /**
     * Создаёт массив элементов с помощью {@code allocator}, помещает в него элементы этой коллекции и возвращает его.
     *
     * @param allocator функция создания массива элементов.
     *
     * @return Элементы этой коллекции в виде массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code allocator} или созданный ей массив элементов не должны быть {@code null}.
     * @throws LessValidationFault длина созданного с помощью {@code allocator} массива элементов не должна быть меньше
     * {@code size()}.
     * @implNote Если {@code sequenced()}, то в возвращаемый массив помещаются элементы этой коллекции, начиная с
     * первого, иначе выбор элементов может быть случайным. Также возвращаемый массив может содержать не более
     * 2147483647 элемента этой коллекции.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("ConstantValue")
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> T @NonNull [] array(final @NonNull IntHandler<T[], F> allocator) throws
                                                                                                   ValidationFault, F {
        Validator.nonNull(allocator, "The passed allocator");
        final var size = (int) size();
        final var array = Validator.nonNull(allocator.handle(size), "An array allocated by the passed allocator");
        Validator.notLess(array.length, size, "A length of an array allocated by the passed allocator",
                          "The size of this collection");
        if (size == 0) return array;
        final @NonNull var it = iterator();
        for (var i = 0; i < size && Validator.isTrue(it.next()); ++i) array[i] = it.element();
        return array;
    }

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
    Iterator<T> iterator();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    ReadableIterableCollection<T> clone();

    /**
     * Если {@code !empty()}, то вычисляет хеш-код этой коллекции на основе её элементов и возвращает его, иначе если
     * {@code sequenced()}, то возвращает {@code 1}, иначе — {@code 0}.
     *
     * @return Хеш-код этой коллекции.
     *
     * @implSpec Если {@code sequenced()}, то при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * var result = 1;
     * for (final @NonNull var it = iterator(); it.next(); ) {
     *     result = 31 * result + Objects.hashCode(it.current());
     * }
     * return result;
     * }
     * </pre>
     * В противном случае при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * var result = 0;
     * for (final @NonNull var it = iterator(); it.next(); ) {
     *     result += Objects.hashCode(it.current());
     * }
     * return result;
     * }
     * </pre>
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
     * @return Если {@code object} эквивалентен этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @implSpec Если {@code sequenced()}, то при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * if (object == this) return true;
     * if (!(object instanceof final @NonNull ReadableIterableCollection<?> that) || size() != that.size()) return false;
     * if (!that.sequenced()) return containsAll((ReadableIterableCollection<T>) that);
     * final @NonNull var it = iterator();
     * final @NonNull var it_ = that.iterator();
     * for (; it.next() && it_.next(); ) {
     *     if (!Objects.equals(it.element(), it_.element())) {
     *         return false;
     *     }
     * }
     * return true;
     * }
     * </pre>
     * В противном случае при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * return object == this || object instanceof final @NonNull ReadableIterableCollection<?> that && size() != that.size() && containsAll((ReadableIterableCollection<T>) that);
     * }
     * </pre>
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    boolean equals(final @Nullable Object object);

    /**
     * Представляет собой итератор элементов читаемой итерируемой коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see ReadableIterableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends IterableCollection.Iterator {

        /**
         * Если {@code current()}, то возвращает текущий элемент, иначе — {@code null}.
         *
         * @return Текущий элемент или {@code null}.
         *
         * @see #exists()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        @Nullable
        T element();

    }

}
