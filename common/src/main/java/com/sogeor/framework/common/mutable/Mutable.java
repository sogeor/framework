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
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой изменяемую обёртку над {@linkplain #object объектом} (1).
 *
 * @param <T> тип [1].
 *
 * @since 1.0.0-RC1
 */
public final class Mutable<T> {

    /**
     * Содержит объект.
     *
     * @since 1.0.0-RC1
     */
    private volatile @Nullable T object;

    /**
     * Создаёт экземпляр на основе {@code null}.
     *
     * @see #Mutable(Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    private Mutable() {
        this.object = null;
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
     * Создаёт и возвращает {@linkplain #Mutable() экземпляр (1) на основе } {@code null} (2).
     *
     * @param <T> тип [2].
     *
     * @return [1].
     *
     * @see #of(Object)
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    public static <T> @NonNull Mutable<T> empty() {
        return new Mutable<>(null);
    }

    /**
     * Создаёт и возвращает {@linkplain #Mutable(Object) экземпляр (2) на основе} {@code object}.
     *
     * @param object объект (1).
     * @param <T> тип [1].
     *
     * @return [2].
     *
     * @see #empty()
     * @since 1.0.0-RC1
     */
    @Contract("$? -> new")
    public static <T> @NonNull Mutable<T> of(final @Nullable T object) {
        return new Mutable<>(object);
    }

    /**
     * Задаёт {@linkplain #object объект} равным [1].
     *
     * @param object объект (1).
     *
     * @return {@code this}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("$? -> this")
    public @NonNull Mutable<T> set(final @Nullable T object) {
        this.object = object;
        return this;
    }

    /**
     * @return {@linkplain #object Объект}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    public @Nullable T get() {
        return object;
    }

    /**
     * @return Если {@linkplain #object объект} отсутствует, то {@code true}, иначе {@code false}.
     *
     * @see #present()
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    public boolean absent() {
        return object == null;
    }

    /**
     * @return Если {@linkplain #object объект} присутствует, то {@code true}, иначе {@code false}.
     *
     * @see #absent()
     * @since 1.0.0-RC1
     */
    @Contract("-> ?")
    public boolean present() {
        return object != null;
    }

    /**
     * Если {@linkplain #absent()}, то выполняет [1].
     *
     * @param action действие (1).
     * @param <F> тип программного сбоя или неисправности, возникающей во время выполнения [1].
     *
     * @return {@code this}.
     *
     * @throws ValidationFault [1] не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> absent(final @NonNull Action<F> action) throws ValidationFault, F {
        Validator.nonNull(action, "action");
        if (absent()) action.perform();
        return this;
    }

    /**
     * Если {@linkplain #absent()}, то выполняет метод {@linkplain Consumer#consume(Object) consumer.consume(Object)} с
     * {@code null}.
     *
     * @param consumer потребитель (1) объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении [2].
     *
     * @return {@code this}.
     *
     * @throws ValidationFault [1] не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> absent(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                            ValidationFault,
                                                                                                            F {
        Validator.nonNull(consumer, "consumer");
        if (absent()) consumer.consume(null);
        return this;
    }

    /**
     * Если {@linkplain #present()}, то выполняет [1].
     *
     * @param action действие (1).
     * @param <F> тип программного сбоя или неисправности, возникающей во время выполнения [1].
     *
     * @return {@code this}.
     *
     * @throws ValidationFault [1] не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> present(final @NonNull Action<F> action) throws ValidationFault,
                                                                                                     F {
        Validator.nonNull(action, "action");
        if (present()) action.perform();
        return this;
    }

    /**
     * Если {@linkplain #present()}, то выполняет метод {@linkplain Consumer#consume(Object) consumer.consume(Object)} с
     * {@linkplain #object объектом}.
     *
     * @param consumer потребитель (1) объектов.
     * @param <F> тип программного сбоя или неисправности, возникающей при неудачном потреблении [2].
     *
     * @return {@code this}.
     *
     * @throws ValidationFault [1] не должно быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("$!null -> this; null -> fault")
    public <F extends Throwable> @NonNull Mutable<T> present(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                             ValidationFault,
                                                                                                             F {
        Validator.nonNull(consumer, "consumer");
        if (present()) consumer.consume(object);
        return this;
    }

}
