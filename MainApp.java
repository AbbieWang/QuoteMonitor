
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.awt.event.*;
import javafx.event.EventHandler;
import org.json.simple.parser.ParseException;

import javax.json.JsonObject;
import javax.json.JsonArray;
import java.io.IOException;
import java.util.ArrayList;

/*
This is a class displaying the real time data on a GUI platform.
 */
public class MainApp extends Application {


    /*
    These are labels for the top 10 companies under the S&P 500 Index.
    Including Microsoft (MSFT), Apple (AAPL), Amazon(AMZN),Facebook (FB), Alphabet Class A (GOOGL),Alphabet Class C (GOOG),
    Johnson & Johnson (JNJ), Berkshire Hathaway Inc. Class B (BRK.B),Visa (V), JPMorgan Chase&Co. (JPM).
     */
    Button bMSFT;
    Button bAAPL;
    Button bAMZN;
    Button bFB;
    Button bGOOG;
    Button bGOOGL;
    Button bJNJ;
    Button bBRKB;
    Button bV;
    Button bJPM;
    Label labelMSFT;
    Label labelAAPL;
    Label labelAMZN;
    Label labelFB;
    Label labelGOOG;
    Label labelGOOGL;
    Label labelJNJ;
    Label labelBRKB;
    Label labelV;
    Label labelJPM;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Quote Monitor");

        Data data = new Data();

        //Get the price and handle button events for Microsoft
        Double displayMSFT = data.getRealTimeData("MSFT");
        labelMSFT = new Label(displayMSFT.toString());
        bMSFT = new Button();
        bMSFT.setText("Microsoft");
        bMSFT.setOnAction(e -> System.out.println(labelMSFT));

        //Get the price and handle button events for Apple
        Double displayAAPL = data.getRealTimeData("AAPL");
        labelAAPL = new Label(displayAAPL.toString());
        bAAPL = new Button();
        bAAPL.setText("Apple");
        bAAPL.setOnAction(e -> System.out.println(labelAAPL));

        //Get the price and handle button events for Amazon
        Double displayAMZN = data.getRealTimeData("AMZN");
        labelAMZN = new Label(displayAMZN.toString());
        bAMZN = new Button();
        bAMZN.setText("Amazon");
        bAMZN.setOnAction(e -> System.out.println(labelAMZN));

        //Get the price and handle button events for Facebook
        Double displayFB =  data.getRealTimeData("FB");
        labelFB = new Label(displayFB.toString());
        bFB = new Button();
        bFB.setText("Facebook");
        bFB.setOnAction(e -> System.out.println(labelFB));

        //Get the price and handle button events for Alphabet Class A (GOOGL)
        Double displayGOOGL = data.getRealTimeData("GOOGL");
        labelGOOGL = new Label(displayGOOGL.toString());
        bGOOGL = new Button();
        bGOOGL.setText("Alphabet Class A");
        bGOOGL.setOnAction(e -> System.out.println(labelGOOGL));

        //Create line chart: the x-axis is categorical and the y-axis is numeric
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("1-h time interval stock prices change");
        final LineChart<String, Number> lineChart = new LineChart<String, Number>(
                xAxis, yAxis);
        lineChart.setTitle("Last-Hour Stock Prices");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("The stock you click on");

        //Import the historical price data
        Data hist = new Data();

        //TODO:Solve this

  /*      bMSFT.setOnAction((event) -> {
            System.out.println(labelMSFT);

            double [] y_axis = new double[30];
            try {
                y_axis = hist.getHistoricalData("MSFT");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] x_axis = new String[30];
            try {
                x_axis = hist.getTimeInterval("MSFT");
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            for(int i = 0;i<30;i++){

                    series.getData().add(new XYChart.Data<String,Number>(x_axis[i],y_axis[i]));
        }});
*/
        double []y_axis = hist.getHistoricalData("AAPL");
        String []x_axis = hist.getTimeInterval("AAPL");



        //Add to the array
        for(int i = 0;i<30;i++){

            series.getData().add(new XYChart.Data<String,Number>(x_axis[i],y_axis[i]));
        }
        lineChart.getData().add(series);

        //Create a flow pane to display each button
        FlowPane flow = new FlowPane(Orientation.VERTICAL,5,5);
        //to add space around the pane from the top of the screen
        flow.setPadding(new Insets(50));
        flow.getChildren().addAll(bMSFT,bAAPL,bAMZN,bFB,bGOOGL);
        flow.getChildren().add(lineChart);
        Scene scene = new Scene(flow, 1000, 800);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

}
