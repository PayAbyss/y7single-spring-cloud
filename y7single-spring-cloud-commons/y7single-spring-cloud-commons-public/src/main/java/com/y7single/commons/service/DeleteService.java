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

import java.io.Serializable;
import java.util.Collection;

/**
 * @author: y7
 * @date: 2019/12/17 14:54
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 删除数据
 */
public interface DeleteService<PK extends Serializable>{

    /**
     * 删除数据
     *
     * @param id id
     * @return 是否删除成功
     */
    boolean delete(PK id);

    /**
     * 批量删除
     *
     * @param ids id 集合
     * @return 是否删除成功
     */
    boolean delete(Collection<PK> ids);

}
