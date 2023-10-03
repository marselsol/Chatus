package com.chatus.messageservicemodule.utils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthMetric implements MeterBinder {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthMetric(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isDatabaseUp() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        meterRegistry.gauge("database.health", Tags.empty(), this, value -> isDatabaseUp() ? 1.0 : 0.0);
    }
}
