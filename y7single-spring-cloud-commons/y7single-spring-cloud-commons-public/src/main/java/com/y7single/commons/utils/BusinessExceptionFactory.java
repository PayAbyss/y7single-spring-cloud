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

import com.y7single.commons.exceptions.BusinessException;
import com.y7single.commons.model.exception.ResultCode;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: y7
 * @date: 2019/12/17 17:46
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public final class BusinessExceptionFactory {

    private static final Map<Class<?>, ResultCode> FACTORY_MAP = new ConcurrentHashMap<>(20);


    public static void register(Class<?> cls, ResultCode resultCode) {
        FACTORY_MAP.putIfAbsent(cls, resultCode);
    }

    public static boolean isSupportException(Class<?> z) {
        final Set<Class<?>> classes = FACTORY_MAP.keySet();
        for (Class<?> cls : classes) {
            if (cls == z) {
                return true;
            }
        }

        return false;
    }

    public static ResultCode getByEClass(Class<? extends BusinessException> eClass) {

        if (eClass == null) {
            return null;
        }

        final Set<Class<?>> classes = FACTORY_MAP.keySet();

        for (Class<?> cls : classes) {
            if (eClass == cls) {
                return FACTORY_MAP.get(cls);
            }
        }

        return null;
    }
}
