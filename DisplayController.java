import com.sun.jdi.event.ClassPrepareEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class DisplayController implements Initializable {

    @FXML
    private TitledPane stock1,stock2,stock3,stock4,stock5, stock6, stock7, stock8, stock9, stock10;
    @FXML
    private TextField p1,p2,p3,p4,p5, p6, p7, p8, p9, p10;//price fields
    @FXML
    private TextField s1,s2,s3,s4,s5, s6, s7, s8, s9, s10;//shares fields
    @FXML
    private TextField a1,a2,a3,a4,a5, a6, a7, a8, a9, a10;//amount fields

    @FXML
    private TextField portfolioVal, balance;

    @FXML
    Text message;

    @FXML
    BorderPane statusMessage;

    @FXML
    private Label dateText;
    @FXML
    private Label timeText;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> lineChart;
    XYChart.Series series;
    Data data;
    double[] y_axis;
    String[] x_axis;

    String currentGraph;

    Database db = new Database();

    public DisplayController() throws Exception {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date();
        time();
        data = new Data();
        series = new XYChart.Series();
        lineChart.setTitle("Hourly Stock Price Changes");
        y_axis = new double[30];
        x_axis = new String[30];
        try {
            balance.setText(convertToString(db.getBalance()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            s1.setText(""+db.getShares("GOOGL"));
            s2.setText(""+db.getShares("AAPL"));
            s3.setText(""+db.getShares("AMZN"));
            s4.setText(""+db.getShares("MSFT"));
            s5.setText(""+db.getShares("TSLA"));
            s6.setText(""+db.getShares("FB"));
            s7.setText(""+db.getShares("JPM"));
            s8.setText(""+db.getShares("V"));
            s9.setText(""+db.getShares("NFLX"));
            s10.setText(""+db.getShares("ZM"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentGraph = "GOOGL";

        try {
              updateApp();

        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTimeline();
    }


    //gets current time and keeps clock running to show real time
    public void time(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            timeText.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //gets current date and keeps clock running to show real time
    public void date(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy");
            dateText.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.hours(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public String convertToString(double price) {
        return price+"";
    }


    //Method that updates both the price and label (including % change)
    @FXML
    public void updateStock1(String stockName, String ticker) throws Exception{
        DoubleStringConverter convert = new DoubleStringConverter();
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p1.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock1.setText(stockName + " ($"+ ticker +")             " + change + "%");

    }
    @FXML
    public void updateStock2(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p2.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock2.setText(stockName + "($"+ ticker +")                " + change + "%");
   
    }

    @FXML
    public void updateStock3(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p3.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock3.setText(stockName + " ($"+ ticker +")               " + change + "%");
    }

    @FXML
    public void updateStock4(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p4.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock4.setText(stockName + " ($"+ ticker +")               " + change + "%");
    }

    @FXML
    public void updateStock5(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p5.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock5.setText(stockName + "($"+ ticker +")               " + change + "%");
    }

    @FXML
    public void updateStock6(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p6.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock6.setText(stockName + "($"+ ticker +")                    " + change + "%");
    }

    @FXML
    public void updateStock7(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p7.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock7.setText(stockName + " ($"+ ticker +")             " + change + "%");
    }

    @FXML
    public void updateStock8(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p8.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock8.setText(stockName + " ($"+ ticker +")                      " + change + "%");
    }

    @FXML
    public void updateStock9(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p9.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock9.setText(stockName + "  ($"+ ticker +")               " + change + "%");
    }

    @FXML
    public void updateStock10(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p10.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock10.setText(stockName + " ($"+ ticker +")                 " + change + "%");
    }

    //This method will have to change later to get portfolio value out of database to keep track
    @FXML
    public void fetchPortfolio() throws Exception {
        double val = calculatePortfolio();
        String pVal = val + "";
        portfolioVal.setText(pVal);
    }

    @FXML
    public double calculatePortfolio() {
        DoubleStringConverter convert = new DoubleStringConverter();
        //calculate the portfolio value based on # of shares * price per share, plus balance
        double total = (convert.fromString(s1.getText()) * convert.fromString(p1.getText()))
                + (convert.fromString(s2.getText()) * convert.fromString(p2.getText()))
                + (convert.fromString(s3.getText()) * convert.fromString(p3.getText()))
                + (convert.fromString(s4.getText()) * convert.fromString(p4.getText()))
                + (convert.fromString(s5.getText()) * convert.fromString(p5.getText()))
                + (convert.fromString(s6.getText()) * convert.fromString(p6.getText()))
                + (convert.fromString(s7.getText()) * convert.fromString(p7.getText()))
                + (convert.fromString(s8.getText()) * convert.fromString(p8.getText()))
                + (convert.fromString(s9.getText()) * convert.fromString(p9.getText()))
                + (convert.fromString(s10.getText()) * convert.fromString(p10.getText()));

        total = total + convert.fromString(balance.getText());
        DecimalFormat df = new DecimalFormat("#.##");
        total = Double.valueOf(df.format(total));
        return total;
    }

    public void updateApp() throws Exception {
        updateStock1("Alphabet Inc. ", "GOOGL");
        updateStock2("Apple Inc.        ", "AAPL");
        updateStock3("Amazon          ", "AMZN");
        updateStock4("Microsoft         ", "MSFT");
        updateStock5("Tesla                ", "TSLA");
        updateStock6("Facebook Inc.  ", "FB");
        updateStock7("JPMorgan Chase", "JPM");
        updateStock8("Visa Inc.          ", "V");
        updateStock9("Netflix Inc.      ", "NFLX");
        updateStock10("Zoom Video Inc.", "ZM");
        fetchPortfolio();
    }
    //missing case for input not being a valid number
    public void purchaseStocks1() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a1.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a1.getText()) * convert.fromString(p1.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }

        money = money - (convert.fromString(a1.getText()) * convert.fromString(p1.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a1.getText()) + convert.fromString(s1.getText());
        s1.setText(convert.toString(tmp));
        a1.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("GOOGL",share);
        db.updateBalanceDatabase(money);
    }

    public void purchaseStocks2() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a2.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a2.getText()) * convert.fromString(p2.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a2.getText()) * convert.fromString(p2.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a2.getText()) + convert.fromString(s2.getText());
        s2.setText(convert.toString(tmp));
        a2.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("AAPL",share);
        db.updateBalanceDatabase(money);
    }

    public void purchaseStocks3() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a3.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a3.getText()) * convert.fromString(p3.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a3.getText()) * convert.fromString(p3.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a3.getText()) + convert.fromString(s3.getText());
        s3.setText(convert.toString(tmp));
        a3.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("AMZN",share);
        db.updateBalanceDatabase(money);
    }

    public void purchaseStocks4() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a4.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a4.getText()) * convert.fromString(p4.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a4.getText()) * convert.fromString(p4.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a4.getText()) + convert.fromString(s4.getText());
        s4.setText(convert.toString(tmp));
        a4.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("MSFT",share);
        db.updateBalanceDatabase(money);
    }
    public void purchaseStocks5() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a5.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a5.getText()) * convert.fromString(p5.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a5.getText()) * convert.fromString(p5.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a5.getText()) + convert.fromString(s5.getText());
        s5.setText(convert.toString(tmp));
        a5.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("TSLA",share);
        db.updateBalanceDatabase(money);
    }
    public void purchaseStocks6() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a6.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a6.getText()) * convert.fromString(p6.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a6.getText()) * convert.fromString(p6.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a6.getText()) + convert.fromString(s6.getText());
        s6.setText(convert.toString(tmp));
        a6.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("FB",share);
        db.updateBalanceDatabase(money);
    }
    public void purchaseStocks7() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a7.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a7.getText()) * convert.fromString(p7.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a7.getText()) * convert.fromString(p7.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a7.getText()) + convert.fromString(s7.getText());
        s7.setText(convert.toString(tmp));
        a7.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("JPM",share);
        db.updateBalanceDatabase(money);
    }
    public void purchaseStocks8() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a8.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a8.getText()) * convert.fromString(p8.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a8.getText()) * convert.fromString(p8.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a8.getText()) + convert.fromString(s8.getText());
        s8.setText(convert.toString(tmp));
        a8.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("V",share);
        db.updateBalanceDatabase(money);
    }
    public void purchaseStocks9() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a9.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a9.getText()) * convert.fromString(p9.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a9.getText()) * convert.fromString(p9.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a9.getText()) + convert.fromString(s9.getText());
        s9.setText(convert.toString(tmp));
        a9.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("NFLX",share);
        db.updateBalanceDatabase(money);
    }
    public void purchaseStocks10() throws Exception {
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a10.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a10.getText()) * convert.fromString(p10.getText()) > money) {
            displayErrorMessage("Insufficient Funds!");
            return;
        }
        money = money - (convert.fromString(a10.getText()) * convert.fromString(p10.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(a10.getText()) + convert.fromString(s10.getText());
        s10.setText(convert.toString(tmp));
        a10.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("ZM",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks1() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a1.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a1.getText())  > convert.fromString(s1.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a1.getText()) * convert.fromString(p1.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s1.getText()) - convert.fromString(a1.getText());
        s1.setText(convert.toString(tmp));
        a1.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("GOOGL",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks2() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a2.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a2.getText())  > convert.fromString(s2.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a2.getText()) * convert.fromString(p2.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s2.getText()) - convert.fromString(a2.getText());
        s2.setText(convert.toString(tmp));
        a2.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("AAPL",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks3() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a3.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a3.getText())  > convert.fromString(s3.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a3.getText()) * convert.fromString(p3.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s3.getText()) - convert.fromString(a3.getText());
        s3.setText(convert.toString(tmp));
        a3.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("AMZN",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks4() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a4.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a4.getText())  > convert.fromString(s4.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a4.getText()) * convert.fromString(p4.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s4.getText()) - convert.fromString(a4.getText());
        s4.setText(convert.toString(tmp));
        a4.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("MSFT",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks5() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a5.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a5.getText())  > convert.fromString(s5.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a5.getText()) * convert.fromString(p5.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s5.getText()) - convert.fromString(a5.getText());
        s5.setText(convert.toString(tmp));
        a5.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("TSLA",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks6() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a6.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a6.getText())  > convert.fromString(s6.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a6.getText()) * convert.fromString(p6.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s6.getText()) - convert.fromString(a6.getText());
        s6.setText(convert.toString(tmp));
        a6.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("FB",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks7() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a7.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a7.getText())  > convert.fromString(s7.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a7.getText()) * convert.fromString(p7.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s7.getText()) - convert.fromString(a7.getText());
        s7.setText(convert.toString(tmp));
        a7.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("JPM",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks8() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a8.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a8.getText())  > convert.fromString(s8.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a8.getText()) * convert.fromString(p8.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s8.getText()) - convert.fromString(a8.getText());
        s8.setText(convert.toString(tmp));
        a8.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("V",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks9() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a9.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a9.getText())  > convert.fromString(s9.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a9.getText()) * convert.fromString(p9.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s9.getText()) - convert.fromString(a9.getText());
        s9.setText(convert.toString(tmp));
        a9.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("NFLX",share);
        db.updateBalanceDatabase(money);
    }
    public void sellStocks10() throws Exception {
        //make sure "amount" is a valid input
        if (!Pattern.matches("^\\d*[1-9]\\d*$", a10.getText() )) {
            displayErrorMessage("Please Enter a Positive Integer");
            return;
        }
        updateApp();
        DoubleStringConverter convert = new DoubleStringConverter();
        double money = convert.fromString(balance.getText());
        if (convert.fromString(a10.getText())  > convert.fromString(s10.getText())) {
            displayErrorMessage("You don't own that many stocks!");
            return;
        }

        money = money + (convert.fromString(a10.getText()) * convert.fromString(p10.getText()));
        DecimalFormat df = new DecimalFormat("#.##");
        money = Double.valueOf(df.format(money));
        balance.setText(convert.toString(money));

        double tmp = convert.fromString(s10.getText()) - convert.fromString(a10.getText());
        s10.setText(convert.toString(tmp));
        a10.setText("0");

        //update database
        int share = (int) tmp;
        db.updateSharesDatabase("ZM",share);
        db.updateBalanceDatabase(money);
    }

    public void displayErrorMessage(String msg) {
        statusMessage.setOpacity(1.0);
        message.setText(msg);
        message.setStyle("-fx-text-fill: red;");
        hideErrorMessage();
    }
    public void hideErrorMessage() {
        Timeline errMsg = new Timeline(new KeyFrame(Duration.millis(2000), ae -> {statusMessage.setOpacity(0);}));
        errMsg.play();
    }
    public void updateTimeline() {
        Timeline update = new Timeline(new KeyFrame(Duration.millis(30000), ae -> {
            try {
                updateApp();
                updateGraph();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        update.setCycleCount(Animation.INDEFINITE);
        update.play();
    }

    //method to find min price for historical data axis
    public int findMin(String company) throws Exception {
        double[] result = data.getHistoricalData(company);
        double min = result[0];
        int length = result.length;
        for (int i = 0; i < length; i++) {
            if (result[i] < min) {
                min = result[i];
            }
        }
        return (int)min;
    }

    //method to find max price for historical data axis
    public int findMax(String company) throws Exception {
        double[] result = data.getHistoricalData(company);
        double max = result[0];
        int length = result.length;
        for (int i = 0; i < length; i++) {
            if (result[i] > max) {
                max = result[i];
            }
        }
        return (int)max;
    }
 
    //clear the data in the chart
    public void updateGraph() throws Exception {
        String ticker = getCurrentGraph();

        //clear the previous data
        lineChart.getData().clear();
        boolean first;
        if(x_axis[0] == null) {
            first = true;
        } else {
            first = false;
        }

        //get the two arrays again
        y_axis = data.getHistoricalData(ticker);
        x_axis = data.getTimeInterval(ticker);
        //Add to the array
        for(int i = 29;i>=0;i--){
            if (first == false) {
                series.getData().remove(i);
            }
            series.getData().add(new XYChart.Data<String,Number>(x_axis[i],y_axis[i]));
        }
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
        //setting the axis
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(findMin(ticker)-5);
        yAxis.setUpperBound(findMax(ticker)+5);
        yAxis.setTickUnit(2);
        lineChart.setLegendVisible(false);
    }

    public void setCurrentGraph1() throws Exception {
        currentGraph = "GOOGL";
        updateGraph();
    }
    public void setCurrentGraph2() throws Exception {
        currentGraph = "AAPL";
        updateGraph();

    }
    public void setCurrentGraph3() throws Exception {
        currentGraph = "AMZN";
        updateGraph();

    }
    public void setCurrentGraph4() throws Exception {
        currentGraph = "MSFT";
        updateGraph();

    }
    public void setCurrentGraph5() throws Exception {
        currentGraph = "TSLA";
        updateGraph();

    }
    public void setCurrentGraph6() throws Exception {
        currentGraph = "FB";
        updateGraph();

    }
    public void setCurrentGraph7() throws Exception {
        currentGraph = "JPM";
        updateGraph();

    }
    public void setCurrentGraph8() throws Exception {
        currentGraph = "V";
        updateGraph();

    }
    public void setCurrentGraph9() throws Exception {
        currentGraph = "NFLX";
        updateGraph();

    }
    public void setCurrentGraph10() throws Exception {
        currentGraph = "ZM";
        updateGraph();

    }

    public String getCurrentGraph() {
        return currentGraph;
    }

}
