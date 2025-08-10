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
import com.sogeor.framework.common.optional.OptionalObject;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.throwable.fault.singleton.SingletonCreationFault;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;

/**
 * Представляет собой неизменяемую обёртку над объектом.
 *
 * @param <T> тип объекта.
 *
 * @since 1.0.0-RC1
 */
public final class Immutable<T> extends OptionalObject<T> {

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
     * @throws SingletonCreationFault второй экземпляр этого класса на основе {@code null} не должен быть создан.
     * @see #EMPTY
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    @SuppressWarnings("ConstantValue")
    private Immutable() throws SingletonCreationFault {
        if (EMPTY != null)
            throw new SingletonCreationFault(SingletonCreationFault.TEMPLATE_MESSAGE.formatted("Immutable"));
        object = null;
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
    @Contract("!null -> new; null -> $!null")
    public static <T> @NonNull Immutable<T> of(final @Nullable T object) {
        return object == null ? empty() : new Immutable<>(object);
    }

    /**
     * {@inheritDoc}
     *
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
     * {@inheritDoc}
     *
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
     * {@inheritDoc}
     *
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
    public <F extends Throwable> @NonNull Immutable<T> absent(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                      F {
        super.absent(action);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param consumer потребитель объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении {@code null} с помощью
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Immutable<T> absent(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                              ValidationFault,
                                                                                                              F {
        super.absent(consumer);
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
    public <F extends Throwable> @NonNull Immutable<T> present(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                       F {
        super.present(action);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param consumer потребитель объектов.
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
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Immutable<T> present(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                               ValidationFault,
                                                                                                               F {
        super.present(consumer);
        return this;
    }

    /**
     * Если {@code this == object}, то возвращает {@code true}.
     * <p>
     * Если {@code object instanceof Immutable<?> that}, то возвращает {@code Objects.equals(object, that.object)},
     * иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @see #object
     * @see Objects#equals(Object, Object)
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean equals(final @Nullable Object object) {
        return this == object ||
               object instanceof final @NonNull Immutable<?> that && Objects.equals(object, that.object);
    }

}
