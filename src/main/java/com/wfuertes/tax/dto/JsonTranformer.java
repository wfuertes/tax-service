package com.wfuertes.tax.dto;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import java.util.Optional;

@Component
public class JsonTranformer implements ResponseTransformer {

    private final Gson gson;

    @Autowired
    public JsonTranformer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String render(Object object) throws Exception {
        if (object instanceof java.util.Optional || object instanceof com.google.common.base.Optional) {
            gson.toJson(((Optional) object).get());
        }
        return gson.toJson(object);
    }
}
