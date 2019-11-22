/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.common.rest.api.service;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.apache.syncope.common.lib.to.ErrorTO;
import org.apache.syncope.common.rest.api.RESTHeaders;

@ApiResponses(
        @ApiResponse(code = 400,
                message = "An error occurred; HTTP status code can vary depending on the actual error: "
                + "400, 403, 404, 409, 412",
                response = ErrorTO.class,
                responseHeaders = {
                    @ResponseHeader(name = RESTHeaders.ERROR_CODE, response = String.class,
                            description = "Error code"),
                    @ResponseHeader(name = RESTHeaders.ERROR_INFO, response = String.class,
                            description = "Error message") })
)
public interface JAXRSService {

    String PARAM_FIQL = "fiql";

    String PARAM_PAGE = "page";

    String PARAM_SIZE = "size";

    String PARAM_ORDERBY = "orderby";

    String PARAM_RESOURCE = "resource";

    String PARAM_NOTIFICATION = "notification";

    String PARAM_ANYTYPE_KIND = "anyTypeKind";

    String PARAM_ENTITY_KEY = "entityKey";

    String PARAM_DETAILS = "details";

    String PARAM_CONNID_PAGED_RESULTS_COOKIE = "connIdPagedResultsCookie";

    String PARAM_MAX = "max";

}