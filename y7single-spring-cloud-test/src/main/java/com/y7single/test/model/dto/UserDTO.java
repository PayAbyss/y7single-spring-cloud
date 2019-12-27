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

package com.y7single.test.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.y7single.commons.model.dto.BaseDTO;
import com.y7single.service.model.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author: y7
 * @date: 2019/12/24 18:17
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseDTO<String> {
    private static final long serialVersionUID = -227224608758237035L;

    @NotBlank(message = "nickname 不能为空")
    private String nickname;

    private String orgId;

    private String username;
}
