package com.wfuertes.tax.dao;

import com.google.inject.Inject;
import com.wfuertes.tax.model.Tax;

import javax.persistence.EntityManager;
import java.util.List;

public class TaxDaoJpa implements TaxDao {

    private final EntityManager entityManager;

    @Inject
    public TaxDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tax findOne(String id) {
        return entityManager.find(Tax.class, id);
    }

    @Override
    public List<Tax> findAll() {
        return entityManager.createQuery("FROM Tax ORDER BY value", Tax.class).getResultList();
    }

    @Override
    public Tax save(Tax tax) {
        entityManager.getTransaction().begin();
        Tax saved = entityManager.merge(tax);
        entityManager.getTransaction().commit();
        return saved;
    }
}
