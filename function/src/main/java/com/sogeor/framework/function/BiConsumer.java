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
 * Представляет собой двойной потребитель объектов.
 *
 * @param <T> тип первичных потребляемых объектов.
 * @param <U> тип вторичных потребляемых объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface BiConsumer<T, U, F extends Throwable> {

    /**
     * Создаёт двойной потребитель объектов со следующей реализацией метода {@code consume(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> {};
     * }
     * </pre>
     *
     * @param <T> тип первичных объектов, потребляемых новым двойным потребителем.
     * @param <U> тип вторичных объектов, потребляемых новым двойным потребителем.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении объектов новым двойным
     * потребителем.
     *
     * @return Новый двойной потребитель объектов.
     *
     * @see #consume(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    static <T, U, F extends Throwable> @NonNull BiConsumer<T, U, F> empty() {
        return (primaryIgnored, secondaryIgnored) -> {};
    }

    /**
     * Возвращает {@code consumer}.
     *
     * @param consumer двойной потребитель объектов.
     * @param <T> тип первичных объектов, потребляемых {@code consumer}.
     * @param <U> тип вторичных объектов, потребляемых {@code consumer}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении объектов
     * {@code consumer}.
     *
     * @return {@code consumer}.
     *
     * @apiNote Предназначен для удобного создания {@code consumer} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, U, F extends Throwable> @NonNull BiConsumer<T, U, F> of(final @NonNull BiConsumer<T, U, F> consumer) {
        return consumer;
    }

    /**
     * Потребляет {@code primaryObject} и {@code secondaryObject} с помощью этого потребителя.
     *
     * @param primaryObject первичный объект, потребляемый этим потребителем.
     * @param secondaryObject вторичный объект, потребляемый этим потребителем.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code primaryObject} и/или
     * {@code secondaryObject}.
     * @throws F неудачное потребление {@code primaryObject} и/или {@code secondaryObject} с помощью этого потребителя.
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    void consume(final T primaryObject, final U secondaryObject) throws ValidationFault, F;

    /**
     * Создаёт двойной потребитель объектов со следующей реализацией метода {@code consume(Object, Object)}:
     * <pre>
     * {@code
     * (primaryObject, secondaryObject) -> {
     *     consume(primaryObject, secondaryObject);
     *     consumer.consume(primaryObject, secondaryObject);
     * };
     * }
     * </pre>
     *
     * @param consumer двойной потребитель объектов.
     *
     * @return Новый двойной потребитель объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #consume(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiConsumer<T, U, F> and(
            final @NonNull BiConsumer<? super T, ? super U, ? extends F> consumer) throws ValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return (primaryObject, secondaryObject) -> {
            consume(primaryObject, secondaryObject);
            consumer.consume(primaryObject, secondaryObject);
        };
    }

    /**
     * Создаёт двойной потребитель объектов со следующей реализацией метода {@code consume(Object, Object)}:
     * <pre>
     * {@code
     * (primaryObject, secondaryObject) -> {
     *     try {
     *         consume(primaryObject, secondaryObject);
     *     } catch (final @NonNull Throwable primary) {
     *         try {
     *             consumer.consume(primaryObject, secondaryObject);
     *         } catch (final @NonNull Throwable secondary) {
     *             primary.addSuppressed(secondary);
     *             throw primary;
     *         }
     *     }
     * };
     * }
     * </pre>
     *
     * @param consumer двойной потребитель объектов.
     *
     * @return Новый двойной потребитель объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #consume(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiConsumer<T, U, F> or(
            final @NonNull BiConsumer<? super T, ? super U, ? extends F> consumer) throws ValidationFault {
        Validator.nonNull(consumer, "The passed consumer");
        return (primaryObject, secondaryObject) -> {
            try {
                consume(primaryObject, secondaryObject);
            } catch (final @NonNull Throwable primary) {
                try {
                    consumer.consume(primaryObject, secondaryObject);
                } catch (final @NonNull Throwable secondary) {
                    primary.addSuppressed(secondary);
                    throw primary;
                }
            }
        };
    }

}
