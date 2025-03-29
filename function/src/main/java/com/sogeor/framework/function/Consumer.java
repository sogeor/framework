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
import com.sogeor.framework.annotation.Experimental;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой потребитель объектов.
 *
 * @param <T> тип объектов.
 * @param <F> тип программного дефекта, возникающего при неудачном потреблении объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface Consumer<T, F extends Throwable> {

    /**
     * Создаёт потребитель объектов с пустым методом {@code consume(Object)}.
     *
     * @param <T> тип объектов, потребляемых новым потребителем.
     * @param <F> тип программного дефекта, возникающего при неудачном потреблении объектов новым потребителем.
     *
     * @return Новый потребитель.
     *
     * @see #consume(Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <T, F extends Throwable> @NonNull Consumer<T, F> empty() {
        return ignored -> {};
    }

    /**
     * Возвращает {@code consumer}.
     *
     * @param consumer потребитель объектов.
     * @param <T> тип объектов, потребляемых {@code consumer}.
     * @param <F> тип программного дефекта, возникающего при неудачном потреблении объектов {@code consumer}.
     *
     * @return {@code consumer}.
     *
     * @apiNote Предназначен для удобного создания {@code consumer} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, F extends Throwable> @NonNull Consumer<T, F> of(final @NonNull Consumer<T, F> consumer) {
        return consumer;
    }

    /**
     * Потребляет {@code object} с помощью этого потребителя.
     *
     * @param object объект.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code object}.
     * @throws F неудачное потребление {@code object} с помощью этого потребителя.
     * @since 1.0.0-RC1
     */
    @Contract("? -> ?")
    void consume(final @Nullable T object) throws ValidationFault, F;

    /**
     * Создаёт потребитель объектов со следующей реализацией метода {@code consume(Object)}:
     * <pre>
     * {@code
     * object -> {
     *     consume(object);
     *     consumer.consume(object);
     * };
     * }
     * </pre>
     *
     * @param consumer потребитель объектов.
     *
     * @return Новый потребитель объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #consume(Object)
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> new; null -> fault")
    default @NonNull Consumer<T, F> and(final @NonNull Consumer<? super T, ? extends F> consumer) throws
                                                                                                  ValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return object -> {
            consume(object);
            consumer.consume(object);
        };
    }

    /**
     * Создаёт потребитель объектов со следующей реализацией метода {@code consume(Object)}:
     * <pre>
     * {@code
     * object -> {
     *     try {
     *         consume(object);
     *     } catch (final @NonNull Throwable primary) {
     *         try {
     *             consumer.consume(object);
     *         } catch (final @NonNull Throwable secondary) {
     *             primary.addSuppressed(secondary);
     *             throw primary;
     *         }
     *     }
     * };
     * }
     * </pre>
     *
     * @param consumer потребитель объектов.
     *
     * @return Новый потребитель объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #consume(Object)
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> new; null -> fault")
    default @NonNull Consumer<T, F> or(final @NonNull Consumer<? super T, ? extends F> consumer) throws
                                                                                                 ValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return object -> {
            try {
                consume(object);
            } catch (final @NonNull Throwable primary) {
                try {
                    consumer.consume(object);
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
