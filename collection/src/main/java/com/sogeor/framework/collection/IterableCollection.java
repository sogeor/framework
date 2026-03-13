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
import com.sogeor.framework.annotation.Nullable;

/**
 * Представляет собой итерируемую коллекцию элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface IterableCollection extends Collection {

    /**
     * Создаёт и возвращает итератор этой коллекции в неопределённом состоянии.
     *
     * @return Новый итератор этой коллекции в неопределённом состоянии.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Iterator iterator();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    IterableCollection clone();

    /**
     * Представляет собой итератор итерируемой коллекции.
     * <p>
     * Каждый итератор находиться либо в определённом, либо в неопределённом состоянии. В определённом состоянии
     * итератор содержит информацию о текущем элементе коллекции, а в неопределённом состоянии нет. Для того чтобы
     * определить состояние итератора можно использовать следующие методы: {@link #determined()} и
     * {@link #undetermined()}, — а чтобы перейти в неопределённое состояние можно воспользоваться методом
     * {@link #reset()}. Также в итераторе предусмотрены методы для перехода к какому-то элементу: {@link #previous()} и
     * {@link #next()}, {@link #start()} и {@link #end()}, — каждый из которых способен перевести итератор в
     * определённое состояние, но только при наличии хотя бы одного элемента в коллекции.
     *
     * @see IterableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator {

        /**
         * Если {@link #undetermined()}, то пытается перейти к последнему элементу коллекции этого итератора и, если
         * удалось, то есть этот итератор перешёл в определённое состояние, возвращает {@code true}, иначе —
         * {@code false}.
         * <p>
         * Если {@link #before()}, то пытается перейти к элементу коллекции этого итератора, который расположен перед
         * текущим элементом этого итератора, то есть к предыдущему элементу, и, если удалось, возвращает {@code true},
         * иначе — {@code false}.
         *
         * @return Если удалось перейти к какому-то элементу коллекции этого итератора, то {@code true}, иначе
         * {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean previous();

        /**
         * Если {@link #undetermined()}, то пытается перейти к первому элементу коллекции этого итератора и, если
         * удалось, то есть этот итератор перешёл в определённое состояние, возвращает {@code true}, иначе —
         * {@code false}.
         * <p>
         * Если {@link #after()}, то пытается перейти к элементу коллекции этого итератора, который расположен после
         * текущего элемента этого итератора, то есть к следующему элементу, и, если удалось, возвращает {@code true},
         * иначе — {@code false}.
         *
         * @return Если удалось перейти к какому-то элементу коллекции этого итератора, то {@code true}, иначе
         * {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean next();

        /**
         * Если {@link #first()}, то возвращает {@code true}, иначе пытается перейти к первому элементу коллекции этого
         * итератора и, если удалось, возвращает {@code true}, иначе — {@code false}.
         *
         * @return Если этот итератор имеет текущий элемент и он является первым элементом коллекции этого итератора, то
         * {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean start();

        /**
         * Если {@link #last()}, то возвращает {@code true}, иначе пытается перейти к последнему элементу коллекции
         * этого итератора и, если удалось, возвращает {@code true}, иначе — {@code false}.
         *
         * @return Если этот итератор имеет текущий элемент и он является последним элементом коллекции этого итератора,
         * то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean end();

        /**
         * @return Если этот итератор имеет текущий элемент и перед ним в коллекции есть другой элемент, то есть он не
         * первый, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean before();

        /**
         * @return Если этот итератор имеет текущий элемент и после него в коллекции есть другой элемент, то есть он не
         * последний, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean after();

        /**
         * @return Если этот итератор имеет текущий элемент и перед ним в коллекции нет другого элемента, то есть он
         * первый, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        default boolean first() {
            return !before();
        }

        /**
         * @return Если этот итератор имеет текущий элемент и после него в коллекции нет другого элемента, то есть он
         * последний, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        default boolean last() {
            return !after();
        }

        /**
         * @return Если этот итератор имеет текущий элемент, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean exists();

        /**
         * @return Если этот итератор находится в определённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean determined();

        /**
         * @return Если этот итератор находится в неопределённом состоянии, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        default boolean undetermined() {
            return !determined();
        }

        /**
         * Если {@link #determined()}, то изменяет состояние этого итератора на неопределённое и возвращает
         * {@code true}, иначе — {@code false}.
         *
         * @return Если состояние этого итератора было изменено на неопределённое, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        boolean reset();

        /**
         * Вычисляет хеш-код на основе коллекции, состояния и текущего элемента этого итератора и возвращает его.
         *
         * @return Хеш-код на основе коллекции, состояния и текущего элемента этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> $value")
        int hashCode();

        /**
         * Если {@code object} является итератором, то сравнивает его с этим итератором, а именно убеждается, что оба
         * итератора относятся к одной и то же коллекции, находятся в одном и том же состоянии и имеют один и тот же
         * текущий элемент. Если все условия истинны, то есть {@code object} эквивалентен этому итератору, то возвращает
         * {@code true}, иначе — {@code false}.
         *
         * @param object объект.
         *
         * @return Если {@code object} эквивалентен этому итератору, то {@code true}, иначе {@code false}.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("null -> false; !null -> value")
        boolean equals(final @Nullable Object object);

        /**
         * Представляет состояние и текущий элемент этого итератора в виде строки и возвращает её.
         *
         * @return Строка, представляющая состояние и текущий элемент этого итератора.
         *
         * @since 1.0.0-RC1
         */
        @Override
        @Contract("-> value")
        @NonNull
        String toString();

    }

}
