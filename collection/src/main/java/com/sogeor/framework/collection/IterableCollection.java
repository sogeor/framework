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
import com.sogeor.framework.function.Consumer;
import com.sogeor.framework.function.IntHandler;
import com.sogeor.framework.function.Predicate;
import com.sogeor.framework.validation.LessValidationFault;
import com.sogeor.framework.validation.NullValidationFault;
import com.sogeor.framework.validation.ValidationFault;
import com.sogeor.framework.validation.Validator;

import java.util.Objects;

/**
 * Представляет собой итерируемую коллекцию элементов.
 *
 * @param <T> тип элементов.
 *
 * @see Iterator
 * @since 1.0.0-RC1
 */
public interface IterableCollection<T> extends Collection {

    /**
     * Если {@code empty()}, то возвращает {@code false}, иначе потребляет каждый из элементов этой коллекции с помощью
     * {@code consumer}, пока не возникнет программный сбой или неисправность. Если удалось, то возвращает
     * {@code true}.
     *
     * @param consumer потребитель элементов.
     *
     * @return {@code !empty()}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code consumer} не должен быть {@code null}.
     * @throws F неудачное потребление элемента с помощью {@code consumer}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> boolean iterate(final @NonNull Consumer<? super T, F> consumer) throws
                                                                                                  ValidationFault, F {
        Validator.nonNull(consumer, "The passed consumer");
        if (empty()) return false;
        final @NonNull var it = iterator();
        while (it.next()) consumer.consume(it.element());
        return true;
    }

    /**
     * Если {@code empty()}, то возвращает {@code true}, иначе оценивает каждый из элементов этой коллекции с помощью
     * {@code predicate}, пока не возникнет программный сбой или неисправность и все оценки равны {@code true}. Если
     * хотя бы одна оценка равна {@code false}, то возвращает {@code false}, иначе — {@code true}.
     *
     * @param predicate предикат элементов.
     *
     * @return Если {@code empty()}, то {@code true}, иначе {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> boolean all(final @NonNull Predicate<? super T, F> predicate) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(predicate, "The passed predicate");
        if (empty()) return true;
        final @NonNull var it = iterator();
        while (it.next()) if (!predicate.evaluate(it.element())) return false;
        return true;
    }

    /**
     * Если {@code empty()}, то возвращает {@code false}, иначе оценивает каждый из элементов этой коллекции с помощью
     * {@code predicate}, пока не возникнет программный сбой или неисправность и все оценки равны {@code false}. Если
     * хотя бы одна оценка равна {@code true}, то возвращает {@code true}, иначе — {@code false}.
     *
     * @param predicate предикат элементов.
     *
     * @return Если {@code empty()}, то {@code false}, иначе {@code true} или {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code predicate} не должен быть {@code null}.
     * @throws F неудачное оценивание элемента с помощью {@code predicate}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> boolean any(final @NonNull Predicate<? super T, F> predicate) throws ValidationFault,
                                                                                                       F {
        Validator.nonNull(predicate, "The passed predicate");
        if (empty()) return false;
        final @NonNull var it = iterator();
        while (it.next()) if (predicate.evaluate(it.element())) return true;
        return false;
    }

    /**
     * @param element элемент.
     *
     * @return Если {@code element} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code element} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> value; null -> ?")
    default boolean contains(final T element) throws ValidationFault {
        if (!nullable()) Validator.nonNull(element, "The passed element");
        return !empty() && any(e -> Objects.equals(e, element));
    }

    /**
     * @param elements элементы.
     *
     * @return Если каждый из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("!null -> ?; null -> fault")
    default boolean containsAll(final T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        if (elements.length > size()) return false;
        for (final var element : elements) if (!contains(element)) return false;
        return true;
    }

    /**
     * @param elements элементы.
     *
     * @return Если каждый из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default boolean containsAll(final @NonNull IterableCollection<T> elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements == this || elements.size() <= size() && elements.all(this::contains);
    }

    /**
     * @param elements элементы.
     *
     * @return Если любой из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("unchecked")
    @Contract("!null -> ?; null -> fault")
    default boolean containsAny(final T @NonNull ... elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        if (empty()) return false;
        for (final var element : elements) if (contains(element)) return true;
        return true;
    }

    /**
     * @param elements элементы.
     *
     * @return Если любой из {@code elements} является элементом этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code elements} не должны быть {@code null}, или если {@code !nullable()}, то ни
     * один из {@code elements} не должен быть {@code null}.
     * @since 1.0.0-RC1
     */
    @Contract("!null -> ?; null -> fault")
    default boolean containsAny(final @NonNull IterableCollection<T> elements) throws ValidationFault {
        Validator.nonNull(elements, "The passed elements");
        return elements == this || !empty() && elements.any(this::contains);
    }

