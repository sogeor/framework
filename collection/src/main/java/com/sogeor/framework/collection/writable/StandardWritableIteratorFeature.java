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

package com.sogeor.framework.collection.writable;

import com.sogeor.framework.collection.IteratorFeature;

/**
 * Представляет собой стандартную особенность итератора элементов записываемой коллекции.
 *
 * @since 1.0.0-RC1
 */
public enum StandardWritableIteratorFeature implements IteratorFeature {

    /**
     * Позволяет задать текущий элемент.
     *
     * @since 1.0.0-RC1
     */
    ELEMENT_OPERATION,

    /**
     * Позволяет вставить один или более элементов перед текущим.
     *
     * @since 1.0.0-RC1
     */
    INSERT_OPERATION,

    /**
     * Позволяет удалить текущий элемент, если он существует.
     *
     * @since 1.0.0-RC1
     */
    REMOVE_OPERATION

}
