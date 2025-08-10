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
import com.sogeor.framework.common.optional.OptionalInteger;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.IntegerConsumer;
import com.sogeor.framework.throwable.fault.singleton.SingletonCreationFault;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой неизменяемую обёртку над значением типа {@code int}.
 *
 * @since 1.0.0-RC1
 */
public final class ImmutableInteger extends OptionalInteger {

    /**
     * Содержит неизменяемую обёртку без значения типа {@code int}.
     *
     * @see #ImmutableInteger()
     * @since 1.0.0-RC1
     */
    private static final @NonNull ImmutableInteger EMPTY = new ImmutableInteger();

    /**
     * Если текущее значение типа {@code int} существует, то содержит {@code true}, иначе — {@code false}.
     *
     * @since 1.0.0-RC1
     */
    private final boolean contains;

    /**
     * Содержит текущее значение типа {@code int}.
     *
     * @since 1.0.0-RC1
     */
    private final int value;

    /**
     * Если {@code EMPTY == null}, то создаёт экземпляр без значения типа {@code int}, иначе генерирует
     * {@linkplain SingletonCreationFault проверяемый программный сбой} с
     * {@linkplain SingletonCreationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе имени этого класса.
     *
     * @throws SingletonCreationFault второй экземпляр этого класса без значения типа {@code int} не должен быть
     * создан.
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    @SuppressWarnings("ConstantValue")
    private ImmutableInteger() throws SingletonCreationFault {
        if (EMPTY != null)
            throw new SingletonCreationFault(SingletonCreationFault.TEMPLATE_MESSAGE.formatted("ImmutableInteger"));
        contains = false;
        value = 0;
    }

    /**
     * Создаёт экземпляр на основе {@code value}.
     *
     * @param value значение типа {@code int}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    private ImmutableInteger(final int value) {
        contains = true;
        this.value = value;
    }

    /**
     * @return Неизменяемую обёртку без значения типа {@code int}.
     *
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    public static @NonNull ImmutableInteger empty() {
        return EMPTY;
    }

    /**
     * Создаёт неизменяемую обёртку над {@code value}.
     *
     * @param value значение типа {@code int}.
     *
     * @return Новую неизменяемую обёртку над {@code value}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public static @NonNull ImmutableInteger of(final int value) {
        return new ImmutableInteger(value);
    }

    /**
     * {@inheritDoc}
     *
     * @return Текущее значение типа {@code int} или {@code 0}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public int get() {
        return value;
    }

    /**
     * Если текущее значение не существует, то возвращает {@code true}, иначе — {@code false}.
     *
     * @return {@code !contains}.
     *
     * @see #contains
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public boolean absent() {
        return !contains;
    }

    /**
     * Если текущее значение существует, то возвращает {@code true}, иначе — {@code false}.
     *
     * @return {@code contains}.
     *
     * @see #contains
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public boolean present() {
        return contains;
    }

    /**
     * {@inheritDoc}
     *
     * @param action действие.
     * @param <F> тип программного сбоя или неисправности, возникающей во время выполнения {@code action}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code action} не должно быть {@code null}.
     * @see Action
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull ImmutableInteger absent(final @NonNull Action<F> action) throws
                                                                                                   ValidationFault, F {
        super.absent(action);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param action действие.
     * @param <F> тип программного сбоя или неисправности, возникающей во время выполнения {@code action}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code action} не должно быть {@code null}.
     * @see Action
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull ImmutableInteger present(final @NonNull Action<F> action) throws
                                                                                                    ValidationFault, F {
        super.present(action);
        return this;
    }

    /**
     * Если {@code present()}, то потребляет {@code get()} с помощью {@code consumer}.
     *
     * @param consumer потребитель значений типа {@code int}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении {@code get()} с помощью
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see IntegerConsumer
     * @see #get()
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull ImmutableInteger present(final @NonNull IntegerConsumer<F> consumer) throws
                                                                                                               ValidationFault,
                                                                                                               F {
        Validator.nonNull(consumer, "The passed consumer");
        if (present()) consumer.consume(get());
        return this;
    }

    /**
     * Если {@code this == object}, то возвращает {@code true}.
     * <p>
     * Если {@code object instanceof ImmutableInteger that}, то возвращает
     * {@code contains == that.contains && (!contains || value == that.value)}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @see #contains
     * @see #value
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean equals(final @Nullable Object object) {
        return this == object || object instanceof final @NonNull ImmutableInteger that && contains == that.contains &&
                                 (!contains || value == that.value);
    }

}
