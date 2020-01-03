/*
 * Copyright 2020 y7
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.y7single.commons.validated.annotations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ValidationException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: y7
 * @date: 2020/1/3 14:59
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 校验枚举值有效性
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {

    String message() default "无效的值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 校验类
     */
    Class<?> vClass();

    /**
     * 校验方法名
     *
     */
    String vMethodName();

    /**
     * 枚举校验
     */
    class Validator implements ConstraintValidator<EnumValue, Object> {

        private Class<?> validatorClass;

        private String validatorMethodName;

        @Override
        public void initialize(EnumValue constraintAnnotation) {
            validatorClass = constraintAnnotation.vClass();
            validatorMethodName = constraintAnnotation.vMethodName();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {

            if (Objects.isNull(value) || Objects.isNull(validatorMethodName) || Objects.isNull(validatorClass)) {
                return Boolean.TRUE;
            }

            Class<?> valueClass = value.getClass();

            try {

                Method method = validatorClass.getMethod(validatorMethodName, valueClass);

                Class<?> returnType = method.getReturnType();

                if (!Boolean.TYPE.equals(returnType) && !Boolean.class.equals(returnType)) {
                    throw new ValidationException(String.format("%s method return type is not boolean in the  %s class", validatorMethodName, validatorClass));
                }

                Boolean result = (Boolean) method.invoke(null, value);

                return Objects.isNull(result) ? false : result;

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new ValidationException(e);
            }
        }
    }

}
