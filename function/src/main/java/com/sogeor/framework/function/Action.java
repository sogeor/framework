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
import com.sogeor.framework.annotation.Experimental;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой действие.
 *
 * @param <F> тип программного дефекта, возникающего во время выполнения.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Action<F extends Throwable> {

    /**
     * Создаёт действие с пустым методом {@code perform()}.
     *
     * @param <F> тип программного дефекта, возникающего во время выполнения нового действия.
     *
     * @return Новое действие.
     *
     * @see #perform()
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <F extends Throwable> @NonNull Action<F> empty() {
        return () -> {};
    }

    /**
     * Возвращает {@code action}.
     *
     * @param action действие.
     * @param <F> тип программного дефекта, возникающего во время выполнения {@code action}.
     *
     * @return {@code action}.
     *
     * @apiNote Предназначен для удобного создания {@code action} на основе лямбда-выражения.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull Action<F> of(final @NonNull Action<F> action) {
        return action;
    }

    /**
     * Выполняет это действие.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws F неудачное выполнение этого действия.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    void perform() throws ValidationFault, F;

    /**
     * Создаёт действие со следующей реализацией метода {@code perform()}:
     * <pre>
     * {@code
     * perform();
     * action.perform();
     * }
     * </pre>
     *
     * @param action действие.
     *
     * @return Новое действие.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code action} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> new; null -> fault")
    default @NonNull Action<F> and(final @NonNull Action<? extends F> action) throws ValidationFault {
        Validator.nonNull(action, "The passed action");
        return () -> {
            perform();
            action.perform();
        };
    }

    /**
     * Создаёт действие со следующей реализацией метода {@code perform()}:
     * <pre>
     * {@code
     * try {
     *     perform();
     * } catch (final @NonNull Throwable primary) {
     *     try {
     *         action.perform();
     *     } catch (final @NonNull Throwable secondary) {
     *         primary.addSuppressed(secondary);
     *         throw primary;
     *     }
     * }
     * }
     * </pre>
     *
     * @param action действие.
     *
     * @return Новое действие.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code action} не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> new; null -> fault")
    default @NonNull Action<F> or(final @NonNull Action<?> action) throws ValidationFault {
        Validator.nonNull(action, "The passed action");
        return () -> {
            try {
                perform();
            } catch (final @NonNull Throwable primary) {
                try {
                    action.perform();
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
