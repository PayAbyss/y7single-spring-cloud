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

package com.y7single.test.controller;

import com.y7single.test.model.dto.OrgDTO;
import com.y7single.test.model.vo.OrgVO;
import com.y7single.test.service.OrgServiceImpl;
import com.y7single.web.controller.BaseTreeController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: y7
 * @date: 2019/12/24 18:20
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Api(tags = "机构接口")
@RestController
@RequestMapping("org")
public class OrgController extends BaseTreeController<OrgServiceImpl, OrgVO, OrgDTO, String> {
}
