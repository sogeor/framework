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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 1.0.0-RC1
 */
final class ConditionTest {

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("direct(boolean)")
    void methodDirect() {
        final @NonNull var falseCondition = Condition.direct(false);
        assertNotNull(falseCondition);
        assertFalse(assertDoesNotThrow(falseCondition::compute));
        final @NonNull var trueCondition = Condition.direct(true);
        assertNotNull(trueCondition);
        assertTrue(assertDoesNotThrow(trueCondition::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("of(Condition)")
    void methodOf() {
        final var value = new Random().nextBoolean();
        final @NonNull var condition = Condition.of(() -> value);
        assertNotNull(condition);
        assertEquals(value, assertDoesNotThrow(condition::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("not()")
    void methodNot() {
        final @NonNull var condition = Condition.direct(false).not();
        assertTrue(assertDoesNotThrow(condition::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("and(Condition)")
    void methodAnd() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertFalse(assertDoesNotThrow(Condition.direct(false).and(falseCondition)::compute));
        assertFalse(assertDoesNotThrow(falseCondition.and(trueCondition)::compute));
        assertFalse(assertDoesNotThrow(trueCondition.and(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(Condition.direct(true).and(trueCondition)::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("nand(Condition)")
    void methodNand() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertTrue(assertDoesNotThrow(Condition.direct(false).nand(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(falseCondition.nand(trueCondition)::compute));
        assertTrue(assertDoesNotThrow(trueCondition.nand(falseCondition)::compute));
        assertFalse(assertDoesNotThrow(Condition.direct(true).nand(trueCondition)::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("or(Condition)")
    void methodOr() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertFalse(assertDoesNotThrow(Condition.direct(false).or(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(falseCondition.or(trueCondition)::compute));
        assertTrue(assertDoesNotThrow(trueCondition.or(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(Condition.direct(true).or(trueCondition)::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("nor(Condition)")
    void methodNor() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertTrue(assertDoesNotThrow(Condition.direct(false).nor(falseCondition)::compute));
        assertFalse(assertDoesNotThrow(falseCondition.nor(trueCondition)::compute));
        assertFalse(assertDoesNotThrow(trueCondition.nor(falseCondition)::compute));
        assertFalse(assertDoesNotThrow(Condition.direct(true).nor(trueCondition)::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("xnor(Condition)")
    void methodXnor() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertTrue(assertDoesNotThrow(Condition.direct(false).xnor(falseCondition)::compute));
        assertFalse(assertDoesNotThrow(falseCondition.xnor(trueCondition)::compute));
        assertFalse(assertDoesNotThrow(trueCondition.xnor(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(Condition.direct(true).xnor(trueCondition)::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("xor(Condition)")
    void methodXor() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertFalse(assertDoesNotThrow(Condition.direct(false).xor(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(falseCondition.xor(trueCondition)::compute));
        assertTrue(assertDoesNotThrow(trueCondition.xor(falseCondition)::compute));
        assertFalse(assertDoesNotThrow(Condition.direct(true).xor(trueCondition)::compute));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("imply(Condition)")
    void methodImply() {
        final @NonNull var falseCondition = Condition.direct(false);
        final @NonNull var trueCondition = Condition.direct(true);
        assertTrue(assertDoesNotThrow(Condition.direct(false).imply(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(falseCondition.imply(trueCondition)::compute));
        assertFalse(assertDoesNotThrow(trueCondition.imply(falseCondition)::compute));
        assertTrue(assertDoesNotThrow(Condition.direct(true).imply(trueCondition)::compute));
    }

}
