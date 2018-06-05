package com.wfuertes.tax;

import com.wfuertes.tax.config.TaxConfig;
import com.wfuertes.tax.rest.RestService;
import com.wfuertes.tax.rest.TaxRest;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TaxConfig.class);

        // Initializing all RestService
        applicationContext.getBeansOfType(RestService.class).forEach((key, restService) -> restService.initialize());
    }
}
