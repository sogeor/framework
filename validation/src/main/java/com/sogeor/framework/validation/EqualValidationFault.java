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

package com.sogeor.framework.validation;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой непроверяемую программную неисправность, связанную с неудачной валидацией неравенства объектов или
 * значений.
 *
 * @see NonEqualValidationFault
 * @since 1.0.0-RC1
 */
public class EqualValidationFault extends ValidationFault {

    /**
     * Содержит шаблонное сообщение.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull String TEMPLATE_MESSAGE = "%s must not be equal";

    /**
     * Содержит сообщение для объектов или значений по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull String DEFAULT_MESSAGE = TEMPLATE_MESSAGE.formatted("The objects or values");

    /**
     * Содержит сообщение для объектов по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull String DEFAULT_OBJECTS_MESSAGE = TEMPLATE_MESSAGE.formatted("The objects");

    /**
     * Содержит сообщение для значений по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull String DEFAULT_VALUES_MESSAGE = TEMPLATE_MESSAGE.formatted("The values");

    /**
     * Создаёт экземпляр по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public EqualValidationFault() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Создаёт экземпляр на основе {@code message}.
     *
     * @param message сообщение.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public EqualValidationFault(final @Nullable String message) {
        super(message);
    }

    /**
     * Создаёт экземпляр на основе {@code cause}.
     *
     * @param cause причина возникновения.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public EqualValidationFault(final @Nullable Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    /**
     * Создаёт экземпляр на основе {@code message} и {@code cause}.
     *
     * @param message сообщение.
     * @param cause причина возникновения.
     *
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> new")
    public EqualValidationFault(final @Nullable String message, final @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Создаёт экземпляр на основе {@code message}, {@code cause}, {@code suppression} и {@code stackTrace}.
     *
     * @param message сообщение.
     * @param cause причина возникновения.
     * @param suppression параметр подавления.
     * @param stackTrace параметр трассировки стека.
     *
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> new")
    public EqualValidationFault(final @Nullable String message, final @Nullable Throwable cause,
                                final boolean suppression, final boolean stackTrace) {
        super(message, cause, suppression, stackTrace);
    }

}
