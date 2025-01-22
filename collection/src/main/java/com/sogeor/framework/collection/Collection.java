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

package com.sogeor.framework.collection;

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;

/**
 * Представляет собой коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface Collection<T> {

    /**
     * @return Итератор элементов.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * @return Размер коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    long size();

    /**
     * @return Если элементы не существуют, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    default boolean empty() {
        return size() == 0;
    }

    /**
     * Представляет собой итератор элементов коллекции.
     *
     * @param <T> тип элементов.
     *
     * @implSpec Каждый итератор должен быть способен переходить к элементу, расположенному либо перед текущим, либо
     * после него, либо к обоим из них.
     * <p>
     * Если итератор способен переходить к элементу, расположенному перед текущим, то он должен быть также способен
     * переходить к последнему. И наоборот, если итератор способен переходить к элементу, расположенному после текущего,
     * то он должен быть также способен переходить к первому. Это необходимо для корректной итерации, например:
     * <pre>
     * {@code
     * void example(final @NonNull Iterator<?> it) {
     *     if (it.canNext()) { // it.canStart() == true
     *         for (it.start(); it.after(); it.next()) {
     *             // ...
     *         }
     *     } else { // it.canPrevious() == true && it.canEnd() == true
     *         for (it.end(); it.before(); it.previous()) {
     *             // ...
     *         }
     *     }
     * }
     * }
     * </pre>
     * @see Collection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> {

        /**
         * Если {@linkplain #canStart() итератор не способен переходить к первому элементу}, то ничего не делает.
         * <p>
         * Если {@linkplain #first() текущий элемент не первый}, то переходит к первому элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #end()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> start();

        /**
         * Если {@linkplain #canPrevious() итератор не способен переходить к элементу, расположенному перед текущим}, то
         * ничего не делает.
         * <p>
         * Если {@linkplain #before() перед текущим элементом существует другой}, то переходит к нему.
         *
         * @return {@code this}.
         *
         * @see #next()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> previous();

        /**
         * Если {@linkplain #canNext() итератор не способен переходить к элементу, расположенному после текущего}, то
         * ничего не делает.
         * <p>
         * Если {@linkplain #after() после текущего элемента существует другой}, то переходит к нему.
         *
         * @return {@code this}.
         *
         * @see #previous()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> next();

        /**
         * Если {@linkplain #canEnd() итератор не способен переходить к последнему элементу}, то ничего не делает.
         * <p>
         * Если {@linkplain #last() текущий элемент не последний}, то переходит к последнему элементу, если он
         * существует.
         *
         * @return {@code this}.
         *
         * @see #start()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> end();

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
         *
         * @see #last()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean first();

        /**
         * @return Если перед текущим элементом существует другой, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean before();

        /**
         * @return Если после текущего элемента существует другой, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean after();

        /**
         * @return Если текущий элемент последний, то {@code true}, иначе {@code false}.
         *
         * @see #first()
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean last();

        /**
         * @return Если текущий элемент существует, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean current();

        /**
         * @return Если итератор способен переходить к первому элементу, то {@code true}, иначе {@code false}.
         *
         * @implSpec Если
         * {@linkplain #canNext() итератор способен переходить к элементу, расположенному после текущего}, то возвращает
         * {@code true}.
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canStart();

        /**
         * @return Если итератор способен переходить к элементу, расположенному перед текущим, то {@code true}, иначе
         * {@code false}.
         *
         * @implSpec Если
         * {@linkplain #canNext() итератор не способен переходить к элементу, расположенному после текущего}, то
         * возвращает {@code true}.
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canPrevious();

        /**
         * @return Если итератор способен переходить к элементу, расположенному после текущего, то {@code true}, иначе
         * {@code false}.
         *
         * @implSpec Если
         * {@linkplain #canPrevious() итератор не способен переходить к элементу, расположенному перед текущим}, то
         * возвращает {@code true}.
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canNext();

        /**
         * @return Если итератор способен переходить к последнему элементу, то {@code true}, иначе {@code false}.
         *
         * @implSpec Если
         * {@linkplain #canPrevious() итератор способен переходить к элементу, расположенному перед текущим}, то
         * возвращает {@code true}.
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canEnd();

    }

}
