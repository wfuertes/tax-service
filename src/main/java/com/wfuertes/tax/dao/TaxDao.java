package com.wfuertes.tax.dao;

import com.wfuertes.tax.model.Tax;

import java.util.List;
import java.util.Optional;

public interface TaxDao {

    Optional<Tax> findOne(String id);

    List<Tax> findAll();

    Tax save(Tax tax);

    Tax update(Tax tax);
}
