import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Data {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlite:C:\\Users\\kec20\\IdeaProjects\\QuoteMonitor\\StockPrices.db";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    //inserts data from JSON into the database
    public static void insertData() throws Exception {
        Connection connection = getConnection();
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/stock/real-time-price");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String sql = "INSERT INTO RealTime(Ticker, Price) VALUES(?,?)";
        JSONObject obj = (JSONObject)parser.parse(reader);
        JSONArray stockArray = (JSONArray)obj.get("stockList");
        //inserts first 20 into database for now... - https://howtodoinjava.com/library/json-simple-read-write-json-examples/
            try (Connection conn = getConnection()) {
                for(int i = 0; i < 20; ++i) {
                    JSONObject object = (JSONObject)stockArray.get(i);
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, object.get("symbol").toString());
                    pstmt.setDouble(2, (Double)object.get("price"));
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }

    public static void main(String[] args) throws Exception {
        getConnection();
        insertData();
    }
}