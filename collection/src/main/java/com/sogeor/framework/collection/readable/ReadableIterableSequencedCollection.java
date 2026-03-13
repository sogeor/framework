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
import com.sogeor.framework.collection.IterableSequencedCollection;
import com.sogeor.framework.function.IntHandler;
import com.sogeor.framework.validation.LessValidationFault;
import com.sogeor.framework.validation.MoreValidationFault;
import com.sogeor.framework.validation.NotInsideValidationFault;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.OutsideValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой читаемую итерируемую упорядоченную коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface ReadableIterableSequencedCollection<T> extends ReadableIterableCollection<T>,
                                                                ReadableSequencedCollection,
                                                                IterableSequencedCollection {

    /**
     * Создаёт массив элементов, помещает в него элементы этой коллекции с {@code from} по {@code to} включительно и
     * возвращает его.
     *
     * @param from индекс начального элемента.
     * @param to индекс конечного элемента.
     *
     * @return Элементы этой коллекции с {@code from} по {@code to} в виде массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NotInsideValidationFault {@code from} или {@code to} должен быть больше -1 и меньше {@code size()}.
     * @throws MoreValidationFault {@code from} должен быть меньше или равен {@code to}.
     * @throws OutsideValidationFault {@code to - from} не должно быть больше 2147483647.
     * @implNote Возвращаемый массив может содержать не более 2147483647 элемента этой коллекции.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("ConstantValue")
    @Contract("?, ? -> ?; ?, ? -> fault")
    default @NonNull Object @NonNull [] array(final long from, final long to) throws ValidationFault {
        final var size = size();
        Validator.inside(from, -1, size, "The passed from index", "0", "The size of this collection");
        Validator.inside(to, -1, size, "The passed to index", "0", "The size of this collection");
        Validator.notMore(from, to, "The passed from index", "The passed to index");
        final var array = new Object[(int) Validator.notOutside(to - from, 0, Integer.MAX_VALUE,
                                                                "The difference between the passed from and to indexes",
                                                                "0", "max value of the int type")];
        if (size == 0) return array;
        final @NonNull var it = iterator();
        Validator.isTrue(it.move(from));
        for (var i = 0; i < array.length && Validator.isTrue(it.next()); ++i) array[i] = it.element();
        return array;
    }

    /**
     * Создаёт массив элементов с помощью {@code allocator}, помещает в него элементы этой коллекции с {@code from} по
     * {@code to} включительно и возвращает его.
     *
     * @param from индекс начального элемента.
     * @param to индекс конечного элемента.
     * @param allocator функция создания массива элементов.
     *
     * @return Элементы этой коллекции с {@code from} по {@code to} в виде массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NotInsideValidationFault {@code from} или {@code to} должен быть больше -1 и меньше {@code size()}.
     * @throws MoreValidationFault {@code from} должен быть меньше или равен {@code to}.
     * @throws OutsideValidationFault {@code to - from} не должно быть больше 2147483647.
     * @throws ValidationFault неудачная валидация.
     * @throws OutsideValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @throws NullValidationFault {@code allocator} не должна быть {@code null}.
     * @throws LessValidationFault длина созданного с помощью {@code allocator} массива элементов не должна быть меньше
     * {@code size()}.
     * @implNote Возвращаемый массив может содержать не более 2147483647 элемента этой коллекции.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("ConstantValue")
    @Contract("?, ?, !null -> ?; ?, ?, null -> fault")
    default <F extends Throwable> @NonNull T @NonNull [] array(final long from, final long to,
                                                               final @NonNull IntHandler<T[], F> allocator) throws
                                                                                                            ValidationFault,
                                                                                                            F {

        final var size = size();
        Validator.inside(from, -1, size, "The passed from index", "0", "The size of this collection");
        Validator.inside(to, -1, size, "The passed to index", "0", "The size of this collection");
        Validator.notMore(from, to, "The passed from index", "The passed to index");
        final var length = (int) (int) Validator.notOutside(to - from, 0, Integer.MAX_VALUE,
                                                            "The difference between the passed from and to indexes",
                                                            "0", "max value of the int type");
        Validator.nonNull(allocator, "The passed allocator");
        final var array = allocator.handle(length);
        Validator.notLess(array.length, size, "A length of an array allocated by the passed allocator",
                          "The size of this collection");
        if (size == 0) return array;
        final @NonNull var it = iterator();
        Validator.isTrue(it.move(from));
        for (var i = 0; i < array.length && Validator.isTrue(it.next()); ++i) array[i] = it.element();
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
    ReadableIterableSequencedCollection<T> clone();

    /**
     * Представляет собой итератор элементов читаемой итерируемой упорядоченной коллекции.
     *
     * @param <T> тип элементов.
     *
     * @see ReadableIterableSequencedCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> extends ReadableIterableCollection.Iterator<T>, IterableSequencedCollection.Iterator {}

}
