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
 * Представляет собой предикат значений типа {@code double}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code double}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface DoublePredicate<F extends Throwable> {

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * ignored -> value;
     * }
     * </pre>
     *
     * @param value оценка значения типа {@code double}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code double}
     * новым предикатом.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull DoublePredicate<F> direct(final boolean value) {
        return ignored -> value;
    }

    /**
     * Возвращает {@code predicate}.
     *
     * @param predicate предикат значений типа {@code double}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code double}
     * {@code predicate}.
     *
     * @return {@code predicate}.
     *
     * @apiNote Предназначен для удобного создания {@code predicate} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull DoublePredicate<F> of(final @NonNull DoublePredicate<F> predicate) {
        return predicate;
    }

    /**
     * Оценивает {@code value} с помощью этого предиката и возвращает его оценку.
     *
     * @param value значение типа {@code double}, оцениваемое этим обработчиком.
     *
     * @return Оценка {@code value}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code value}.
     * @throws F неудачная обработка {@code value}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    boolean evaluate(final double value) throws ValidationFault, F;

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> !evaluate(value);
     * }
     * </pre>
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull DoublePredicate<F> not() {
        return value -> !evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> evaluate(value) && predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> and(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                   ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) && predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> !(evaluate(value) && predicate.evaluate(value));
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> nand(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                    ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !(evaluate(value) && predicate.evaluate(value));
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> evaluate(value) || predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> or(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                  ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) || predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> !(evaluate(value) || predicate.evaluate(value));
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> nor(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                   ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !(evaluate(value) || predicate.evaluate(value));
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> evaluate(value) == predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> xnor(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                    ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) == predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> evaluate(value) ^ predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> xor(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                   ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) ^ predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code double} со следующей реализацией метода {@code evaluate(double)}:
     * <pre>
     * {@code
     * value -> !evaluate(value) || predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code double}.
     *
     * @return Новый предикат значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoublePredicate<F> imply(final @NonNull DoublePredicate<? extends F> predicate) throws
                                                                                                     ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !evaluate(value) || predicate.evaluate(value);
    }

}
