import java.sql.Connection;
import java.sql.DriverManager;


//this class is for accessing the database
public class Database {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlite:C:\\Users\\kec20\\IdeaProjects\\QuoteMonitor\\StockPrices.db";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }



}
