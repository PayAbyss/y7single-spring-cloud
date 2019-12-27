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


import com.y7single.commons.model.TreeModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author: y7
 * @date: 2019/12/17 16:18
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Data
public class TreeNodeBO<E extends TreeModel<PK>, PK extends Serializable> implements BO {

    private static final long serialVersionUID = -3502985155711136612L;
    /**
     * 父节点
     */
    private E parent;

    /**
     * 子节点
     */
    private Collection<TreeNodeBO<E, PK>> children;


}
