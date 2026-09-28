package com.mixplus.library.mysql;

import com.mixplus.library.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class Increment implements TableElement{
    private final StringBuilder sql;
    private final List<Object> parameters = new ArrayList<>();

    private Increment(String column) {
        if (!StringUtil.isValidIdentifier(column)) {
            throw new IllegalArgumentException(
                    "Invalid column name: " + column
            );
        }

        this.sql = new StringBuilder(column);
    }

    public Increment add(long value) {
        sql.insert(0, "(");
        sql.append(" + ?)");
        parameters.add(value);
        return this;
    }

    public Increment subtract(long value) {
        sql.insert(0, "(");
        sql.append(" - ?)");
        parameters.add(value);
        return this;
    }

    public Increment multiply(long value) {
        sql.insert(0, "(");
        sql.append(" * ?)");
        parameters.add(value);
        return this;
    }

    @Override
    public String toSQL() {
        return sql.toString();
    }

    @Override
    public List<Object> getParameters() {
        return this.parameters;
    }
}
