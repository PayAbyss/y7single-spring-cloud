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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.y7single.commons.dao.service.mp.BaseApiServiceForMp;
import com.y7single.commons.enums.DefaultResultCode;
import com.y7single.commons.exceptions.SQLException;
import com.y7single.commons.model.bo.PageBO;
import com.y7single.commons.model.dto.BaseDTO;
import com.y7single.commons.model.po.PO;
import com.y7single.commons.model.qo.JoinQO;
import com.y7single.commons.model.qo.PageQO;
import com.y7single.commons.utils.BeanUtils;
import com.y7single.service.mapper.BaseSQLCurdMapper;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: y7
 * @date: 2019/12/20 14:51
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: mybatis plus 实现 M Mapper E dao PK 主键类型
 */
public abstract class BaseApiServiceForMpImpl<M extends BaseSQLCurdMapper<E, PK>, E extends PO<PK>, D extends BaseDTO<PK>, PK extends Serializable> extends ServiceImpl<M, E> implements BaseApiServiceForMp<E, D, PK> {

    protected Class<E> poType;

    protected Class<D> dtoType;


    private String idColumn;

    protected static Map<String, ColumnCache> columnMap;

    @SuppressWarnings("unchecked")
    public BaseApiServiceForMpImpl() {

        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();

        poType = (Class<E>) parameterizedType.getActualTypeArguments()[1];
        dtoType = (Class<D>) parameterizedType.getActualTypeArguments()[2];
    }

    /**
     * @param record 对象
     * @return 是否插入成功
     */
    @Override
    public boolean insert(D record) {

        final E e = record.convertToR(poType);

        return save(e);
    }


    /**
     * @param records 数据集合
     * @return 是否插入成功
     */
    @Override
    public boolean insert(Collection<D> records) {

        final List<E> poRecords = records.stream().map(record -> record.convertToR(poType)).collect(Collectors.toList());

        return saveBatch(poRecords);
    }

    /**
     * @param id id
     * @return 是否删除成功
     */
    @Override
    public boolean delete(PK id) {
        return removeById(id);
    }

    /**
     * @param ids id 集合
     * @return 是否删除成功
     */
    @Override
    public boolean delete(Collection<PK> ids) {
        return removeByIds(ids);
    }


    /**
     * @param id 主键
     * @return {@link D}
     */
    @Override
    public D findById(PK id) {

        final E e = getById(id);

        return convertToD(e);
    }

    /**
     * @param ids 主键集合
     * @return {@link Collection<D>}
     */
    @Override
    public Collection<D> findById(Collection<PK> ids) {

        final Collection<E> poResults = listByIds(ids);

        return poResults.stream().map(this::convertToD).collect(Collectors.toList());

    }

    /**
     * @return {@link Collection<D>}
     */
    @Override
    public Collection<D> find() {

        final List<E> es = list();

        return convertToD(es);

    }

