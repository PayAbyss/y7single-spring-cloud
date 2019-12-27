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

package com.y7single.service.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.y7single.commons.model.po.PO;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: y7
 * @date: 2019/12/17 18:03
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Data
public abstract class BasePO<PK extends Serializable> implements PO<PK> {

    private static final long serialVersionUID = 8400332953637322349L;

    @TableId
    private PK id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