    /**
     * Создаёт массив элементов, помещает в него элементы этой коллекции и возвращает его.
     *
     * @return Элементы этой коллекции в виде массива.
     *
     * @implNote Если {@code sequenced()}, то в возвращаемый массив помещаются элементы этой коллекции, начиная с
     * первого, иначе выбор элементов может быть случайным. Также возвращаемый массив может содержать не более
     * 2147483647 элемента этой коллекции.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("ConstantValue")
    @Contract("-> value")
    default Object @NonNull [] array() {
        final var array = new Object[(int) size()];
        if (array.length == 0) return array;
        final @NonNull var it = iterator();
        for (var i = 0; i < array.length && Validator.isTrue(it.next()); ++i) array[i] = it.element();
        return array;
    }

    /**
     * Создаёт массив элементов с помощью {@code allocator}, помещает в него элементы этой коллекции и возвращает его.
     *
     * @param allocator функция создания массива элементов.
     *
     * @return Элементы этой коллекции в виде массива.
     *
     * @throws ValidationFault неудачная валидация.
     * @throws NullValidationFault {@code allocator} или созданный ей массив элементов не должны быть {@code null}.
     * @throws LessValidationFault длина созданного с помощью {@code allocator} массива элементов не должна быть меньше
     * {@code size()}.
     * @implNote Если {@code sequenced()}, то в возвращаемый массив помещаются элементы этой коллекции, начиная с
     * первого, иначе выбор элементов может быть случайным. Также возвращаемый массив может содержать не более
     * 2147483647 элемента этой коллекции.
     * @since 1.0.0-RC1
     */
    @SuppressWarnings("ConstantValue")
    @Contract("!null -> ?; null -> fault")
    default <F extends Throwable> T @NonNull [] array(final @NonNull IntHandler<T[], F> allocator) throws
                                                                                                   ValidationFault, F {
        Validator.nonNull(allocator, "The passed allocator");
        final var size = (int) size();
        final var array = Validator.nonNull(allocator.handle(size), "An array allocated by the passed allocator");
        Validator.notLess(array.length, size, "A length of an array allocated by the passed allocator",
                          "The size of this collection");
        if (size == 0) return array;
        final @NonNull var it = iterator();
        for (var i = 0; i < size && Validator.isTrue(it.next()); ++i) array[i] = it.element();
        return array;
    }

    /**
     * Создаёт и возвращает итератор этой коллекции в неопределённом состоянии.
     *
     * @return Новый итератор этой коллекции в неопределённом состоянии.
     *
     * @since 1.0.0-RC1
     */
    @Contract("-> new")
    @NonNull
    Iterator<T> iterator();

    /**
     * @return Копию этой коллекции.
     *
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> new")
    @NonNull
    IterableCollection<T> clone();

    /**
     * Если {@code !empty()}, то вычисляет хеш-код этой коллекции на основе её элементов и возвращает его, иначе если
     * {@code sequenced()}, то возвращает {@code 1}, иначе — {@code 0}.
     *
     * @return Хеш-код этой коллекции.
     *
     * @implSpec Если {@code sequenced()}, то при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * var result = 1;
     * for (var it = iterator(); it.next(); ) {
     *     result = 31 * result + Objects.hashCode(it.element());
     * }
     * return result;
     * }
     * </pre>
     * В противном случае при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * var result = 0;
     * for (var it = iterator(); it.next(); ) {
     *     result += Objects.hashCode(it.element());
     * }
     * return result;
     * }
     * </pre>
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    int hashCode();

    /**
     * {@inheritDoc}
     *
     * @param object объект.
     *
     * @return Если {@code object} эквивалентен этой коллекции, то {@code true}, иначе {@code false}.
     *
     * @implSpec Если {@code sequenced()}, то при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * if (object == this) return true;
     * if (!(object instanceof IterableCollection<?> that) || size() != that.size()) return false;
     * if (empty()) return true;
     * if (!that.sequenced()) return containsAll((IterableCollection<T>) that);
     * var it = iterator();
     * var it_ = that.iterator();
     * while (it.next() && it_.next()) {
     *     if (!Objects.equals(it.element(), it_.element())) {
     *         return false;
     *     }
     * }
     * return true;
     * }
     * </pre>
     * В противном случае при переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * return object == this || object instanceof IterableCollection<?> that && (empty() || size() != that.size() && containsAll((IterableCollection<T>) that));
     * }
     * </pre>
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("!null -> value; null -> false")
    boolean equals(final @Nullable Object object);

    /**
     * Представляет элементы этой коллекции в виде строки и возвращает её.
     *
     * @return Строка, представляющая элементы этой коллекции.
     *
     * @implSpec При переопределении должен соблюдаться следующий алгоритм:
     * <pre>
     * {@code
     * var builder = new StringBuilder().append('{');
     * if (!empty()) for ( var it = iterator(); it.next(); ) {
     *     builder.append(it.element());
     *     if (it.after()) builder.append(", ");
     * }
     * return builder.append('}').toString();
     * }
     * </pre>
     * @since 1.0.0-RC1
     */
    @Override
    @Contract("-> value")
    @NonNull
    String toString();

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
     * @param <T> тип элементов.
     *
     * @see IterableCollection
     * @since 1.0.0-RC1
     */
    interface Iterator<T> {

        /**
         * Если {@link #exists()}, то возвращает текущий элемент, иначе — {@code null}.
         *
         * @return Текущий элемент или {@code null}.
         *
         * @since 1.0.0-RC1
         */
        @Contract("-> value")
        @Nullable
        T element();

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
