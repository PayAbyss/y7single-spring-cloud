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

package com.y7single.commons.model.dto;

import com.y7single.commons.enums.DefaultResultCode;
import com.y7single.commons.exceptions.BeanUtilsException;
import com.y7single.commons.model.Model;
import com.y7single.commons.utils.BeanUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author: y7
 * @date: 2019/12/23 18:17
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Data
@SuppressWarnings("unchecked")
public abstract class BaseDTO<PK> implements DTO {


    private static final long serialVersionUID = -6094682362867958179L;


    private PK id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public <T> T convertToR(Class<T> clz) {


        if (this.getClass() == clz) return (T) this;

        try {

            T t = clz.newInstance();

            BeanUtils.copyProperties(this, t);

            return t;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanUtilsException(DefaultResultCode.BEAN_INSTANCE_FAILED);
        }

    }


    public void convertToFor(Model model) {

        if (Objects.isNull(model)) {
           return;
        }
        if (this.getClass() == model.getClass()) return;
        BeanUtils.copyProperties(model, this);
    }
}
