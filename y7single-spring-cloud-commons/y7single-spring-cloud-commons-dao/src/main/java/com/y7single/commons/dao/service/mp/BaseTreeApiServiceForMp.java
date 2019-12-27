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

package com.y7single.commons.dao.service.mp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.y7single.commons.model.TreeModel;
import com.y7single.commons.model.dto.BaseTreeDTO;
import com.y7single.commons.service.BaseTreeApiService;

import java.io.Serializable;

/**
 * @author: y7
 * @date: 2019/12/20 15:36
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public interface BaseTreeApiServiceForMp<E extends TreeModel<PK>, D extends BaseTreeDTO<PK>, PK extends Serializable> extends BaseTreeApiService<D, PK>, IService<E> {
}
