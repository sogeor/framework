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
 * Представляет собой обработчик объектов.
 *
 * @param <T> тип обрабатываемых объектов.
 * @param <R> тип возвращаемых объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Handler<T, R, F extends Throwable> {

    /**
     * Создаёт обработчик объектов с методом {@linkplain #handle(Object)}, возвращающим {@code object}.
     *
     * @param object объект.
     * @param <T> тип объектов, обрабатываемых новым обработчиком.
     * @param <R> тип объектов, возвращаемых новым обработчиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов
     * новым обработчиком.
     *
     * @return Новый обработчик объектов.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <T, R, F extends Throwable> @NonNull Handler<T, R, F> direct(final @Nullable R object) {
        return ignored -> object;
    }

    /**
     * Возвращает {@code handler}.
     *
     * @param handler обработчик объектов.
     * @param <T> тип объектов, обрабатываемых {@code handler}.
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
    static <T, R, F extends Throwable> @NonNull Handler<T, R, F> of(final @NonNull Handler<T, R, F> handler) {
        return handler;
    }

    /**
     * Обрабатывает {@code object} и возвращает, предположительно, другой объект.
     *
     * @param object объект.
     *
     * @return Возвращаемый объект.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code object} и/или возвращаемого объекта.
     * @throws F неудачная обработка {@code object} или возврат объекта.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    @Nullable
    R handle(final @Nullable T object) throws ValidationFault, F;

    /**
     * Создаёт обработчик объектов с методом {@linkplain #handle(Object)}, выполняющим сначала метод
     * {@linkplain #handle(Object) this.handle(Object)}, а потом получающим от метода
     * {@linkplain #handle(Object) handler.handle(Object)} объект и возвращающим его.
     *
     * @param handler обработчик объектов.
     * @param <R2> тип объектов, возвращаемых {@code handler}.
     *
     * @return Новый обработчик объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default <R2> @NonNull Handler<T, R2, F> and(final @NonNull Handler<? super R, R2, ? extends F> handler) throws
                                                                                                            ValidationFault {
        Validator.nonNull(handler, "The passed handler");
        return object -> handler.handle(handle(object));
    }

    /**
     * Создаёт обработчик объектов с методом {@linkplain #handle(Object)}, пытающимся получить сначала от метода
     * {@linkplain #handle(Object) this.handle(Object)}, а потом, если неудачно, от метода
     * {@linkplain #handle(Object) handler.handle(Object)} объект и вернуть его.
     *
     * @param handler обработчик объектов.
     *
     * @return Новый обработчик объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Handler<T, R, F> or(final @NonNull Handler<? super T, ? extends R, ? extends F> handler) throws
                                                                                                              ValidationFault {
        Validator.nonNull(handler, "The passed handler");
        return object -> {
            try {
                return handle(object);
            } catch (final @NonNull Throwable primary) {
                try {
                    return handler.handle(object);
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
