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

package com.y7single.service.sql;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.y7single.service.model.po.BasePO;
import com.y7single.service.annotations.JoinTable;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author: y7
 * @date: 2019/10/29 16:45
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: 自定义SQL执行
 */
@Slf4j
public final class BaseSQLCurdMapperSQLProvider {

    private static final String SQL_BY_ID = "SELECT %s FROM %s %s WHERE %s.%s='%s'";
    private static final String SQL_BY_WHERE = "SELECT %s FROM %s %s WHERE %s.%s='%s' ORDER BY %s.%s %s";
    private static final String SQL_BY_All = "SELECT %s FROM %s %s ORDER BY %s.%s %s";
    private static final String JOIN_TABLE_SQL = "  LEFT JOIN  %s As %s ON %s.%s=%s.%s  ";
    private static final String JOIN_QUERY = " %s.%s AS %s";
    private static final String QUERY_SQL = "%s.%s";
    private static Map<String, String> SQL_CACHE = new LinkedHashMap<>();


    /**
     * 根据主键关联查询
     *
     * @param param 查询参数
     * @return sql
     */
    public String selectByPKJoin(Map<String, Object> param) {

        Class<? extends BasePO> poClass = (Class<? extends BasePO>) param.get("po");
        String tableName = poClass.getAnnotation(TableName.class).value();
        String mapKey = tableName.concat(":").concat("byPK");
        String pk = (String) param.get("pk");

        //清除特殊符号 简单防止sql注入
        pk = pk.replaceAll(".*([';]+|(--)+).*", " ");

        if (SQL_CACHE.containsKey(mapKey)) {
            String sql = String.format(SQL_CACHE.get(mapKey), pk);
            log.debug("Class:[BaseSQLCurdMapperSQLProvider]:Method:[selectByPKInnerJoin]:SQL:[{}]", sql);
            return sql;
        }

        String sql = String.format(generateInnerJoinSQL(poClass, true, null, null, false, mapKey), pk);
        log.debug("Class:[BaseSQLCurdMapperSQLProvider]:Method:[selectByPKInnerJoin]:SQL:[{}]", sql);
        return sql;
    }

    public String selectAllJoin(Map<String, Object> param) {

        Class<? extends BasePO> poClass = (Class<? extends BasePO>) param.get("po");
        String tableName = poClass.getAnnotation(TableName.class).value();
        String orderColumn = Objects.isNull(param.get("orderColumn")) ? "create_time" : (String) param.get("orderColumn");
        String order = Objects.isNull(param.get("order")) ? "DESC" : (String) param.get("order");

        String mapKey = tableName.concat(":").concat(orderColumn).concat(":").concat(order);


        if (SQL_CACHE.containsKey(mapKey)) {
            String sql = SQL_CACHE.get(mapKey);
            log.debug("Class:[BaseSQLCurdMapperSQLProvider]:Method:[selectAllInnerJoin]:SQL:[{}]", sql);
            return sql;
        }

        String sql = generateInnerJoinSQL(poClass, false, orderColumn, order, false, mapKey);
        log.debug("Class:[BaseSQLCurdMapperSQLProvider]:Method:[selectAllInnerJoin]:SQL:[{}]", sql);
        return sql;
    }


    public String selectAllJoinWhere(Map<String, Object> param) {

        Class<? extends BasePO> poClass = (Class<? extends BasePO>) param.get("po");
        String tableName = poClass.getAnnotation(TableName.class).value();
        String orderColumn = Objects.isNull(param.get("orderColumn")) ? "create_time" : (String) param.get("orderColumn");
        String order = Objects.isNull(param.get("order")) ? "DESC" : (String) param.get("order");
        String whereColumnName = (String) param.get("whereColumnName");
        Object whereColumnValue = param.get("whereColumnValue");
        String mapKey = tableName.concat(":").concat(":").concat("where").concat(":").concat(whereColumnName).concat(":").concat(orderColumn).concat(":").concat(order);


        if (SQL_CACHE.containsKey(mapKey)) {
            String sql = String.format(SQL_CACHE.get(mapKey), whereColumnName, whereColumnValue);
            log.debug("Class:[BaseSQLCurdMapperSQLProvider]:Method:[selectAllInnerJoinWhere]:SQL:[{}]", sql);
            return sql;
        }


        String sql = String.format(generateInnerJoinSQL(poClass, false, orderColumn, order, true, mapKey), whereColumnName, whereColumnValue);
        log.debug("Class:[BaseSQLCurdMapperSQLProvider]:Method:[selectAllInnerJoinWhere]:SQL:[{}]", sql);
        return sql;
    }


