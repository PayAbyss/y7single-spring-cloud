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


import com.y7single.commons.enums.DefaultResultCode;
import com.y7single.commons.exceptions.BeanUtilsException;
import com.y7single.commons.exceptions.ParameterInvalidException;
import com.y7single.commons.model.bo.PageBO;
import com.y7single.commons.model.dto.BaseDTO;
import com.y7single.commons.model.qo.JoinQO;
import com.y7single.commons.model.qo.PageQO;
import com.y7single.commons.model.vo.BaseVO;
import com.y7single.commons.service.BaseApiService;
import com.y7single.commons.utils.BeanUtils;
import com.y7single.commons.utils.ParameterizedTypeUtils;
import com.y7single.commons.web.controller.BaseApiController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: y7
 * @date: 2019/12/20 18:04
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: S BaseApiService 实现类需要交予spring 容器管理
 * 如果使用dubbo 请调用 supper(BaseApiService) 注入service
 * V 普通返回类
 * D 与BaseApiService 交互dto 类型
 * PK id 类型
 */
@SuppressWarnings("all")
public abstract class BaseController<S extends BaseApiService, V extends BaseVO<PK>, D extends BaseDTO<PK>, PK extends Serializable> implements BaseApiController<S, V, D, PK> {


    @Autowired(required = false)
    protected S baseApiService;

    protected Class<V> vType;

    protected Class<D> dType;


    public BaseController() {

        final Class<? extends BaseController> thisClass = this.getClass();

        vType = (Class<V>) ParameterizedTypeUtils.getParameterizedType(thisClass, 1);

        dType = (Class<D>) ParameterizedTypeUtils.getParameterizedType(thisClass, 2);

    }


    @ApiOperation(value = "添加数据", notes = "添加数据", produces = "application/json")
    @PostMapping
    @Override
    public boolean insert(@RequestBody D record) {

        return this.baseApiService.insert(record);

    }


    @ApiOperation(value = "添加数据(多条)", notes = "添加数据(多条)", produces = "application/json")
    @PostMapping(value = "creates")
    @Override
    public boolean insert(@RequestBody Collection<D> records) {

        return this.baseApiService.insert(records);
    }


    @ApiOperation(value = "删除数据(单条)", notes = "删除数据(单条)")
    @ApiImplicitParam(name = "id", value = "需要删除的数据id", paramType = "path", required = true, dataTypeClass = Serializable.class)
    @DeleteMapping(value = "{id}")
    @Override
    public boolean delete(@PathVariable(value = "id") PK id) {
        return this.baseApiService.delete(id);
    }


    @ApiOperation(value = "删除数据(多条)", notes = "删除数据(多条)")
    @DeleteMapping(value = "deletes")
    @Override
    public boolean delete(@RequestBody Collection<PK> ids) {
        return this.baseApiService.delete(ids);
    }


    @ApiOperation(value = "修改数据", notes = "修改数据", produces = "application/json")
    @PutMapping
    @Override
    public boolean update(@RequestBody D record) {

        return this.baseApiService.update(record);
    }


    @ApiOperation(value = "修改部分数据", notes = "修改部分数据如果数据中的字段不为null则进行修改", produces = "application/json")
    @PutMapping(value = "updatePart")
    @Override
    public boolean updatePart(@RequestBody D record) {
        return this.baseApiService.updatePart(record, null);
    }


    @ApiOperation(value = "查询数据(单条)", notes = "查询数据(单条)")
    @ApiImplicitParam(name = "id", value = "数据id", paramType = "path", required = true)
    @GetMapping(value = "{id}")
    @Override
    public V findById(@PathVariable(value = "id") PK id) {

        final D d = (D) this.baseApiService.findById(id);

        return d.convertToR(vType);

    }


    @ApiOperation(value = "查询数据(多条)", notes = "查询数据(多条)", produces = "application/json")
    @GetMapping(value = "findByIds")
    @Override
    public Collection<V> findById(@RequestBody Collection<PK> ids) {

        final Collection<D> dResults = this.baseApiService.findById(ids);

        return copyResult(dResults);

    }


    @ApiOperation(value = "查询数据(全部)", notes = "查询数据(全部)")
    @GetMapping
    @Override
    public Collection<V> find() {


        final Collection<D> dResults = this.baseApiService.find();

        return copyResult(dResults);


    }


