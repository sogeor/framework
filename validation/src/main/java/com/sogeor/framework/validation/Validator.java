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

import com.sogeor.framework.annotation.Contract;
import com.sogeor.framework.annotation.NonNull;
import com.sogeor.framework.annotation.Null;
import com.sogeor.framework.annotation.Nullable;
import com.sogeor.framework.throwable.failure.utility.UtilityCreationFailure;

/**
 * Представляет собой валидатор объектов и значений.
 *
 * @since 1.0.0-RC1
 */
public final class Validator {

    /**
     * Генерирует {@linkplain UtilityCreationFailure проверяемый программный сбой} с
     * {@linkplain UtilityCreationFailure#TEMPLATE_MESSAGE шаблонным сообщением} на основе имени этого класса.
     *
     * @throws UtilityCreationFailure экземпляр этого класса не должен быть создан.
     * @since 1.0.0-RC1
     */
    @Contract("-> failure")
    private Validator() throws UtilityCreationFailure {
        throw new UtilityCreationFailure(UtilityCreationFailure.TEMPLATE_MESSAGE.formatted("the Validator class"));
    }

    /**
     * Если {@code !value}, то генерирует {@linkplain ValidationFault непроверяемую программную неисправность} с
     * {@linkplain ValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param value логическое значение.
     *
     * @throws ValidationFault {@code value} должно быть {@code true}.
     * @apiNote Предназначен для удобной валидации {@code value} — результата вычисления условия.
     * @see #validate(boolean, String)
     * @since 1.0.0-RC1
     */
    @Contract("true -> true; false -> fault")
    public static void validate(final boolean value) throws ValidationFault {
        if (value) return;
        throw new ValidationFault();
    }

    /**
     * Если {@code !value}, то генерирует {@linkplain ValidationFault непроверяемую программную неисправность} с
     * {@code message}, или, если {@code message == null}, с
     * {@linkplain ValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param value логическое значение.
     * @param message сообщение о неисправности.
     *
     * @throws ValidationFault {@code value} должно быть {@code true}.
     * @apiNote Предназначен для удобной валидации {@code value} — результата вычисления условия.
     * @see #validate(boolean)
     * @since 1.0.0-RC1
     */
    @Contract("true, ? -> true; false, ? -> fault")
    public static void validate(final boolean value, final @Nullable String message) throws ValidationFault {
        if (value) return;
        if (message == null) throw new ValidationFault();
        throw new ValidationFault(message);
    }

    /**
     * Если {@code object == null}, то возвращает {@code null}, в противном случае генерирует
     * {@linkplain NonNullValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonNullValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param object объект.
     * @param <T> тип {@code object}.
     *
     * @return {@code null}.
     *
     * @throws NonNullValidationFault {@code object} должен быть {@code null}.
     * @see #isNull(Object, String)
     * @since 1.0.0-RC1
     */
    @Contract("null -> null; !null -> fault")
    public static <T> @Null T isNull(final @Nullable T object) throws NonNullValidationFault {
        if (object == null) return null;
        throw new NonNullValidationFault();
    }

    /**
     * Если {@code object == null}, то возвращает {@code null}, в противном случае генерирует
     * {@linkplain NonNullValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonNullValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code name}, или, если
     * {@code name == null}, с {@linkplain NonNullValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param object объект.
     * @param name имя {@code object}.
     * @param <T> тип {@code object}.
     *
     * @return {@code null}.
     *
     * @throws NonNullValidationFault {@code object} должен быть {@code null}.
     * @see #isNull(Object)
     * @since 1.0.0-RC1
     */
    @Contract("null, ? -> null; !null, ? -> fault")
    public static <T> @Null T isNull(final @Nullable T object, final @Nullable String name) throws
                                                                                            NonNullValidationFault {
        if (object == null) return null;
        if (name == null) throw new NonNullValidationFault();
        throw new NonNullValidationFault(NonNullValidationFault.TEMPLATE_MESSAGE.formatted(name));
    }

    /**
     * Если {@code object != null}, то возвращает {@code object}, в противном случае генерирует
     * {@linkplain NullValidationFault непроверяемую программную неисправность} с
     * {@linkplain NullValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param object объект.
     * @param <T> тип {@code object}.
     *
     * @return {@code object}.
     *
     * @throws NullValidationFault {@code object} не должен быть {@code null}.
     * @see #nonNull(Object, String)
     * @since 1.0.0-RC1
     */
    @Contract("!null -> 1; null -> fault")
    public static <T> @NonNull T nonNull(final @Nullable T object) throws NullValidationFault {
        if (object != null) return object;
        throw new NullValidationFault();
    }

