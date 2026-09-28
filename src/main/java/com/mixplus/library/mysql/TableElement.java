package com.mixplus.library.mysql;

import java.util.List;

public interface TableElement {

    String toSQL();

    default List<Object> getParameters() {
        return List.of();
    }
}
