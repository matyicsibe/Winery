package jpa;
/*-
 * #%L
 * Winery
 * %%
 * Copyright (C) 2019 Faculty of Informatics, University of Debrecen
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
//CHECKSTYLE:OFF

public abstract class GenericJpaDao<T> {

    protected Class<T> entityClass;
    protected EntityManager entityManager;

    public GenericJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public Optional<T> find(Object primaryKey) {
        return Optional.ofNullable(entityManager.find(entityClass, primaryKey));
    }

    @Transactional
    public List<T> findAll() {
        TypedQuery<T> typedQuery = entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass);
        return typedQuery.getResultList();
    }

    @Transactional
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }


//CHECKSTYLE:ON
}
