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

import static org.junit.jupiter.api.Assertions.*;

final class ConsumerTest { // TODO: rewrite it

    @Test
    void methodEmpty() {
        final @NonNull var consumer = Consumer.empty();
        assertNotNull(consumer);

        assertDoesNotThrow(() -> consumer.consume(null));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodOf() {
        final @NonNull var data = new AtomicInteger();

        final @NonNull var consumer = Consumer.<AtomicInteger, ImaginaryFault>of(object -> object.set(1));
        assertNotNull(consumer);

        assertDoesNotThrow(() -> consumer.consume(data));
        assertEquals(1, data.get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodAnd() {
        final @NonNull var data = new AtomicInteger();

        final @NonNull var consumer = Consumer.<AtomicInteger, ImaginaryFault>of(object -> object.set(1))
                                              .and(object -> object.incrementAndGet());
        assertNotNull(consumer);

        assertDoesNotThrow(() -> consumer.consume(data));
        assertEquals(2, data.get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodOr() {
        final @NonNull var data = new AtomicInteger();

        final @NonNull var consumer = Consumer.<AtomicInteger, ImaginaryFault>of(object -> object.set(1))
                                              .or(object -> object.set(2));
        assertNotNull(consumer);

        assertDoesNotThrow(() -> consumer.consume(data));
        assertEquals(1, data.get());
    }

}
