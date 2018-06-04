package com.wfuertes.tax.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wfuertes.tax.dao.TaxDao;
import com.wfuertes.tax.dto.CalculationForm;
import com.wfuertes.tax.dto.CalculationView;
import com.wfuertes.tax.dto.TaxForm;
import com.wfuertes.tax.dto.TaxView;
import com.wfuertes.tax.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TaxService {

    private static final String TAX_VIEW_LIST = "TAX_VIEW_LIST";
    private static final Cache<String, List<TaxView>> LIST_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    private static final Cache<String, TaxView> TAX_VIEW_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    private final TaxDao taxDao;

    @Autowired
    public TaxService(TaxDao taxDao) {
        this.taxDao = taxDao;
    }

    @Transactional(readOnly = true)
    public List<TaxView> findAll() {
        try {
            return LIST_CACHE.get(TAX_VIEW_LIST, () -> taxDao.findAll()
                    .stream().map(tax -> TaxView.fromTax(tax).build())
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public Optional<TaxView> findOne(String id) {
        try {
            return Optional.of(TAX_VIEW_CACHE.get(id, () -> TaxView.fromTax(taxDao.findOne(id).get()).build()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public TaxView save(TaxForm taxForm) {
        return TaxView.fromTax(
                taxDao.save(Tax.fromForm(taxForm).withCreateAt(LocalDateTime.now()).build())
        ).withMessage("New tax created successfully").build();
    }

    @Transactional(readOnly = true)
    public CalculationView calculate(String id, CalculationForm calculationForm) {
        Optional<TaxView> taxView = this.findOne(id);
        if (taxView.isPresent()) {
            CalculationView calculationView = new CalculationView(201, "Tax calculated successfully");
            calculationView.setTaxName(id);
            calculationView.setTaxValue(taxView.get().calculate(calculationForm.getValue()));
            calculationView.setValue(taxView.get().getValue());
            return calculationView;
        }
        return new CalculationView(400, "It's not possible calculate tax for id: " + id);
    }
}
