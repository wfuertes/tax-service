package com.wfuertes.tax.dao;

import com.wfuertes.tax.model.Tax;

import java.util.List;

public interface TaxDao {

    Tax findOne(String id);

    List<Tax> findAll();

    Tax save(Tax tax);
}
