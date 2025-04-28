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
import com.sogeor.framework.function.Action;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой обёртку над объектом или значением.
 * <p>
 * Если объект равен {@code null}, то он не существует, иначе — существует. Также и со значением: оно может
 * существовать, а может и не существовать. Обёртка содержит методы для удобной обработки как существования объекта или
 * значения, так и самого объекта или значения. Например, метод {@code present(Action)} выполняет переданное в него
 * действие, если объект или значение существует.
 * <p>
 * Обёртка над объектом или значением бывает неизменяемой и изменяемой. Если она неизменяема, то и объект или значение,
 * и его существование неизменяемо. Однако изменяемая обёртка позволяет добавлять, заменять и удалять свой объект или
 * значение. Данный класс является родительским как для неизменяемой, так и для изменяемой обёртки, поэтому нет гарантий
 * неизменяемости объекта или значения и его существования.
 *
 * @see #present(Action)
 * @since 1.0.0-RC1
 */
public abstract sealed class Optional permits OptionalInteger, OptionalObject {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    protected Optional() {}

    /**
     * @return Если текущий объект или значение не существует, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    public boolean absent() {
        return !present();
    }

    /**
     * @return Если текущий объект или значение существует, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    public abstract boolean present();

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
    public <F extends Throwable> @NonNull Optional absent(final @NonNull Action<F> action) throws ValidationFault, F {
        Validator.nonNull(action, "The passed action");
        if (absent()) action.perform();
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
    public <F extends Throwable> @NonNull Optional present(final @NonNull Action<F> action) throws ValidationFault, F {
        Validator.nonNull(action, "The passed action");
        if (present()) action.perform();
        return this;
    }

}
