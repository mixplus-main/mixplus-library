package com.mixplus.test;



import com.mixplus.library.mysql.Column;
import com.mixplus.library.mysql.DataType;
import com.mixplus.library.mysql.MySQL;
import com.mixplus.library.network.Https;
import com.mixplus.library.network.Response;
import com.mixplus.library.util.StringUtil;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        MySQL mySQL = new MySQL();
        mySQL.setHost("localhost");
        mySQL.setPort(3306);
        mySQL.setUsername("root");
        mySQL.setPassword(System.getenv("MYSQL_PASSWORD"));


        if (!mySQL.isDatabase("test")) {
            mySQL.createDatabase("test");
        }

        mySQL.connect();

        if (!mySQL.isTable("test")) {
            mySQL.createTable(
                    "test",
                    Column.of("amount", DataType.BIGINT).notNull().defaultValue(0)
            );
        }


    }

}
