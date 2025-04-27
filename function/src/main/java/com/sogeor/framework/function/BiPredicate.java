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
 * Представляет собой двойной предикат объектов.
 *
 * @param <T> тип первичных оцениваемых объектов.
 * @param <U> тип вторичных оцениваемых объектов.
 * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке объектов.
 *
 * @since 1.0.0-RC1
 */
@FunctionalInterface
public interface BiPredicate<T, U, F extends Throwable> {

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> value;
     * }
     * </pre>
     *
     * @param value оценка объекта.
     * @param <T> тип первичных объектов, оцениваемых новым двойным предикатом.
     * @param <U> тип вторичных объектов, оцениваемых новым двойным предикатом.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке объектов новым двойным
     * предикатом.
     *
     * @return Новый двойной предикат объектов.
     *
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    static <T, U, F extends Throwable> @NonNull BiPredicate<T, U, F> direct(final boolean value) {
        return (primaryIgnored, secondaryIgnored) -> value;
    }

    /**
     * Возвращает {@code predicate}.
     *
     * @param predicate двойной предикат объектов.
     * @param <T> тип первичных объектов, оцениваемых {@code predicate}.
     * @param <U> тип вторичных объектов, оцениваемых {@code predicate}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной оценке объектов {@code predicate}.
     *
     * @return {@code predicate}.
     *
     * @apiNote Предназначен для удобного создания {@code predicate} на основе лямбда-выражений.
     * @since 1.0.0-RC1
     */
    @Contract("? -> 1")
    static <T, U, F extends Throwable> @NonNull BiPredicate<T, U, F> of(final @NonNull BiPredicate<T, U, F> predicate) {
        return predicate;
    }

    /**
     * Оценивает {@code primaryObject} и {@code secondaryObject} с помощью этого предиката и возвращает их оценку.
     *
     * @param primaryObject первичный объект, оцениваемый этим обработчиком.
     * @param secondaryObject вторичный объект, оцениваемый этим обработчиком.
     *
     * @return Оценка {@code primaryObject} и {@code secondaryObject}.
     *
     * @throws ValidationFault неудачная валидация, предположительно, {@code primaryObject} и/или
     * {@code secondaryObject}.
     * @throws F неудачная обработка {@code primaryObject} и/или {@code secondaryObject}.
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    boolean evaluate(final @Nullable T primaryObject, final @Nullable U secondaryObject) throws ValidationFault, F;

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> !evaluate(primaryIgnored, secondaryIgnored);
     * }
     * </pre>
     *
     * @return Новый двойной предикат объектов.
     *
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    default @NonNull BiPredicate<T, U, F> not() {
        return (primaryIgnored, secondaryIgnored) -> !evaluate(primaryIgnored, secondaryIgnored);
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) && predicate.evaluate(primaryIgnored, secondaryIgnored);
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> and(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) &&
                                                     predicate.evaluate(primaryIgnored, secondaryIgnored);
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> !(evaluate(primaryIgnored, secondaryIgnored) && predicate.evaluate(primaryIgnored, secondaryIgnored));
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> nand(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> !(evaluate(primaryIgnored, secondaryIgnored) &&
                                                       predicate.evaluate(primaryIgnored, secondaryIgnored));
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) || predicate.evaluate(primaryIgnored, secondaryIgnored);
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> or(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) ||
                                                     predicate.evaluate(primaryIgnored, secondaryIgnored);
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> !(evaluate(primaryIgnored, secondaryIgnored) || predicate.evaluate(primaryIgnored, secondaryIgnored));
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> nor(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> !(evaluate(primaryIgnored, secondaryIgnored) ||
                                                       predicate.evaluate(primaryIgnored, secondaryIgnored));
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) == predicate.evaluate(primaryIgnored, secondaryIgnored);
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> xnor(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) ==
                                                     predicate.evaluate(primaryIgnored, secondaryIgnored);
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) ^ predicate.evaluate(primaryIgnored, secondaryIgnored);
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> xor(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> evaluate(primaryIgnored, secondaryIgnored) ^
                                                     predicate.evaluate(primaryIgnored, secondaryIgnored);
    }

    /**
     * Создаёт двойной предикат объектов со следующей реализацией метода {@code evaluate(Object, Object)}:
     * <pre>
     * {@code
     * (primaryIgnored, secondaryIgnored) -> !evaluate(primaryIgnored, secondaryIgnored) || predicate.evaluate(primaryIgnored, secondaryIgnored);
     * }
     * </pre>
     *
     * @param predicate двойной предикат объектов.
     *
     * @return Новый двойной предикат объектов.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @see #evaluate(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    default @NonNull BiPredicate<T, U, F> imply(
            final @NonNull BiPredicate<? super T, ? super U, ? extends F> predicate) throws ValidationFault {
        Validator.nonNull(predicate, "The passed predicate");
        return (primaryIgnored, secondaryIgnored) -> !evaluate(primaryIgnored, secondaryIgnored) ||
                                                     predicate.evaluate(primaryIgnored, secondaryIgnored);
    }

}
