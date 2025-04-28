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
 * Представляет собой потребитель значений типа {@code double}.
 *
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении значений типа
 * {@code double}.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface DoubleConsumer<F extends Throwable> {

    /**
     * Создаёт потребитель значений типа {@code double} со следующей реализацией метода {@code consume(double)}:
     * <pre>
     * {@code
     * ignored -> {};
     * }
     * </pre>
     *
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении значений типа
     * {@code double} новым потребителем.
     *
     * @return Новый потребитель значений типа {@code double}.
     *
     * @see #consume(double)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <F extends Throwable> @NonNull DoubleConsumer<F> empty() {
        return ignored -> {};
    }

    /**
     * Возвращает {@code consumer}.
     *
     * @param consumer потребитель значений типа {@code double}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении значений типа
     * {@code double} {@code consumer}.
     *
     * @return {@code consumer}.
     *
     * @apiNote Предназначен для удобного создания {@code consumer} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <F extends Throwable> @NonNull DoubleConsumer<F> of(final @NonNull DoubleConsumer<F> consumer) {
        return consumer;
    }

    /**
     * Потребляет {@code value} с помощью этого потребителя.
     *
     * @param value значение типа {@code double}, потребляемое этим потребителем.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code value}.
     * @throws F неудачное потребление {@code value} с помощью этого потребителя.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    void consume(final @Nullable double value) throws ValidationFault, F;

    /**
     * Создаёт потребитель значений типа {@code double} со следующей реализацией метода {@code consume(double)}:
     * <pre>
     * {@code
     * value -> {
     *     consume(value);
     *     consumer.consume(value);
     * };
     * }
     * </pre>
     *
     * @param consumer потребитель значений типа {@code double}.
     *
     * @return Новый потребитель значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #consume(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoubleConsumer<F> and(final @NonNull DoubleConsumer<? extends F> consumer) throws ValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return value -> {
            consume(value);
            consumer.consume(value);
        };
    }

    /**
     * Создаёт потребитель значений типа {@code double} со следующей реализацией метода {@code consume(double)}:
     * <pre>
     * {@code
     * value -> {
     *     try {
     *         consume(value);
     *     } catch (final @NonNull Throwable primary) {
     *         try {
     *             consumer.consume(value);
     *         } catch (final @NonNull Throwable secondary) {
     *             primary.addSuppressed(secondary);
     *             throw primary;
     *         }
     *     }
     * };
     * }
     * </pre>
     *
     * @param consumer потребитель значений типа {@code double}.
     *
     * @return Новый потребитель значений типа {@code double}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #consume(double)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull DoubleConsumer<F> or(final @NonNull DoubleConsumer<? extends F> consumer) throws ValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return value -> {
            try {
                consume(value);
            } catch (final @NonNull Throwable primary) {
                try {
                    consumer.consume(value);
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
