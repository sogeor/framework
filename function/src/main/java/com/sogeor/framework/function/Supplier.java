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
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой поставщик объектов (1).
 *
 * @param <T> тип [1].
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке [1].
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Supplier<T, F extends Throwable> {

    /**
     * Создаёт поставщик (2) объектов (3) с методом {@linkplain #get()}, поставляющим [1].
     *
     * @param object объект (1).
     * @param <T> тип [3].
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке [3].
     *
     * @return [2].
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <T, F extends Throwable> @NonNull Supplier<T, F> direct(final @Nullable T object) {
        return () -> object;
    }

    /**
     * Возвращает [1].
     *
     * @param supplier поставщик (1) объектов (2).
     * @param <T> тип [2].
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке [2].
     *
     * @return [1].
     *
     * @apiNote Предназначен для удобного создания [1] на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, F extends Throwable> @NonNull Supplier<T, F> of(final @NonNull Supplier<T, F> supplier) {
        return supplier;
    }

    /**
     * Поставляет объект (1).
     *
     * @return [1].
     *
     * @throws ValidationFault неудачная валидация, предположительно, [1].
     * @throws F неудачная поставка [1].
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    @Nullable
    T get() throws ValidationFault, F;

    /**
     * Создаёт поставщик (2) объектов с методом {@linkplain #get()}, сначала пытающимся получить с помощью метода
     * {@linkplain #get() this.get()} объект и вернуть его, а потом, если неудачно, возвращающим [1].
     *
     * @param object объект (1).
     *
     * @return [2].
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    default @NonNull Supplier<T, F> orPassed(final @Nullable T object) {
        return () -> {
            try {
                return get();
            } catch (final @NonNull Throwable ignored) {
                return object;
            }
        };
    }

    /**
     * Создаёт поставщик (2) объектов с методом {@linkplain #get()}, пытающимся получить сначала от метода
     * {@linkplain #get() this.get()}, а потом, если неудачно, от метода {@linkplain #get() supplier.get()} объект и
     * вернуть его.
     *
     * @param supplier поставщик (1) объектов.
     *
     * @return [2].
     *
     * @throws ValidationFault неудачная валидация [1].
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Supplier<T, F> orSupplied(final @NonNull Supplier<? extends T, ? extends F> supplier) throws
                                                                                                           ValidationFault {
        Validator.nonNull(supplier, "The passed supplier");
        return () -> {
            try {
                return get();
            } catch (final @NonNull Throwable primary) {
                try {
                    return supplier.get();
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
