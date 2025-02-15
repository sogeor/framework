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

package com.sogeor.framework.common.immutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
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
public final class Immutable<T> {

    /**
     * Содержит неизменяемую обёртку над {@code null}.
     *
     * @see #Immutable()
     * @since 1.0.0-RC1
     */
    private static final @Nullable Immutable<?> EMPTY = new Immutable<>();

    /**
     * Содержит объект.
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
     * @see #Immutable(Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    private Immutable() throws SingletonCreationFault {
        if (EMPTY != null)
            throw new SingletonCreationFault(SingletonCreationFault.TEMPLATE_MESSAGE.formatted("the Immutable class"));
        this.object = null;
    }

    /**
     * Создаёт экземпляр на основе {@code object}.
     *
     * @param object объект.
     *
     * @throws ValidationFault {@code object} не должен быть {@code null}.
     * @see #Immutable()
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> new; null -> fault")
    private Immutable(final @NonNull T object) throws ValidationFault {
        this.object = Validator.nonNull(object, "The passed object");
    }

    /**
     * @param <T> тип {@code null}.
     *
     * @return Неизменяемую обёртку над {@code null}.
     *
     * @see #EMPTY
     * @see #of(Object)
     * @since 1.0.0-RC1
     */
    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    @Contract("-> $!null")
    public static <T> @NonNull Immutable<T> empty() {
        return (Immutable<T>) EMPTY;
    }

    /**
     * Если {@code object == null}, то получает с помощью {@code empty()} неизменяемую обёртку над {@code null} и
     * возвращает её, иначе создаёт и возвращает неизменяемую обёртку над {@code object}.
     *
     * @param object объект.
     * @param <T> тип {@code object}.
     *
     * @return Неизменяемую обёртку над {@code null} или новую неизменяемую обёртку над {@code object}.
     *
     * @see #empty()
     * @since 1.0.0-RC1
     */
    @Contract("null -> $!null; $!null -> new")
    public static <T> @NonNull Immutable<T> of(final @Nullable T object) {
        return object == null ? empty() : new Immutable<>(object);
    }

    /**
     * @return {@code this.object}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Contract("-> $?")
    public @Nullable T get() {
        return object;
    }

    /**
     * @return Если {@code get() == null}, то {@code true}, иначе {@code false}.
     *
     * @see #get()
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Contract("-> $?")
    public boolean absent() {
        return get() == null;
    }

    /**
     * @return Если {@code get() != null}, то {@code true}, иначе {@code false}.
     *
     * @see #get()
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Contract("-> $?")
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
     * @see Action#perform()
     * @see #absent()
     * @see #absent(Consumer)
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Immutable<T> absent(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                      F {
        Validator.nonNull(action, "The passed action");
        if (absent()) action.perform();
        return this;
    }

    /**
     * Если {@code absent()}, то выполняет метод {@code consumer.consume(null)}.
     *
     * @param consumer потребитель объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении объектов
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see Consumer#consume(Object)
     * @see #absent()
     * @see #absent(Action)
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Immutable<T> absent(final @NonNull Consumer<? super T, F> consumer) throws
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
     * @see Action#perform()
     * @see #present()
     * @see #present(Consumer)
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Immutable<T> present(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(action, "The passed action");
        if (present()) action.perform();
        return this;
    }

    /**
     * Если {@code present()}, то выполняет метод {@code consumer.consume(get())}.
     *
     * @param consumer потребитель объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении объектов
     * {@code consumer}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @see #get()
     * @see Consumer#consume(Object)
     * @see #present()
     * @see #present(Action)
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Immutable<T> present(final @NonNull Consumer<? super T, F> consumer) throws
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
     * @return Если {@code this} равно {@code object}, то {@code true}, иначе {@code false}.
     *
     * @see #get()
     * @see Objects#equals(Object, Object)
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean equals(final @Nullable Object object) {
        return object instanceof final @NonNull Immutable<?> that && Objects.equals(get(), that.get());
    }

    /**
     * @return Строковое представление {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + "{object=" + object + '}';
    }

}
