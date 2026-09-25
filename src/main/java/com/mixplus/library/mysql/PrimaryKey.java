package com.mixplus.library.mysql;

import java.util.List;

public final class PrimaryKey implements TableElement {
    private final List<String> columns;

    private PrimaryKey(List<String> columns) {
        this.columns = columns;

    }

    public static PrimaryKey of(List<String> columns) {
        return new PrimaryKey(columns);
    }

    public static PrimaryKey of(String... columns) {
        return new PrimaryKey(List.of(columns));
    }

    @Override
    public String toSQL() {
        return "PRIMARY KEY (" + String.join(", ", columns) + ")";
    }

}
