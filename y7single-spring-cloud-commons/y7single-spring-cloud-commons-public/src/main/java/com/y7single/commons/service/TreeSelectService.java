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

package com.y7single.commons.service;


import com.y7single.commons.model.bo.TreeNodeBO;
import com.y7single.commons.model.TreeModel;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author: y7
 * @date: 2019/12/17 16:29
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public interface TreeSelectService<T extends TreeModel<PK>, PK extends Serializable> {

    /**
     * 根据父节点id获取子节点数据
     *
     * @param parentId 父节点ID
     * @return 子节点数据
     */
    Collection<T> findChildren(PK parentId);


    /**
     * 根据父节点id获取子节点数据(关联查询)
     *
     * @param parentId 父节点ID
     * @return 子节点数据
     */
    Collection<Map<String, Object>> findChildrenJoin(PK parentId);

    /**
     * @param parentId 父节点ID
     * @param retType  要转换的类型
     * @return 子节点数据
     */
    <R> Collection<R> findChildrenJoin(PK parentId, Class<R> retType);

    /**
     * 获取当前节点下树数据
     *
     * @param parentId 父节点ID
     * @return 树信息
     */
    TreeNodeBO<T, PK> findNodeByParentId(PK parentId);

    /**
     * 获取当前节点下树数据(关联查询)
     *
     * @param parentId 父节点ID 其中 内容为map
     * @return 树信息
     */
    TreeNodeBO<? extends TreeModel<PK>, PK> findNodeByParentIdJoin(PK parentId);

    /**
     * 获取当前节点下树数据(关联查询)
     *
     * @param parentId 父节点ID
     * @param retType  将Map 内容map 转为相应的对象
     * @param <R>      转换类型
     * @return 树信息
     */
    <R extends TreeModel<PK>> TreeNodeBO<R, PK> findNodeByParentIdJoin(PK parentId, Class<R> retType);

    /**
     * 查询全部树节点
     *
     * @return 树节点
     */
    Collection<TreeNodeBO<T, PK>> findNode();

    /**
     * 查询全部树节点（关联查询）
     * 查询出来的内容将全部用map封装
     *
     * @return 树节点
     */
    Collection<TreeNodeBO<? extends TreeModel<PK>, PK>> findNodeJoin();

    /**
     * 查询全部树节点（关联查询）
     * 查询出来的内容（map） 转为相应的对象
     *
     * @return 树节点
     */
    <R extends TreeModel<PK>> Collection<TreeNodeBO<R, PK>> findNodeJoin(Class<R> retType);

}