    /**
     * @param pageQO 查询逻辑
     * @return {@link PageBO<D>}
     */
    @Override
    public PageBO<D> findByPage(PageQO pageQO) {

        pageQO.setOrderColumn(updateColumn(pageQO.getOrderColumn()));

        Page<E> page = new Page<>(pageQO.getPageNum(), pageQO.getPageSize());

        if (pageQO.isAsc())
            page.addOrder(OrderItem.asc(pageQO.getOrderColumn()));
        else
            page.addOrder(OrderItem.desc(pageQO.getOrderColumn()));

        final IPage<E> iPage = page(page);

        final List<D> ds = convertToD(iPage.getRecords());

        return new PageBO<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages(), ds);
    }

    /**
     * @param pk 主键
     * @return 关联查询数据map
     */
    @Override
    public Map<String, Object> findByIdJoin(PK pk) {
        return this.baseMapper.findByIdJoin(poType, pk);
    }

    /**
     * @param joinQO {@link JoinQO}
     * @return 关联查询数据Map集合
     */
    @Override
    public Collection<Map<String, Object>> findJoin(JoinQO joinQO) {

        joinQO.setOrderColumn(updateColumn(joinQO.getOrderColumn()));

        return this.baseMapper.findJoin(poType, joinQO.getOrderColumn(), joinQO.isAsc() ? "asc" : "desc");
    }

    /**
     * @param joinQO {@link JoinQO}
     * @return 关联查询数据Map集合
     */
    @Override
    public Collection<Map<String, Object>> findJoinWhere(JoinQO joinQO) {

        joinQO.setWhereColumnName(updateColumn(joinQO.getWhereColumnName()));
        joinQO.setOrderColumn(StringUtils.isEmpty(joinQO.getOrderColumn()) ? "create_time" : joinQO.getOrderColumn());
        return this.baseMapper.findJoinWhere(poType, joinQO.getOrderColumn(), joinQO.isAsc() ? "asc" : "desc", joinQO.getWhereColumnName(), joinQO.getWhereColumnValue());
    }

    /**
     * @param pk      主键
     * @param retType 返回类型Class
     * @param <T>     返回类型
     * @return T
     */
    @Override
    public <T> T findByIdJoin(PK pk, Class<T> retType) {

        final Map<String, Object> resultMaps = this.findByIdJoin(pk);

        try {
            final T newInstance = retType.newInstance();

            BeanUtils.copyProperties(resultMaps, newInstance);

            return newInstance;

        } catch (InstantiationException | IllegalAccessException e) {

            throw new SQLException(DefaultResultCode.BEAN_INSTANCE_FAILED);
        }


    }

    /**
     * @param joinQO  {@link JoinQO}
     * @param retType 返回类型Class
     * @param <T>     返回类型
     * @return Collection<T>
     */
    @Override
    public <T> Collection<T> findJoin(JoinQO joinQO, Class<T> retType) {

        final Collection<Map<String, Object>> resultMaps = this.findJoin(joinQO);

        List<T> results = new ArrayList<>(resultMaps.size());

        BeanUtils.copyProperties(resultMaps, results, retType);

        return results;
    }

    /**
     * @param joinQO  {@link JoinQO}
     * @param retType 返回类型Class
     * @param <T>     返回类型
     * @return Collection<T>
     */
    @Override
    public <T> Collection<T> findJoinWhere(JoinQO joinQO, Class<T> retType) {

        final Collection<Map<String, Object>> resultMaps = this.findJoinWhere(joinQO);

        List<T> results = new ArrayList<>(resultMaps.size());

        BeanUtils.copyProperties(resultMaps, results, retType);

        return results;
    }

    /**
     * @param record 需要修改的对象
     * @return 是否修改成功
     */
    @Override
    public boolean update(D record) {

        return updateById(record.convertToR(poType));
    }

    /**
     * 此处只修改属性值不为null的数据
     *
     * @param record 数据
     * @return 是否修改成功
     */
    @Override
    public boolean updatePart(D record) {

        UpdateWrapper<E> update = getUpdateWrapper();

        final E e = record.convertToR(poType);

        PropertyDescriptor[] propertyDescriptors = org.springframework.beans.BeanUtils.getPropertyDescriptors(poType);

        try {

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                Method readMethod = propertyDescriptor.getReadMethod();

                final Object invoke = readMethod.invoke(e);

                if (Objects.isNull(invoke)) continue;

                final String fieldName = PropertyNamer.methodToProperty(propertyDescriptor.getName());

                final String column = columnMap.get(LambdaUtils.formatKey(fieldName)).getColumn();

                update.set(column, invoke);
            }

            final int result = this.baseMapper.update(e, update);

            return SqlHelper.retBool(result);

        } catch (IllegalAccessException | InvocationTargetException ex) {

            throw new SQLException(DefaultResultCode.UPDATE_DATA_ERR);
        }


    }

    protected QueryWrapper<E> getQueryWrapper() {

        return Wrappers.query();
    }


    protected UpdateWrapper<E> getUpdateWrapper() {

        return Wrappers.update();
    }

    protected String getIdColumn() {
        return Optional.ofNullable(idColumn).orElseGet(() -> idColumn = getColumn(E::getId));
    }


    protected String getColumn(SFunction<E, ?> func) {

        if (Objects.isNull(columnMap) || columnMap.isEmpty()) columnMap = LambdaUtils.getColumnMap(poType);

        final SerializedLambda serializedLambda = LambdaUtils.resolve(func);

        final String fieldName = PropertyNamer.methodToProperty(serializedLambda.getImplMethodName());

        final String formatKey = LambdaUtils.formatKey(fieldName);

        ColumnCache columnCache = columnMap.get(formatKey);

        return columnCache.getColumn();
    }

    protected D convertToD(E po) {

        try {
            final D newInstance = dtoType.newInstance();

            newInstance.convertToFor(po);

            return newInstance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SQLException(DefaultResultCode.UPDATE_DATA_ERR);
        }
    }

    protected List<D> convertToD(List<E> pos) {
        return pos.stream().map(this::convertToD).collect(Collectors.toList());
    }


    /**
     * 将驼峰字段改为数据库字段
     *
     * @param columnName 字段名
     */
    protected String updateColumn(String columnName) {

        if (StringUtils.isCamel(columnName))
            return StringUtils.camelToUnderline(columnName);

        return columnName;
    }

}