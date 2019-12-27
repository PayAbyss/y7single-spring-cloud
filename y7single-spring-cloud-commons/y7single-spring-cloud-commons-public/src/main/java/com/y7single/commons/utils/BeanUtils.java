/*
 * Copyright 2019 y7
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

package com.y7single.commons.utils;

import com.y7single.commons.enums.DefaultResultCode;
import com.y7single.commons.exceptions.BeanUtilsException;
import com.y7single.commons.model.bo.TreeNodeBO;
import com.y7single.commons.model.TreeModel;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author: y7
 * @date: 2019/12/17 16:33
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public final class BeanUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void copyProperties(Object source, Object target) {

        Assert.notNull(source, null);
        Assert.notNull(target, null);

        if (source instanceof Map)
            copyProperties((Map<String, ?>) source, target);
        else
            org.springframework.beans.BeanUtils.copyProperties(source, target);
    }


    public static void copyProperties(Map<String, ?> source, Object target) {

        Assert.notNull(source, null);
        Assert.notNull(target, null);

        try {

            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                String key = propertyDescriptor.getName();

                if (!source.containsKey(key)) continue;

                Object val = source.get(key);

                final Method writeMethod = propertyDescriptor.getWriteMethod();

                final Method readMethod = propertyDescriptor.getReadMethod();

                Class<?> returnType = readMethod.getReturnType();

                writeValue(target, writeMethod, val, returnType);
            }


        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanUtilsException(DefaultResultCode.DATA_CONVERSION_FAILED);
        }


    }


    public static <T> void copyProperties(Collection<?> sources, Collection<T> target, Class<T> targetClass) {

        Assert.notNull(sources, null);
        Assert.notNull(target, null);
        Assert.notNull(targetClass, null);

        sources.forEach(source -> {
            try {

                final T t = targetClass.newInstance();

                if (source instanceof Collection)
                    copyProperties((Collection) source, new ArrayList<>(((Collection) source).size()), targetClass);
                else
                    copyProperties(source, t);

                target.add(t);

            } catch (InstantiationException | IllegalAccessException e) {
                throw new BeanUtilsException(DefaultResultCode.BEAN_INSTANCE_FAILED);
            }

        });
    }

    public static <T extends TreeModel<PK>, R extends TreeModel<PK>, PK extends Serializable> void copyProperties(TreeNodeBO<T, PK> sources, TreeNodeBO<R, PK> treeNodeBO, Class<R> targetClass) {

        Assert.notNull(sources, null);
        Assert.notNull(targetClass, null);


        final Object parent = sources.getParent();

        try {

            final R r = targetClass.newInstance();

            copyProperties(parent, r);

            final Collection<TreeNodeBO<T, PK>> children = sources.getChildren();

            final ArrayList<TreeNodeBO<R, PK>> treeNodeBOS = new ArrayList<>(children.size());

            treeNodeBO.setParent(r);

            if (!CollectionUtils.isEmpty(children)) {

                for (TreeNodeBO<T, PK> child : children) {

                    final TreeNodeBO<R, PK> nodeBO = new TreeNodeBO<>();

                    copyProperties(child, nodeBO, targetClass);

                    treeNodeBOS.add(nodeBO);
                }
            }

            treeNodeBO.setChildren(treeNodeBOS);

        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanUtilsException(DefaultResultCode.BEAN_INSTANCE_FAILED);
        }

    }


    private static void writeValue(Object target, Method writeMethod, Object value, Class<?> propertyType) throws InvocationTargetException, IllegalAccessException {

        if (propertyType == byte.class || propertyType == Byte.class) {
            byte flag = (byte) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == short.class || propertyType == Short.class) {
            short flag = (short) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == int.class || propertyType == Integer.class) {
            byte flag = (byte) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == long.class || propertyType == Long.class) {
            long flag = (long) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == float.class || propertyType == Float.class) {
            float flag = (float) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == double.class || propertyType == Double.class) {
            double flag = (double) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == boolean.class || propertyType == Boolean.class) {
            boolean flag = (boolean) value;
            writeMethod.invoke(target, flag);
        } else if (propertyType == LocalDateTime.class && value.getClass() == Timestamp.class) {
            final LocalDateTime localDateTime = ((Timestamp) value).toLocalDateTime();
            writeMethod.invoke(target, localDateTime);
        } else {
            writeMethod.invoke(target, value);
        }

    }
}