    /**
     * Если {@code object != null}, то возвращает {@code object}, в противном случае генерирует
     * {@linkplain NullValidationFault непроверяемую программную неисправность} с
     * {@linkplain NullValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code name}, или, если
     * {@code name == null}, с {@linkplain NullValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param object объект.
     * @param name имя {@code object}.
     * @param <T> тип {@code object}.
     *
     * @return {@code object}.
     *
     * @throws NullValidationFault {@code object} не должен быть {@code null}.
     * @see #nonNull(Object)
     * @since 1.0.0-RC1
     */
    @Contract("!null, ? -> 1; null, ? -> fault")
    public static <T> @NonNull T nonNull(final @Nullable T object, final @Nullable String name) throws
                                                                                                NullValidationFault {
        if (object != null) return object;
        if (name == null) throw new NullValidationFault();
        throw new NullValidationFault(NullValidationFault.TEMPLATE_MESSAGE.formatted(name));
    }

    /**
     * Если {@code !value}, то возвращает {@code false}, в противном случае генерирует
     * {@linkplain TrueValidationFault непроверяемую программную неисправность} с
     * {@linkplain TrueValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param value логическое значение.
     *
     * @return {@code false}.
     *
     * @throws TrueValidationFault {@code value} должно быть {@code false}.
     * @see #isFalse(boolean, String)
     * @since 1.0.0-RC1
     */
    @Contract("false -> false; true -> fault")
    public static boolean isFalse(final boolean value) throws TrueValidationFault {
        if (!value) return false;
        throw new TrueValidationFault();
    }

    /**
     * Если {@code !value}, то возвращает {@code false}, в противном случае генерирует
     * {@linkplain TrueValidationFault непроверяемую программную неисправность} с
     * {@linkplain TrueValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code name}, или, если
     * {@code name == null}, с {@linkplain TrueValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param value логическое значение.
     * @param name имя {@code value}.
     *
     * @return {@code false}.
     *
     * @throws TrueValidationFault {@code value} должно быть {@code false}.
     * @see #isFalse(boolean)
     * @since 1.0.0-RC1
     */
    @Contract("false, ? -> false; true, ? -> fault")
    public static boolean isFalse(final boolean value, final @Nullable String name) throws TrueValidationFault {
        if (!value) return false;
        if (name == null) throw new TrueValidationFault();
        throw new TrueValidationFault(TrueValidationFault.TEMPLATE_MESSAGE.formatted(name));
    }

    /**
     * Если {@code value}, то возвращает {@code true}, в противном случае генерирует
     * {@linkplain FalseValidationFault непроверяемую программную неисправность} с
     * {@linkplain FalseValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param value логическое значение.
     *
     * @return {@code true}.
     *
     * @throws FalseValidationFault {@code value} должно быть {@code true}.
     * @see #isTrue(boolean, String)
     * @since 1.0.0-RC1
     */
    @Contract("true -> true; false -> fault")
    public static boolean isTrue(final boolean value) throws FalseValidationFault {
        if (value) return true;
        throw new FalseValidationFault();
    }

    /**
     * Если {@code value}, то возвращает {@code true}, в противном случае генерирует
     * {@linkplain FalseValidationFault непроверяемую программную неисправность} с
     * {@linkplain FalseValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code name}, или, если
     * {@code name == null}, с {@linkplain FalseValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param value логическое значение.
     * @param name имя {@code value}.
     *
     * @return {@code true}.
     *
     * @throws FalseValidationFault {@code value} должно быть {@code true}.
     * @see #isTrue(boolean)
     * @since 1.0.0-RC1
     */
    @Contract("true, ? -> true; false, ? -> fault")
    public static boolean isTrue(final boolean value, final @Nullable String name) throws FalseValidationFault {
        if (value) return true;
        if (name == null) throw new FalseValidationFault();
        throw new FalseValidationFault(FalseValidationFault.TEMPLATE_MESSAGE.formatted(name));
    }

