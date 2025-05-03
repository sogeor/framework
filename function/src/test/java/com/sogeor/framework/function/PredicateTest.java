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

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

final class PredicateTest { // TODO: rewrite it

    @Test
    void methodDirect() {
        final @NonNull var truePredicate = Predicate.<Object, ImaginaryFault>direct(true);
        assertNotNull(truePredicate);

        assertTrue(truePredicate.evaluate(null));

        final @NonNull var falsePredicate = Predicate.<Object, ImaginaryFault>direct(false);
        assertNotNull(falsePredicate);

        assertFalse(falsePredicate.evaluate(null));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void methodOf() {
        final @NonNull var data = new AtomicBoolean();

        final @NonNull var predicate = Predicate.<AtomicBoolean, ImaginaryFault>of(AtomicBoolean::get);
        assertNotNull(predicate);

        assertFalse(predicate.evaluate(data));
    }

    @Test
    void methodNot() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(false).not();
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodAnd() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(true).and(Predicate.direct(true));
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodNand() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(false).nand(Predicate.direct(false));
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodOr() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(false).or(Predicate.direct(true));
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodNor() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(false).nor(Predicate.direct(false));
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodXnor() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(true).xnor(Predicate.direct(true));
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodXor() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(false).xor(Predicate.direct(true));
        assertNotNull(predicate);

        assertTrue(predicate.evaluate(null));
    }

    @Test
    void methodImply() {
        final @NonNull var predicate = Predicate.<Object, ImaginaryFault>direct(true).imply(Predicate.direct(false));
        assertNotNull(predicate);

        assertFalse(predicate.evaluate(null));
    }

}
