package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

/*
This is a class displaying the real time data on a GUI platform.
 */
public class Main extends Application {


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

        Process data = new Process();

        //Get the price and handle button events for Microsoft
        String displayMSFT = data.getInfo("MSFT", "stock/real-time-price");
        labelMSFT = new Label(displayMSFT);
        bMSFT = new Button();
        bMSFT.setText("Microsoft");
        bMSFT.setOnAction(e -> System.out.println(labelMSFT));

        //Get the price and handle button events for Apple
        String displayAAPL = data.getInfo("AAPL", "stock/real-time-price");
        labelAAPL = new Label(displayAAPL);
        bAAPL = new Button();
        bAAPL.setText("Apple");
        bAAPL.setOnAction(e -> System.out.println(labelAAPL));

        //Get the price and handle button events for Amazon
        String displayAMZN = data.getInfo("AMZN", "stock/real-time-price");
        labelAMZN = new Label(displayAMZN);
        bAMZN = new Button();
        bAMZN.setText("Amazon");
        bAMZN.setOnAction(e -> System.out.println(labelAMZN));

        //Get the price and handle button events for Facebook
        String displayFB = data.getInfo("FB", "stock/real-time-price");
        labelFB = new Label(displayFB);
        bFB = new Button();
        bFB.setText("Facebook");
        bFB.setOnAction(e -> System.out.println(labelFB));

        //Get the price and handle button events for Alphabet Class A (GOOGL)
        String displayGOOGL = data.getInfo("GOOGL", "stock/real-time-price");
        labelGOOGL = new Label(displayGOOGL);
        bGOOGL = new Button();
        bGOOGL.setText("Alphabet Class A");
        bGOOGL.setOnAction(e -> System.out.println(labelGOOGL));

        //Creating the Scatter chart
        NumberAxis xAxis = new NumberAxis(0, 12, 3);
        xAxis.setLabel("Time");
        NumberAxis yAxis = new NumberAxis(0, 16, 4);
        yAxis.setLabel("Price");
        ScatterChart<String, Number> scatterChart = new ScatterChart(xAxis, yAxis);

        String chartGOOGL = data.getInfo("GOOGL", "historical-chart/5min");

        //Create a flow pane to display each button
        FlowPane flow = new FlowPane(Orientation.VERTICAL,5,5);
        //to add space around the pane
        flow.setPadding(new Insets(50));
        flow.getChildren().addAll(bMSFT,bAAPL,bAMZN,bFB,bGOOGL);
        flow.getChildren().add(scatterChart);
        Scene scene = new Scene(flow, 1000, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}