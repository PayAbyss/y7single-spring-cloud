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

package com.y7single.service.service.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y7single.commons.dao.service.mp.BaseTreeApiServiceForMp;
import com.y7single.commons.enums.DefaultResultCode;
import com.y7single.commons.model.bo.TreeNodeBO;
import com.y7single.commons.model.TreeModel;
import com.y7single.commons.model.dto.BaseTreeDTO;
import com.y7single.commons.model.po.TreePO;
import com.y7single.commons.model.qo.JoinQO;
import com.y7single.commons.model.vo.BaseTreeVO;
import com.y7single.commons.utils.Assert;
import com.y7single.commons.utils.BeanUtils;
import com.y7single.service.mapper.BaseSQLCurdMapper;
import com.y7single.service.model.TreeMap;
import com.y7single.service.model.po.BaseTreePO;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: y7
 * @date: 2019/12/20 15:38
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: M Mapper E dao PK 主键类型 D dto传输模型
 */
public class BaseTreeApiServiceForMpImpl<M extends BaseSQLCurdMapper<E, PK>, E extends BaseTreePO<PK>, D extends BaseTreeDTO<PK>, PK extends Serializable> extends BaseApiServiceForMpImpl<M, E, D, PK> implements BaseTreeApiServiceForMp<E, D, PK> {

    private String parentColumn;

    /**
     * @param parentId 父节点ID
     * @return 所有父节点下的子节点
     */
    @Override
    public Collection<D> findChildren(PK parentId) {

        final QueryWrapper<E> queryWrapper = getQueryWrapper();

        queryWrapper.eq(getParentColumn(), parentId);

        return this.baseMapper.selectList(queryWrapper).stream().map(this::convertToD).collect(Collectors.toList());

    }

    /**
     * @param parentId 父节点ID
     * @return 查询所有属于该节点 包含改节点的所有数据具体格式请参考{@link TreeNodeBO}
     */
    @Override
    public TreeNodeBO<D, PK> findNodeByParentId(PK parentId) {

        E parent = super.getById(parentId);

        Assert.notNull(parent, DefaultResultCode.DATA_NOT_EXISTED);

        final TreeNodeBO<E, PK> treeNodeBO = warpNode(parent);

        buildTree(treeNodeBO);

        final TreeNodeBO<D, PK> treeNodeBOD = new TreeNodeBO<>();

        BeanUtils.copyProperties(treeNodeBO, treeNodeBOD, dtoType);

        return treeNodeBOD;

    }

    /**
     * 查询所有节点并且整理好所有节点位置
     *
     * @return 包含改节点的所有数据具体格式请参考 {@link TreeNodeBO} 每一个TreeNodeBO 都是一个顶级节点
     */
    @Override
    public Collection<TreeNodeBO<D, PK>> findNode() {

        return this.getSupperParent()
                .stream()
                .map(record -> this.findNodeByParentId(record.getId()))
                .collect(Collectors.toList());

    }


    /**
     * 关联查询 parentId 下的所有子节点
     *
     * @param parentId 父节点ID
     * @return 用map包装的子节点数据集
     */
    @Override
    public Collection<Map<String, Object>> findChildrenJoin(PK parentId) {

        JoinQO joinQO = new JoinQO();

        joinQO.setWhereColumnName(getParentColumn());

        joinQO.setWhereColumnValue(parentId);

        return super.findJoinWhere(joinQO);

    }

    /**
     * @param parentId 父节点ID
     * @param retType  返回类型Class
     * @param <T>      返回类型
     * @return 转换后T的数据集
     */
    @Override
    public <T> Collection<T> findChildrenJoin(PK parentId, Class<T> retType) {

        final Collection<Map<String, Object>> resultMaps = this.findChildrenJoin(parentId);

        final ArrayList<T> result = new ArrayList<>();

        BeanUtils.copyProperties(resultMaps, result, retType);

        return result;
    }


    /**
     * 关联查询根据父节点id 查询该节点链条的整体信息
     *
     * @param parentId 父节点ID 其中 内容为map
     * @return TreeNode 格式
     */
    @Override
    public TreeNodeBO<? extends TreeModel<PK>, PK> findNodeByParentIdJoin(PK parentId) {

        final Map<String, Object> parentMap = super.findByIdJoin(parentId);

        final TreeNodeBO<TreeMap<String, Object, PK>, PK> treeNodeBO = warpMapNode(parentMap);

        buildTreeMap(treeNodeBO);

        return treeNodeBO;
    }

    /**
     * @param parentId 父节点ID
     * @param retType  返回类型Class
     * @param <T>      返回类型
     * @return TreeNodeBO<T>
     */
    @Override
    public <T extends TreeModel<PK>> TreeNodeBO<T, PK> findNodeByParentIdJoin(PK parentId, Class<T> retType) {

        final TreeNodeBO<? extends TreeModel<PK>, PK> treeNodeBO = this.findNodeByParentIdJoin(parentId);

        TreeNodeBO<T, PK> resultTreeNode = new TreeNodeBO<>();

        BeanUtils.copyProperties(treeNodeBO, resultTreeNode, retType);

        return resultTreeNode;
    }


    /**
     * 关联查询全部数据
     *
     * @return TreeNode数据集  每一个TreeNode 都为一个整体节点
     */
    @Override
    public Collection<TreeNodeBO<? extends TreeModel<PK>, PK>> findNodeJoin() {
        final Collection<E> supperParent = this.getSupperParent();
        return supperParent
                .stream()
                .map(record -> this.findNodeByParentIdJoin(record.getId(), dtoType))
                .collect(Collectors.toList());


    }

