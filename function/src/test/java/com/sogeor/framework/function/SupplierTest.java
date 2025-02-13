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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class SupplierTest {

    @Test
    void methodDirect() {
        final @NonNull var object = new Object();
        final @NonNull var supplier = Supplier.<Object, ImaginaryFault>direct(object);
        assertNotNull(supplier);

        assertEquals(object, supplier.get());
    }

    @Test
    void methodOf() {
        final @NonNull var object = new Object();
        final @NonNull var supplier = Supplier.<Object, ImaginaryFault>of(() -> object);
        assertNotNull(supplier);

        assertEquals(object, supplier.get());
    }

    @Test
    void methodPassed() {
        final @NonNull var object = new Object();
        final @NonNull var supplier = Supplier.of(() -> {
            throw new RuntimeException();
        }).passed(object);
        assertNotNull(supplier);

        assertEquals(object, supplier.get());
    }

    @Test
    void methodSupplied() {
        final @NonNull var object = new Object();
        final @NonNull var supplier = Supplier.of(() -> {
            throw new RuntimeException();
        }).supplied(Supplier.direct(object));
        assertNotNull(supplier);

        assertEquals(object, supplier.get());
    }

}
