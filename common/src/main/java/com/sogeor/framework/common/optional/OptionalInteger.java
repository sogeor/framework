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

package com.sogeor.framework.common.optional;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.common.optional.immutable.ImmutableInteger;
import com.sogeor.framework.common.optional.mutable.MutableInteger;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.IntegerConsumer;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;

/**
 * Представляет собой обёртку над значением типа {@code int}.
 *
 * @see #present(Action)
 * @since 1.0.0-RC1
 */
public abstract sealed class OptionalInteger extends Optional permits ImmutableInteger, MutableInteger {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    protected OptionalInteger() {}

    /**
     * Если {@code present()}, то возвращает текущее значение типа {@code int}, иначе — {@code 0}.
     *
     * @return Текущее значение типа {@code int} или {@code 0}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    public abstract int get();

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
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull OptionalInteger absent(final @NonNull Action<F> action) throws
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
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull OptionalInteger present(final @NonNull Action<F> action) throws
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
     * @see #present()
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull OptionalInteger present(final @NonNull IntegerConsumer<F> consumer) throws
                                                                                                              ValidationFault,
                                                                                                              F {
        Validator.nonNull(consumer, "The passed consumer");
        if (present()) consumer.consume(get());
        return this;
    }

    /**
     * @return {@code Objects.hashCode(get())}.
     *
     * @see #get()
     * @see Objects#hashCode(Object)
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public int hashCode() {
        return Objects.hashCode(get());
    }

    /**
     * Если {@code this == object}, то возвращает {@code true}.
     * <p>
     * Если {@code object instanceof OptionalInteger that}, то возвращает
     * {@code present() == that.present() && (absent() || get() == that.get())}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @see #present()
     * @see #absent()
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean equals(final @Nullable Object object) {
        return this == object || object instanceof final @NonNull OptionalInteger that && present() == that.present() &&
                                 (absent() || get() == that.get());
    }

    /**
     * @return {@code super.toString() + "{contains=" + present() + ",value=" + get() + '}'}.
     *
     * @see Object#toString()
     * @see #present()
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public @NonNull String toString() {
        return super.toString() + "{contains=" + present() + ",value=" + get() + '}';
    }

}
