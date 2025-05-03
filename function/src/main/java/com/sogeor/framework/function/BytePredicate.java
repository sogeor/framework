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
 * Представляет собой предикат значений типа {@code byte}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code byte}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface BytePredicate<F extends Throwable> {

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * ignored -> value;
     * }
     * </pre>
     *
     * @param value оценка значения типа {@code byte}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code byte}
     * новым предикатом.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull BytePredicate<F> direct(final boolean value) {
        return ignored -> value;
    }

    /**
     * Возвращает {@code predicate}.
     *
     * @param predicate предикат значений типа {@code byte}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке значений типа {@code byte}
     * {@code predicate}.
     *
     * @return {@code predicate}.
     *
     * @apiNote Предназначен для удобного создания {@code predicate} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull BytePredicate<F> of(final @NonNull BytePredicate<F> predicate) {
        return predicate;
    }

    /**
     * Оценивает {@code value} с помощью этого предиката и возвращает его оценку.
     *
     * @param value значение типа {@code byte}, оцениваемое этим обработчиком.
     *
     * @return Оценка {@code value}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code value}.
     * @throws F неудачная обработка {@code value}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    boolean evaluate(final byte value) throws ValidationFault, F;

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> !evaluate(value);
     * }
     * </pre>
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull BytePredicate<F> not() {
        return value -> !evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> evaluate(value) && predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> and(final @NonNull BytePredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) && predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> !(evaluate(value) && predicate.evaluate(value));
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> nand(final @NonNull BytePredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !(evaluate(value) && predicate.evaluate(value));
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> evaluate(value) || predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> or(final @NonNull BytePredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) || predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> !(evaluate(value) || predicate.evaluate(value));
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> nor(final @NonNull BytePredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !(evaluate(value) || predicate.evaluate(value));
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> evaluate(value) == predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> xnor(final @NonNull BytePredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) == predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> evaluate(value) ^ predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> xor(final @NonNull BytePredicate<? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> evaluate(value) ^ predicate.evaluate(value);
    }

    /**
     * Создаёт предикат значений типа {@code byte} со следующей реализацией метода {@code evaluate(byte)}:
     * <pre>
     * {@code
     * value -> !evaluate(value) || predicate.evaluate(value);
     * }
     * </pre>
     *
     * @param predicate предикат значений типа {@code byte}.
     *
     * @return Новый предикат значений типа {@code byte}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(byte)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BytePredicate<F> imply(final @NonNull BytePredicate<? extends F> predicate) throws
                                                                                                 ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return value -> !evaluate(value) || predicate.evaluate(value);
    }

}
