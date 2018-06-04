package com.wfuertes.tax.dao;

import com.wfuertes.tax.model.Tax;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class TaxDaoJooq implements TaxDao {

    private final DSLContext dsl;

    @Autowired
    public TaxDaoJooq(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tax> findOne(String id) {
        Record3<Object, Object, Object> taxRecord = dsl
                .select(field("id"), field("value"), field("created_at"))
                .from(table("taxes"))
                .where(field("id").eq(id))
                .fetchOne();

        if (taxRecord != null) {
            return taxRecord.map(record -> parseTax(record));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tax> findAll() {
        List<Tax> taxList = dsl
                .select(field("id"), field("value"), field("created_at"))
                .from(table("taxes"))
                .fetch()
                .map(record -> parseTax(record).get());
        return taxList;
    }

    @Override
    @Transactional
    public Tax update(Tax tax) {
        dsl.update(table("taxes"))
                .set(field("value"), tax.getValue())
                .where(field("id").eq(tax.getId()))
                .execute();
        return tax;
    }

    @Override
    @Transactional
    public Tax save(Tax tax) {
        tax.setCreatedAt(LocalDateTime.now());
        dsl.insertInto(table("taxes"))
                .columns(field("id"), field("value"), field("created_at"))
                .values(tax.getId(), tax.getValue(), tax.getCreatedAt())
                .execute();
        return tax;
    }

    private Optional<Tax> parseTax(Record record) {
        if (record == null) {
            return Optional.empty();
        }
        Tax tax = new Tax();
        tax.setId(record.getValue("id", String.class));
        tax.setValue(record.getValue("value", BigDecimal.class));
        tax.setCreatedAt(record.getValue("created_at", LocalDateTime.class));
        return Optional.of(tax);
    }
}
