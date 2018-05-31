package com.wfuertes.tax.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Singleton
public class TaxPersistenceUnitInfo implements PersistenceUnitInfo {

    public static final String TAX_PU = "TAX_PU";
    private static final String JPA_VERSION = "2.1";
    private final List<String> mappingFileNames = new ArrayList<>();

    private final DataSource dataSource;
    private final List<String> managedClassNames;
    private final Properties properties;

    @Inject
    public TaxPersistenceUnitInfo(DataSource dataSource,
                                  List<Class> entities,
                                  Properties properties) {

        this.dataSource = dataSource;
        this.managedClassNames = entities.stream().map(clazz -> clazz.getCanonicalName()).collect(Collectors.toList());
        this.properties = properties;
    }

    @Override
    public String getPersistenceUnitName() {
        return TAX_PU;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return dataSource;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return dataSource;
    }

    @Override
    public List<String> getMappingFileNames() {
        return mappingFileNames;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return Collections.emptyList();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return managedClassNames;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return JPA_VERSION;
    }

    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {
    }
}