    private String generateInnerJoinSQL(Class<? extends BasePO> cls, boolean isById, String orderColumn, String order, boolean isWhere, String mapKey) {

        final Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(cls);

        //表名
        String tableName = cls.getAnnotation(TableName.class).value();

        //查询SQL拼接器
        StringBuilder queryColumns = new StringBuilder();
        //关联SQL拼接器
        StringBuilder joinSQL = new StringBuilder();
        //获取id列名
        String idColumn = null;

        TreeSet<Field> fields = getField(cls);

        //组装SQL语句
        for (Field declaredField : fields) {

            if (declaredField.isAnnotationPresent(TableField.class)) {
                if (!declaredField.getAnnotation(TableField.class).exist()) {
                    continue;
                }
            }
            String column = columnMap.get(LambdaUtils.formatKey(declaredField.getName())).getColumn();

            if (declaredField.isAnnotationPresent(JoinTable.class)) {
                joinField(declaredField, queryColumns, joinSQL);
            } else {
                queryColumns.append(String.format(QUERY_SQL, tableName, column));
            }
            //获得Id字段
            if (declaredField.isAnnotationPresent(TableId.class)) {
                idColumn = column;
            }

            queryColumns.append(",");
        }

        queryColumns.deleteCharAt(queryColumns.length() - 1);

        //格式化组装SQL
        final String sql;
        if (isById) {
            sql = String.format(SQL_BY_ID, queryColumns, tableName, joinSQL, tableName, Optional.ofNullable(idColumn).orElse("id"), "%s");
        } else if (isWhere) {
            sql = String.format(SQL_BY_WHERE, queryColumns, tableName, joinSQL, tableName, "%s", "%s", tableName, orderColumn, order);
        } else {
            sql = String.format(SQL_BY_All, queryColumns, tableName, joinSQL, tableName, orderColumn, order);
        }
        //缓存SQL
        SQL_CACHE.put(mapKey, sql);
        return sql;

    }


    /**
     * 根据连接字段生成sql
     *
     * @param field        字段
     * @param queryBuilder 查询sql拼接器
     * @param sqlBuilder   连接sql拼接而器
     */
    private void joinField(Field field, StringBuilder queryBuilder, StringBuilder sqlBuilder) {

        final JoinTable joinTable = field.getAnnotation(JoinTable.class);

        //连接表
        String connTableName = joinTable.connectClass().getAnnotation(TableName.class).value();
        //关联表
        String joinTableName = joinTable.joinClass().getAnnotation(TableName.class).value();

        String uid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5).toLowerCase();

        String asJoinTableName = joinTableName.concat(uid);

        //连接列
        String connColumn = joinTable.connectColumn();
        //关联列
        String joinColumn = joinTable.joinColumn();

        //别名
        String aliasName = joinTable.aliasName();
        //查询列
        String queryColumn = joinTable.queryName();

        String joinQuery = String.format(JOIN_QUERY, asJoinTableName, queryColumn, aliasName);
        String join = String.format(JOIN_TABLE_SQL, joinTableName, asJoinTableName, asJoinTableName, joinColumn, connTableName, connColumn);

        queryBuilder.append(joinQuery);
        sqlBuilder.append(join);
    }


    private TreeSet<Field> getField(Class<? extends BasePO> cls) {


        //获取当前类全部字段
        final Field[] declaredFields = cls.getDeclaredFields();

        Set<Field> declaredAllFields = new LinkedHashSet<>(Arrays.asList(declaredFields));

        //获取父类全部字段
        while (cls != BasePO.class) {
            cls = (Class<? extends BasePO>) cls.getSuperclass();
            Field[] f = cls.getDeclaredFields();
            declaredAllFields.addAll(Arrays.asList(f));
        }
        //字段去重
        return declaredAllFields.stream().filter(field -> !field.getName().equals("serialVersionUID")).collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Field::getName))));

    }


}
