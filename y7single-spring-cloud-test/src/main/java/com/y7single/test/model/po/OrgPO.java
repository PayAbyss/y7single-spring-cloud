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

package com.y7single.test.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.y7single.service.annotations.JoinTable;
import com.y7single.service.model.po.BaseTreePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: y7
 * @date: 2019/12/19 21:38
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_org")
public class OrgPO extends BaseTreePO<String> {

    private static final long serialVersionUID = 8190460345105885988L;

    private String label;

    private String parentId;

    @JoinTable(connectClass = OrgPO.class, connectColumn = "user_id", joinClass = UserPO.class, joinColumn = "id", aliasName = "nickname", queryName = "nickname")
    private String userId;




}
