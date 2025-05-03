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

package com.sogeor.framework.function;

import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.throwable.fault.ImaginaryFault;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class HandlerTest { // TODO: rewrite it

    @Test
    void methodDirect() {
        final @NonNull var object = new Object();
        final @NonNull var handler = Handler.<Object, Object, ImaginaryFault>direct(object);
        assertNotNull(handler);

        assertEquals(object, handler.handle(object));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodOf() {
        final @NonNull var data = new AtomicInteger();

        final @NonNull var handler = Handler.<AtomicInteger, AtomicInteger, ImaginaryFault>of(object -> {
            object.set(1);
            return object;
        });
        assertNotNull(handler);

        assertEquals(data, handler.handle(data));
        assertEquals(1, data.get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodAnd() {
        final @NonNull var data = new AtomicInteger();

        final @NonNull var handler = Handler.<AtomicInteger, AtomicInteger, ImaginaryFault>of(object -> {
            object.set(1);
            return object;
        }).and(object -> {
            object.incrementAndGet();
            return object;
        });
        assertNotNull(handler);

        assertEquals(data, handler.handle(data));
        assertEquals(2, data.get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodOr() {
        final @NonNull var data = new AtomicInteger();

        final @NonNull var handler = Handler.<AtomicInteger, AtomicInteger, ImaginaryFault>of(object -> {
            object.set(1);
            return object;
        }).or(object -> {
            object.set(2);
            return object;
        });
        assertNotNull(handler);

        assertEquals(data, handler.handle(data));
        assertEquals(1, data.get());
    }

}
