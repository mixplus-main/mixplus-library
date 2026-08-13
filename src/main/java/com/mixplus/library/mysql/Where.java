package com.mixplus.library.mysql;

import com.mixplus.library.unit.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class Where {
    private final StringBuilder sql = new StringBuilder();
    private final List<Object> parameters = new ArrayList<>();

    private Where(String column) {
        if (!StringUtil.isValidIdentifier(column)) {
            throw new IllegalArgumentException(
                    "Invalid column name: " + column
            );
        }

        sql.append(column);
    }

    public static Where of(String column) {
        return new Where(column);
    }

    public Where equal(Object value) {
        sql.append(" = ?");
        parameters.add(value);
        return this;
    }

    public Where notEqual(Object value) {
        sql.append(" != ?");
        parameters.add(value);
        return this;
    }

    public Where greaterThan(Object value) {
        sql.append(" > ?");
        parameters.add(value);
        return this;
    }

    public Where greaterThanOrEqual(Object value) {
        sql.append(" >= ?");
        parameters.add(value);
        return this;
    }

    public Where lessThan(Object value) {
        sql.append(" < ?");
        parameters.add(value);
        return this;
    }

    public Where lessThanOrEqual(Object value) {
        sql.append(" <= ?");
        parameters.add(value);
        return this;
    }

    public Where and(String column) {
        validateColumn(column);

        sql.append(" AND ").append(column);
        return this;
    }

    public Where or(String column) {
        validateColumn(column);

        sql.append(" OR ").append(column);
        return this;
    }

    public String getSql() {
        return this.sql.toString();
    }

    public List<Object> getParameters() {
        return this.parameters;
    }

    private void validateColumn(String column) {
        if (!StringUtil.isValidIdentifier(column)) {
            throw new IllegalArgumentException(
                    "Invalid column name: " + column
            );
        }
    }
}
