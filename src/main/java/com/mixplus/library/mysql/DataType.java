package com.mixplus.library.mysql;

public enum DataType {
    INT("INT", false),
    BIGINT("BIGINT", false),
    VARCHAR("VARCHAR", true),
    TEXT("TEXT", false),
    DOUBLE("DOUBLE", false),
    FLOAT("FLOAT", false),
    BOOLEAN("BOOLEAN", false),
    CHAR("CHAR", true),
    DATE("DATE", false),
    DATETIME("DATETIME", false),
    TIMESTAMP("TIMESTAMP", false),
    BLOB("BLOB", false);

    private final String sql;
    private final boolean requiresSize;

    DataType(String sql, boolean requiresSize) {
        this.sql = sql;
        this.requiresSize = requiresSize;
    }

    public String toSQL() {
        return sql;
    }

    public boolean requiresSize() {
        return requiresSize;
    }
}
