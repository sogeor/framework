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
 * Представляет собой обработчик значений типа {@code int}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате значений типа
 * {@code int}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface IntegerToIntegerHandler<F extends Throwable> {

    /**
     * Создаёт обработчик значений типа {@code int} со следующей реализацией метода {@code handle(int)}:
     * <pre>
     * {@code
     * value -> value;
     * }
     * </pre>
     *
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате значений
     * типа {@code int} новым обработчиком.
     *
     * @return Новый обработчик значений типа {@code int}
     *
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <F extends Throwable> @NonNull IntegerToIntegerHandler<F> empty() {
        return value -> value;
    }

    /**
     * Создаёт обработчик значений типа {@code int} со следующей реализацией метода {@code handle(int)}:
     * <pre>
     * {@code
     * ignored -> value;
     * }
     * </pre>
     *
     * @param value значение типа {@code int}, возвращаемое новым обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате значений
     * типа {@code int} новым обработчиком.
     *
     * @return Новый обработчик значений типа {@code int}
     *
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull IntegerToIntegerHandler<F> direct(final int value) {
        return ignored -> value;
    }

    /**
     * Возвращает {@code handler}.
     *
     * @param handler обработчик значений типа {@code int}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате значений
     * типа {@code int} {@code handler}.
     *
     * @return {@code handler}.
     *
     * @apiNote Предназначен для удобного создания {@code handler} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull IntegerToIntegerHandler<F> of(
            final @NonNull IntegerToIntegerHandler<F> handler) {
        return handler;
    }

    /**
     * Обрабатывает {@code value} с помощью этого обработчика и возвращает значение типа {@code int}.
     *
     * @param value значение типа {@code int}, обрабатываемое этим обработчиком.
     *
     * @return Возвращаемое значение типа {@code int}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code value} и/или возвращаемого значения типа
     * {@code int}.
     * @throws F неудачная обработка {@code value} или возврат значения типа {@code int}.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    int handle(final int value) throws ValidationFault, F;

    /**
     * Создаёт обработчик значений типа {@code int} со следующей реализацией метода {@code handle(int)}:
     * <pre>
     * {@code
     * value -> handler.handle(handle(value));
     * }
     * </pre>
     *
     * @param handler обработчик значений типа {@code int}.
     *
     * @return Новый обработчик значений типа {@code int}
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull IntegerToIntegerHandler<F> and(final @NonNull IntegerToIntegerHandler<? extends F> handler) throws
                                                                                                                 ValidationFault {
        Validator.nonNull(handler, "The passed handler");
        return value -> handler.handle(handle(value));
    }

    /**
     * Создаёт обработчик значений типа {@code int} со следующей реализацией метода {@code handle(int)}:
     * <pre>
     * {@code
     * value -> {
     *     try {
     *         return handle(value);
     *     } catch (final @NonNull Throwable primary) {
     *         try {
     *             return handler.handle(value);
     *         } catch (final @NonNull Throwable secondary) {
     *             primary.addSuppressed(secondary);
     *             throw primary;
     *         }
     *     }
     * };
     * }
     * </pre>
     *
     * @param handler обработчик значений типа {@code int}.
     *
     * @return Новый обработчик значений типа {@code int}
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull IntegerToIntegerHandler<F> or(final @NonNull IntegerToIntegerHandler<? extends F> handler) throws
                                                                                                                ValidationFault {
        Validator.nonNull(handler, "The passed handler");
        return value -> {
            try {
                return handle(value);
            } catch (final @NonNull Throwable primary) {
                try {
                    return handler.handle(value);
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
