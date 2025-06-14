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

package com.sogeor.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Представляет собой аннотацию, применимую к элементу, который может как быть, так и не быть {@code null}.
 * <p>
 * Элемент может быть: полем, методом, параметром, локальной переменной, применением типа и компонентом записи.
 *
 * @see NonNull
 * @see Null
 * @since 1.0.0-RC1
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE,
        ElementType.RECORD_COMPONENT
})
public @interface Nullable {}
