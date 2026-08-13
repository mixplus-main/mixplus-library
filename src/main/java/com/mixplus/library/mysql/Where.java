package com.mixplus.library.mysql;

import com.mixplus.library.unit.StringUtil;

import java.util.List;

public class Where {
    private final String sql;
    private final List<Object> parameters;

    private Where(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public static Where of(String column, String operator, Object value) {
        if (!StringUtil.isValidIdentifier(column)) {
            throw new IllegalArgumentException(
                    "Invalid column name: " + column
            );
        }

        if (operator == null || operator.isBlank()) {
            throw new IllegalArgumentException(
                    "Operator cannot be null or empty"
            );
        }

        return new Where(
                column + " " + operator + " ?",
                List.of(value)
        );
    }

    public String getSql() {
        return this.sql;
    }

    public List<Object> getParameters() {
        return this.parameters;
    }
}
