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
import com.sogeor.framework.common.optional.OptionalShort;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.ShortConsumer;
import com.sogeor.framework.throwable.fault.singleton.SingletonCreationFault;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой неизменяемую обёртку над значением типа {@code short}.
 *
 * @since 1.0.0-RC1
 */
public final class ImmutableShort extends OptionalShort {

    /**
     * Содержит неизменяемую обёртку без значения типа {@code short}.
     *
     * @see #ImmutableShort()
     * @since 1.0.0-RC1
     */
    private static final @NonNull ImmutableShort EMPTY = new ImmutableShort();

    /**
     * Если текущее значение типа {@code short} существует, то содержит {@code true}, иначе — {@code false}.
     *
     * @since 1.0.0-RC1
     */
    private final boolean contains;

    /**
     * Содержит текущее значение типа {@code short}.
     *
     * @since 1.0.0-RC1
     */
    private final short value;

    /**
     * Если {@code EMPTY == null}, то создаёт экземпляр без значения типа {@code short}, иначе генерирует
     * {@linkplain SingletonCreationFault проверяемый программный сбой} с
     * {@linkplain SingletonCreationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе имени этого класса.
     *
     * @throws SingletonCreationFault второй экземпляр этого класса без значения типа {@code short} не должен быть
     * создан.
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    @SuppressWarnings("ConstantValue")
    private ImmutableShort() throws SingletonCreationFault {
        if (EMPTY == null) {
            contains = false;
            value = 0;
            return;
        }
        throw new SingletonCreationFault(SingletonCreationFault.TEMPLATE_MESSAGE.formatted("the ImmutableShort class"));
    }

    /**
     * Создаёт экземпляр на основе {@code value}.
     *
     * @param value значение типа {@code short}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    private ImmutableShort(final short value) {
        contains = true;
        this.value = value;
    }

    /**
     * @return Неизменяемую обёртку без значения типа {@code short}.
     *
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @Contract("-> $!null")
    public static @NonNull ImmutableShort empty() {
        return EMPTY;
    }

    /**
     * Создаёт неизменяемую обёртку над {@code value}.
     *
     * @param value значение типа {@code short}.
     *
     * @return Новую неизменяемую обёртку над {@code value}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public static @NonNull ImmutableShort of(final short value) {
        return new ImmutableShort(value);
    }

    /**
     * {@inheritDoc}
     *
     * @return Текущее значение типа {@code short} или {@code 0}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> $value")
    public short get() {
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
    public <F extends Throwable> @NonNull ImmutableShort absent(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                        F {
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
    public <F extends Throwable> @NonNull ImmutableShort present(final @NonNull Action<F> action) throws
                                                                                                  ValidationFault, F {
        super.present(action);
        return this;
    }

    /**
     * Если {@code present()}, то потребляет {@code get()} с помощью {@code consumer}.
     *
     * @param consumer потребитель значений типа {@code short}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении {@code get()} с помощью
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see ShortConsumer
     * @see #get()
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull ImmutableShort present(final @NonNull ShortConsumer<F> consumer) throws
                                                                                                           ValidationFault,
                                                                                                           F {
        Validator.nonNull(consumer, "The passed consumer");
        if (present()) consumer.consume(get());
        return this;
    }

    /**
     * Если {@code this == object}, то возвращает {@code true}.
     * <p>
     * Если {@code object instanceof ImmutableShort that}, то возвращает
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
        return this == object || object instanceof final @NonNull ImmutableShort that && contains == that.contains &&
                                 (!contains || value == that.value);
    }

}
