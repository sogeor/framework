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

package com.sogeor.framework.collection;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;

/**
 * Представляет собой особенность (1) итератора элементов коллекции.
 *
 * @since 1.0.0-RC1
 */
public interface IteratorFeature {

    /**
     * Позволяет переходить к первому элементу, если он существует.
     *
     * @since 1.0.0-RC1
     */
    @NonNull
    IteratorFeature START_OPERATION = new Standard("START_OPERATION");

    /**
     * Позволяет переходить к предыдущему элементу, если он существует.
     *
     * @since 1.0.0-RC1
     */
    @NonNull
    IteratorFeature PREVIOUS_OPERATION = new Standard("PREVIOUS_OPERATION");

    /**
     * Позволяет переходить к следующему элементу, если он существует.
     *
     * @since 1.0.0-RC1
     */
    @NonNull
    IteratorFeature NEXT_OPERATION = new Standard("NEXT_OPERATION");

    /**
     * Позволяет переходить к последнему элементу, если он существует.
     *
     * @since 1.0.0-RC1
     */
    @NonNull
    IteratorFeature END_OPERATION = new Standard("END_OPERATION");

    /**
     * Позволяет переходить к последнему элементу, если он существует.
     *
     * @since 1.0.0-RC1
     */
    @NonNull
    IteratorFeature BEFORE_OPERATION = new Standard("BEFORE_OPERATION");

    /**
     * @return Имя {1}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> $value")
    @NonNull
    String name();

    /**
     * Представляет собой стандартную {1} (1).
     *
     * @since 1.0.0-RC1
     */
    class Standard implements IteratorFeature {

        /**
         * Содержит имя {1}.
         *
         * @since 1.0.0-RC1
         */
        private final @NonNull String name;

        /**
         * Создаёт экземпляр на основе {@code name}.
         *
         * @param name имя {1}.
         */
        protected Standard(final @NonNull String name) {
            this.name = name;
        }

        /**
         * @return Имя {1}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public @NonNull String name() {
            return name;
        }

        /**
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public int hashCode() {
            return name.hashCode();
        }

        /**
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        public boolean equals(final @NonNull Object object) {
            return this == object || object instanceof IteratorFeature that && name.equals(that.name());
        }

        /**
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        public @NonNull String toString() {
            return name;
        }

    }

}
