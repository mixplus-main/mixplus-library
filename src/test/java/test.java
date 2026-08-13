import com.mixplus.library.mysql.Column;
import com.mixplus.library.mysql.DataType;
import com.mixplus.library.mysql.MySQL;
import com.mixplus.library.mysql.Where;


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



        try {
            mySQL.executeUpdate("""
                drop table ttt;
                """);
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        mySQL.createTable(
                "ttt",
                Column.of("id", DataType.INT).primaryKey(),
                Column.of("name", DataType.VARCHAR, 30)
        );

        mySQL.insert(
                "ttt",
                Map.of("id", 5,
                        "name", "teahi"
                )
                );

        int count = mySQL.update(
                "ttt",
                Map.of(
                        "id", 0,
                        "name", ""
                ),
                Where.of("id", "=", 5)
        );

        int c2 = mySQL.delete(
                "ttt",
                Where.of("id", "=", 5)
        );


    }
}
