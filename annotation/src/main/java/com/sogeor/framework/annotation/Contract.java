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
 * Обозначает элемент (1), исполняющий определённый контракт.
 *
 * @since 1.0.0-RC1
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Contract {

    /**
     * Определяет поведение (1) {1} при определённых условиях и аргументах.
     *
     * @return [1].
     *
     * @implSpec При определении [1] должен соблюдаться следующий синтаксис (2):
     * <pre>{@code
     * <contract> ::= <clause> ("; " <clause>)*
     * <clause> ::= <args> " -> " <result>
     * <args> ::= <arg> (", " <arg>)*
     * <arg> ::= ("this" | "null" | "false" | "true") | "$"? ("?" | "!null")
     * <result> ::= ("this" | "new" | "null" | "false" | "true" | "failure" | "fault" | [1-9]+) | "$"? ("?" | "value" | "!null")
     * }</pre>
     * @implNote Подробности, связанные с [2], будут добавлены сюда позже.
     * @since 1.0.0-RC1
     */
    String value();

}
