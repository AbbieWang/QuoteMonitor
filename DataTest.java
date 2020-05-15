import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataTest {
    Data data = new Data();
    Database db = new Database();

    //tests for accessing data from url
    @Test
    void gettingUrl() throws IOException {
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/5min/GOOG");
        System.out.print("url: " + url.openConnection());
    }

    @Test
    void gettingJsonArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/GOOG");
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        JSONArray body = (JSONArray) parser.parse(new InputStreamReader(in));
        System.out.print("json array: " + body);
    }

    @Test
    void gettingJsonObject() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/1hour/GOOG");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject)body.get(0);
        System.out.print("json object: " + obj);
    }

    @Test
    void gettingJsonObjectKeyValue() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/GOOG");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject)body.get(0);
        System.out.print("price: " + obj.get("price"));
    }

    @Test
    void gettingHistoricalDataArray() throws Exception {
        System.out.println("array index 0: " + data.getHistoricalData("GOOG")[0]);
        System.out.println("array index 1: " + data.getHistoricalData("GOOG")[1]);
        System.out.println("array index 2: " + data.getHistoricalData("GOOG")[2]);
    }

    @Test
    void gettingTimeInterval() throws IOException, ParseException {
        System.out.print("day and time: " + data.getTimeInterval("GOOG")[0]);
        System.out.print("day and time: " + data.getTimeInterval("GOOG")[1]);
        System.out.print("day and time: " + data.getTimeInterval("GOOG")[2]);
    }


    @Test
    void gettingChangesPercentage() throws Exception {
        System.out.print("percent change: " + data.getChangesPercentage("GOOG"));
        System.out.print("percent change: " + data.getChangesPercentage("AAPL"));
        System.out.print("percent change: " + data.getChangesPercentage("MSFT"));
    }

    //tests for methods in  controller class
    @Test
    void calculatingPortfolio() {
        int balance = 10;
        int s1 = 1; int s2 = 2; int s3 = 2; int s4 = 0; int s5 = 0;
        int p1 = 1; int p2 = 1; int p3 = 1; int p4 = 1; int p5 = 1;
        //calculate the portfolio value based on # of shares * price per share, plus balance
        double total = (s1 * p1)
                + (s2 * p2)
                + (s3 * p3)
                + (s4 * p4)
                + (s5 * p5);

        total = total + balance;
        assertEquals(total,15);
    }

    @Test
    void purchasing() {
        double money = 100;
        double p1 = 1.11111;
        int a1 = 7;
        int s1 = 2;
        money = money - (a1 * p1);
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        s1 = s1 + a1;
        assertEquals(money,92.22);
        assertEquals(s1, 9);
    }

    @Test
    void selling() {
        double money = 100;
        double p1 = 1.11111;
        int a1 = 6;
        int s1 = 9;
        money = money + (a1 * p1);
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        s1 = s1 - a1;
        assertEquals(money,106.67);
        assertEquals(s1, 3);
    }

    //tests for accessing and updating database
    @Test
    void connectTest() throws Exception {
        String url = "jdbc:sqlite:DatabaseTest.db";
        Connection conn = DriverManager.getConnection(url);
        assertNotNull(conn);
    }

    Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:DatabaseTest.db";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    @Test
    void isSelectingShare() {
        int shares = 0;
        String sql = "SELECT Shares FROM Stocks WHERE Ticker = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, "AAPL");
            ResultSet rs = pstmt.executeQuery();
            shares = rs.getInt("Shares");
            System.out.print(shares);
            assertEquals(shares, 8);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void isUpdatingShares() throws Exception {
        String sql = "UPDATE Stocks SET Shares = ? WHERE Ticker = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "AAPL");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int shares = 0;
        String sql2 = "SELECT Shares FROM Stocks WHERE Ticker = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql2)){
            pstmt.setString(1, "AAPL");
            ResultSet rs = pstmt.executeQuery();
            shares = rs.getInt("Shares");
            System.out.print(shares);
            assertEquals(shares, 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //update back to 8 AAPL shares so all tests will pass when entire class is run
        String sql3 = "UPDATE Stocks SET Shares = ? WHERE Ticker = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql3)) {
            pstmt.setInt(1, 8);
            pstmt.setString(2, "AAPL");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void gettingBalance() throws Exception {
        String sql = "SELECT Balance FROM Money WHERE AccountId = 1";
        double balance = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            balance = rs.getDouble("Balance");
            System.out.print(balance);
            assertEquals(balance, 10000);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void isUpdatingBalance() throws Exception {
        //updating
        String sql = "UPDATE Money SET Balance = ? WHERE AccountId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, 10);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //querying
        String sql2 = "SELECT Balance FROM Money WHERE AccountId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql2)){
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            int balance = rs.getInt("Balance");
            System.out.print(balance);
            assertEquals(balance, 10);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //update balance back to 10000 so all tests will pass when entire class is run
        String sql3 = "UPDATE Money SET Balance = ? WHERE AccountId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql3)) {
            pstmt.setInt(1, 10000);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
