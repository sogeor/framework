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
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.common.optional.Optional;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.function.Handler;
import com.sogeor.framework.function.Supplier;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Представляет собой изменяемую обёртку над объектом.
 * <p>
 * Изменяемая обёртка основана на паре блокировок для чтения и записи, из-за чего она способна обеспечить правильный
 * доступ к объекту и его существованию.
 *
 * @param <T> тип объекта.
 *
 * @since 1.0.0-RC1
 */
public final class Mutable<T> extends Optional<T> {

    /**
     * Содержит пару блокировок для чтения и записи.
     *
     * @since 1.0.0-RC1
     */
    private final @NonNull ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Содержит текущий объект.
     *
     * @since 1.0.0-RC1
     */
    private @Nullable T object;

    /**
     * Создаёт экземпляр на основе {@code null}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    private Mutable() {
        this(null);
    }

    /**
     * Создаёт экземпляр на основе {@code object}.
     *
     * @param object объект.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    private Mutable(final @Nullable T object) {
        this.object = object;
    }

    /**
     * Создаёт изменяемую обёртку над {@code null}.
     *
     * @param <T> тип {@code null}.
     *
     * @return Новую изменяемую обёртку над {@code null}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public static <T> @NonNull Mutable<T> empty() {
        return new Mutable<>();
    }

    /**
     * Создаёт изменяемую обёртку над {@code object}.
     *
     * @param object объект.
     * @param <T> тип {@code object}.
     *
     * @return Новую изменяемую обёртку над {@code object}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> new")
    public static <T> @NonNull Mutable<T> of(final @Nullable T object) {
        return new Mutable<>(object);
    }

    /**
     * @return {@code object}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public @Nullable T get() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var object = this.object;
        lock.unlock();
        return object;
    }

    /**
     * Задаёт {@code this.object} равным {@code object}.
     *
     * @param object объект.
     *
     * @return {@code this}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Contract("? -> this")
    public @NonNull Mutable<T> set(final @Nullable T object) {
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        this.object = object;
        lock.unlock();
        return this;
    }

    /**
     * Задаёт {@code object} равным {@code handler.handle(object)}.
     *
     * @param handler обработчик объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов
     * {@code handler}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see #object
     * @see Handler#handle(Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> set(
            final @NonNull Handler<? super T, ? extends T, F> handler) throws ValidationFault, F {
        Validator.nonNull(handler, "The passed handler");
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        try {
            object = handler.handle(get());
        } finally {
            lock.unlock();
        }
        return this;
    }

    /**
     * Задаёт {@code object} равным {@code supplier.get()}.
     *
     * @param supplier поставщик объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке объектов
     * {@code supplier}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see #object
     * @see Supplier#get()
     * @since 1.0.0-RC1
     */
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> set(final @NonNull Supplier<? extends T, F> supplier) throws
                                                                                                           ValidationFault,
                                                                                                           F {
        Validator.nonNull(supplier, "The passed supplier");
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        try {
            object = supplier.get();
        } finally {
            lock.unlock();
        }
        return this;
    }

    /**
     * @return {@code object == null}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean absent() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var result = object == null;
        lock.unlock();
        return result;
    }

    /**
     * @return {@code object != null}.
     *
     * @see #object
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean present() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var result = object != null;
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
     * @see Action
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> absent(final @NonNull Action<F> action) throws ValidationFault, F {
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
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> absent(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                            ValidationFault,
                                                                                                            F {
        Validator.nonNull(consumer, "The passed consumer");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (present()) lock.unlock();
        else try {
            consumer.consume(null);
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
     * @see Action
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> present(final @NonNull Action<F> action) throws ValidationFault,
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
    @Override
    @Contract("!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> present(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                             ValidationFault,
                                                                                                             F {
        Validator.nonNull(consumer, "The passed consumer");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (absent()) lock.unlock();
        else try {
            consumer.consume(get());
        } finally {
            lock.unlock();
        }
        return this;
    }

}
