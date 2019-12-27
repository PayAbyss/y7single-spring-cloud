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

package com.y7single.service.model;


import com.y7single.commons.model.TreeModel;
import com.y7single.commons.model.po.TreePO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author: y7
 * @date: 2019/12/19 14:51
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@SuppressWarnings("unchecked")
public class TreeMap<K, V, PK extends Serializable> implements Map<K, V>, TreePO<PK> {

    private static final long serialVersionUID = -8545871775392088236L;

    private java.util.HashMap<K, V> kvHashMap;
    private K idKey;
    private K parentIdKey;

    public TreeMap() {
        kvHashMap = new java.util.HashMap<>();
    }


    @Override
    public PK getParentId() {
        return (PK) get(parentIdKey);
    }

    @Override
    public void setParentId(PK parentId) {
        kvHashMap.put(parentIdKey, (V) parentId);
    }

    @Override
    public PK getId() {
        return (PK) kvHashMap.get(idKey);
    }

    @Override
    public void setId(PK pk) {
        kvHashMap.put(idKey, (V) pk);
    }

    @Override
    public LocalDateTime getCreateTime() {
        return null;
    }

    @Override
    public void setCreateTime(LocalDateTime createTime) {

    }

    @Override
    public LocalDateTime getUpdateTime() {
        return null;
    }

    @Override
    public void setUpdateTime(LocalDateTime updateTime) {

    }


    public void setPatentIdKey(K key) {
        this.parentIdKey = key;
    }

    public void setIdKey(K key) {
        this.idKey = key;
    }

    @Override
    public int size() {
        return kvHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return kvHashMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return kvHashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return kvHashMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return kvHashMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return kvHashMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return kvHashMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        kvHashMap.putAll(m);
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return kvHashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return kvHashMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return kvHashMap.entrySet();
    }
}
