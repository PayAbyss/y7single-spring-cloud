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

package com.y7single.commons.model.qo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author: y7
 * @date: 2019/12/23 17:32
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Data
public class JoinQO implements QO {

    private static final long serialVersionUID = -6744170825540118499L;


    /**
     * 排序列
     */
    private String orderColumn = "create_time";

    /**
     * 排序方式
     */
    private boolean asc = true;


    /**
     * 条件列
     */
    private String whereColumnName;

    /**
     * 条件值
     */
    private Serializable whereColumnValue;


}
