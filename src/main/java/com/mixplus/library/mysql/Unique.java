package com.mixplus.library.mysql;

import java.util.List;

public final class Unique implements TableElement {
    private final List<String> columns;

    private Unique(List<String> columns) {
        this.columns = columns;
    }

    public static Unique of(List<String> columns) {
        return new Unique(columns);
    }

    public static Unique of(String... columns) {
        return new Unique(List.of(columns));
    }

    @Override
    public String toSQL() {
        return "UNIQUE (" + String.join(", ", columns) + ")";
    }

}
