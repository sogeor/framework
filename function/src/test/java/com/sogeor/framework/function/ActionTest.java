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

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 1.0.0-RC1
 */
final class ActionTest { // TODO: rewrite it

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("empty()")
    void methodEmpty() {
        final @NonNull var action = Action.empty();
        assertNotNull(action);
        assertDoesNotThrow(action::perform);
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("of(Action)")
    void methodOf() {
        final @NonNull var action = Action.empty();
        assertSame(action, Action.of(action));
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("and(Action)")
    void methodAnd() {
        final @NonNull var primaryStatus = new AtomicBoolean();
        final @NonNull var secondaryStatus = new AtomicBoolean();
        final @NonNull var action = Action.of(() -> primaryStatus.set(true)).and(() -> secondaryStatus.set(true));
        assertDoesNotThrow(action::perform);
        assertTrue(primaryStatus.get());
        assertTrue(secondaryStatus.get());
        assertThrowsExactly(RuntimeException.class, action.and(() -> {
            throw new RuntimeException();
        })::perform);
    }

    /**
     * @since 1.0.0-RC1
     */
    @Test
    @DisplayName("or(Action)")
    void methodOr() {
        final @NonNull var status = new AtomicBoolean();
        final @NonNull var action = Action.of(() -> {
            throw new RuntimeException();
        });
        assertThrowsExactly(RuntimeException.class, action::perform);
        assertDoesNotThrow(action.or(() -> status.set(true))::perform);
        assertTrue(status.get());
    }

}
