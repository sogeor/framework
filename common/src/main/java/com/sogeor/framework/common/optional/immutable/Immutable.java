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

package com.sogeor.framework.common.optional.immutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.common.optional.Optional;
import com.sogeor.framework.throwable.fault.singleton.SingletonCreationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой неизменяемую обёртку над объектом.
 *
 * @param <T> тип объекта.
 *
 * @since 1.0.0-RC1
 */
public final class Immutable<T> extends Optional<T> {

    /**
     * Содержит неизменяемую обёртку над {@code null}.
     *
     * @see #Immutable()
     * @since 1.0.0-RC1
     */
    private static final @NonNull Immutable<?> EMPTY = new Immutable<>();

    /**
     * Содержит текущий объект.
     *
     * @since 1.0.0-RC1
     */
    private final @Nullable T object;

    /**
     * Если {@code EMPTY == null}, то создаёт экземпляр на основе {@code null}, иначе генерирует
     * {@linkplain SingletonCreationFault проверяемый программный сбой} с
     * {@linkplain SingletonCreationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе имени этого класса.
     *
     * @throws SingletonCreationFault второй экземпляр этого класса не должен быть создан.
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    @SuppressWarnings("ConstantValue")
    private Immutable() throws SingletonCreationFault {
        if (EMPTY != null) {
            this.object = null;
            return;
        }
        throw new SingletonCreationFault(SingletonCreationFault.TEMPLATE_MESSAGE.formatted("the Immutable class"));
    }

    /**
     * Создаёт экземпляр на основе {@code object}.
     *
     * @param object объект.
     *
     * @throws ValidationFault {@code object} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> new; null -> fault")
    private Immutable(final @NonNull T object) throws ValidationFault {
        this.object = Validator.nonNull(object, "The passed object");
    }

    /**
     * Возвращает {@code EMPTY} — неизменяемую обёртку над {@code null}.
     *
     * @param <T> тип {@code null}.
     *
     * @return Неизменяемую обёртку над {@code null}.
     *
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("-> $!null")
    public static <T> @NonNull Immutable<T> empty() {
        return (Immutable<T>) EMPTY;
    }

    /**
     * Если {@code object == null}, то возвращает {@code empty()}, иначе создаёт и возвращает неизменяемую обёртку над
     * {@code object}.
     *
     * @param object объект.
     * @param <T> тип {@code object}.
     *
     * @return Неизменяемую обёртку над {@code null} или {@code object}.
     *
     * @see #empty()
     * @since 1.0.0-RC1
     */
    @Contract("null -> $!null; $!null -> new")
    public static <T> @NonNull Immutable<T> of(final @Nullable T object) {
        return object == null ? empty() : new Immutable<>(object);
    }

    /**
     * @return {@code object}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public @Nullable T get() {
        return object;
    }

    /**
     * @return {@code object == null}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public boolean absent() {
        return object == null;
    }

    /**
     * @return {@code object != null}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public boolean present() {
        return object != null;
    }

}
