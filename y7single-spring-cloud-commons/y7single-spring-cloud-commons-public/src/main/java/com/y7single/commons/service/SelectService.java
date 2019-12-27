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

import com.y7single.commons.model.bo.PageBO;
import com.y7single.commons.model.dto.BaseDTO;
import com.y7single.commons.model.dto.DTO;
import com.y7single.commons.model.qo.JoinQO;
import com.y7single.commons.model.qo.PageQO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author: y7
 * @date: 2019/12/17 14:56
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 查询数据
 */
public interface SelectService<T, PK extends Serializable> {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 查询结果, 无结果时返回{@code null}
     */
    T findById(PK id);

    /**
     * 根据多个主键查询
     *
     * @param ids 主键集合
     * @return 查询结果, 如果无结果返回空集合
     */
    Collection<T> findById(Collection<PK> ids);

    /**
     * 查询所有结果
     *
     * @return 所有结果, 如果无结果则返回空集合
     */
    Collection<T> find();


    /**
     * 分页查询
     *
     * @param pageQO 查询逻辑
     * @return {@link PageBO}
     */
    PageBO<T> findByPage(PageQO pageQO);


    /**
     * 根据id关联查询
     *
     * @param pk 主键
     * @return 将查询对象封装为map
     */
    Map<String, Object> findByIdJoin(PK pk);

    /**
     * 查询所有关联对象
     *
     * @param joinQO {@link JoinQO}
     * @return 将查询对象封装为map 集合
     */
    Collection<Map<String, Object>> findJoin(JoinQO joinQO);


    /**
     * 条件查询
     *
     * @param joinQO {@link JoinQO}
     * @return 关联查询Map集合
     */
    Collection<Map<String, Object>> findJoinWhere(JoinQO joinQO);


    /**
     * 根据id关联查询
     *
     * @param pk  主键
     * @param <R> 需要返回的类型
     * @return 将查询对象封装为map
     */
    <R> R findByIdJoin(PK pk, Class<R> retType);

    /**
     * 查询所有关联对象
     *
     * @param joinQO {@link JoinQO}
     * @return 将查询对象封装为map 集合
     */

    <R> Collection<R> findJoin(JoinQO joinQO, Class<R> retType);


    /**
     * 条件查询
     *
     * @param joinQO {@link JoinQO}
     * @param <R>    返回类型
     * @return 关联查询Map集合
     */
    <R> Collection<R> findJoinWhere(JoinQO joinQO, Class<R> retType);

}
