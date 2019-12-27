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

package com.y7single.commons.enums;

import com.y7single.commons.model.exception.ResultCode;

/**
 * @author: y7
 * @date: 2019/12/17 16:45
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public enum DefaultResultCode implements ResultCode {

    SUCCESS(200, "请求成功"),


    /*
     * 系统自定义状态码 格式
     * <p>
     * 000(错误原因) 0000(错误代码)
     */


    //**********************参数问题：100 错误代码：1000-9999**********************
    PARAM_IS_INVALID(1001000, "参数无效"),
    PARAM_IS_BLANK(1001001, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1001002, "参数类型错误"),
    PARAM_NOT_COMPLETE(1001003, "参数缺失"),

    //**********************用户错误：101 错误代码：1000-9999**********************
    USER_NOT_LOGGED_IN(1011000, "用户未登录"),
    USER_LOGIN_ERROR(1011001, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(1011002, "账号已被禁用"),
    USER_NOT_EXIST(1011003, "用户不存在"),
    USER_HAS_EXISTED(1011004, "用户已存在"),
    LOGIN_CREDENTIAL_EXISTED(1011005, "凭证已存在"),

    //**********************业务错误：102 错误代码：1000-9999**********************
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(10211000, "业务错误"),

    //**********************系统错误：103 错误代码：1000-9999**********************
    SYSTEM_INNER_ERROR(1031000, "系统繁忙，请稍后重试"),

    //**********************数据错误：104 错误代码：1000-9999**********************
    DATA_NOT_EXISTED(1041000, "数据未找到"),
    DATA_WRONG(1041001, "数据有误"),
    DATA_EXISTED(1041002, "数据已存在"),
    DATA_DECRYPT_FAILED(1041003, "数据加密失败"),
    DATA_ENCRYPT_FAILED(1041004, "数据解密失败"),
    BEAN_INSTANCE_FAILED(1041005, "数据实例失败"),
    DATA_CONVERSION_FAILED(1041006, "数据操作失败"),

    //**********************接口错误：105 错误代码：1000-9999**********************
    INTERFACE_INNER_INVOKE_ERROR(1051000, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(1051001, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(1051002, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(1051003, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(1051004, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(1051005, "接口负载过高"),


    //**********************权限错误：106 错误代码：1000-9999**********************
    PERMISSION_NO_ACCESS(1061000, "无访问权限"),
    RESOURCE_EXISTED(1061002, "资源已存在"),
    RESOURCE_NOT_EXISTED(1061003, "资源不存在"),


    //**********************断言错误：108 错误代码：1000-9999**********************
    INSERT_DATA_ERR(1081000, "数据添加失败"),
    UPDATE_DATA_ERR(1081001, "数据修改失败"),
    DELETE_DATA_ERR(1081001, "数据删除失败");


    private Integer code;
    private String description;


    DefaultResultCode(Integer code, String message) {
        this.code = code;
        this.description = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
