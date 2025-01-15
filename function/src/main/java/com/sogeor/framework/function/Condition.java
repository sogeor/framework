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
 * Представляет собой условие (1).
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачном вычислении [1].
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Condition<F extends Throwable> {

    /**
     * Создаёт условие (1) с методом {@linkplain #compute()}, возвращающим {@code value}.
     *
     * @param value результат вычисления.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном вычислении [1].
     *
     * @return [1].
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull Condition<F> direct(final boolean value) {
        return () -> value;
    }

    /**
     * Возвращает {@code condition}.
     *
     * @param condition условие.
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
     * Вычисляет {1} и возвращает результат (2) его вычисления.
     *
     * @return [2].
     *
     * @throws ValidationFault неудачная валидация.
     * @throws F неудачное вычисление {1}.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    boolean compute() throws ValidationFault, F;

    /**
     * Создаёт условие (1) с методом {@linkplain #compute()}, получающим от метода
     * {@linkplain #compute() this.compute()} результат (2) вычисления и возвращающим инверсию [2].
     *
     * @return [1].
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull Condition<F> not() {
        return () -> !compute();
    }

    /**
     * Если {@code this == condition}, то возвращает {@code this}, в противном случае создаёт условие (1) с методом
     * {@linkplain #compute()}, получающим от методов {@linkplain #compute() this.compute()} и
     * {@linkplain #compute() condition.compute()} результаты (2) вычислений и возвращающим конъюнкцию (2).
     *
     * @param condition условие.
     *
     * @return {@code this} или [1].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("this -> this; !null -> new; null -> fault")
    default @NonNull Condition<F> and(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? this : () -> compute() && condition.compute();
    }

    /**
     * Если {@code this == condition}, то создаёт условие (1) с методом {@linkplain #compute()}, получающим от метода
     * {@linkplain #compute() this.compute()} результат (2) вычисления и возвращающим инверсию [2], в противном случае
     * создаёт условие (3) с методом {@linkplain #compute()}, получающим от методов
     * {@linkplain #compute() this.compute()} и {@linkplain #compute() condition.compute()} результаты (4) вычислений и
     * возвращающим штрих Шеффера [4].
     *
     * @param condition условие.
     *
     * @return [1] или [3].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> nand(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? () -> !compute() : () -> !(compute() && condition.compute());
    }

    /**
     * Если {@code this == condition}, то возвращает {@code this}, в противном случае создаёт условие (1) с методом
     * {@linkplain #compute()}, получающим от методов {@linkplain #compute() this.compute()} и
     * {@linkplain #compute() condition.compute()} результаты (2) вычислений и возвращающим мягкую дизъюнкцию [2].
     *
     * @param condition условие.
     *
     * @return {@code this} или [1].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("this -> this; !null -> new; null -> fault")
    default @NonNull Condition<F> or(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? this : () -> compute() || condition.compute();
    }

    /**
     * Если {@code this == condition}, то создаёт условие (1) с методом {@linkplain #compute()}, получающим от метода
     * {@linkplain #compute() this.compute()} результат (2) вычисления и возвращающим инверсию [2], в противном случае
     * создаёт условие (3) с методом {@linkplain #compute()}, получающим от методов
     * {@linkplain #compute() this.compute()} и {@linkplain #compute() condition.compute()} результаты (4) вычислений и
     * возвращающим стрелку Пирса [4].
     *
     * @param condition условие.
     *
     * @return [1] или [3].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> nor(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? () -> !compute() : () -> !(compute() || condition.compute());
    }

    /**
     * Если {@code this == condition}, то создаёт условие (1) с методом {@linkplain #compute()}, возвращающим
     * {@code true}, в противном случае создаёт условие (2) с методом {@linkplain #compute()}, получающим от методов
     * {@linkplain #compute() this.compute()} и {@linkplain #compute() condition.compute()} результаты (3) вычислений и
     * возвращающим эквивалентность [3].
     *
     * @param condition условие.
     *
     * @return [1] или [2].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> xnor(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? () -> true : () -> compute() == condition.compute();
    }

    /**
     * Если {@code this == condition}, то создаёт условие (1) с методом {@linkplain #compute()}, возвращающим
     * {@code false}, в противном случае создаёт условие (2) с методом {@linkplain #compute()}, получающим от методов
     * {@linkplain #compute() this.compute()} и {@linkplain #compute() condition.compute()} результаты (3) вычислений и
     * возвращающим строгую дизъюнкцию [3].
     *
     * @param condition условие.
     *
     * @return [1] или [2].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> xor(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? () -> false : () -> compute() ^ condition.compute();
    }

    /**
     * Если {@code this == condition}, то создаёт условие (1) с методом {@linkplain #compute()}, возвращающим
     * {@code true}, в противном случае создаёт условие (2) с методом {@linkplain #compute()}, получающим от методов
     * {@linkplain #compute() this.compute()} и {@linkplain #compute() condition.compute()} результаты (3) вычислений и
     * возвращающим импликацию [3].
     *
     * @param condition условие.
     *
     * @return [1] или [2].
     *
     * @throws NullValidationFault {@code condition} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Condition<F> imply(final @NonNull Condition<? extends F> condition) throws NullValidationFault {
        Validator.nonNull(condition, "The passed condition");
        return this == condition ? () -> true : () -> !compute() || condition.compute();
    }

}
