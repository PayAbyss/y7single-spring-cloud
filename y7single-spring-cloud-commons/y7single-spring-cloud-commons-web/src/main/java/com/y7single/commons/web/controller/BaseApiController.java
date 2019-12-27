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

import com.y7single.commons.model.bo.PageBO;
import com.y7single.commons.model.dto.BaseDTO;
import com.y7single.commons.model.qo.JoinQO;
import com.y7single.commons.model.qo.PageQO;
import com.y7single.commons.model.vo.BaseVO;
import com.y7single.commons.service.BaseApiService;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
public interface BaseApiController<S extends BaseApiService, V extends BaseVO, D extends BaseDTO, PK extends Serializable> {


    /**
     * 插入数据
     *
     * @param record 对象
     * @return 插入成功数据
     */
    boolean insert(@Valid D record);


    /**
     * 批量插入
     *
     * @param records 数据集合
     * @return 插入成功数据
     */
    boolean insert(@Valid @NotEmpty(message = "数据不能为空") @NotNull(message = "数据格式错误") Collection<D> records);


    /**
     * 删除数据
     *
     * @param id id
     * @return 是否删除成功
     */
    boolean delete(@NotNull(message = "id不能为空") PK id);

    /**
     * 批量删除
     *
     * @param ids id 集合
     * @return 是否删除成功
     */
    boolean delete(@Valid @NotEmpty(message = "数据不能为空") @NotNull(message = "数据格式错误") Collection<PK> ids);

    /**
     * 修改记录
     *
     * @param record 需要修改的对象
     * @return 修改成功数据
     */
    boolean update(@Valid D record);


    /**
     * 修改部分数据
     *
     * @param record 数据
     * @return 修改成功数据
     */
    boolean updatePart(@Valid D record);


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 查询结果, 无结果时返回{@code null}
     */
    V findById(@NotNull(message = "id不能为空") PK id);

    /**
     * 根据多个主键查询
     *
     * @param ids 主键集合
     * @return 查询结果, 如果无结果返回空集合
     */
    Collection<V> findById(@Valid @NotEmpty(message = "数据不能为空") @NotNull(message = "数据格式错误") Collection<PK> ids);

    /**
     * 查询所有结果
     *
     * @return 所有结果, 如果无结果则返回空集合
     */
    Collection<V> find();


    /**
     * 分页查询
     *
     * @param pageQO 查询逻辑
     * @return {@link PageBO}
     */
    PageBO<V> findByPage(@Valid PageQO pageQO);


    /**
     * 根据id关联查询
     *
     * @param id  主键
     * @param <T> 需要返回的类型
     * @return 将查询对象封装为map
     */
    V findByIdJoin(@NotNull(message = "id不能为空") PK id);

    /**
     * 查询所有关联对象
     *
     * @param joinQO {@link JoinQO}
     * @return 将查询对象封装为map 集合
     */

    Collection<V> findJoin(@Valid JoinQO joinQO);


    /**
     * 条件查询
     *
     * @param joinQO {@link JoinQO}
     * @param <T>    返回类型
     * @return 关联查询Map集合
     */
    Collection<V> findJoinWhere(@Valid JoinQO joinQO);

}
