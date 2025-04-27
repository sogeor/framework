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
 * Представляет собой двойной обработчик объектов.
 *
 * @param <T> тип первичных обрабатываемых объектов.
 * @param <U> тип вторичных обрабатываемых объектов.
 * @param <R> тип возвращаемых объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface BiHandler<T, U, R, F extends Throwable> {

    /**
     * Создаёт двойной обработчик объектов со следующей реализацией метода {@code handle(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> null;
     * }
     * </pre>
     *
     * @param <T> тип первичных объектов, обрабатываемых новым двойным обработчиком.
     * @param <U> тип вторичных объектов, обрабатываемых новым двойным обработчиком.
     * @param <R> тип объектов, возвращаемых новым двойным обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов
     * новым двойным обработчиком.
     *
     * @return Новый двойной обработчик объектов.
     *
     * @see #handle(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <T, U, R, F extends Throwable> @NonNull BiHandler<T, U, R, F> empty() {
        return (primaryIgnored, secondaryIgnored) -> null;
    }

    /**
     * Создаёт двойной обработчик объектов со следующей реализацией метода {@code handle(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> object;
     * }
     * </pre>
     *
     * @param object объект, обрабатываемый новым двойным обработчиком.
     * @param <T> тип первичных объектов, обрабатываемых новым двойным обработчиком.
     * @param <U> тип вторичных объектов, обрабатываемых новым двойным обработчиком.
     * @param <R> тип объектов, возвращаемых новым двойным обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов
     * новым двойным обработчиком.
     *
     * @return Новый двойной обработчик объектов.
     *
     * @see #handle(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <T, U, R, F extends Throwable> @NonNull BiHandler<T, U, R, F> direct(final @Nullable R object) {
        return (primaryIgnored, secondaryIgnored) -> object;
    }

    /**
     * Возвращает {@code handler}.
     *
     * @param handler двойной обработчик объектов.
     * @param <T> тип первичных объектов, обрабатываемых {@code handler}.
     * @param <U> тип вторичных объектов, обрабатываемых {@code handler}.
     * @param <R> тип объектов, возвращаемых {@code handler}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов
     * {@code handler}.
     *
     * @return {@code handler}.
     *
     * @apiNote Предназначен для удобного создания {@code handler} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, U, R, F extends Throwable> @NonNull BiHandler<T, U, R, F> of(
            final @NonNull BiHandler<T, U, R, F> handler) {
        return handler;
    }

    /**
     * Обрабатывает {@code primaryObject} и {@code secondaryObject} с помощью этого обработчика и возвращает объект.
     *
     * @param primaryObject первичный объект, обрабатываемый этим обработчиком.
     * @param secondaryObject вторичный объект, обрабатываемый этим обработчиком.
     *
     * @return Возвращаемый объект.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code primaryObject}, и/или
     * {@code secondaryObject}, и/или возвращаемого объекта.
     * @throws F неудачная обработка {@code primaryObject} и/или {@code secondaryObject} или возврат объекта.
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    @Nullable
    R handle(final @Nullable T primaryObject, final @Nullable U secondaryObject) throws ValidationFault, F;

    /**
     * Создаёт двойной обработчик объектов со следующей реализацией метода {@code handle(Object, Object)}:
     * <pre>
     * {@code
     * (primaryObject, secondaryObject) -> secondaryHandler.handle(handle(primaryObject, secondaryObject), primaryHandler.handle(primaryObject, secondaryObject));
     * }
     * </pre>
     *
     * @param primaryHandler первичный двойной обработчик объектов.
     * @param secondaryHandler вторичный двойной обработчик объектов.
     * @param <R2> тип объектов, возвращаемых {@code primaryHandler}.
     * @param <R3> тип объектов, возвращаемых {@code secondaryHandler}.
     *
     * @return Новый двойной обработчик объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code primaryHandler} и/или {@code secondaryHandler} не должен быть {@code null}.
     * @see #handle(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default <R2, R3> @NonNull BiHandler<T, U, R3, F> and(
            final @NonNull BiHandler<? super T, ? super U, R2, ? extends F> primaryHandler,
            final @NonNull BiHandler<? super R, ? super R2, R3, ? extends F> secondaryHandler) throws ValidationFault {
        Validator.nonNull(primaryHandler, "The passed primary handler");
        Validator.nonNull(secondaryHandler, "The passed secondary handler");
        return (primaryObject, secondaryObject) -> secondaryHandler.handle(handle(primaryObject, secondaryObject),
                                                                           primaryHandler.handle(primaryObject,
                                                                                                 secondaryObject));
    }

    /**
     * Создаёт двойной обработчик объектов со следующей реализацией метода {@code handle(Object, Object)}:
     * <pre>
     * {@code
     * (primaryObject, secondaryObject) -> {
     *     try {
     *         return handle(primaryObject, secondaryObject);
     *     } catch (final @NonNull Throwable primary) {
     *         try {
     *             return handler.handle(primaryObject, secondaryObject);
     *         } catch (final @NonNull Throwable secondary) {
     *             primary.addSuppressed(secondary);
     *             throw primary;
     *         }
     *     }
     * };
     * }
     * </pre>
     *
     * @param handler двойной обработчик объектов.
     *
     * @return Новый двойной обработчик объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #handle(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiHandler<T, U, R, F> or(
            final @NonNull BiHandler<? super T, ? super U, ? extends R, ? extends F> handler) throws ValidationFault {
        Validator.nonNull(handler, "The passed handler");
        return (primaryObject, secondaryObject) -> {
            try {
                return handle(primaryObject, secondaryObject);
            } catch (final @NonNull Throwable primary) {
                try {
                    return handler.handle(primaryObject, secondaryObject);
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
