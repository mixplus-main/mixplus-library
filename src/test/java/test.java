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




        if (!mySQL.isTable("ttt")) {
            mySQL.createTable(
                    "ttt",
                    Column.of("id", DataType.INT).primaryKey(),
                    Column.of("name", DataType.VARCHAR, 30)
            );
        }



        int count = mySQL.update(
                "ttt",
                Map.of(
                        "id", 0,
                        "name", "5LLL"
                ),
                Where.of("id").equal(0)
        );

        int c2 = mySQL.delete(
                "ttt",
                Where.of("id").equal(5)
        );


    }
}
