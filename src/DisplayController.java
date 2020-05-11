import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DisplayController implements Initializable {
    @FXML
    private Label dateText;
    @FXML
    private Label timeText;
    @FXML
    private LineChart<?,?> lineChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

//    /*
//   These are labels for the top 10 companies under the S&P 500 Index.
//   Including Microsoft (MSFT), Apple (AAPL), Amazon(AMZN),Facebook (FB), Alphabet Class A (GOOGL),Alphabet Class C (GOOG),
//   Johnson & Johnson (JNJ), Berkshire Hathaway Inc. Class B (BRK.B),Visa (V), JPMorgan Chase&Co. (JPM).
//    */
//    Button bMSFT;
//    Button bAAPL;
//    Button bAMZN;
//    Button bFB;
//    Button bGOOG;
//    Button bGOOGL;
//    Button bJNJ;
//    Button bBRKB;
//    Button bV;
//    Button bJPM;
//    Label labelMSFT;
//    Label labelAAPL;
//    Label labelAMZN;
//    Label labelFB;
//    Label labelGOOG;
//    Label labelGOOGL;
//    Label labelJNJ;
//    Label labelBRKB;
//    Label labelV;
//    Label labelJPM;


//    Process data = new Process();
//
//    //Get the price and handle button events for Microsoft
//    String displayMSFT = data.getInfo("MSFT", "stock/real-time-price");
//    labelMSFT = new Label(displayMSFT);
//    bMSFT = new Button();
//        bMSFT.setText("Microsoft");
//        bMSFT.setOnAction(e -> System.out.println(labelMSFT));
//
//    //Get the price and handle button events for Apple
//    String displayAAPL = data.getInfo("AAPL", "stock/real-time-price");
//    labelAAPL = new Label(displayAAPL);
//    bAAPL = new Button();
//        bAAPL.setText("Apple");
//        bAAPL.setOnAction(e -> System.out.println(labelAAPL));
//
//    //Get the price and handle button events for Amazon
//    String displayAMZN = data.getInfo("AMZN", "stock/real-time-price");
//    labelAMZN = new Label(displayAMZN);
//    bAMZN = new Button();
//        bAMZN.setText("Amazon");
//        bAMZN.setOnAction(e -> System.out.println(labelAMZN));
//
//    //Get the price and handle button events for Facebook
//    String displayFB = data.getInfo("FB", "stock/real-time-price");
//    labelFB = new Label(displayFB);
//    bFB = new Button();
//        bFB.setText("Facebook");
//        bFB.setOnAction(e -> System.out.println(labelFB));
//
//    //Get the price and handle button events for Alphabet Class A (GOOGL)
//    String displayGOOGL = data.getInfo("GOOGL", "stock/real-time-price");
//    labelGOOGL = new Label(displayGOOGL);
//    bGOOGL = new Button();
//        bGOOGL.setText("Alphabet Class A");
//        bGOOGL.setOnAction(e -> System.out.println(labelGOOGL));
//
//    //Creating the Scatter chart
//    NumberAxis xAxis = new NumberAxis(0, 12, 3);
//        xAxis.setLabel("Time");
//    NumberAxis yAxis = new NumberAxis(0, 16, 4);
//        yAxis.setLabel("Price");
//    ScatterChart<String, Number> scatterChart = new ScatterChart(xAxis, yAxis);
//
//    String chartGOOGL = data.getInfo("GOOGL", "historical-chart/5min");
//
//    //Create a flow pane to display each button
//    FlowPane flow = new FlowPane(Orientation.VERTICAL,5,5);
//    //to add space around the pane
//        flow.setPadding(new Insets(50));
//        flow.getChildren().addAll(bMSFT,bAAPL,bAMZN,bFB,bGOOGL);
//        flow.getChildren().add(scatterChart);
@FXML
public void createGraph() {
    XYChart.Series series = new XYChart.Series();
    for (int i = 0; i < 10; i++) {
        series.getData().add(i, i);
    }

}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date();
        time();
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

}
