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
 * Представляет собой поставщик значений типа {@code boolean}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа
 * {@code boolean}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface BooleanSupplier<F extends Throwable> {

    /**
     * Создаёт поставщик значений типа {@code boolean} со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> value;
     * }
     * </pre>
     *
     * @param value значение типа {@code boolean}, поставляемое новым поставщиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа
     * {@code boolean} новым поставщиком.
     *
     * @return Новый поставщик значений типа {@code boolean}.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull BooleanSupplier<F> direct(final boolean value) {
        return () -> value;
    }

    /**
     * Возвращает {@code supplier}.
     *
     * @param supplier поставщик значений типа {@code boolean}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа
     * {@code boolean} {@code supplier}.
     *
     * @return Новый поставщик значений типа {@code boolean}.
     *
     * @apiNote Предназначен для удобного создания {@code supplier} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull BooleanSupplier<F> of(final @NonNull BooleanSupplier<F> supplier) {
        return supplier;
    }

    /**
     * Поставляет значение типа {@code boolean} с помощью этого поставщика.
     *
     * @return Поставляемое этим поставщиком значение типа {@code boolean}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, поставленного значения типа {@code boolean}.
     * @throws F неудачная поставка значения типа {@code boolean} с помощью этого поставщика.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    boolean get() throws ValidationFault, F;

    /**
     * Создаёт поставщик значений типа {@code boolean} со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> {
     *     try {
     *         return get();
     *     } catch (final @NonNull Throwable ignored) {
     *         return value;
     *     }
     * };
     * }
     * </pre>
     *
     * @param value значение типа {@code boolean}.
     *
     * @return Новый поставщик значений типа {@code boolean}.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    default @NonNull BooleanSupplier<F> passed(final boolean value) {
        return () -> {
            try {
                return get();
            } catch (final @NonNull Throwable ignored) {
                return value;
            }
        };
    }

    /**
     * Создаёт поставщик значений типа {@code boolean} со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> {
     *     try {
     *         return get();
     *     } catch (final @NonNull Throwable primary) {
     *         try {
     *             return supplier.get();
     *         } catch (final @NonNull Throwable secondary) {
     *             primary.addSuppressed(secondary);
     *             throw primary;
     *         }
     *     }
     * };
     * }
     * </pre>
     *
     * @param supplier поставщик значений типа {@code boolean}.
     *
     * @return Новый поставщик значений типа {@code boolean}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BooleanSupplier<F> supplied(final @NonNull BooleanSupplier<? extends F> supplier) throws
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
