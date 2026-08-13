import com.mixplus.library.mysql.MySQL;
import com.mixplus.library.network.Ping;
import com.mixplus.library.system.CPU;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class test {
    static void main(String[] args) {
        MySQL mySQL = new MySQL();
        mySQL.setHost("localhost");
        mySQL.setPort(3306);
        mySQL.setUsername("root");
        mySQL.setDatabase("test_db");
        mySQL.setPassword(System.getenv("MYSQL_PASSWORD"));

        mySQL.connect();
        System.out.println("Successfully connected to MySQL!");






    }
}
