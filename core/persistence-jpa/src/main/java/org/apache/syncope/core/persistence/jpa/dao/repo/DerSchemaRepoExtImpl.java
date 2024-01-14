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
package org.apache.syncope.core.persistence.jpa.dao.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import org.apache.syncope.core.persistence.api.dao.ExternalResourceDAO;
import org.apache.syncope.core.persistence.api.entity.AnyTypeClass;
import org.apache.syncope.core.persistence.api.entity.DerSchema;
import org.apache.syncope.core.persistence.jpa.entity.JPADerSchema;

public class DerSchemaRepoExtImpl implements DerSchemaRepoExt {

    protected final ExternalResourceDAO resourceDAO;

    protected final EntityManager entityManager;

    public DerSchemaRepoExtImpl(final ExternalResourceDAO resourceDAO, final EntityManager entityManager) {
        this.resourceDAO = resourceDAO;
        this.entityManager = entityManager;
    }

    @Override
    public List<? extends DerSchema> findByAnyTypeClasses(final Collection<AnyTypeClass> anyTypeClasses) {
        StringBuilder queryString = new StringBuilder("SELECT e FROM ").
                append(JPADerSchema.class.getSimpleName()).
                append(" e WHERE ");
        anyTypeClasses.forEach(anyTypeClass -> queryString.
                append("e.anyTypeClass.id='").
                append(anyTypeClass.getKey()).append("' OR "));

        TypedQuery<DerSchema> query = entityManager.createQuery(
                queryString.substring(0, queryString.length() - 4), DerSchema.class);

        return query.getResultList();
    }

    @Override
    public DerSchema save(final DerSchema schema) {
        ((JPADerSchema) schema).map2json();
        return entityManager.merge(schema);
    }

    @Override
    public void deleteById(final String key) {
        DerSchema schema = entityManager.find(JPADerSchema.class, key);
        if (schema == null) {
            return;
        }

        resourceDAO.deleteMapping(key);

        if (schema.getAnyTypeClass() != null) {
            schema.getAnyTypeClass().getDerSchemas().remove(schema);
        }

        entityManager.remove(schema);
    }
}