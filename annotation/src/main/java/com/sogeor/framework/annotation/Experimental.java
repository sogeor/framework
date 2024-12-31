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
 * Обозначает элемент, который с высокой долей вероятности может быть изменён или удалён. Вопреки этому, он уже может
 * быть использован, поэтому его изменение или удаление может быть затруднено и отложено.
 *
 * @see Obsolete
 * @since 1.0.0-RC1
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR,
        ElementType.ANNOTATION_TYPE, ElementType.PACKAGE, ElementType.TYPE_USE, ElementType.MODULE,
        ElementType.RECORD_COMPONENT
})
public @interface Experimental {}
