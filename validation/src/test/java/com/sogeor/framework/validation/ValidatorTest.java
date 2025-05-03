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

package com.sogeor.framework.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SuppressWarnings("DataFlowIssue")
final class ValidatorTest {

    @Test
    @DisplayName("validate(boolean)")
    void validateBoolean() {
        assertDoesNotThrow(() -> Validator.validate(true));
        assertThrowsExactly(ValidationFault.class, () -> Validator.validate(false));
    }

    @Test
    @DisplayName("validate(boolean, String)")
    void validateBooleanString() {
        assertDoesNotThrow(() -> Validator.validate(true, "The test value"));
        assertThrowsExactly(ValidationFault.class, () -> Validator.validate(false, "The test value"));
    }

    @Test
    @DisplayName("isNull(Object)")
    void isNullObject() {
        assertDoesNotThrow(() -> Validator.isNull(null));
        assertThrowsExactly(NonNullValidationFault.class, () -> Validator.isNull(this));
    }

    @Test
    @DisplayName("isNull(Object, String)")
    void isNullObjectString() {
        assertDoesNotThrow(() -> Validator.isNull(null, "The test value"));
        assertThrowsExactly(NonNullValidationFault.class, () -> Validator.isNull(this, "The test value"));
    }

    @Test
    @DisplayName("nonNull(Object)")
    void nonNullObject() {
        assertDoesNotThrow(() -> Validator.nonNull(this));
        assertThrowsExactly(NullValidationFault.class, () -> Validator.nonNull(null));
    }

    @Test
    @DisplayName("nonNull(Object, String)")
    void nonNullObjectString() {
        assertDoesNotThrow(() -> Validator.nonNull(this, "The test value"));
        assertThrowsExactly(NullValidationFault.class, () -> Validator.nonNull(null, "The test value"));
    }

    @Test
    @DisplayName("isFalse(boolean)")
    void isFalseBoolean() {
        assertDoesNotThrow(() -> Validator.isFalse(false));
        assertThrowsExactly(TrueValidationFault.class, () -> Validator.isFalse(true));
    }

    @Test
    @DisplayName("isFalse(boolean, String)")
    void isFalseBooleanString() {
        assertDoesNotThrow(() -> Validator.isFalse(false, "The test value"));
        assertThrowsExactly(TrueValidationFault.class, () -> Validator.isFalse(true, "The test value"));
    }

    @Test
    @DisplayName("isTrue(boolean)")
    void isTrueBoolean() {
        assertDoesNotThrow(() -> Validator.isTrue(true));
        assertThrowsExactly(FalseValidationFault.class, () -> Validator.isTrue(false));
    }

    @Test
    @DisplayName("isTrue(boolean, String)")
    void isTrueBooleanString() {
        assertDoesNotThrow(() -> Validator.isTrue(true, "The test value"));
        assertThrowsExactly(FalseValidationFault.class, () -> Validator.isTrue(false, "The test value"));
    }

    @Test
    @DisplayName("isNaN(float)")
    void isNaNFloat() {
        assertDoesNotThrow(() -> Validator.isNaN(Float.NaN));
        assertThrowsExactly(NumberValidationFault.class, () -> Validator.isNaN(0f));
    }

    @Test
    @DisplayName("isNaN(float, String)")
    void isNaNFloatString() {
        assertDoesNotThrow(() -> Validator.isNaN(Float.NaN, "The test value"));
        assertThrowsExactly(NumberValidationFault.class, () -> Validator.isNaN(0f, "The test value"));
    }

    @Test
    @DisplayName("isNaN(double)")
    void isNaNDouble() {
        assertDoesNotThrow(() -> Validator.isNaN(Double.NaN));
        assertThrowsExactly(NumberValidationFault.class, () -> Validator.isNaN(0d));
    }

    @Test
    @DisplayName("isNaN(double, String)")
    void isNaNDoubleString() {
        assertDoesNotThrow(() -> Validator.isNaN(Double.NaN, "The test value"));
        assertThrowsExactly(NumberValidationFault.class, () -> Validator.isNaN(0d, "The test value"));
    }

    @Test
    @DisplayName("isInfinite(float)")
    void isInfiniteFloat() {
        assertDoesNotThrow(() -> Validator.isInfinite(Float.POSITIVE_INFINITY));
        assertDoesNotThrow(() -> Validator.isInfinite(Float.NEGATIVE_INFINITY));
        assertThrowsExactly(FiniteValidationFault.class, () -> Validator.isInfinite(0f));
    }

    @Test
    @DisplayName("isInfinite(float, String)")
    void isInfiniteFloatString() {
        assertDoesNotThrow(() -> Validator.isInfinite(Float.POSITIVE_INFINITY, "The test value"));
        assertDoesNotThrow(() -> Validator.isInfinite(Float.NEGATIVE_INFINITY, "The test value"));
        assertThrowsExactly(FiniteValidationFault.class, () -> Validator.isInfinite(0f, "The test value"));
    }

