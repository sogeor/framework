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

package com.sogeor.framework.collection.readable;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.collection.Collection;
import com.sogeor.framework.function.IntHandler;
import com.sogeor.framework.validation.Validator;

/**
 * Представляет собой читаемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @since 1.0.0-RC1
 */
public interface ReadableCollection<T> extends Collection {

    /**
     * @param element элемент.
     *
     * @return Если {@code element} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("? -> value")
    boolean contains(final @Nullable T element);

    /**
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> @NonNull T @NonNull [] array(final @NonNull IntHandler<T[], F> allocator) throws F {
        Validator.nonNull(allocator, "The passed allocator");
        return array(0, (int) size(), allocator);
    }

    /**
     * @since 1.0.0-RC1
     */
    @Contract("")
    default <F extends Throwable> @NonNull T @NonNull [] array(final long from, final long to,
                                                               final @NonNull IntHandler<T[], F> allocator) throws F {
        final var length = (int) Validator.notOutside(to - from, 0, Integer.MAX_VALUE,
                                                      "The difference between the passed from and to indexes", "0",
                                                      "max value of the int type");
        Validator.nonNull(allocator, "The passed allocator");
        return allocator.handle(length);
    }

}
