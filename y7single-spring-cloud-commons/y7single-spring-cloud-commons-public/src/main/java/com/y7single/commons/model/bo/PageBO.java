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

package com.y7single.commons.model.bo;

import com.y7single.commons.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: y7
 * @date: 2019/12/17 16:05
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBO<T> implements BO {

    private static final long serialVersionUID = 8047379835500458246L;
    /**
     * 当前页号
     */
    private long current;
    /**
     * 每页数量
     */
    private long pageSize;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 数据集
     */
    private List<T> records;
}
