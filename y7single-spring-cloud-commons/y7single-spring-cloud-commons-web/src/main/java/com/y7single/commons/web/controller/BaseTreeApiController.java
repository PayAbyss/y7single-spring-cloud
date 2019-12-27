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

package com.y7single.commons.web.controller;


import com.y7single.commons.model.bo.TreeNodeBO;
import com.y7single.commons.model.dto.BaseTreeDTO;
import com.y7single.commons.model.vo.BaseTreeVO;
import com.y7single.commons.service.BaseTreeApiService;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author: y7
 * @date: 2019/12/19 22:49
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@SuppressWarnings("all")
@Validated
public interface BaseTreeApiController<S extends BaseTreeApiService, V extends BaseTreeVO<PK>, D extends BaseTreeDTO<PK>, PK extends Serializable>
        extends BaseApiController<S, V, D, PK> {

    /**
     * 根据父节点id获取子节点数据
     *
     * @param parentId 父节点ID
     * @return 子节点数据
     */
    Collection<V> findChildren(@NotNull(message = "parentId不能为空") PK parentId);


    /**
     * @param parentId 父节点ID
     * @return 子节点数据
     */
    Collection<V> findChildrenJoin(@NotNull(message = "parentId不能为空") PK parentId);

    /**
     * 获取当前节点下树数据
     *
     * @param parentId 父节点ID
     * @return 树信息
     */
    TreeNodeBO<V, PK> findNodeByParentId(@NotNull(message = "parentId不能为空") PK parentId);

    /**
     * 获取当前节点下树数据(关联查询)
     *
     * @param parentId 父节点ID 其中 内容为map
     * @return 树信息
     */
    TreeNodeBO<V, PK> findNodeByParentIdJoin(@NotNull(message = "parentId不能为空") PK parentId);


    /**
     * 查询全部树节点
     *
     * @return 树节点
     */
    Collection<TreeNodeBO<V, PK>> findNode();

    /**
     * 查询全部树节点（关联查询）
     * 查询出来的内容将全部用map封装
     *
     * @return 树节点
     */
    Collection<TreeNodeBO<V, PK>> findNodeJoin();


}
