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


/**
 * @author: y7
 * @date: 2019/12/17 16:13
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 修改
 */
public interface UpdateService<T> {

    /**
     * 修改记录
     *
     * @param record 需要修改的对象
     * @return 修改成功数据
     */
    boolean update(T record);


    /**
     * 修改部分数据
     *
     * @param record 数据
     * @return 修改成功数据
     */
    boolean updatePart(T record);
}
