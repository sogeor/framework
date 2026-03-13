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
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой обработчик значений типа {@code int}.
 *
 * @param <R> тип возвращаемых объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке значений типа {@code int} или
 * возврате объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface IntHandler<R, F extends Throwable> {

    /**
     * Создаёт обработчик значений типа {@code int} со следующей реализацией метода {@code handle(int)}:
     * <pre>
     * {@code
     * value -> null;
     * }
     * </pre>
     *
     * @param <R> тип объектов, возвращаемых новым обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке значений типа {@code int}
     * или возврате объектов новым обработчиком.
     *
     * @return Новый обработчик значений типа {@code int}.
     *
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <R, F extends Throwable> @NonNull IntHandler<R, F> empty() {
        return value -> null;
    }

    /**
     * Создаёт обработчик значений типа {@code int} со следующей реализацией метода {@code handle(int)}:
     * <pre>
     * {@code
     * ignored -> object;
     * }
     * </pre>
     *
     * @param object объект, возвращаемый новым обработчиком.
     * @param <R> тип объектов, возвращаемых новым обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке значений типа {@code int}
     * или возврате объектов новым обработчиком.
     *
     * @return Новый обработчик значений типа {@code int}.
     *
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <R, F extends Throwable> @NonNull IntHandler<R, F> direct(final @Nullable R object) {
        return ignored -> object;
    }

    /**
     * Возвращает {@code handler}.
     *
     * @param handler обработчик значений типа {@code int}.
     * @param <R> тип объектов, возвращаемых новым обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке значений типа {@code int}
     * или возврате объектов {@code handler}.
     *
     * @return {@code handler}.
     *
     * @apiNote Предназначен для удобного создания {@code handler} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <R, F extends Throwable> @NonNull IntHandler<R, F> of(final @NonNull IntHandler<R, F> handler) {
        return handler;
    }

    /**
     * Обрабатывает {@code value} с помощью этого обработчика и возвращает объект.
     *
     * @param value значение типа {@code int}, обрабатываемое этим обработчиком.
     *
     * @return Возвращаемый объект.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code value} и/или возвращаемого объекта.
     * @throws F неудачная обработка {@code value} или возврат объекта.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    R handle(final int value) throws ValidationFault, F;

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
     * @return Новый обработчик значений типа {@code int}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #handle(int)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull IntHandler<R, F> or(final @NonNull IntHandler<? extends R, ? extends F> handler) throws
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
