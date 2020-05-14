import java.sql.*;


//this class is for accessing the database
public class Database {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlite:C:Portfolio.db";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    //updates portfolio in the database
    public void updateSharesDatabase(String ticker, int shares) throws Exception {
        Database db = new Database();
        //uppdate # of shares in database for a company
        String sql = "UPDATE Stocks SET Shares = ? WHERE Ticker = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shares);
            pstmt.setString(2, ticker);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateBalanceDatabase(double balance) throws Exception {
        Database db = new Database();
        //updates account balance
        String sql = "UPDATE Money SET Balance = ? WHERE AccountId = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //gets the balance from the database
    public double getBalance() throws Exception {
        Database db = new Database();
        String sql = "SELECT Balance FROM Money WHERE AccountId = 1";
        double balance = 0;
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            balance = rs.getDouble("Balance");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    //gets the shares from the database on array
    //will need to change if portfolio is able to change
    public int getShares(String ticker) throws Exception {
        Database db = new Database();
        int shares = 0;
        String sql = "SELECT Shares FROM Stocks WHERE Ticker = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, ticker);
            ResultSet rs = pstmt.executeQuery();
            shares = rs.getInt("Shares");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return shares;
    }



}
