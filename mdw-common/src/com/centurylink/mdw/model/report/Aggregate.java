/*
 * Copyright (C) 2017 CenturyLink, Inc.
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
package com.centurylink.mdw.model.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * For aggregation service responses, an InstanceCount Jsonable returns
 * identity and count information for each type in the breakdown.
 */
@ApiModel(value="Aggregate", description="Identity and value info for a breakdown type")
public interface Aggregate {

    long getId();
    String getName();

    @ApiModelProperty(value="Value for an aggregate")
    long getValue();

    @ApiModelProperty(value="Count for a particular aggregate")
    long getCount();
}
