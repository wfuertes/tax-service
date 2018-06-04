package com.wfuertes.tax.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
@EnableTransactionManagement
public class Persistence {

    @Bean
    @Primary
    public DataSource dataSourceH2() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:taxes_db;USER=root;PASSWORD=root;autocommit=off;INIT=RUNSCRIPT FROM 'classpath:sql/create-h2.sql'");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public DataSource dataSourceMysql() throws URISyntaxException, IOException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://192.168.99.100:3306/tax_service_db?autoReconnect=true&useSSL=false");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSourceProxy(DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean
    public DataSourceConnectionProvider dataSourceConnectionProvider(
            TransactionAwareDataSourceProxy transactionAwareDataSourceProxy) {

        return new DataSourceConnectionProvider(transactionAwareDataSourceProxy);
    }

    @Bean
    public ExceptionTranslator  exceptionTranslator() {
        return new ExceptionTranslator();
    }

    @Bean
    public DefaultConfiguration defaultConfiguration(
            DataSourceConnectionProvider dataSourceConnectionProvider,
            ExceptionTranslator exceptionTranslator) {

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.setSQLDialect(SQLDialect.H2);
        defaultConfiguration.setConnectionProvider(dataSourceConnectionProvider);
        defaultConfiguration.setExecuteListenerProvider(new DefaultExecuteListenerProvider(exceptionTranslator));
        return defaultConfiguration;
    }

    @Bean
    public DefaultDSLContext dslContext(DefaultConfiguration defaultConfiguration) {
        return new DefaultDSLContext(defaultConfiguration);
    }
}
