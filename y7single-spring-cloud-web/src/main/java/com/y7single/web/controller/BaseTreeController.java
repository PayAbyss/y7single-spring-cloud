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

package com.y7single.web.controller;


import com.y7single.commons.model.bo.TreeNodeBO;
import com.y7single.commons.model.dto.BaseTreeDTO;
import com.y7single.commons.model.vo.BaseTreeVO;
import com.y7single.commons.service.BaseTreeApiService;
import com.y7single.commons.utils.BeanUtils;
import com.y7single.commons.web.controller.BaseTreeApiController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: y7
 * @date: 2019/12/20 18:04
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 其他详情请参考 {@link BaseController}
 */
@SuppressWarnings("all")
public abstract class BaseTreeController<S extends BaseTreeApiService, V extends BaseTreeVO<PK>, D extends BaseTreeDTO<PK>, PK extends Serializable>
        extends BaseController<S, V, D, PK> implements BaseTreeApiController<S, V, D, PK> {

    @Autowired(required = false)
    protected S baseApiService;

    public BaseTreeController() {
        super.setBaseApiService(baseApiService);
        this.baseApiService = baseApiService;
    }


    @ApiOperation(value = "查询数据(根据父id查询)", notes = "查询数据(根据父id查询) 查询该id下的子节点")
    @ApiImplicitParam(name = "parentId", value = "父id", paramType = "path", required = true)
    @GetMapping(value = "findChildren/{parentId}")
    @Override
    public Collection<V> findChildren(@PathVariable(value = "parentId") PK parentId) {

        final Collection<D> dResults = this.baseApiService.findChildren(parentId);

        return copyResult(dResults);

    }

    @ApiOperation(value = "关联查询(根据父id查询)", notes = "关联查询(根据父id查询) 查询该id下的子节点")
    @ApiImplicitParam(name = "parentId", value = "父id", paramType = "path", required = true)
    @GetMapping(value = "findChildrenJoin/{parentId}")
    @Override
    public Collection<V> findChildrenJoin(@PathVariable(value = "parentId")  PK parentId) {

        final Collection<D> dResults = this.baseApiService.findChildren(parentId);

        return copyResult(dResults);
    }

    @ApiOperation(value = "查询数据(节点查询)", notes = "查询数据(节点查询) 根据父id 查询该节点包含的所有子节点")
    @ApiImplicitParam(name = "parentId", value = "父id", paramType = "path", required = true)
    @GetMapping(value = "findNodeByParentId/{parentId}")
    @Override
    public TreeNodeBO<V, PK> findNodeByParentId(@PathVariable(value = "parentId")  PK parentId) {

        final TreeNodeBO<D, PK> treeNodeBO = this.baseApiService.findNodeByParentId(parentId);

        return copyTreeNode(treeNodeBO);

    }

    @ApiOperation(value = "关联查询(节点查询)", notes = "关联查询(节点查询) 根据父id 查询该节点包含的所有子节点")
    @ApiImplicitParam(name = "parentId", value = "父id", paramType = "path", required = true)
    @GetMapping(value = "findNodeByParentIdJoin/{parentId}")
    @Override
    public TreeNodeBO<V, PK> findNodeByParentIdJoin(@PathVariable  PK parentId) {

        final TreeNodeBO<D, PK> treeNodeBO = this.baseApiService.findNodeByParentIdJoin(parentId);

        return copyTreeNode(treeNodeBO);
    }

    @ApiOperation(value = "查询数据(节点查询)", notes = "查询数据(节点查询) 查询所有节点以及所包含的子节点")
    @GetMapping(value = "findNode")
    @Override
    public Collection<TreeNodeBO<V, PK>> findNode() {

        final Collection<TreeNodeBO<D, PK>> treeNodeBOS = this.baseApiService.findNode();

        return copyTreeNode(treeNodeBOS);
    }

    @ApiOperation(value = "关联查询(节点查询)", notes = "关联查询(节点查询) 查询所有节点以及所包含的子节点")
    @GetMapping(value = "findNodeJoin")
    @Override
    public Collection<TreeNodeBO<V, PK>> findNodeJoin() {

        final Collection<TreeNodeBO<D, PK>> treeNodeBOS = this.baseApiService.findNodeJoin();

        return copyTreeNode(treeNodeBOS);

    }


    /**
     * 将 TreeNodeBO<D, PK> 中的D 数据内容转为 V
     *
     * @param treeNodeBO 数据源
     * @return TreeNodeBO<V, PK>
     */
    protected TreeNodeBO<V, PK> copyTreeNode(TreeNodeBO<D, PK> treeNodeBO) {

        final TreeNodeBO<V, PK> vTreeNodeBO = new TreeNodeBO<>();

        BeanUtils.copyProperties(treeNodeBO, vTreeNodeBO, vType);

        return vTreeNodeBO;
    }

    /**
     * 将  Collection 中 TreeNodeBO<D, PK> 中的D 数据内容转为 V
     *
     * @param treeNodeBOs 数据源
     * @return List<TreeNodeBO < V, PK>>
     */
    protected List<TreeNodeBO<V, PK>> copyTreeNode(Collection<TreeNodeBO<D, PK>> treeNodeBOs) {

        return treeNodeBOs.stream().map(this::copyTreeNode).collect(Collectors.toList());

    }

    @Override
    public void setBaseApiService(S baseApiService) {
        super.setBaseApiService(baseApiService);
        this.baseApiService = baseApiService;
    }
}
