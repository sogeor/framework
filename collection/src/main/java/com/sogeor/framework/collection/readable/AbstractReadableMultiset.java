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

package com.sogeor.framework.collection.readable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.collection.AbstractMultiset;

/**
 * Представляет собой абстрактное читаемое мультимножество элементов.
 *
 * @param <T> тип элементов.
 *
 * @see AbstractIterator
 * @since 1.0.0-RC1
 */
public abstract class AbstractReadableMultiset<T> extends AbstractMultiset<T> implements ReadableMultiset<T> {

    /**
     * Создаёт экземпляр.
     *
     * @since 1.0.0-RC1
     */
    protected AbstractReadableMultiset() {}

    /**
     * @return Новый итератор элементов этого мультимножества.
     *
     * @implSpec Если {@code !empty()}, то возвращаемый итератор должен находится в определённом состоянии, а также его
     * текущим элементом должен быть первый элемент этого мультимножества.
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    public abstract @NonNull AbstractIterator<T> iterator();

    /**
     * Представляет собой абстрактный итератор элементов абстрактного читаемого мультимножества.
     *
     * @param <T> тип элементов.
     *
     * @see AbstractReadableMultiset
     * @since 1.0.0-RC1
     */
    public abstract static class AbstractIterator<T> extends AbstractMultiset.AbstractIterator<T> implements ReadableMultiset.Iterator<T> {

        /**
         * Создаёт экземпляр.
         *
         * @since 1.0.0-RC1
         */
        protected AbstractIterator() {}

    }

}