    @Test
    @DisplayName("isInfinite(double)")
    void isInfiniteDouble() {
        assertDoesNotThrow(() -> Validator.isInfinite(Double.POSITIVE_INFINITY));
        assertDoesNotThrow(() -> Validator.isInfinite(Double.NEGATIVE_INFINITY));
        assertThrowsExactly(FiniteValidationFault.class, () -> Validator.isInfinite(0d));
    }

    @Test
    @DisplayName("isInfinite(double, String)")
    void isInfiniteDoubleString() {
        assertDoesNotThrow(() -> Validator.isInfinite(Double.POSITIVE_INFINITY, "The test value"));
        assertDoesNotThrow(() -> Validator.isInfinite(Double.NEGATIVE_INFINITY, "The test value"));
        assertThrowsExactly(FiniteValidationFault.class, () -> Validator.isInfinite(0d, "The test value"));
    }

    @Test
    @DisplayName("isFinite(float)")
    void isFiniteFloat() {
        assertDoesNotThrow(() -> Validator.isFinite(0f));
        assertThrowsExactly(InfiniteValidationFault.class, () -> Validator.isFinite(Float.POSITIVE_INFINITY));
        assertThrowsExactly(InfiniteValidationFault.class, () -> Validator.isFinite(Float.NEGATIVE_INFINITY));
    }

    @Test
    @DisplayName("isFinite(float, String)")
    void isFiniteFloatString() {
        assertDoesNotThrow(() -> Validator.isFinite(0f, "The test value"));
        assertThrowsExactly(InfiniteValidationFault.class,
                            () -> Validator.isFinite(Float.POSITIVE_INFINITY, "The test value"));
        assertThrowsExactly(InfiniteValidationFault.class,
                            () -> Validator.isFinite(Float.NEGATIVE_INFINITY, "The test value"));
    }

    @Test
    @DisplayName("isFinite(double)")
    void isFiniteDouble() {
        assertDoesNotThrow(() -> Validator.isFinite(0d));
        assertThrowsExactly(InfiniteValidationFault.class, () -> Validator.isFinite(Double.POSITIVE_INFINITY));
        assertThrowsExactly(InfiniteValidationFault.class, () -> Validator.isFinite(Double.NEGATIVE_INFINITY));
    }

    @Test
    @DisplayName("isFinite(double, String)")
    void isFiniteDoubleString() {
        assertDoesNotThrow(() -> Validator.isFinite(0d, "The test value"));
        assertThrowsExactly(InfiniteValidationFault.class,
                            () -> Validator.isFinite(Double.POSITIVE_INFINITY, "The test value"));
        assertThrowsExactly(InfiniteValidationFault.class,
                            () -> Validator.isFinite(Double.NEGATIVE_INFINITY, "The test value"));
    }

    @Test
    @DisplayName("equal(Object, Object)")
    void equalObjectObject() {
        assertDoesNotThrow(() -> Validator.equal(this, this));
        assertThrowsExactly(NonEqualValidationFault.class, () -> Validator.equal(this, null));
        assertThrowsExactly(NonEqualValidationFault.class, () -> Validator.equal(this, null));
    }

    @Test
    @DisplayName("equal(Object, Object, String, String)")
    void equalObjectObjectStringString() {
        assertDoesNotThrow(() -> Validator.equal(this, this, "The primary test value", "the secondary test value"));
        assertThrowsExactly(NonEqualValidationFault.class,
                            () -> Validator.equal(this, null, "The primary test value", "the secondary test value"));
        assertThrowsExactly(NonEqualValidationFault.class,
                            () -> Validator.equal(this, null, "The primary test value", "the secondary test value"));
    }

    @Test
    @DisplayName("equal(boolean, boolean)")
    void equalBooleanBoolean() {
        assertDoesNotThrow(() -> Validator.equal(false, false));
        assertDoesNotThrow(() -> Validator.equal(true, true));
        assertThrowsExactly(NonEqualValidationFault.class, () -> Validator.equal(false, true));
        assertThrowsExactly(NonEqualValidationFault.class, () -> Validator.equal(true, false));
    }

    @Test
    @DisplayName("equal(boolean, boolean, String, String)")
    void equalBooleanBooleanStringString() {
        assertDoesNotThrow(() -> Validator.equal(false, false, "The primary test value", "the secondary test value"));
        assertDoesNotThrow(() -> Validator.equal(true, true, "The primary test value", "the secondary test value"));
        assertThrowsExactly(NonEqualValidationFault.class,
                            () -> Validator.equal(false, true, "The primary test value", "the secondary test value"));
        assertThrowsExactly(NonEqualValidationFault.class,
                            () -> Validator.equal(true, false, "The primary test value", "the secondary test value"));
    }

    // TODO: finish it

}