    /**
     * Если {@code primaryObject} равен {@code secondaryObject}, то возвращает {@code primaryObject}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_OBJECTS_MESSAGE сообщением для объектов по умолчанию}.
     *
     * @param primaryObject первичный объект.
     * @param secondaryObject вторичный объект.
     * @param <T> тип {@code primaryObject} и {@code secondaryObject}.
     *
     * @return {@code primaryObject}.
     *
     * @throws NonEqualValidationFault {@code primaryObject} должен быть равен {@code secondaryObject}.
     * @see #equal(Object, Object, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("null, null -> null; null, !null -> fault; !null, null -> fault; !null, !null -> ?")
    public static <T> @Nullable T equal(final @Nullable T primaryObject, final @Nullable T secondaryObject) throws
                                                                                                            NonEqualValidationFault {
        if (primaryObject == secondaryObject || primaryObject != null && primaryObject.equals(secondaryObject))
            return primaryObject;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_OBJECTS_MESSAGE);
    }

    /**
     * Если {@code primaryObject} равен {@code secondaryObject}, то возвращает {@code primaryObject}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_OBJECTS_MESSAGE сообщением для объектов по умолчанию}.
     *
     * @param primaryObject первичный объект.
     * @param secondaryObject вторичный объект.
     * @param primaryName имя {@code primaryObject}.
     * @param secondaryName имя {@code secondaryObject}.
     * @param <T> тип {@code primaryObject} и {@code secondaryObject}.
     *
     * @return {@code primaryObject}.
     *
     * @throws NonEqualValidationFault {@code primaryObject} должен быть равен {@code secondaryObject}.
     * @see #equal(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract(
            "null, null, ?, ? -> null; null, !null, ?, ? -> fault; !null, null, ?, ? -> fault; !null, !null, ?, ? -> ?")
    public static <T> @Nullable T equal(final @Nullable T primaryObject, final @Nullable T secondaryObject,
                                        final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                                  NonEqualValidationFault {
        if (primaryObject == secondaryObject || primaryObject != null && primaryObject.equals(secondaryObject))
            return primaryObject;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_OBJECTS_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное логическое значение.
     * @param secondaryValue вторичное логическое значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(boolean, boolean, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("false, false -> false; true, true -> true; false, true -> fault; true, false -> fault")
    public static boolean equal(final boolean primaryValue, final boolean secondaryValue) throws
                                                                                          NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное логическое значение.
     * @param secondaryValue вторичное логическое значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(boolean, boolean)
     * @since 1.0.0-RC1
     */
    @Contract(
            "false, false, ?, ? -> false; true, true, ?, ? -> true; false, true, ?, ? -> fault; true, false, ?, ? -> fault")
    public static boolean equal(final boolean primaryValue, final boolean secondaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                          NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(byte, byte, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static byte equal(final byte primaryValue, final byte secondaryValue) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static byte equal(final byte primaryValue, final byte secondaryValue, final @Nullable String primaryName,
                             final @Nullable String secondaryName) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(short, short, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static short equal(final short primaryValue, final short secondaryValue) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static short equal(final short primaryValue, final short secondaryValue, final @Nullable String primaryName,
                              final @Nullable String secondaryName) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(int, int, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static int equal(final int primaryValue, final int secondaryValue) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static int equal(final int primaryValue, final int secondaryValue, final @Nullable String primaryName,
                            final @Nullable String secondaryName) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(long, long, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static long equal(final long primaryValue, final long secondaryValue) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static long equal(final long primaryValue, final long secondaryValue, final @Nullable String primaryName,
                             final @Nullable String secondaryName) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(float, float, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static float equal(final float primaryValue, final float secondaryValue) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static float equal(final float primaryValue, final float secondaryValue, final @Nullable String primaryName,
                              final @Nullable String secondaryName) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(double, double, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static double equal(final double primaryValue, final double secondaryValue) throws NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain NonEqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain NonEqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NonEqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NonEqualValidationFault {@code primaryValue} должно быть равно {@code secondaryValue}.
     * @see #equal(double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static double equal(final double primaryValue, final double secondaryValue,
                               final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                         NonEqualValidationFault {
        if (primaryValue == secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new NonEqualValidationFault(NonEqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new NonEqualValidationFault(
                NonEqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryObject} не равен {@code secondaryObject}, то возвращает {@code primaryObject}, в противном
     * случае генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_OBJECTS_MESSAGE сообщением для объектов по умолчанию}.
     *
     * @param primaryObject первичный объект.
     * @param secondaryObject вторичный объект.
     * @param <T> тип {@code primaryObject} и {@code secondaryObject}.
     *
     * @return {@code primaryObject}.
     *
     * @throws EqualValidationFault {@code primaryObject} не должен быть равен {@code secondaryObject}.
     * @see #nonEqual(Object, Object, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("null, !null -> null; !null, null -> 1; null, null -> fault; !null, !null -> ?")
    public static <T> @Nullable T nonEqual(final @Nullable T primaryObject, final @Nullable T secondaryObject) throws
                                                                                                               EqualValidationFault {
        if (primaryObject != secondaryObject && (primaryObject == null || !primaryObject.equals(secondaryObject)))
            return primaryObject;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_OBJECTS_MESSAGE);
    }

    /**
     * Если {@code primaryObject} не равен {@code secondaryObject}, то возвращает {@code primaryObject}, в противном
     * случае генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_OBJECTS_MESSAGE сообщением для объектов по умолчанию}.
     *
     * @param primaryObject первичный объект.
     * @param secondaryObject вторичный объект.
     * @param primaryName имя {@code primaryObject}.
     * @param secondaryName имя {@code secondaryObject}.
     * @param <T> тип {@code primaryObject} и {@code secondaryObject}.
     *
     * @return {@code primaryObject}.
     *
     * @throws EqualValidationFault {@code primaryObject} не должен быть равен {@code secondaryObject}.
     * @see #nonEqual(Object, Object)
     * @since 1.0.0-RC1
     */
    @Contract("null, !null, ?, ? -> null; !null, null, ?, ? -> 1; null, null, ?, ? -> fault; !null, !null, ?, ? -> ?")
    public static <T> @Nullable T nonEqual(final @Nullable T primaryObject, final @Nullable T secondaryObject,
                                           final @Nullable String primaryName,
                                           final @Nullable String secondaryName) throws EqualValidationFault {
        if (primaryObject != secondaryObject && (primaryObject == null || !primaryObject.equals(secondaryObject)))
            return primaryObject;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_OBJECTS_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное логическое значение.
     * @param secondaryValue вторичное логическое значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(boolean, boolean, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("false, true -> false; true, false -> true; false, false -> fault; true, true -> fault")
    public static boolean nonEqual(final boolean primaryValue, final boolean secondaryValue) throws
                                                                                             EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное логическое значение.
     * @param secondaryValue вторичное логическое значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(boolean, boolean)
     * @since 1.0.0-RC1
     */
    @Contract(
            "false, true, ?, ? -> false; true, false, ?, ? -> true; false, false, ?, ? -> fault; true, true, ?, ? -> fault")
    public static boolean nonEqual(final boolean primaryValue, final boolean secondaryValue,
                                   final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                             EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(byte, byte, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static byte nonEqual(final byte primaryValue, final byte secondaryValue) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static byte nonEqual(final byte primaryValue, final byte secondaryValue, final @Nullable String primaryName,
                                final @Nullable String secondaryName) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(short, short, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static short nonEqual(final short primaryValue, final short secondaryValue) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static short nonEqual(final short primaryValue, final short secondaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                           EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(int, int, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static int nonEqual(final int primaryValue, final int secondaryValue) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static int nonEqual(final int primaryValue, final int secondaryValue, final @Nullable String primaryName,
                               final @Nullable String secondaryName) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(long, long, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static long nonEqual(final long primaryValue, final long secondaryValue) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static long nonEqual(final long primaryValue, final long secondaryValue, final @Nullable String primaryName,
                                final @Nullable String secondaryName) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(float, float, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static float nonEqual(final float primaryValue, final float secondaryValue) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static float nonEqual(final float primaryValue, final float secondaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                           EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(double, double, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static double nonEqual(final double primaryValue, final double secondaryValue) throws EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
    }

    /**
     * Если {@code primaryValue} не равно {@code secondaryValue}, то возвращает {@code primaryValue}, в противном случае
     * генерирует {@linkplain EqualValidationFault непроверяемую программную неисправность} с
     * {@linkplain EqualValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain EqualValidationFault#DEFAULT_VALUES_MESSAGE сообщением для значений по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws EqualValidationFault {@code primaryValue} не должно быть равно {@code secondaryValue}.
     * @see #nonEqual(double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static double nonEqual(final double primaryValue, final double secondaryValue,
                                  final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                            EqualValidationFault {
        if (primaryValue != secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null)
            throw new EqualValidationFault(EqualValidationFault.DEFAULT_VALUES_MESSAGE);
        throw new EqualValidationFault(
                EqualValidationFault.TEMPLATE_MESSAGE.formatted("%s and %s".formatted(primaryName, secondaryName)));
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(byte, byte, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static byte less(final byte primaryValue, final byte secondaryValue) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        throw new NotLessValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static byte less(final byte primaryValue, final byte secondaryValue, final @Nullable String primaryName,
                            final @Nullable String secondaryName) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotLessValidationFault();
        throw new NotLessValidationFault(NotLessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(short, short, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static short less(final short primaryValue, final short secondaryValue) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        throw new NotLessValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static short less(final short primaryValue, final short secondaryValue, final @Nullable String primaryName,
                             final @Nullable String secondaryName) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotLessValidationFault();
        throw new NotLessValidationFault(NotLessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(int, int, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static int less(final int primaryValue, final int secondaryValue) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        throw new NotLessValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static int less(final int primaryValue, final int secondaryValue, final @Nullable String primaryName,
                           final @Nullable String secondaryName) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotLessValidationFault();
        throw new NotLessValidationFault(NotLessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(long, long, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static long less(final long primaryValue, final long secondaryValue) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        throw new NotLessValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static long less(final long primaryValue, final long secondaryValue, final @Nullable String primaryName,
                            final @Nullable String secondaryName) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotLessValidationFault();
        throw new NotLessValidationFault(NotLessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(float, float, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static float less(final float primaryValue, final float secondaryValue) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        throw new NotLessValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static float less(final float primaryValue, final float secondaryValue, final @Nullable String primaryName,
                             final @Nullable String secondaryName) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotLessValidationFault();
        throw new NotLessValidationFault(NotLessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(double, double, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static double less(final double primaryValue, final double secondaryValue) throws NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        throw new NotLessValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotLessValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotLessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotLessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotLessValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue}.
     * @see #less(double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static double less(final double primaryValue, final double secondaryValue,
                              final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                        NotLessValidationFault {
        if (primaryValue < secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotLessValidationFault();
        throw new NotLessValidationFault(NotLessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(byte, byte, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static byte more(final byte primaryValue, final byte secondaryValue) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        throw new NotMoreValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static byte more(final byte primaryValue, final byte secondaryValue, final @Nullable String primaryName,
                            final @Nullable String secondaryName) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotMoreValidationFault();
        throw new NotMoreValidationFault(NotMoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(short, short, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static short more(final short primaryValue, final short secondaryValue) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        throw new NotMoreValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static short more(final short primaryValue, final short secondaryValue, final @Nullable String primaryName,
                             final @Nullable String secondaryName) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotMoreValidationFault();
        throw new NotMoreValidationFault(NotMoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(int, int, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static int more(final int primaryValue, final int secondaryValue) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        throw new NotMoreValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static int more(final int primaryValue, final int secondaryValue, final @Nullable String primaryName,
                           final @Nullable String secondaryName) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotMoreValidationFault();
        throw new NotMoreValidationFault(NotMoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(long, long, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static long more(final long primaryValue, final long secondaryValue) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        throw new NotMoreValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static long more(final long primaryValue, final long secondaryValue, final @Nullable String primaryName,
                            final @Nullable String secondaryName) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotMoreValidationFault();
        throw new NotMoreValidationFault(NotMoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(float, float, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static float more(final float primaryValue, final float secondaryValue) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        throw new NotMoreValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static float more(final float primaryValue, final float secondaryValue, final @Nullable String primaryName,
                             final @Nullable String secondaryName) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotMoreValidationFault();
        throw new NotMoreValidationFault(NotMoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(double, double, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static double more(final double primaryValue, final double secondaryValue) throws NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        throw new NotMoreValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} должно быть больше {@code secondaryValue}.
     * @see #more(double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static double more(final double primaryValue, final double secondaryValue,
                              final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                        NotMoreValidationFault {
        if (primaryValue > secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new NotMoreValidationFault();
        throw new NotMoreValidationFault(NotMoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(byte, byte, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static byte notLess(final byte primaryValue, final byte secondaryValue) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        throw new LessValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static byte notLess(final byte primaryValue, final byte secondaryValue, final @Nullable String primaryName,
                               final @Nullable String secondaryName) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new LessValidationFault();
        throw new LessValidationFault(LessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(short, short, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static short notLess(final short primaryValue, final short secondaryValue) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        throw new LessValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static short notLess(final short primaryValue, final short secondaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                          LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new LessValidationFault();
        throw new LessValidationFault(LessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(int, int, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static int notLess(final int primaryValue, final int secondaryValue) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        throw new LessValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static int notLess(final int primaryValue, final int secondaryValue, final @Nullable String primaryName,
                              final @Nullable String secondaryName) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new LessValidationFault();
        throw new LessValidationFault(LessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(long, long, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static long notLess(final long primaryValue, final long secondaryValue) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        throw new LessValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static long notLess(final long primaryValue, final long secondaryValue, final @Nullable String primaryName,
                               final @Nullable String secondaryName) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new LessValidationFault();
        throw new LessValidationFault(LessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(float, float, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static float notLess(final float primaryValue, final float secondaryValue) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        throw new LessValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static float notLess(final float primaryValue, final float secondaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                          LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new LessValidationFault();
        throw new LessValidationFault(LessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(double, double, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static double notLess(final double primaryValue, final double secondaryValue) throws LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        throw new LessValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain LessValidationFault непроверяемую программную неисправность} с
     * {@linkplain LessValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain LessValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws LessValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue}.
     * @see #notLess(double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static double notLess(final double primaryValue, final double secondaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                           LessValidationFault {
        if (primaryValue >= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new LessValidationFault();
        throw new LessValidationFault(LessValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain NotMoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotMoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotMoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(byte, byte, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static byte notMore(final byte primaryValue, final byte secondaryValue) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        throw new MoreValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static byte notMore(final byte primaryValue, final byte secondaryValue, final @Nullable String primaryName,
                               final @Nullable String secondaryName) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new MoreValidationFault();
        throw new MoreValidationFault(MoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(short, short, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static short notMore(final short primaryValue, final short secondaryValue) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        throw new MoreValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static short notMore(final short primaryValue, final short secondaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                          MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new MoreValidationFault();
        throw new MoreValidationFault(MoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(int, int, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static int notMore(final int primaryValue, final int secondaryValue) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        throw new MoreValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static int notMore(final int primaryValue, final int secondaryValue, final @Nullable String primaryName,
                              final @Nullable String secondaryName) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new MoreValidationFault();
        throw new MoreValidationFault(MoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(long, long, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static long notMore(final long primaryValue, final long secondaryValue) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        throw new MoreValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static long notMore(final long primaryValue, final long secondaryValue, final @Nullable String primaryName,
                               final @Nullable String secondaryName) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new MoreValidationFault();
        throw new MoreValidationFault(MoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(float, float, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static float notMore(final float primaryValue, final float secondaryValue) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        throw new MoreValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static float notMore(final float primaryValue, final float secondaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                          MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new MoreValidationFault();
        throw new MoreValidationFault(MoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(double, double, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ? -> ?")
    public static double notMore(final double primaryValue, final double secondaryValue) throws MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        throw new MoreValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue}, то возвращает {@code primaryValue}, в противном случае генерирует
     * {@linkplain MoreValidationFault непроверяемую программную неисправность} с
     * {@linkplain MoreValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName} и
     * {@code secondaryName}, или, если {@code primaryName == null || secondaryName == null}, с
     * {@linkplain MoreValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws MoreValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue}.
     * @see #notMore(double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ? -> ?")
    public static double notMore(final double primaryValue, final double secondaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName) throws
                                                                                                           MoreValidationFault {
        if (primaryValue <= secondaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null) throw new MoreValidationFault();
        throw new MoreValidationFault(MoreValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(byte, byte, byte, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static byte outside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue) throws
                                                                                                             NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        throw new NotOutsideValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(byte, byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static byte outside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue,
                               final @Nullable String primaryName, final @Nullable String secondaryName,
                               final @Nullable String tertiaryName) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotOutsideValidationFault();
        throw new NotOutsideValidationFault(
                NotOutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(short, short, short, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static short outside(final short primaryValue, final short secondaryValue, final short tertiaryValue) throws
                                                                                                                 NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        throw new NotOutsideValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(short, short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static short outside(final short primaryValue, final short secondaryValue, final short tertiaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName,
                                final @Nullable String tertiaryName) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotOutsideValidationFault();
        throw new NotOutsideValidationFault(
                NotOutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(int, int, int, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static int outside(final int primaryValue, final int secondaryValue, final int tertiaryValue) throws
                                                                                                         NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        throw new NotOutsideValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(int, int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static int outside(final int primaryValue, final int secondaryValue, final int tertiaryValue,
                              final @Nullable String primaryName, final @Nullable String secondaryName,
                              final @Nullable String tertiaryName) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotOutsideValidationFault();
        throw new NotOutsideValidationFault(
                NotOutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(long, long, long, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static long outside(final long primaryValue, final long secondaryValue, final long tertiaryValue) throws
                                                                                                             NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        throw new NotOutsideValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(long, long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static long outside(final long primaryValue, final long secondaryValue, final long tertiaryValue,
                               final @Nullable String primaryName, final @Nullable String secondaryName,
                               final @Nullable String tertiaryName) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotOutsideValidationFault();
        throw new NotOutsideValidationFault(
                NotOutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(float, float, float, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static float outside(final float primaryValue, final float secondaryValue, final float tertiaryValue) throws
                                                                                                                 NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        throw new NotOutsideValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(float, float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static float outside(final float primaryValue, final float secondaryValue, final float tertiaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName,
                                final @Nullable String tertiaryName) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotOutsideValidationFault();
        throw new NotOutsideValidationFault(
                NotOutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(double, double, double, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static double outside(final double primaryValue, final double secondaryValue,
                                 final double tertiaryValue) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        throw new NotOutsideValidationFault();
    }

    /**
     * Если {@code primaryValue < secondaryValue || primaryValue > tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotOutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotOutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotOutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotOutsideValidationFault {@code primaryValue} должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #outside(double, double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static double outside(final double primaryValue, final double secondaryValue, final double tertiaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName,
                                 final @Nullable String tertiaryName) throws NotOutsideValidationFault {
        if (primaryValue < secondaryValue || primaryValue > tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotOutsideValidationFault();
        throw new NotOutsideValidationFault(
                NotOutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(byte, byte, byte, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static byte inside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue) throws
                                                                                                            NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        throw new NotInsideValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(byte, byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static byte inside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue,
                              final @Nullable String primaryName, final @Nullable String secondaryName,
                              final @Nullable String tertiaryName) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotInsideValidationFault();
        throw new NotInsideValidationFault(
                NotInsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(short, short, short, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static short inside(final short primaryValue, final short secondaryValue, final short tertiaryValue) throws
                                                                                                                NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        throw new NotInsideValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(short, short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static short inside(final short primaryValue, final short secondaryValue, final short tertiaryValue,
                               final @Nullable String primaryName, final @Nullable String secondaryName,
                               final @Nullable String tertiaryName) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotInsideValidationFault();
        throw new NotInsideValidationFault(
                NotInsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(int, int, int, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static int inside(final int primaryValue, final int secondaryValue, final int tertiaryValue) throws
                                                                                                        NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        throw new NotInsideValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(int, int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static int inside(final int primaryValue, final int secondaryValue, final int tertiaryValue,
                             final @Nullable String primaryName, final @Nullable String secondaryName,
                             final @Nullable String tertiaryName) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotInsideValidationFault();
        throw new NotInsideValidationFault(
                NotInsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(long, long, long, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static long inside(final long primaryValue, final long secondaryValue, final long tertiaryValue) throws
                                                                                                            NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        throw new NotInsideValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(long, long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static long inside(final long primaryValue, final long secondaryValue, final long tertiaryValue,
                              final @Nullable String primaryName, final @Nullable String secondaryName,
                              final @Nullable String tertiaryName) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotInsideValidationFault();
        throw new NotInsideValidationFault(
                NotInsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(float, float, float, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static float inside(final float primaryValue, final float secondaryValue, final float tertiaryValue) throws
                                                                                                                NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        throw new NotInsideValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(float, float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static float inside(final float primaryValue, final float secondaryValue, final float tertiaryValue,
                               final @Nullable String primaryName, final @Nullable String secondaryName,
                               final @Nullable String tertiaryName) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotInsideValidationFault();
        throw new NotInsideValidationFault(
                NotInsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(double, double, double, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static double inside(final double primaryValue, final double secondaryValue,
                                final double tertiaryValue) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        throw new NotInsideValidationFault();
    }

    /**
     * Если {@code primaryValue > secondaryValue && primaryValue < tertiaryValue}, то возвращает {@code primaryValue}, в
     * противном случае генерирует {@linkplain NotInsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain NotInsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain NotInsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws NotInsideValidationFault {@code primaryValue} должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #inside(double, double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static double inside(final double primaryValue, final double secondaryValue, final double tertiaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName,
                                final @Nullable String tertiaryName) throws NotInsideValidationFault {
        if (primaryValue > secondaryValue && primaryValue < tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new NotInsideValidationFault();
        throw new NotInsideValidationFault(
                NotInsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(byte, byte, byte, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static byte notOutside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue) throws
                                                                                                                OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        throw new OutsideValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(byte, byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static byte notOutside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue,
                                  final @Nullable String primaryName, final @Nullable String secondaryName,
                                  final @Nullable String tertiaryName) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new OutsideValidationFault();
        throw new OutsideValidationFault(
                OutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(short, short, short, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static short notOutside(final short primaryValue, final short secondaryValue,
                                   final short tertiaryValue) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        throw new OutsideValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(short, short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static short notOutside(final short primaryValue, final short secondaryValue, final short tertiaryValue,
                                   final @Nullable String primaryName, final @Nullable String secondaryName,
                                   final @Nullable String tertiaryName) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new OutsideValidationFault();
        throw new OutsideValidationFault(
                OutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(int, int, int, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static int notOutside(final int primaryValue, final int secondaryValue, final int tertiaryValue) throws
                                                                                                            OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        throw new OutsideValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(int, int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static int notOutside(final int primaryValue, final int secondaryValue, final int tertiaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName,
                                 final @Nullable String tertiaryName) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new OutsideValidationFault();
        throw new OutsideValidationFault(
                OutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(long, long, long, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static long notOutside(final long primaryValue, final long secondaryValue, final long tertiaryValue) throws
                                                                                                                OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        throw new OutsideValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(long, long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static long notOutside(final long primaryValue, final long secondaryValue, final long tertiaryValue,
                                  final @Nullable String primaryName, final @Nullable String secondaryName,
                                  final @Nullable String tertiaryName) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new OutsideValidationFault();
        throw new OutsideValidationFault(
                OutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(float, float, float, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static float notOutside(final float primaryValue, final float secondaryValue,
                                   final float tertiaryValue) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        throw new OutsideValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(float, float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static float notOutside(final float primaryValue, final float secondaryValue, final float tertiaryValue,
                                   final @Nullable String primaryName, final @Nullable String secondaryName,
                                   final @Nullable String tertiaryName) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new OutsideValidationFault();
        throw new OutsideValidationFault(
                OutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(double, double, double, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static double notOutside(final double primaryValue, final double secondaryValue,
                                    final double tertiaryValue) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        throw new OutsideValidationFault();
    }

    /**
     * Если {@code primaryValue >= secondaryValue && primaryValue <= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain OutsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain OutsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain OutsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws OutsideValidationFault {@code primaryValue} не должно быть меньше {@code secondaryValue} или больше
     * {@code tertiaryValue}.
     * @see #notOutside(double, double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static double notOutside(final double primaryValue, final double secondaryValue, final double tertiaryValue,
                                    final @Nullable String primaryName, final @Nullable String secondaryName,
                                    final @Nullable String tertiaryName) throws OutsideValidationFault {
        if (primaryValue >= secondaryValue && primaryValue <= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new OutsideValidationFault();
        throw new OutsideValidationFault(
                OutsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(byte, byte, byte, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static byte notInside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue) throws
                                                                                                               InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        throw new InsideValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(byte, byte, byte)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static byte notInside(final byte primaryValue, final byte secondaryValue, final byte tertiaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName,
                                 final @Nullable String tertiaryName) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new InsideValidationFault();
        throw new InsideValidationFault(
                InsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(short, short, short, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static short notInside(final short primaryValue, final short secondaryValue,
                                  final short tertiaryValue) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        throw new InsideValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(short, short, short)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static short notInside(final short primaryValue, final short secondaryValue, final short tertiaryValue,
                                  final @Nullable String primaryName, final @Nullable String secondaryName,
                                  final @Nullable String tertiaryName) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new InsideValidationFault();
        throw new InsideValidationFault(
                InsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(int, int, int, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static int notInside(final int primaryValue, final int secondaryValue, final int tertiaryValue) throws
                                                                                                           InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        throw new InsideValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(int, int, int)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static int notInside(final int primaryValue, final int secondaryValue, final int tertiaryValue,
                                final @Nullable String primaryName, final @Nullable String secondaryName,
                                final @Nullable String tertiaryName) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new InsideValidationFault();
        throw new InsideValidationFault(
                InsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(long, long, long, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static long notInside(final long primaryValue, final long secondaryValue, final long tertiaryValue) throws
                                                                                                               InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        throw new InsideValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(long, long, long)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static long notInside(final long primaryValue, final long secondaryValue, final long tertiaryValue,
                                 final @Nullable String primaryName, final @Nullable String secondaryName,
                                 final @Nullable String tertiaryName) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new InsideValidationFault();
        throw new InsideValidationFault(
                InsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(float, float, float, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static float notInside(final float primaryValue, final float secondaryValue,
                                  final float tertiaryValue) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        throw new InsideValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(float, float, float)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static float notInside(final float primaryValue, final float secondaryValue, final float tertiaryValue,
                                  final @Nullable String primaryName, final @Nullable String secondaryName,
                                  final @Nullable String tertiaryName) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new InsideValidationFault();
        throw new InsideValidationFault(
                InsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(double, double, double, String, String, String)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ? -> ?")
    public static double notInside(final double primaryValue, final double secondaryValue,
                                   final double tertiaryValue) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        throw new InsideValidationFault();
    }

    /**
     * Если {@code primaryValue <= secondaryValue || primaryValue >= tertiaryValue}, то возвращает {@code primaryValue},
     * в противном случае генерирует {@linkplain InsideValidationFault непроверяемую программную неисправность} с
     * {@linkplain InsideValidationFault#TEMPLATE_MESSAGE шаблонным сообщением} на основе {@code primaryName},
     * {@code secondaryName} и {@code tertiaryName}, или, если
     * {@code primaryName == null || secondaryName == null || tertiaryName == null}, с
     * {@linkplain InsideValidationFault#DEFAULT_MESSAGE сообщением по умолчанию}.
     *
     * @param primaryValue первичное численное значение.
     * @param secondaryValue вторичное численное значение.
     * @param tertiaryValue третичное численное значение.
     * @param primaryName имя {@code primaryValue}.
     * @param secondaryName имя {@code secondaryValue}.
     * @param tertiaryName имя {@code tertiaryValue}.
     *
     * @return {@code primaryValue}.
     *
     * @throws InsideValidationFault {@code primaryValue} не должно быть больше {@code secondaryValue} и меньше
     * {@code tertiaryValue}.
     * @see #notInside(double, double, double)
     * @since 1.0.0-RC1
     */
    @Contract("?, ?, ?, ?, ?, ? -> ?")
    public static double notInside(final double primaryValue, final double secondaryValue, final double tertiaryValue,
                                   final @Nullable String primaryName, final @Nullable String secondaryName,
                                   final @Nullable String tertiaryName) throws InsideValidationFault {
        if (primaryValue <= secondaryValue || primaryValue >= tertiaryValue) return primaryValue;
        if (primaryName == null || secondaryName == null || tertiaryName == null) throw new InsideValidationFault();
        throw new InsideValidationFault(
                InsideValidationFault.TEMPLATE_MESSAGE.formatted(primaryName, secondaryName, tertiaryName));
    }

}
