package com.mixplus.library.mysql;

import com.mixplus.library.util.StringUtil;

public class ForeignKey implements TableElement {
    private final String column;
    private final String referenceTable;
    private final String referenceColumn;

    private ForeignKey(
            String column,
            String referenceTable,
            String referenceColumn
    ) {
        if (!StringUtil.isValidIdentifier(column)) {
            throw new IllegalArgumentException(
                    "Invalid column name: " + column
            );
        }

        if (!StringUtil.isValidIdentifier(referenceTable)) {
            throw new IllegalArgumentException(
                    "Invalid table name: " + referenceTable
            );
        }

        if (!StringUtil.isValidIdentifier(referenceColumn)) {
            throw new IllegalArgumentException(
                    "Invalid column name: " + referenceColumn
            );
        }


        this.column = column;
        this.referenceTable = referenceTable;
        this.referenceColumn = referenceColumn;
    }

    public static ForeignKey of(
            String column,
            String referenceTable,
            String referenceColumn
    ) {
        return new ForeignKey(
                column,
                referenceTable,
                referenceColumn
        );
    }

    @Override
    public String toSQL() {
        return "FOREIGN KEY (" + column + ") REFERENCES "
                + referenceTable
                + "(" + referenceColumn + ")";
    }


}
