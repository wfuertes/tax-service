package com.wfuertes.tax.dao;

import com.wfuertes.tax.model.Tax;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TaxDaoJpa implements TaxDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tax findOne(String id) {
        return entityManager.find(Tax.class, id);
    }

    @Override
    public List<Tax> findAll() {
        return entityManager.createQuery("FROM Tax ORDER BY value", Tax.class).getResultList();
    }

    @Override
    @Transactional
    public Tax save(Tax tax) {
        Tax saved = entityManager.merge(tax);
        return saved;
    }
}
