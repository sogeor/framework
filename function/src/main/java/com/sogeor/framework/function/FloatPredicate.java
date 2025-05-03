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
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой предикат значений типа {@code float}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code float}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface FloatPredicate<F extends Throwable> {

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * ignored -> value;
     * }
     * </pre>
     *
     * @param value оценка значения типа {@code float}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code float}
     * новым предикатом.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull FloatPredicate<F> direct(final boolean value) {
        return ignored -> value;
    }

    /**
     * Возвращает {@code predicate}.
     *
     * @param predicate предикат значений типа {@code float}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code float}
     * {@code predicate}.
     *
     * @return {@code predicate}.
     *
     * @apiNote Предназначен для удобного создания {@code predicate} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull FloatPredicate<F> of(final @NonNull FloatPredicate<F> predicate) {
        return predicate;
    }

    /**
     * Оценивает {@code value} с помощью этого предиката и возвращает его оценку.
     *
     * @param value значение типа {@code float}, оцениваемое этим обработчиком.
     *
     * @return Оценка {@code value}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code value}.
     * @throws F неудачная обработка {@code value}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    boolean evaluate(final float value) throws ValidationFault, F;

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> !evaluate(value);
     * }
     * </pre>
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull FloatPredicate<F> not() {
        return value -> !evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> evaluate(value) && predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> and(final @NonNull FloatPredicate<? extends F> predicate) throws
                                                                                                 ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) && predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> !(evaluate(value) && predicate.evaluate(value));
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> nand(final @NonNull FloatPredicate<? extends F> predicate) throws
                                                                                                  ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !(evaluate(value) && predicate.evaluate(value));
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> evaluate(value) || predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> or(final @NonNull FloatPredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) || predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> !(evaluate(value) || predicate.evaluate(value));
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> nor(final @NonNull FloatPredicate<? extends F> predicate) throws
                                                                                                 ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !(evaluate(value) || predicate.evaluate(value));
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> evaluate(value) == predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> xnor(final @NonNull FloatPredicate<? extends F> predicate) throws
                                                                                                  ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) == predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> evaluate(value) ^ predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> xor(final @NonNull FloatPredicate<? extends F> predicate) throws
                                                                                                 ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) ^ predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code float} со следующей реализацией метода {@code evaluate(float)}:
     * <pre>
     * {@code
     * value -> !evaluate(value) || predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code float}.
     *
     * @return Новый предикат значений типа {@code float}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(float)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull FloatPredicate<F> imply(final @NonNull FloatPredicate<? extends F> predicate) throws
                                                                                                   ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !evaluate(value) || predicate.evaluate(value);
    }

}
