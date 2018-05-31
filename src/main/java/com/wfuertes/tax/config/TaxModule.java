package com.wfuertes.tax.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.*;
import com.wfuertes.tax.dao.TaxDao;
import com.wfuertes.tax.dao.TaxDaoJpa;
import com.wfuertes.tax.model.Tax;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TaxModule extends AbstractModule {

    private final static Injector applicationInjector = Guice.createInjector(new TaxModule());

    public static final <T> T getInstance(Class<T> clazz) {
        return applicationInjector.getInstance(clazz);
    }

    @Override
    protected void configure() {
        bind(TaxDao.class).to(TaxDaoJpa.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public DataSource dataSource() throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:taxes_db;USER=root;PASSWORD=root;INIT=RUNSCRIPT FROM 'classpath:/create.sql'");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Provides
    @Singleton
    public PersistenceUnitInfo persistenceUnitInfo(DataSource dataSource) {

        List<Class> entities = Arrays.asList(Tax.class);

        return new TaxPersistenceUnitInfo(dataSource, entities, new Properties());
    }

    @Provides
    @Singleton
    public EntityManagerFactory entityManagerFactory(PersistenceUnitInfo persistenceUnitInfo) {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.generate_statistics", Boolean.TRUE.toString());
        properties.put("show_sql", true);
        properties.put("format_sql", true);
        properties.put("use_sql_comments", true);

        return new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo, properties);
    }

    @Provides
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Provides
    @Singleton
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

}
