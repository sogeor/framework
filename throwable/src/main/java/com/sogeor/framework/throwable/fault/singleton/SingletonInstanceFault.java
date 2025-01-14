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

package com.sogeor.framework.throwable.fault.singleton;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой непроверяемую программную неисправность, связанную с получением ещё не созданного экземпляра
 * класса, спроектированного согласно порождающему шаблону проектирования — одиночке.
 *
 * @since 1.0.0-RC1
 */
public class SingletonInstanceFault extends SingletonFault {

    /**
     * Содержит сообщение по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull String DEFAULT_MESSAGE = "An instance of the singleton class has not been created";

    /**
     * Содержит шаблонное сообщение.
     *
     * @since 1.0.0-RC1
     */
    public static final @NonNull String TEMPLATE_MESSAGE = "An instance of %s has not been created";

    /**
     * Создаёт экземпляр с {@linkplain #DEFAULT_MESSAGE сообщением}, {@linkplain #DEFAULT_CAUSE причиной возникновения},
     * параметрами {@linkplain #DEFAULT_SUPPRESSION подавления} и {@linkplain #DEFAULT_STACK_TRACE трассировки стека} по
     * умолчанию.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public SingletonInstanceFault() {
        super(DEFAULT_MESSAGE, DEFAULT_CAUSE, DEFAULT_SUPPRESSION, DEFAULT_STACK_TRACE);
    }

    /**
     * Создаёт экземпляр с {@linkplain #DEFAULT_CAUSE причиной возникновения}, параметрами
     * {@linkplain #DEFAULT_SUPPRESSION подавления} и {@linkplain #DEFAULT_STACK_TRACE трассировки стека} по умолчанию,
     * а также с {@code message}.
     *
     * @param message сообщение.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public SingletonInstanceFault(final @Nullable String message) {
        super(message, DEFAULT_CAUSE, DEFAULT_SUPPRESSION, DEFAULT_STACK_TRACE);
    }

    /**
     * Создаёт экземпляр с {@linkplain #DEFAULT_MESSAGE сообщением}, параметрами
     * {@linkplain #DEFAULT_SUPPRESSION подавления} и {@linkplain #DEFAULT_STACK_TRACE трассировки стека} по умолчанию,
     * а также с {@code cause}.
     *
     * @param cause причина возникновения.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public SingletonInstanceFault(final @Nullable Throwable cause) {
        super(DEFAULT_MESSAGE, cause, DEFAULT_SUPPRESSION, DEFAULT_STACK_TRACE);
    }

    /**
     * Создаёт экземпляр с параметрами {@linkplain #DEFAULT_SUPPRESSION подавления} и
     * {@linkplain #DEFAULT_STACK_TRACE трассировки стека} по умолчанию, а также с {@code message} и {@code cause}.
     *
     * @param message сообщение.
     * @param cause причина возникновения.
     *
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> new")
    public SingletonInstanceFault(final @Nullable String message, final @Nullable Throwable cause) {
        super(message, cause, DEFAULT_SUPPRESSION, DEFAULT_STACK_TRACE);
    }

    /**
     * Создаёт экземпляр с {@linkplain #DEFAULT_MESSAGE сообщением} и {@linkplain #DEFAULT_CAUSE причиной возникновения}
     * по умолчанию, а также с {@code suppression} и {@code stackTrace}.
     *
     * @param suppression параметр подавления.
     * @param stackTrace параметр трассировки стека.
     *
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> new")
    public SingletonInstanceFault(final boolean suppression, final boolean stackTrace) {
        super(DEFAULT_MESSAGE, DEFAULT_CAUSE, suppression, stackTrace);
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
    public SingletonInstanceFault(final @Nullable String message, final @Nullable Throwable cause,
                                  final boolean suppression, final boolean stackTrace) {
        super(message, cause, suppression, stackTrace);
    }

}
