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

package com.y7single.commons.http;

import java.io.IOException;
import java.util.Map;

/**
 * @author: y7
 * @date: 2019/11/1 15:04
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: http 接口定义
 */
public interface Http {

    /**
     * invoke http get method
     *
     * @param path    http path
     * @param headers http headers
     * @return HttpResult http response
     * @throws IOException If an input or output exception occurred
     */

    HttpResult httpGet(String path, Map<String, String> headers) throws IOException;


    void httpGet(String path, Map<String, String> headers, Call callback) throws IOException;

    /**
     * invoke http post method
     *
     * @param path    http path
     * @param headers http headers
     * @param params  http paramValues
     * @return HttpResult http response
     * @throws IOException If an input or output exception occurred
     */
    HttpResult httpPost(String path, Map<String, String> headers, Map<String, Object> params) throws IOException;

    void httpPost(String path, Map<String, String> headers, Map<String, Object> params, Call callback) throws IOException;

    /**
     * invoke http put method
     *
     * @param path    http path
     * @param headers http headers
     * @param params  http paramValues
     * @return HttpResult http response
     * @throws IOException If an input or output exception occurred
     */
    HttpResult httpPut(String path, Map<String, String> headers, Map<String, Object> params) throws IOException;

    void httpPut(String path, Map<String, String> headers, Map<String, Object> params, Call callback) throws IOException;

    /**
     * invoke http delete method
     *
     * @param path    http path
     * @param headers http headers
     * @param params  http paramValues
     * @return HttpResult http response
     * @throws IOException If an input or output exception occurred
     */
    HttpResult httpDelete(String path, Map<String, String> headers, Map<String, Object> params) throws IOException;

    void httpDelete(String path, Map<String, String> headers, Map<String, Object> params, Call callback) throws IOException;
}