    @ApiOperation(value = "分页查询", notes = "分页查询", produces = "application/json")
    @PostMapping(value = "findByPage")
    @Override
    public PageBO<V> findByPage(@RequestBody PageQO pageQO) {

        checkeParamerStringIsEmpty(pageQO.getOrderColumn());

        final PageBO<D> pageBO = this.baseApiService.findByPage(pageQO);

        PageBO<V> pageBOV = new PageBO<>(pageBO.getCurrent(), pageBO.getPageSize(), pageBO.getTotal(), pageBO.getPages(), null);

        final List<V> records = pageBO.getRecords().stream().map(record -> record.convertToR(vType)).collect(Collectors.toList());

        pageBOV.setRecords(records);

        return pageBOV;
    }


    @ApiOperation(value = "关联查询(单条)", notes = "关联查询(单条)")
    @ApiImplicitParam(name = "id", value = "数据id", paramType = "path", required = true)
    @GetMapping(value = "findByIdJoin/{id}")
    @Override
    public V findByIdJoin(@PathVariable(value = "id") PK id) {

        final Map map = this.baseApiService.findByIdJoin(id);

        return copyResult(map);
    }


    @ApiOperation(value = "关联查询(全部)", notes = "关联查询(全部)", produces = "application/json")
    @PostMapping(value = "findJoin")
    @Override
    public Collection<V> findJoin(@RequestBody JoinQO joinQO) {

        checkeParamerStringIsEmpty(joinQO.getOrderColumn());

        final Collection<Map<String, Object>> dResults = this.baseApiService.findJoin(joinQO);

        return copyResultMap(dResults);
    }


    @ApiOperation(value = "关联查询(条件)", notes = "关联查询(条件)", produces = "application/json")
    @PostMapping(value = "findJoinWhere")
    @Override
    public Collection<V> findJoinWhere(@RequestBody JoinQO joinQO) {

        checkeParamerStringIsEmpty(joinQO.getOrderColumn(), joinQO.getWhereColumnName());

        final Collection<Map<String, Object>> dResults = this.baseApiService.findJoinWhere(joinQO);

        return copyResultMap(dResults);
    }


    /**
     * @param d {@link D}
     * @return {@link V}
     */
    protected V copyResult(D d) {

        return this.copyObjectToResult(d);
    }

    /**
     * @param map {@link Map}
     * @return {@link V}
     */
    protected V copyResult(Map map) {

        return this.copyObjectToResult(map);
    }


    /**
     * 数据类型转换 Collection D 数据复制转为 Collection V
     *
     * @param dResults D 集合
     * @return {@link  Collection<V>}
     */
    protected Collection<V> copyResult(Collection<D> dResults) {

        return dResults
                .stream()
                .map(d -> d.convertToR(vType))
                .collect(Collectors.toList());

    }

    /**
     * 数据类型转换 Collection D 数据复制转为 Collection V
     *
     * @param dResults D 集合
     * @return {@link  Collection<V>}
     */
    protected Collection<V> copyResultMap(Collection<Map<String, Object>> dResults) {

        List<V> result = new ArrayList<>(dResults.size());

        BeanUtils.copyProperties(dResults, result, vType);

        return result;

    }

    /**
     * 类型转换将 任意类型复制转为 V
     *
     * @param obj
     * @return {@link V}
     */
    private V copyObjectToResult(Object obj) {

        if (obj.getClass() == vType.getClass()) return (V) obj;

        try {
            final V newInstance = vType.newInstance();

            BeanUtils.copyProperties(obj, newInstance);

            return newInstance;

        } catch (InstantiationException | IllegalAccessException e) {

            throw new BeanUtilsException(DefaultResultCode.BEAN_INSTANCE_FAILED);
        }

    }


    public void setBaseApiService(S baseApiService) {

        this.baseApiService = baseApiService;

    }

    protected void checkeParamerStringIsEmpty(String... params) {

        if (params.length <= 0) throw new ParameterInvalidException(DefaultResultCode.PARAM_IS_INVALID);

        for (String param : params) {
            if (StringUtils.isEmpty(param))
                throw new ParameterInvalidException(DefaultResultCode.PARAM_IS_INVALID);

        }
    }
}
