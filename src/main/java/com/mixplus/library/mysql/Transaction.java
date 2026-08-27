package com.mixplus.library.mysql;

import java.sql.SQLException;

public interface Transaction {
    boolean execute() throws SQLException;
}
