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
import com.sogeor.framework.annotation.Nullable;

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
     * @return Итератор элементов этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * @return Размер этой коллекции — количество её элементов.
     *
     * @implNote Стандартная реализация с оценкой временной сложности {@code Θ(n)} неэффективна и должна быть
     * переопределена.
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    default long size() {
        final @NonNull var it = iterator();
        var result = 0L;
        if (it.canNext()) for (it.start(); it.after(); it.next()) ++result;
        else for (it.end(); it.before(); it.previous()) ++result;
        return result;
    }

    /**
     * @return Если {@code size() == 0}, то {@code true}, иначе {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> value")
    default boolean empty() {
        return size() == 0;
    }

    /**
     * Если {@code size() == 0}, то возвращает {@code 1}, иначе вычисляет хеш-код этой коллекции на основе её элементов
     * и возвращает его.
     *
     * @return Хеш-код этой коллекции.
     *
     * @implSpec При переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * final @NonNull var it = iterator();
     * var result = 1;
     * if (it.canNext()) for (it.start(); it.after(); it.next()) result = 31 * result + Objects.hashCode(it.current());
     * else for (it.end(); it.before(); it.previous()) result = 31 * result + Objects.hashCode(it.current());
     * return result;
     * }
     * </pre>
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * Если {@code !(object instanceof Collection<?> collection) || size() != collection.size()}, то возвращает
     * {@code false}.
     * <p>
     * Если {@code empty() && collection.empty()}, то возвращает {@code true}.
     * <p>
     * Сравнивает все элементы этой коллекции с соответствующими им элементами из {@code collection}. Если все элементы
     * равны соответствующим им элементам, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param object объект.
     *
     * @return {@code true} или {@code false}.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("? -> value")
    boolean equals(final @Nullable Object object);

    /**
     * Представляет эту коллекцию и все её элементы в виде строки, а после возвращает её.
     *
     * @return Строковое представление этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    @NonNull
    String toString();

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
         * Если {@code !canStart()}, то ничего не делает.
         * <p>
         * Если {@code !first()}, то переходит к первому элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #first()
         * @see #canStart()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> start();

        /**
         * Если {@code !canPrevious()}, то ничего не делает.
         * <p>
         * Если {@code before()}, то переходит к элементу перед текущим.
         *
         * @return {@code this}.
         *
         * @see #before()
         * @see #canPrevious()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> previous();

        /**
         * Если {@code !canNext()}, то ничего не делает.
         * <p>
         * Если {@code after()}, то переходит к элементу после текущего.
         *
         * @return {@code this}.
         *
         * @see #after()
         * @see #canNext()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> next();

        /**
         * Если {@code !canEnd()}, то ничего не делает.
         * <p>
         * Если {@code !last()}, то переходит к последнему элементу, если он существует.
         *
         * @return {@code this}.
         *
         * @see #last()
         * @see #canEnd()
         * @since 1.0.0-RC1
         */
        @Contract("-> this")
        @NonNull
        Iterator<T> end();

        /**
         * @return Если текущий элемент первый, то {@code true}, иначе {@code false}.
         *
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
         * @return Если этот итератор способен переходить к первому элементу, то {@code true}, иначе {@code false}.
         *
         * @implSpec Если {@code canNext()}, то возвращает {@code true}.
         * @see #canNext()
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canStart();

        /**
         * @return Если этот итератор способен переходить к элементу, расположенному перед текущим, то {@code true},
         * иначе {@code false}.
         *
         * @implSpec Если {@code !canNext()}, то возвращает {@code true}.
         * @see #canNext()
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canPrevious();

        /**
         * @return Если этот итератор способен переходить к элементу, расположенному после текущего, то {@code true},
         * иначе {@code false}.
         *
         * @implSpec Если {@code !canPrevious()}, то возвращает {@code true}.
         * @see #canPrevious()
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canNext();

        /**
         * @return Если этот итератор способен переходить к последнему элементу, то {@code true}, иначе {@code false}.
         *
         * @implSpec Если {@code canPrevious()}, то возвращает {@code true}.
         * @see #canPrevious()
         * @since 1.0.0-RC1
         */
        @Contract("-> $value")
        boolean canEnd();

        /**
         * @return Хеш-код этого итератора.
         *
         * @see Object#hashCode()
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        int hashCode();

        /**
         * @param object объект.
         *
         * @return Если {@code this} равно {@code object}, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("$? -> $value")
        boolean equals(final @Nullable Object object);

        /**
         * @return Строковое представление этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        @NonNull
        String toString();

    }

}
