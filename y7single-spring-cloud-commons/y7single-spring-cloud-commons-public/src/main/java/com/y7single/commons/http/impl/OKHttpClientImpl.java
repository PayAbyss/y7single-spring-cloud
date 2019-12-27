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

package com.y7single.commons.http.impl;

import com.y7single.commons.http.Http;
import com.y7single.commons.http.HttpResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: y7
 * @date: 2019/12/27 16:45
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
public final class OKHttpClientImpl implements Http {

    /**
     * 读取事件
     */
    private static final int READ_TIMEOUT = 100;
    /**
     * 连接时间
     */
    private static final int CONNECT_TIMEOUT = 60;
    /**
     * 写入时间
     */
    private static final int WRITE_TIMEOUT = 60;
    /**
     * MediaType
     */
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 线程锁
     */
    private static final byte[] LOCKER = new byte[0];

    /**
     * okHttp 实例
     */
    private OkHttpClient httpClient;

    /**
     * 单例实例
     */
    private static OKHttpClientImpl instance;

    /**
     * 初始化 {@link OkHttpClient}
     */
    private OKHttpClientImpl() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 读取超时
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        // 连接超时
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //写入超时
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        //构建 实例
        httpClient = builder.build();
    }

    /**
     * 初始化 {@link OkHttpClient}
     */
    private OKHttpClientImpl(int readTimeoutMs, int connectTimeMs, int writeTimeoutMs, TimeUnit timeUnit) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 读取超时
        builder.readTimeout(readTimeoutMs, timeUnit);
        // 连接超时
        builder.connectTimeout(connectTimeMs, timeUnit);
        //写入超时
        builder.writeTimeout(writeTimeoutMs, timeUnit);
        //构建 实例
        httpClient = builder.build();
    }


    /**
     * 单例模式获取 OKHttpClientImpl
     *
     * @return {@link OKHttpClientImpl}
     */
    public static OKHttpClientImpl getInstance() {
        if (instance == null) {
            synchronized (LOCKER) {
                if (instance == null) {
                    instance = new OKHttpClientImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 单例模式获取 OKHttpClientImpl
     *
     * @return {@link OKHttpClientImpl}
     */
    public static OKHttpClientImpl getInstance(int readTimeoutMs, int connectTimeMs, int writeTimeoutMs) {
        if (instance == null) {
            synchronized (LOCKER) {
                if (instance == null) {
                    instance = new OKHttpClientImpl();
                }
            }
        }
        return instance;
    }

    /**
     * sync get request。
     *
     * @param path    http path
     * @param headers http headers
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    @Override
    public HttpResult httpGet(String path, Map<String, String> headers) throws IOException {

        Request request = generateGetRequest(path, headers);

        return executeHttpSync(request);
    }

    /**
     * Async get request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param callback {@link com.y7single.commons.http.Call}
     */
    @Override
    public void httpGet(String path, Map<String, String> headers, com.y7single.commons.http.Call callback) {

        Request request = generateGetRequest(path, headers);

        executeHttpASync(request, callback);
    }


    /**
     * sync post application/x-www-form-urlencoded request。
     *
     * @param path    http path
     * @param headers http headers
     * @param params  http paramValues
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    @Override
    public HttpResult httpPost(String path, Map<String, String> headers, Map<String, Object> params) throws IOException {

        Request request = generatePostRequest(path, headers, params, null);

        return executeHttpSync(request);
    }


    /**
     * sync post Json request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param jsonBody http paramValues
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    public HttpResult httpPost(String path, Map<String, String> headers, String jsonBody) throws IOException {

        Request request = generatePostRequest(path, headers, null, jsonBody);

        return executeHttpSync(request);
    }


    /**
     * Async post application/x-www-form-urlencoded request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param callback {@link com.y7single.commons.http.Call}
     */
    @Override
    public void httpPost(String path, Map<String, String> headers, Map<String, Object> params, com.y7single.commons.http.Call callback) {

        Request request = generatePostRequest(path, headers, params, null);

        executeHttpASync(request, callback);
    }

    /**
     * Async post Json request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param jsonBody http paramValues
     * @param callback {@link com.y7single.commons.http.Call}
     */
    public void httpPost(String path, Map<String, String> headers, String jsonBody, com.y7single.commons.http.Call callback) {

        Request request = generatePostRequest(path, headers, null, jsonBody);

        executeHttpASync(request, callback);
    }


    /**
     * sync put application/x-www-form-urlencoded  request。
     *
     * @param path    http path
     * @param headers http headers
     * @param params  http paramValues
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    @Override
    public HttpResult httpPut(String path, Map<String, String> headers, Map<String, Object> params) throws IOException {

        Request request = generatePutRequest(path, headers, params, null);

        return executeHttpSync(request);
    }

    /**
     * sync put Json request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param jsonBody http paramValues
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    public HttpResult httpPut(String path, Map<String, String> headers, String jsonBody) throws IOException {

        Request request = generatePutRequest(path, headers, null, jsonBody);

        return executeHttpSync(request);

    }

    /**
     * Async put request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param params   http paramValues
     * @param callback {@link  com.y7single.commons.http.Call}
     */
    @Override
    public void httpPut(String path, Map<String, String> headers, Map<String, Object> params, com.y7single.commons.http.Call callback) throws IOException {

        Request request = generatePutRequest(path, headers, params, null);

        executeHttpASync(request, callback);

    }

    /**
     * Async put request。
     *
     * @param path     http path
     * @param headers  http headers
     * @param jsonBody http paramValues
     * @param callback {@link  com.y7single.commons.http.Call}
     */
    public void httpPut(String path, Map<String, String> headers, String jsonBody, String encoding, long readTimeoutMs, com.y7single.commons.http.Call callback) throws IOException {

        Request request = generatePutRequest(path, headers, null, jsonBody);

        executeHttpASync(request, callback);

    }

    /**
     * sync delete request
     *
     * @param path    http path
     * @param headers http headers
     * @param params  http paramValues
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    @Override
    public HttpResult httpDelete(String path, Map<String, String> headers, Map<String, Object> params) throws IOException {

        Request request = generateDeleteRequest(path, headers);

        return executeHttpSync(request);
    }


    /**
     * Async delete request
     *
     * @param path     http path
     * @param headers  http headers
     * @param params   http paramValues
     * @param callback {@link com.y7single.commons.http.Call}
     */
    public void httpDelete(String path, Map<String, String> headers, Map<String, Object> params, com.y7single.commons.http.Call callback) throws IOException {

        Request request = generateDeleteRequest(path, headers);

        executeHttpASync(request, callback);
    }

    /**
     * sync http request。
     *
     * @param request {@link Request}
     * @return {@link HttpResult}
     * @throws IOException If an input or output exception occurred
     */
    private HttpResult executeHttpSync(Request request) throws IOException {

        Call call = httpClient.newCall(request);

        Response response = call.execute();

        return HttpResult.builder().code(response.code()).headers(response.headers().toMultimap()).body(Objects.requireNonNull(response.body()).string()).build();

    }


    /**
     * Async http request。
     *
     * @param request  {@link Request}
     * @param callback {@link com.y7single.commons.http.Call }
     */
    private void executeHttpASync(Request request, com.y7single.commons.http.Call callback) {

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.onResponse(HttpResult.builder().code(response.code()).headers(response.headers().toMultimap()).body(Objects.requireNonNull(response.body()).string()).build());
            }
        });
    }

    /**
     * generate Get Request
     *
     * @param path    http path
     * @param headers http header
     * @return {@link Request}
     */
    private Request generateGetRequest(String path, Map<String, String> headers) {

        Request.Builder builder = new Request.Builder();

        generateRequestHeader(builder, headers);

        return builder.get().url(path).build();
    }

    /**
     * map to {@link RequestBody}。
     *
     * @param params request params
     * @return {@link RequestBody}
     */
    private RequestBody generateRequestBody(Map<String, Object> params) {

        FormBody.Builder builder = new FormBody.Builder();

        if (params != null) {
            Iterator<String> iterator = params.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                builder.add(key, String.valueOf(params.get(key)));
            }
            return builder.build();
        }

        throw new RuntimeException("params is null");
    }

    /**
     * map to {@link RequestBody}。
     *
     * @param jsonBody request params
     * @return {@link RequestBody}
     */
    private RequestBody generateRequestBody(String jsonBody) {

        return RequestBody.create(jsonBody, JSON);
    }


    /**
     * header add to builder。
     *
     * @param builder {@link Request.Builder}
     * @param headers Custom Header
     */
    private void generateRequestHeader(Request.Builder builder, Map<String, String> headers) {

        if (!Objects.isNull(headers)) {
            builder.headers(Headers.of(headers));
        }
    }

    /**
     * generate Post Request
     *
     * @param path    http path
     * @param headers http header
     * @param params  http params
     * @return {@link Request}
     */
    private Request generatePostRequest(String path, Map<String, String> headers, Map<String, Object> params, String jsonBody) {

        Request.Builder builder = new Request.Builder();

        generateRequestHeader(builder, headers);

        RequestBody body;
        if (Objects.nonNull(params)) {
            body = generateRequestBody(params);
        } else {
            body = generateRequestBody(jsonBody);
        }

        return builder.post(body).url(path).build();

    }

    /**
     * generate Put Request
     *
     * @param path    http path
     * @param headers http header
     * @param params  http params
     * @return {@link Request}
     */
    private Request generatePutRequest(String path, Map<String, String> headers, Map<String, Object> params, String jsonBody) {

        Request.Builder builder = new Request.Builder();

        generateRequestHeader(builder, headers);

        RequestBody body;
        if (Objects.nonNull(params)) {
            body = generateRequestBody(params);
        } else {
            body = generateRequestBody(jsonBody);
        }

        return builder.put(body).url(path).build();

    }

    /**
     * generate delete Request
     *
     * @param path    http path
     * @param headers http header
     * @return {@link Request}
     */
    private Request generateDeleteRequest(String path, Map<String, String> headers) {

        Request.Builder builder = new Request.Builder();

        generateRequestHeader(builder, headers);

        return builder.delete().url(path).build();

    }


}
