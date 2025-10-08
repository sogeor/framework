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
import com.sogeor.framework.common.optional.OptionalChar;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.CharConsumer;
import com.sogeor.framework.function.CharSupplier;
import com.sogeor.framework.function.CharToCharHandler;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Представляет собой изменяемую обёртку над значением типа {@code char}.
 * <p>
 * Изменяемая обёртка основана на паре блокировок для чтения и записи, поэтому она обеспечивает правильный доступ к
 * значению типа {@code char} и его существованию даже в многопоточной среде.
 *
 * @since 1.0.0-RC1
 */
public final class MutableChar extends OptionalChar {

    /**
     * Содержит пару блокировок для чтения и записи.
     *
     * @since 1.0.0-RC1
     */
    private final @NonNull ReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * Если текущее значение типа {@code char} существует, то содержит {@code true}, иначе — {@code false}.
     *
     * @since 1.0.0-RC1
     */
    private boolean contains;

    /**
     * Содержит текущее значение типа {@code char}.
     *
     * @since 1.0.0-RC1
     */
    private char value;

    /**
     * Создаёт экземпляр без значения типа {@code char}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    private MutableChar() {
        contains = false;
        value = 0;
    }

    /**
     * Создаёт экземпляр на основе {@code value}.
     *
     * @param value значение типа {@code char}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    private MutableChar(final char value) {
        contains = true;
        this.value = value;
    }

    /**
     * Создаёт изменяемую обёртку без значения типа {@code char}.
     *
     * @return Новую изменяемую обёртку без значения типа {@code char}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public static @NonNull MutableChar empty() {
        return new MutableChar();
    }

    /**
     * Создаёт изменяемую обёртку над {@code object}.
     *
     * @param value значение типа {@code char}.
     *
     * @return Новую изменяемую обёртку над {@code value}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public static @NonNull MutableChar of(final char value) {
        return new MutableChar(value);
    }

    /**
     * {@inheritDoc}
     *
     * @return Текущее значение типа {@code char} или {@code 0}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public char get() {
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
    public @NonNull MutableChar reset() {
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        contains = false;
        value = 0;
        lock.unlock();
        return this;
    }

    /**
     * Задаёт {@code this.value} равным {@code value}.
     *
     * @param value значение типа {@code char}.
     *
     * @return {@code this}.
     *
     * @see #value
     * @since 1.0.0-RC1
     */
    @Contract("? -> this")
    public @NonNull MutableChar set(final char value) {
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
     * @param handler обработчик значений типа {@code char}.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате значений
     * типа {@code char} {@code handler}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #value
     * @see CharToCharHandler#handle(char)
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull MutableChar set(final @NonNull CharToCharHandler<F> handler) throws
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
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке значений типа {@code char}
     * {@code supplier}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #value
     * @see CharSupplier#get()
     * @since 1.0.0-RC1
     */
    @Experimental
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull MutableChar set(final @NonNull CharSupplier<F> supplier) throws
                                                                                                   ValidationFault, F {
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
     * Если текущее значение типа {@code char} не существует, то возвращает {@code true}, иначе — {@code false}.
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
     * Если текущее значение типа {@code char} существует, то возвращает {@code true}, иначе — {@code false}.
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
    public <F extends Throwable> @NonNull MutableChar absent(final @NonNull Action<F> action) throws ValidationFault,
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
    public <F extends Throwable> @NonNull MutableChar present(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                      F {
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
     * @param consumer потребитель значений типа {@code char}.
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
    public <F extends Throwable> @NonNull MutableChar present(final @NonNull CharConsumer<F> consumer) throws
                                                                                                       ValidationFault,
                                                                                                       F {
        super.present(consumer);
        return this;
    }

    /**
     * Если {@code this == object}, то возвращает {@code true}.
     * <p>
     * Если {@code object instanceof MutableChar that}, то возвращает
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
        return this == object || object instanceof final @NonNull MutableChar that && present() == that.present() &&
                                 (absent() || get() == that.get());
    }

}
