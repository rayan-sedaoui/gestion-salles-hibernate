package com.example.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {
    
    protected final EntityManagerFactory emf;
    protected final Class<T> entityClass;
    
    @SuppressWarnings("unchecked")
    public AbstractCrudService(EntityManagerFactory emf) {
        this.emf = emf;
        // Cette ligne un peu complexe permet de deviner automatiquement le type de l'entité (Utilisateur ou Salle)
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
    
    @Override
    public T save(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Optional<T> findById(ID id) {
        EntityManager em = emf.createEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void update(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // On s'assure que l'objet est bien géré par l'EntityManager avant de le supprimer
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }
}