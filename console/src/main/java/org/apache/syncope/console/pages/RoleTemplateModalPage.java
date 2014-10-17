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
package org.apache.syncope.console.pages;

import org.apache.syncope.common.to.RoleTO;
import org.apache.syncope.common.to.SyncTaskTO;
import org.apache.syncope.console.commons.Mode;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;

public class RoleTemplateModalPage extends RoleModalPage {

    private static final long serialVersionUID = -3849135555203409845L;

    private final SyncTaskTO syncTaskTO;

    public RoleTemplateModalPage(final PageReference callerPageRef, final ModalWindow window,
            final SyncTaskTO syncTaskTO) {

        super(callerPageRef, window, syncTaskTO.getRoleTemplate() == null
                ? new RoleTO()
                : syncTaskTO.getRoleTemplate(), Mode.TEMPLATE);

        this.syncTaskTO = syncTaskTO;
    }

    @Override
    protected void submitAction(final AjaxRequestTarget target, final Form form) {
        syncTaskTO.setRoleTemplate((RoleTO) form.getModelObject());
        taskRestClient.updateSyncTask(syncTaskTO);
    }
}
