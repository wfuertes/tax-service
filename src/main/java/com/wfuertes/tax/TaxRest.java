package com.wfuertes.tax;

import com.google.gson.Gson;
import com.wfuertes.tax.config.TaxConfig;
import com.wfuertes.tax.dto.*;
import com.wfuertes.tax.service.TaxService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class TaxRest {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaxConfig.class);

        final Gson gson = context.getBean(Gson.class);
        final TaxService taxService = context.getBean(TaxService.class);
        final ResponseTransformer transformToJson = context.getBean(ResponseTransformer.class);

        /**
         * Show the available taxes in the database
         */
        get("/taxes", (req, res) -> {
            res.status(200);
            res.type("application/json");
            return taxService.findAll();
        }, transformToJson);

        /**
         * Applies the specified tax to the given value at the body
         */
        post("/taxes/:id", (req, res) -> {
            res.status(201);
            res.type("application/json");
            String taxId = req.params("id");
            return taxService.calculate(taxId, gson.fromJson(req.body(), CalculationForm.class));
        }, transformToJson);

        /**
         * Create a new type of tax
         */
        post("/taxes", (req, res) -> {
            TaxForm taxForm = gson.fromJson(req.body(), TaxForm.class);
            res.status(201);
            res.type("application/json");
            return taxService.save(taxForm);
        }, transformToJson);

        internalServerError((req, res) -> {
            return "Enexpected error :(";
        });

        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.body("Internal server error: " + e.getMessage());
        });

        exception(DuplicateKeyException.class, (e, req, res) -> {
            TaxForm taxForm = gson.fromJson(req.body(), TaxForm.class);
            res.status(400);
            res.type("application/json");
            res.body(gson.toJson(new BasicResponse(400, "Already exists a tax with ID: " + taxForm.getId())));
        });
    }
}
