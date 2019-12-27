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

package com.y7single.service.annotations;

import com.y7single.service.model.po.BasePO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: y7
 * @date: 2019/10/29 17:43
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {

    /**
     * 连接对象
     *
     * @return cls
     */
    Class<? extends BasePO> connectClass();

    /**
     * 连接列
     *
     * @return 列名
     */
    String connectColumn();

    /**
     * 关联对象
     *
     * @return
     */
    Class<? extends BasePO> joinClass();

    /**
     * 连接列
     */
    String joinColumn();

    /**
     * 查询别名
     *
     * @return 查询别名
     */
    String aliasName();

    /**
     * 查询列
     *
     * @return 列明
     */
    String queryName();
}
