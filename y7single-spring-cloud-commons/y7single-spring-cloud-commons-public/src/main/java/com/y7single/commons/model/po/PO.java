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

package com.y7single.commons.model.po;

import com.y7single.commons.model.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: y7
 * @date: 2019/12/20 14:32
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public interface PO<PK extends Serializable> extends Model {

    /**
     * id
     *
     * @return id
     */
    PK getId();

    void setId(PK pk);

    /**
     * 创建时间
     *
     * @return 创建时间
     */
    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    /**
     * 最后一次更新时间
     *
     * @return 最后一次更新时间
     */
    LocalDateTime getUpdateTime();

    void setUpdateTime(LocalDateTime updateTime);

}
