package com.wfuertes.tax.dao;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wfuertes.tax.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Primary
@Repository
public class TaxDaoCacheable implements TaxDao {

    private static final String TAX_LIST = "TAX_LIST";

    private static final Cache<String, List<Tax>> LIST_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1)
            .build();

    private static final Cache<String, Tax> TAX_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    private final TaxDao taxDao;

    @Autowired
    public TaxDaoCacheable(@Qualifier("taxDaoJooq") TaxDao taxDao) {
        this.taxDao = taxDao;
    }

    @Override
    public Optional<Tax> findOne(String id) {
        try {
            return Optional.of(TAX_CACHE.get(id, () -> taxDao.findOne(id).get()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tax> findAll() {
        try {
            return LIST_CACHE.get(TAX_LIST, () -> taxDao.findAll());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Tax save(Tax tax) {
        return taxDao.save(tax);
    }

    @Override
    public Tax update(Tax tax) {
        return taxDao.update(tax);
    }
}
