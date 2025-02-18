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

package com.sogeor.framework.common.mutable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.function.Action;
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.function.Handler;
import com.sogeor.framework.function.Supplier;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Представляет собой изменяемую обёртку над объектом.
 *
 * @param <T> тип объекта.
 *
 * @since 1.0.0-RC1
 */
public final class Mutable<T> {

    /**
     * Содержит пару блокировок для чтения и записи.
     *
     * @since 1.0.0-RC1
     */
    private final @NonNull ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Содержит объект.
     *
     * @since 1.0.0-RC1
     */
    private @Nullable T object;

    /**
     * Создаёт экземпляр на основе {@code null}.
     *
     * @see #Mutable(Object)
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
     * @see #Mutable()
     * @since 1.0.0-RC1
     */
    @Contract("$? -> new")
    private Mutable(final @Nullable T object) {
        this.object = object;
    }

    /**
     * Создаёт и возвращает изменяемую обёртку над {@code null}.
     *
     * @param <T> тип {@code null}.
     *
     * @return Новую изменяемую обёртку над {@code null}.
     *
     * @see #of(Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public static <T> @NonNull Mutable<T> empty() {
        return new Mutable<>();
    }

    /**
     * Создаёт и возвращает изменяемую обёртку над {@code object}.
     *
     * @param object объект.
     * @param <T> тип {@code object}.
     *
     * @return Новую изменяемую обёртку над {@code object}.
     *
     * @see #empty()
     * @since 1.0.0-RC1
     */
    @Contract("$? -> new")
    public static <T> @NonNull Mutable<T> of(final @Nullable T object) {
        return new Mutable<>(object);
    }

    /**
     * @return {@code this.object}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
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
     * @see #set(Handler)
     * @see #set(Supplier)
     * @since 1.0.0-RC1
     */
    @Contract("$? -> this")
    public @NonNull Mutable<T> set(final @Nullable T object) {
        final @NonNull var lock = this.lock.writeLock();
        lock.lock();
        this.object = object;
        lock.unlock();
        return this;
    }

    /**
     * Задаёт {@code this.object} равным {@code handler.handle(get())}.
     *
     * @param handler обработчик объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной обработке или возврате объектов
     * {@code supplier}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code handler} не должен быть {@code null}.
     * @see Handler#handle(Object)
     * @see #set(Object)
     * @see #set(Supplier)
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
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
     * Задаёт {@code this.object} равным {@code supplier.get()}.
     *
     * @param supplier поставщик объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачной поставке объектов
     * {@code supplier}.
     *
     * @return {@code this}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code supplier} не должен быть {@code null}.
     * @see Supplier#get()
     * @see #set(Object)
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
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
     * @return Если {@code get() == null}, то {@code true}, иначе {@code false}.
     *
     * @see #get()
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    public boolean absent() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var result = get() == null;
        lock.unlock();
        return result;
    }

    /**
     * @return Если {@code get() != null}, то {@code true}, иначе {@code false}.
     *
     * @see #get()
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    public boolean present() {
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        final @Nullable var result = get() != null;
        lock.unlock();
        return result;
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
    public <F extends Throwable> @NonNull Mutable<T> absent(final @NonNull Action<F> action) throws ValidationFault, F {
        Validator.nonNull(action, "The passed action");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (absent()) try {
            action.perform();
        } finally {
            lock.unlock();
        }
        else lock.unlock();
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
    public <F extends Throwable> @NonNull Mutable<T> absent(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                            ValidationFault,
                                                                                                            F {
        Validator.nonNull(consumer, "The passed consumer");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (absent()) try {
            consumer.consume(null);
        } finally {
            lock.unlock();
        }
        else lock.unlock();
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
    public <F extends Throwable> @NonNull Mutable<T> present(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                     F {
        Validator.nonNull(action, "The passed action");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (present()) try {
            action.perform();
        } finally {
            lock.unlock();
        }
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
    public <F extends Throwable> @NonNull Mutable<T> present(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                             ValidationFault,
                                                                                                             F {
        Validator.nonNull(consumer, "The passed consumer");
        final @NonNull var lock = this.lock.readLock();
        lock.lock();
        if (present()) try {
            consumer.consume(get());
        } finally {
            lock.unlock();
        }
        else lock.unlock();
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
     * @param object объект.
     *
     * @return Если {@code this} равно {@code object}, то {@code true}, иначе {@code false}.
     *
     * @see #get()
     * @see Objects#equals(Object, Object)
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public boolean equals(final @Nullable Object object) {
        return object instanceof final @NonNull Mutable<?> that && Objects.equals(get(), that.get());
    }

    /**
     * @return Строковое представление {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    public String toString() {
        return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode()) + "{object=" + object + '}';
    }

}
