package com.wfuertes.tax;

import com.google.gson.Gson;
import com.wfuertes.tax.config.TaxModule;
import com.wfuertes.tax.dao.TaxDao;
import com.wfuertes.tax.dto.CalculationForm;
import com.wfuertes.tax.dto.CalculationView;
import com.wfuertes.tax.dto.TaxForm;
import com.wfuertes.tax.dto.TaxView;
import com.wfuertes.tax.model.Tax;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class TaxRest {

    public static void main(String[] args) {

        final Gson gson = TaxModule.getInstance(Gson.class);
        final TaxDao taxDao = TaxModule.getInstance(TaxDao.class);

        /**
         * Show the available taxes in the database
         */
        get("/taxes", (req, res) -> {
            List<TaxView> taxViews = taxDao.findAll().stream().map(entity -> {
                TaxView taxView = new TaxView();
                taxView.setId(entity.getId());
                taxView.setCreatedAt(entity.getCreatedAt());
                taxView.setValue(entity.getValue());
                return taxView;
            }).collect(Collectors.toList());

            res.status(200);
            res.type("application/json");

            return gson.toJson(taxViews);
        });

        /**
         * Applies the specified tax to the given value at the body
         */
        post("/taxes/:id", (req, res) -> {
            String taxType = req.params("id");
            CalculationForm calculationForm = gson.fromJson(req.body(), CalculationForm.class);

            Tax tax = taxDao.findOne(taxType);

            CalculationView calculationView = new CalculationView();
            calculationView.setTaxName(taxType);
            calculationView.setTaxValue(tax.calculate(calculationForm.getValue()));
            calculationView.setValue(tax.getValue());
            calculationView.setMessage("Tax calculated successfully");

            res.status(201);
            res.type("application/json");

            return gson.toJson(calculationView);
        });

        /**
         * Create a new type of tax
         */
        post("/taxes", (req, res) -> {
            TaxForm taxForm = gson.fromJson(req.body(), TaxForm.class);
            Tax newTax = new Tax();
            newTax.setId(taxForm.getId());
            newTax.setValue(taxForm.getValue());
            newTax.setCreatedAt(LocalDateTime.now());

            Tax createdTax = taxDao.save(newTax);
            TaxView taxView = new TaxView();
            taxView.setId(createdTax.getId());
            taxView.setValue(createdTax.getValue());
            taxView.setCreatedAt(createdTax.getCreatedAt());
            taxView.setMessage("New tax created successfully");

            res.status(201);
            res.type("application/json");

            return gson.toJson(taxView);
        });
    }
}
