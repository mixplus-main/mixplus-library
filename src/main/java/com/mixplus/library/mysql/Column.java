package com.mixplus.library.mysql;

import com.mixplus.library.util.StringUtil;


public class Column implements TableElement {
    private final String name;
    private final String type;

    private boolean primaryKey;
    private boolean notNull;
    private boolean autoIncrement;
    private boolean unique;
    private Object defaultValue;

    private Column(String name, String type) {
        if (!StringUtil.isValidIdentifier(name)) {
            throw new IllegalArgumentException("Invalid column name: " + name);
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Column type cannot be null or empty");
        }

        this.name = name;
        this.type = type;
    }

    public static Column of(String name, DataType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        if (type.requiresSize()) {
            throw new IllegalArgumentException(
                    type + " requires a size"
            );
        }
        return new Column(name, type.toSQL());
    }

    public static Column of(String name, DataType type, int size) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        if (!type.requiresSize()) {
            throw new IllegalArgumentException(
                    type + " requires a size"
            );
        }

        if (size <= 0) {
            throw new IllegalArgumentException(
                    "Size must be greater than 0"
            );
        }

        return new Column(
                name,
                type.toSQL() + "(" + size + ")"
        );
    }

    public Column primaryKey() {
        this.primaryKey = true;
        return this;
    }

    public Column ai() {
        if (!type.equals("INT") && !type.equals("BIGINT")) {
            throw new IllegalStateException(
                    "AUTO_INCREMENT requires an integer type"
            );
        }

        this.autoIncrement = true;
        return this;
    }

    public Column unique() {
        this.unique = true;
        return this;
    }

    public Column notNull() {
        this.notNull = true;
        return this;
    }

    public Column defaultValue(Object value) {
        this.defaultValue = value;
        return this;
    }

    @Override
    public String toSQL() {
        StringBuilder sql = new StringBuilder();

        sql.append(name)
                .append(" ")
                .append(type);

        if (primaryKey) {
            sql.append(" PRIMARY KEY");
        }

        if (notNull) {
            sql.append(" NOT NULL");
        }

        if (autoIncrement) {
            sql.append(" AUTO_INCREMENT");
        }

        if (unique) {
            sql.append(" UNIQUE");
        }

        if (defaultValue != null) {
            sql.append(" DEFAULT ");

            if (defaultValue instanceof String) {
                sql.append("'")
                        .append(defaultValue.toString().replace("'", "''"))
                        .append("'");
            } else if (defaultValue instanceof Boolean bool) {
                sql.append(bool ? "TRUE" : "FALSE");
            } else {
                sql.append(defaultValue);
            }
        }

        return sql.toString();
    }


}
