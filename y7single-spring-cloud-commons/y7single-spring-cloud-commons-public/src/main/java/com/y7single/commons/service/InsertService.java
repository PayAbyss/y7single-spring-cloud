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

package com.y7single.commons.service;

import java.util.Collection;

/**
 * @author: y7
 * @date: 2019/12/17 14:47
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 插入定义
 */
public interface InsertService<T> {

    /**
     * 插入数据
     *
     * @param record 对象
     * @return 插入成功数据
     */
    boolean insert(T record);


    /**
     * 批量插入
     *
     * @param records 数据集合
     * @return 插入成功数据
     */
    boolean insert(Collection<T> records);
}
