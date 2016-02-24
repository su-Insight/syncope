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
package org.apache.syncope.common.lib.to;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.syncope.common.lib.types.TaskType;

@XmlRootElement(name = "taskExec")
@XmlType
public class TaskExecTO extends AbstractExecTO {

    private static final long serialVersionUID = -5401795154606268973L;

    private long task;

    private TaskType type;

    public long getTask() {
        return task;
    }

    public void setTask(final long task) {
        this.task = task;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(final TaskType type) {
        this.type = type;
    }

}
