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
 * Представляет собой условное выражение.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачном вычислении.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Condition<F extends Throwable> {

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> value;
     * }
     * </pre>
     *
     * @param value результат вычисления нового условного выражения.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном вычислении нового условного
     * выражения.
     *
     * @return Новое условное выражение.
     *
     * @see #compute()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull Condition<F> direct(final boolean value) {
        return () -> value;
    }

    /**
     * Возвращает {@code condition}.
     *
     * @param condition условное выражение.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном вычислении {@code condition}.
     *
     * @return {@code condition}.
     *
     * @apiNote Предназначен для удобного создания {@code condition} на основе лямбда-выражения.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull Condition<F> of(final @NonNull Condition<F> condition) {
        return condition;
    }

    /**
     * Вычисляет это условное выражение и возвращает результат его вычисления.
     *
     * @return Результат вычисления этого условного выражения.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws F неудачное вычисление этого условного выражения.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    boolean compute() throws ValidationFault, F;

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> !compute();
     * }
     * </pre>
     *
     * @return Новое условное выражение.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull Condition<F> not() {
        return () -> !compute();
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> compute() && condition.compute();
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> and(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> compute() && condition.compute();
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> !(compute() && condition.compute());
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> nand(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> !(compute() && condition.compute());
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> compute() || condition.compute();
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("this -> this; !null -> new; null -> fault")
    default @NonNull Condition<F> or(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> compute() || condition.compute();
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> !(compute() || condition.compute());
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> nor(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> !(compute() || condition.compute());
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> compute() == condition.compute();
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> xnor(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> compute() == condition.compute();
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> compute() ^ condition.compute();
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> xor(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> compute() ^ condition.compute();
    }

    /**
     * Создаёт условное выражение со следующей реализацией метода {@code compute()}:
     * <pre>
     * {@code
     * () -> !compute() || condition.compute();
     * }
     * </pre>
     *
     * @param condition условное выражение.
     *
     * @return Новое условное выражение.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> imply(final @NonNull Condition<? extends F> condition) throws ValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return () -> !compute() || condition.compute();
    }

}
