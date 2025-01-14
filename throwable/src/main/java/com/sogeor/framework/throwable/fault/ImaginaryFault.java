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

package com.sogeor.framework.throwable.fault;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.throwable.failure.utility.UtilityCreationFailure;

/**
 * Представляет собой непроверяемую программную неисправность, ни один экземпляр которой не должен быть создан.
 *
 * @since 1.0.0-RC1
 */
public final class ImaginaryFault extends UncheckedFault {

    /**
     * Генерирует {@linkplain UtilityCreationFailure проверяемый программный сбой} с
     * {@linkplain UtilityCreationFailure#TEMPLATE_MESSAGE шаблонным сообщением} на основе имени этого класса (1).
     *
     * @throws UtilityCreationFailure экземпляр [1] не должен быть создан.
     * @since 1.0.0-RC1
     */
    @Contract("-> failure")
    private ImaginaryFault() throws UtilityCreationFailure {
        throw new UtilityCreationFailure(UtilityCreationFailure.TEMPLATE_MESSAGE.formatted("the ImaginaryFault class"));
    }

}
