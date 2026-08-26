package com.mixplus.library.mysql;

import java.sql.SQLException;

public interface Transaction {
    void execute() throws SQLException;
}
