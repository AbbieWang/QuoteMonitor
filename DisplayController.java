import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;

import java.awt.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class DisplayController implements Initializable {

    @FXML
    private TitledPane stock1,stock2,stock3,stock4,stock5;
    @FXML
    private TextField p1,p2,p3,p4,p5;//price fields
    @FXML
    private TextField s1,s2,s3,s4,s5;//shares fields
    @FXML
    private TextField a1,a2,a3,a4,a5;//amount fields

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

    Data data = new Data();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date();
        time();
        try {
              updateApp();
//            updateStock1("Alphabet Inc.", "GOOGL");
//            updateStock2("Apple Inc.        ", "AAPL");
//            updateStock3("Amazon          ", "AMZN");
//            updateStock4("Microsoft         ", "MSFT");
//            updateStock5("Tesla                 ", "TSLA");
//            fetchPortfolio();


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

//    @FXML
//    public void getGoogPrice() throws Exception {
//       String price = convertToString(data.getRealTimeData("GOOGL"));
//       p1.setText(price);
//    }
//
//    @FXML
//    public void setGoogLabel() throws Exception {
//        double change = data.getChangesPercentage("GOOGL");
//        stock1.setText("Alphabet Inc. ($GOOGL)                 +" + change + "%");
//    }



    //Method that updates both the price and label (including % change)
    @FXML
    public void updateStock1(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p1.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock1.setText(stockName + " ($"+ ticker +")                 " + change + "%");
    }
    @FXML
    public void updateStock2(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p2.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock2.setText(stockName + " ($"+ ticker +")                 " + change + "%");
    }

    @FXML
    public void updateStock3(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p3.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock3.setText(stockName + " ($"+ ticker +")                 " + change + "%");
    }

    @FXML
    public void updateStock4(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));
        p4.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock4.setText(stockName + " ($"+ ticker +")                 " + change + "%");
    }

    @FXML
    public void updateStock5(String stockName, String ticker) throws Exception{
        DecimalFormat df = new DecimalFormat("#.##");
        String price = convertToString(Double.valueOf(df.format(data.getRealTimeData(ticker))));


        p5.setText(price);
        String change = data.getChangesPercentage(ticker);
        stock5.setText(stockName + " ($"+ ticker +")                 " + change + "%");
    }

    //This method will have to change later to get portfolio value out of database to keep track
    @FXML
    public void fetchPortfolio() {
        //PortfolioController portfolio = new PortfolioController();
        double val = calculatePortfolio();
        //CHANGE
        String pVal = val + "";
        portfolioVal.setText(pVal);
        //balance.setText("10000");

    }

    @FXML
    public double calculatePortfolio() {
        DoubleStringConverter convert = new DoubleStringConverter();
        //calculate the portfolio value based on # of shares * price per share, plus balance
        double total = (convert.fromString(s1.getText()) * convert.fromString(p1.getText()))
                + (convert.fromString(s2.getText()) * convert.fromString(p2.getText()))
                + (convert.fromString(s3.getText()) * convert.fromString(p3.getText()))
                + (convert.fromString(s4.getText()) * convert.fromString(p4.getText()))
                + (convert.fromString(s5.getText()) * convert.fromString(p5.getText()));

        total = total + convert.fromString(balance.getText());
        DecimalFormat df = new DecimalFormat("#.##");
        total = Double.valueOf(df.format(total));
        return total;
    }

    public void updateApp() throws Exception {
        updateStock1("Alphabet Inc.", "GOOGL");
        updateStock2("Apple Inc.        ", "AAPL");
        updateStock3("Amazon          ", "AMZN");
        updateStock4("Microsoft         ", "MSFT");
        updateStock5("Tesla                 ", "TSLA");
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
        Timeline update = new Timeline(new KeyFrame(Duration.millis(60000), ae -> {
            try {
                updateApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        update.setCycleCount(Animation.INDEFINITE);
        update.play();
    }

//    @Override
//    @FXML
//    public void run() {
//
//    }
}
