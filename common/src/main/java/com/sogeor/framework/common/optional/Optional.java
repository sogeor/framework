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
import com.sogeor.framework.common.optional.immutable.Immutable;
import com.sogeor.framework.common.optional.mutable.Mutable;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;

/**
 * Представляет собой обёртку над объектом.
 * <p>
 * Если объект равен {@code null}, то он не существует, иначе — существует. Обёртка содержит методы для удобной
 * обработки как существования объекта, так и самого объекта. Например, метод {@code present(Action)} выполняет
 * переданное в него действие, если объект существует.
 * <p>
 * Обёртка над объектом бывает неизменяемой и изменяемой. Если она неизменяема, то и объект, и его существование
 * неизменяемо. Однако изменяемая обёртка позволяет добавлять, заменять и удалять свой объект. Данный класс является
 * родительским как для неизменяемой, так и для изменяемой обёртки, поэтому нет гарантий неизменяемости объекта и его
 * существования.
 *
 * @param <T> тип объекта.
 *
 * @see #present(Action)
 * @since 1.0.0-RC1
 */
public abstract sealed class Optional<T> permits Immutable, Mutable {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    protected Optional() {}

    /**
     * Если {@code present()}, то возвращает текущий объект, иначе — {@code null}.
     *
     * @return Текущий объект или {@code null}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    public abstract @Nullable T get();

    /**
     * @return Если текущий объект не существует, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    public boolean absent() {
        return !present();
    }

    /**
     * @return Если текущий объект существует, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    public boolean present() {
        return get() != null;
    }

    /**
     * Если {@code absent()}, то выполняет {@code action}.
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
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Optional<T> absent(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                     F {
        Validator.nonNull(action, "The passed action");
        if (absent()) action.perform();
        return this;
    }

    /**
     * Если {@code absent()}, то потребляет {@code null} с помощью {@code consumer}.
     *
     * @param consumer потребитель объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении {@code null} с помощью
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see Consumer
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Optional<T> absent(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                             ValidationFault,
                                                                                                             F {
        Validator.nonNull(consumer, "The passed consumer");
        if (absent()) consumer.consume(null);
        return this;
    }

    /**
     * Если {@code present()}, то выполняет {@code action}.
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
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Optional<T> present(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                      F {
        Validator.nonNull(action, "The passed action");
        if (present()) action.perform();
        return this;
    }

    /**
     * Если {@code present()}, то потребляет {@code get()} с помощью {@code consumer}.
     *
     * @param consumer потребитель объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении {@code get()} с помощью
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see Consumer
     * @see #get()
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Optional<T> present(final @NonNull Consumer<? super T, F> consumer) throws
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
     * Если {@code !(object instanceof Optional<?> that)}, то возвращает {@code false}.
     * <p>
     * Если {@code Objects.equals(get(), that.get())}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @see #get()
     * @see Objects#equals(Object, Object)
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean equals(final @Nullable Object object) {
        return this == object || object instanceof final @NonNull Optional<?> that && Objects.equals(get(), that.get());
    }

    /**
     * @return {@code super.toString() + "{object=" + get() + '}'}.
     *
     * @see Object#toString()
     * @see #get()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public @NonNull String toString() {
        return super.toString() + "{object=" + get() + '}';
    }

}
