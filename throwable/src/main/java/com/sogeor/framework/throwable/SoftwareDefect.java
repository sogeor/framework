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

package com.sogeor.framework.throwable;

import com.sogeor.framework.annotation.Experimental;
import com.sogeor.framework.annotation.Null;

/**
 * Представляет собой программный дефект.
 *
 * @since 1.0.0-RC1
 */
@Experimental
public interface SoftwareDefect {

    /**
     * Содержит сообщение по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    @Null
    String DEFAULT_MESSAGE = null;

    /**
     * Содержит причину возникновения по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    @Null
    Throwable DEFAULT_CAUSE = null;

    /**
     * Содержит параметр подавления по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    boolean DEFAULT_SUPPRESSION = true;

    /**
     * Содержит параметр трассировки стека по умолчанию.
     *
     * @since 1.0.0-RC1
     */
    boolean DEFAULT_STACK_TRACE = true;

}