    /**
     * @param retType 返回类型Class
     * @param <T>     返回类型
     * @return TreeNode数据集其内类型为T
     */
    @Override
    public <T extends TreeModel<PK>> Collection<TreeNodeBO<T, PK>> findNodeJoin(Class<T> retType) {

        return this.getSupperParent()
                .stream()
                .map(record -> this.findNodeByParentIdJoin(record.getId(), retType))
                .collect(Collectors.toList());

    }


    /**
     * 获取顶级没有父Id 的数据
     *
     * @return Collection<E>
     */
    protected Collection<E> getSupperParent() {

        final QueryWrapper<E> queryWrapper = getQueryWrapper();

        final String column = getParentColumn();

        queryWrapper.isNull(column).or().eq(column, "");

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 包装 TreeNodeBO
     *
     * @param parent E
     * @return TreeNodeBO
     */
    protected TreeNodeBO<E, PK> warpNode(E parent) {

        TreeNodeBO<E, PK> treeNodeBO = new TreeNodeBO<>();

        treeNodeBO.setParent(parent);

        return treeNodeBO;
    }


    /**
     * 包装继承 BaseTreePO 类
     *
     * @param parent T
     * @param <T>    T
     * @return TreeNodeBO<T>
     */
    protected <T extends TreeModel<PK>> TreeNodeBO<T, PK> warpOtherNode(T parent) {

        TreeNodeBO<T, PK> treeNodeBO = new TreeNodeBO<>();

        treeNodeBO.setParent(parent);

        return treeNodeBO;
    }


    /**
     * 特殊map 处理类型
     *
     * @param parent Map
     * @return TreeNodeBO<TreeMap < String, Object>>
     */
    protected TreeNodeBO<TreeMap<String, Object, PK>, PK> warpMapNode(Map<String, Object> parent) {

        TreeNodeBO<TreeMap<String, Object, PK>, PK> treeNodeBO = new TreeNodeBO<>();

        TreeMap<String, Object, PK> treeMap = new TreeMap<>();

        treeMap.putAll(parent);

        treeMap.setIdKey(getIdColumn());

        treeMap.setPatentIdKey(getParentColumn());

        treeNodeBO.setParent(treeMap);

        return treeNodeBO;
    }


    /**
     * 递归生成节点数据
     *
     * @param treeNodeBO treeNodeBO必须包含顶级节点信息 treeNodeBO.getParent() not null
     */
    private void buildTree(TreeNodeBO<E, PK> treeNodeBO) {

        List<TreeNodeBO<E, PK>> descendantNodes = getDescendantNodes(treeNodeBO.getParent().getId());

        List<TreeNodeBO<E, PK>> children = new ArrayList<>((treeNodeBO.getChildren() == null) ? Collections.emptyList() : new LinkedList<>(treeNodeBO.getChildren()));

        children.addAll(descendantNodes);

        treeNodeBO.setChildren(children);

        if (!CollectionUtils.isEmpty(children))
            descendantNodes.forEach(this::buildTree);
    }

    /**
     * 递归生成节点数据 TreeMap 特殊处理
     *
     * @param treeNodeBO treeNodeBO treeNodeBO必须包含顶级节点信息 treeNodeBO.getParent() not null
     */
    protected void buildTreeMap(TreeNodeBO<TreeMap<String, Object, PK>, PK> treeNodeBO) {

        List<TreeNodeBO<TreeMap<String, Object, PK>, PK>> descendantNodes = getDescendantMapNodes(treeNodeBO.getParent().getId());

        List<TreeNodeBO<TreeMap<String, Object, PK>, PK>> children = new ArrayList<>((treeNodeBO.getChildren() == null) ? Collections.emptyList() : new LinkedList<>(treeNodeBO.getChildren()));

        children.addAll(descendantNodes);

        treeNodeBO.setChildren(children);

        if (!CollectionUtils.isEmpty(children))
            descendantNodes.forEach(this::buildTreeMap);
    }


    /**
     * 生成每一条节点的数据以及子数据
     *
     * @param parentId 父id
     * @return 该 parentId 下的所有子节点
     */
    protected List<TreeNodeBO<E, PK>> getDescendantNodes(PK parentId) {

        List<E> list = this.findChildren(parentId).stream().map(d -> d.convertToR(poType)).collect(Collectors.toList());

        List<TreeNodeBO<E, PK>> nodes = new ArrayList<>();

        list.forEach(e -> {

            TreeNodeBO<E, PK> treeNodeBO = warpNode(e);

            nodes.add(treeNodeBO);

        });

        return nodes;

    }

    /**
     * 生成每一条节点的数据以及子数据
     *
     * @param parentId 父id
     * @return 该 parentId 下的所有子节点  TreeMap 特殊处理
     */
    protected List<TreeNodeBO<TreeMap<String, Object, PK>, PK>> getDescendantMapNodes(PK parentId) {

        List<Map<String, Object>> list = new ArrayList<>(this.findChildrenJoin(parentId));

        List<TreeNodeBO<TreeMap<String, Object, PK>, PK>> nodes = new ArrayList<>();

        list.forEach(e -> {

            TreeNodeBO<TreeMap<String, Object, PK>, PK> treeNodeBO = warpMapNode(e);

            nodes.add(treeNodeBO);

        });

        return nodes;

    }


    private String getParentColumn() {
        return Optional.ofNullable(parentColumn).orElseGet(() -> parentColumn = getColumn(E::getParentId));
    }


}
