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

/**
 * @author: y7
 * @date: 2019/11/1 15:09
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: http 事件回调
 */
public interface Call {

    /**
     * 正常数据返回
     *
     * @param httpResult {@link HttpResult}
     */
    void onResponse(HttpResult httpResult);

    /**
     * 异常返回
     *
     * @param e {@link IOException}
     */
    void onFailure(IOException e);
}
