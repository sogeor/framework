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

package com.sogeor.framework.common.optional.mutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.Experimental;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.common.optional.OptionalBoolean;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.BooleanConsumer;
import com.sogeor.framework.function.BooleanSupplier;
import com.sogeor.framework.function.BooleanToBooleanHandler;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Представляет собой изменяемую обёртку над значением типа {@code boolean}.
 * <p>
 * Изменяемая обёртка основана на паре блокировок для чтения и записи, поэтому она обеспечивает правильный доступ к
 * значению типа {@code boolean} и его существованию даже в многопоточной среде.
 *
 * @since 1.0.0-RC1
 */
public final class MutableBoolean extends OptionalBoolean {

    /**
     * Содержит пару блокировок для чтения и записи.
     *
     * @since 1.0.0-RC1
     */
    private final @NonNull ReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * Если текущее значение типа {@code boolean} существует, то содержит {@code true}, иначе — {@code false}.
     *
     * @since 1.0.0-RC1
     */
    private boolean contains;

    /**
     * Содержит текущее значение типа {@code boolean}.
     *
     * @since 1.0.0-RC1
     */
    private boolean value;

    /**
     * Создаёт экземпляр без значения типа {@code boolean}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    private MutableBoolean() {
        contains = false;
        value = false;
    }

    /**
     * Создаёт экземпляр на основе {@code value}.
     *
     * @param value значение типа {@code boolean}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    private MutableBoolean(final boolean value) {
        contains = true;
        this.value = value;
    }

    /**
     * Создаёт изменяемую обёртку без значения типа {@code boolean}.
     *
     * @return Новую изменяемую обёртку без значения типа {@code boolean}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public static @NonNull MutableBoolean empty() {
        return new MutableBoolean();
    }

    /**
     * Создаёт изменяемую обёртку над {@code object}.
     *
     * @param value значение типа {@code boolean}.
     *
     * @return Новую изменяемую обёртку над {@code value}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public static @NonNull MutableBoolean of(final boolean value) {
        return new MutableBoolean(value);
    }

    /**
     * {@inheritDoc}
     *
     * @return Текущее значение типа {@code boolean} или {@code 0}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean get() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var value = this.value;
        lock.unlock();
        return value;
    }

    /**
     * Сбрасывает {@code value}.
     *
     * @return {@code this}.
     *
     * @see #value
     * @since 1.0.0-RC1
     */
    @Contract("? -> this")
    public @NonNull MutableBoolean reset() {
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        contains = false;
        value = false;
        lock.unlock();
        return this;
    }

    /**
     * Задаёт {@code this.value} равным {@code value}.
     *
     * @param value значение типа {@code boolean}.
     *
     * @return {@code this}.
     *
     * @see #value
     * @since 1.0.0-RC1
     */
    @Contract("? -> this")
    public @NonNull MutableBoolean set(final boolean value) {
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        contains = true;
        this.value = value;
        lock.unlock();
        return this;
    }

    /**
     * Задаёт {@code value} равным {@code handler.handle(value)}.
     *
     * @param handler обработчик значений типа {@code boolean}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате значений
     * типа {@code boolean} {@code handler}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #value
     * @see BooleanToBooleanHandler#handle(boolean)
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull MutableBoolean set(final @NonNull BooleanToBooleanHandler<F> handler) throws
                                                                                                                ValidationFault,
                                                                                                                F {
        Validator.nonNull(handler, "The passed handler");
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        try {
            value = handler.handle(value);
            contains = true;
        } finally {
            lock.unlock();
        }
        return this;
    }

    /**
     * Задаёт {@code value} равным {@code supplier.get()}.
     *
     * @param supplier поставщик объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа
     * {@code boolean} {@code supplier}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #value
     * @see BooleanSupplier#get()
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull MutableBoolean set(final @NonNull BooleanSupplier<F> supplier) throws
                                                                                                         ValidationFault,
                                                                                                         F {
        Validator.nonNull(supplier, "The passed supplier");
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        try {
            value = supplier.get();
            contains = true;
        } finally {
            lock.unlock();
        }
        return this;
    }

    /**
     * Если текущее значение типа {@code boolean} не существует, то возвращает {@code true}, иначе — {@code false}.
     *
     * @return {@code !contains}.
     *
     * @see #contains
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean absent() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var result = !contains;
        lock.unlock();
        return result;
    }

    /**
     * Если текущее значение типа {@code boolean} существует, то возвращает {@code true}, иначе — {@code false}.
     *
     * @return {@code contains}.
     *
     * @see #contains
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean present() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var result = contains;
        lock.unlock();
        return result;
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
    public <F extends Throwable> @NonNull MutableBoolean absent(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                        F {
        Validator.nonNull(action, "The passed action");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (present()) lock.unlock();
        else try {
            action.perform();
        } finally {
            lock.unlock();
        }
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
    public <F extends Throwable> @NonNull MutableBoolean present(final @NonNull Action<F> action) throws
                                                                                                  ValidationFault, F {
        Validator.nonNull(action, "The passed action");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (absent()) lock.unlock();
        else try {
            action.perform();
        } finally {
            lock.unlock();
        }
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param consumer потребитель значений типа {@code boolean}.
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
    public <F extends Throwable> @NonNull MutableBoolean present(final @NonNull BooleanConsumer<F> consumer) throws
                                                                                                             ValidationFault,
                                                                                                             F {
        super.present(consumer);
        return this;
    }

    /**
     * Если {@code this == object}, то возвращает {@code true}.
     * <p>
     * Если {@code object instanceof MutableBoolean that}, то возвращает
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
        return this == object || object instanceof final @NonNull MutableBoolean that && present() == that.present() &&
                                 (absent() || get() == that.get());
    }

}
