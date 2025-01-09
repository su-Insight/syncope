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
package org.apache.syncope.core.persistence.neo4j.dao.repo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.syncope.core.persistence.api.entity.Any;
import org.apache.syncope.core.persistence.api.entity.AnyType;
import org.apache.syncope.core.persistence.api.entity.ExternalResource;
import org.apache.syncope.core.persistence.api.entity.Relationship;
import org.apache.syncope.core.persistence.api.entity.anyobject.AMembership;
import org.apache.syncope.core.persistence.api.entity.anyobject.AnyObject;
import org.apache.syncope.core.persistence.api.entity.group.Group;

public interface AnyObjectRepoExt extends AnyRepoExt<AnyObject> {

    Optional<? extends AnyObject> findByName(String type, String name);

    List<AnyObject> findByName(String name);

    void securityChecks(Set<String> authRealms, String key, String realm, Collection<String> groups);

    Map<AnyType, Long> countByType();

    Map<String, Long> countByRealm(AnyType anyType);

    AMembership findMembership(String key);

    List<Group> findDynGroups(String key);

    List<Relationship<Any<?>, AnyObject>> findAllRelationships(AnyObject anyObject);

    Collection<Group> findAllGroups(AnyObject anyObject);

    Collection<String> findAllGroupKeys(AnyObject anyObject);

    Collection<ExternalResource> findAllResources(AnyObject anyObject);

    Pair<Set<String>, Set<String>> saveAndGetDynGroupMembs(AnyObject anyObject);
}
