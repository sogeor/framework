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
 * Представляет собой поставщик объектов.
 *
 * @param <T> тип поставляемых объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Supplier<T, F extends Throwable> {

    /**
     * Создаёт поставщик объектов со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> null;
     * }
     * </pre>
     *
     * @param <T> тип объектов, поставляемых новым поставщиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке объектов новым
     * поставщиком.
     *
     * @return Новый поставщик объектов.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <T, F extends Throwable> @NonNull Supplier<T, F> empty() {
        return () -> null;
    }

    /**
     * Создаёт поставщик объектов со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> object;
     * }
     * </pre>
     *
     * @param object объект, поставляемый новым поставщиком.
     * @param <T> тип объектов, поставляемых новым поставщиком.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке объектов новым
     * поставщиком.
     *
     * @return Новый поставщик объектов.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <T, F extends Throwable> @NonNull Supplier<T, F> direct(final @Nullable T object) {
        return () -> object;
    }

    /**
     * Возвращает {@code supplier}.
     *
     * @param supplier поставщик объектов.
     * @param <T> тип объектов, поставляемых {@code supplier}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке объектов
     * {@code supplier}.
     *
     * @return Новый поставщик объектов.
     *
     * @apiNote Предназначен для удобного создания {@code supplier} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, F extends Throwable> @NonNull Supplier<T, F> of(final @NonNull Supplier<T, F> supplier) {
        return supplier;
    }

    /**
     * Поставляет объект с помощью этого поставщика.
     *
     * @return Поставляемый этим поставщиком объект.
     *
     * @throws ValidationFault неудачная валидация, предположительно, поставленного объекта.
     * @throws F неудачная поставка объекта с помощью этого поставщика.
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    @Nullable
    T get() throws ValidationFault, F;

    /**
     * Создаёт поставщик объектов со следующей реализацией метода {@code get()}:
     * <pre>
     * {@code
     * () -> {
     *     try {
     *         return get();
     *     } catch (final @NonNull Throwable ignored) {
     *         return object;
     *     }
     * };
     * }
     * </pre>
     *
     * @param object объект.
     *
     * @return Новый поставщик объектов.
     *
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    default @NonNull Supplier<T, F> passed(final @Nullable T object) {
        return () -> {
            try {
                return get();
            } catch (final @NonNull Throwable ignored) {
                return object;
            }
        };
    }

    /**
     * Создаёт поставщик объектов со следующей реализацией метода {@code get()}:
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
     * @param supplier поставщик объектов.
     *
     * @return Новый поставщик объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull Supplier<T, F> supplied(final @NonNull Supplier<? extends T, ? extends F> supplier) throws
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
