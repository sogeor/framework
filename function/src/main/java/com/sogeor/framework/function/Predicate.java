/*
 * Copyright 2024 Sogeor
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

package com.sogeor.framework.function;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой предикат объектов.
 *
 * @param <T> тип объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Predicate<T, F extends Throwable> {

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, возвращающим {@code value}.
     *
     * @param value оценка объекта.
     * @param <T> тип объектов, оцениваемых новым предикатом.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке объектов новым предикатом.
     *
     * @return Новый предикат объектов.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <T, F extends Throwable> @NonNull Predicate<T, F> direct(final boolean value) {
        return ignored -> value;
    }

    /**
     * Возвращает {@code predicate}.
     *
     * @param predicate предикат объектов.
     * @param <T> тип объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке объектов.
     *
     * @return {@code predicate}.
     *
     * @apiNote Предназначен для удобного создания {@code predicate} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, F extends Throwable> @NonNull Predicate<T, F> of(final @NonNull Predicate<T, F> predicate) {
        return predicate;
    }

    /**
     * Оценивает {@code object} и возвращает его оценку.
     *
     * @param object объект.
     *
     * @return Оценка {@code object}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code object}.
     * @throws F неудачная обработка {@code object}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    boolean evaluate(final @Nullable T object) throws ValidationFault, F;

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от метода
     * {@linkplain #evaluate(Object) this.evaluate(Object)} оценку и возвращающим её инверсию.
     *
     * @return Новый предикат объектов.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull Predicate<T, F> not() {
        return object -> !evaluate(object);
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их конъюнкцию.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> and(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                     ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> evaluate(object) && predicate.evaluate(object);
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их штрих Шеффера.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> nand(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                      ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> !(evaluate(object) && predicate.evaluate(object));
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их мягкую дизъюнкцию.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> or(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                    ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> evaluate(object) || predicate.evaluate(object);
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их стрелку Пирса.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> nor(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                     ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> !(evaluate(object) || predicate.evaluate(object));
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их эквивалентность.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> xnor(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                      ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> evaluate(object) == predicate.evaluate(object);
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их строгую дизъюнкцию.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> xor(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                     ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> evaluate(object) ^ predicate.evaluate(object);
    }

    /**
     * Создаёт предикат объектов с методом {@linkplain #evaluate(Object)}, получающим от методов
     * {@linkplain #evaluate(Object) this.evaluate(Object)} и {@linkplain #evaluate(Object) predicate.evaluate(Object)}
     * оценки и возвращающим их импликацию.
     *
     * @param predicate предикат объектов.
     *
     * @return Новый предикат объектов.
     *
     * @throws ValidationFault неудачная валидация {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Predicate<T, F> imply(final @NonNull Predicate<? super T, ? extends F> predicate) throws
                                                                                                       ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return object -> !evaluate(object) || predicate.evaluate(object);
    }

}
