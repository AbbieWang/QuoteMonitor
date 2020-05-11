import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//this class gets the data from url and puts ticker and stock price into database
public class Data {

    private static Database db = new Database();

    //inserts 5 min historical data from JSON into the database
    public static void insertHistoricalData(String company) throws Exception {
        Connection connection = db.getConnection();
        JSONParser parser = new JSONParser();
        String sql;
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/5min/" + company);

        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        JSONArray body = (JSONArray) parser.parse(new InputStreamReader(in));
        sql = "INSERT INTO PALL(Time, Price) VALUES(?,?)";

        try (Connection conn = db.getConnection()) {
            //depends on how many points  we want to plot on graph
            for (int i = 0; i < 30; ++i) {
                JSONObject obj = (JSONObject)body.get(i);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, obj.get("date").toString());
                pstmt.setDouble(2, (double) obj.get("open"));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //inserts real time data from JSON into the database
    public static void insertRealTimeData(String company) throws Exception {
        Connection connection = db.getConnection();
        JSONParser parser = new JSONParser();
        String sql;
        URL url = new URL("https://financialmodelingprep.com/api/v3/stock/real-time-price/" + company);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONObject body = (JSONObject) parser.parse(reader);

        sql = "INSERT INTO RealTime(Ticker, Price) VALUES(?,?)";
        try (Connection conn = db.getConnection()) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, body.get("symbol").toString());
                pstmt.setDouble(2, (double) body.get("price"));
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //checks if data is already in the database
    public boolean inDatabase() {
        return false;
    }
}