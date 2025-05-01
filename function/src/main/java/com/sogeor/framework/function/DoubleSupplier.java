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
 * Представляет собой поставщик значений типа {@code double}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа {@code double}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface DoubleSupplier<F extends Throwable> {

    /**
     * Создаёт поставщик значений типа {@code double} со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> value;
     * }
     * </pre>
     *
     * @param value значение типа {@code double}, поставляемое новым поставщиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа
     * {@code double} новым поставщиком.
     *
     * @return Новый поставщик значений типа {@code double}.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull DoubleSupplier<F> direct(final double value) {
        return () -> value;
    }

    /**
     * Возвращает {@code supplier}.
     *
     * @param supplier поставщик значений типа {@code double}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа
     * {@code double} {@code supplier}.
     *
     * @return Новый поставщик значений типа {@code double}.
     *
     * @apiNote Предназначен для удобного создания {@code supplier} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull DoubleSupplier<F> of(final @NonNull DoubleSupplier<F> supplier) {
        return supplier;
    }

    /**
     * Поставляет значение типа {@code double} с помощью этого поставщика.
     *
     * @return Поставляемое этим поставщиком значение типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, поставленного значения типа {@code double}.
     * @throws F неудачная поставка значения типа {@code double} с помощью этого поставщика.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    double get() throws ValidationFault, F;

    /**
     * Создаёт поставщик значений типа {@code double} со следующей реализацией метода {@code get()}:
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
     * @param value значение типа {@code double}.
     *
     * @return Новый поставщик значений типа {@code double}.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    default @NonNull DoubleSupplier<F> passed(final double value) {
        return () -> {
            try {
                return get();
            } catch (final @NonNull Throwable ignored) {
                return value;
            }
        };
    }

    /**
     * Создаёт поставщик значений типа {@code double} со следующей реализацией метода {@code get()}:
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
     * @param supplier поставщик значений типа {@code double}.
     *
     * @return Новый поставщик значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoubleSupplier<F> supplied(final @NonNull DoubleSupplier<? extends F> supplier) throws
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
