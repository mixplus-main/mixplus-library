package com.mixplus.library.mysql;

public class Column {
    private final String name;
    private final String type;

    private boolean primaryKey;
    private boolean notNull;

    private Column(String name, String type) {
        if (name == null || !name.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid column name: " + name);
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Column type cannot be null or empty");
        }

        this.name = name;
        this.type = type;
    }

    public static Column of(String name, String type) {
        return new Column(name, type);
    }

    public Column primaryKey() {
        this.primaryKey = true;
        return this;
    }

    public Column notNull() {
        this.notNull = true;
        return this;
    }

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

        return sql.toString();
    }


}
