/*
 * Copyright 2025 Sogeor
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
 * Представляет собой поставщик значений типа {@code char}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа {@code char}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface CharacterSupplier<F extends Throwable> {

    /**
     * Создаёт поставщик значений типа {@code char} со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> value;
     * }
     * </pre>
     *
     * @param value значение типа {@code char}, поставляемое новым поставщиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа {@code char}
     * новым поставщиком.
     *
     * @return Новый поставщик значений типа {@code char}.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <F extends Throwable> @NonNull CharacterSupplier<F> direct(final char value) {
        return () -> value;
    }

    /**
     * Возвращает {@code supplier}.
     *
     * @param supplier поставщик значений типа {@code char}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа {@code char}
     * {@code supplier}.
     *
     * @return Новый поставщик значений типа {@code char}.
     *
     * @apiNote Предназначен для удобного создания {@code supplier} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull CharacterSupplier<F> of(final @NonNull CharacterSupplier<F> supplier) {
        return supplier;
    }

    /**
     * Поставляет значение типа {@code char} с помощью этого поставщика.
     *
     * @return Поставляемое этим поставщиком значение типа {@code char}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, поставленного значения типа {@code char}.
     * @throws F неудачная поставка значения типа {@code char} с помощью этого поставщика.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    char get() throws ValidationFault, F;

    /**
     * Создаёт поставщик значений типа {@code char} со следующей реализацией метода {@code get()}:
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
     * @param value значение типа {@code char}.
     *
     * @return Новый поставщик значений типа {@code char}.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    default @NonNull CharacterSupplier<F> passed(final char value) {
        return () -> {
            try {
                return get();
            } catch (final @NonNull Throwable ignored) {
                return value;
            }
        };
    }

    /**
     * Создаёт поставщик значений типа {@code char} со следующей реализацией метода {@code get()}:
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
     * @param supplier поставщик значений типа {@code char}.
     *
     * @return Новый поставщик значений типа {@code char}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull CharacterSupplier<F> supplied(final @NonNull CharacterSupplier<? extends F> supplier) throws
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
