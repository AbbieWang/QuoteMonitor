
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/*
This is a class displaying the real time data on a GUI platform.
 */
public class MainApp extends Application implements EventHandler<ActionEvent> {


    private static double[] y_axis;
    private static String[] x_axis;
    private static CategoryAxis xAxis;
    private static NumberAxis yAxis;
    final LineChart<String, Number> lineChart;
    XYChart.Series series;
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

    public MainApp() {
        series = new XYChart.Series();
        y_axis = new double[30];
        x_axis = new String[30];
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        this.lineChart = new LineChart<String, Number>(xAxis, yAxis);
    }

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
        //  bAAPL.setOnAction(e -> System.out.println(labelAAPL));

        bAAPL.setOnAction(this);

        //Get the price and handle button events for Amazon
        Double displayAMZN = data.getRealTimeData("AMZN");
        labelAMZN = new Label(displayAMZN.toString());
        bAMZN = new Button();
        bAMZN.setText("Amazon");
        bAMZN.setOnAction(e -> System.out.println(labelAMZN));

        //Get the price and handle button events for Facebook
        Double displayFB = data.getRealTimeData("FB");
        labelFB = new Label(displayFB.toString());
        bFB = new Button();
        bFB.setText("Facebook");
   //     bFB.setOnAction(e -> System.out.println(labelFB));

        bFB.setOnAction(this);

        //Get the price and handle button events for Alphabet Class A (GOOGL)
        Double displayGOOGL = data.getRealTimeData("GOOGL");
        labelGOOGL = new Label(displayGOOGL.toString());
        bGOOGL = new Button();
        bGOOGL.setText("Alphabet Class A");
        bGOOGL.setOnAction(e -> System.out.println(labelGOOGL));


        xAxis.setLabel("1-h time interval stock prices change");
        lineChart.setTitle("Last-Hour Stock Prices");
        //defining a series
        series.setName("The stock you click on");


        Data hist = new Data();

        y_axis = hist.getHistoricalData("MSFT");
        x_axis = hist.getTimeInterval("MSFT");


        //Add to the array
        for(int i = 0;i<30;i++){
            series.getData().add(new XYChart.Data<String,Number>(x_axis[i],y_axis[i]));
        }
        lineChart.getData().add(series);
        //Create a flow pane to display each button
        FlowPane flow = new FlowPane(Orientation.VERTICAL, 5, 5);
        //to add space around the pane from the top of the screen
        flow.setPadding(new Insets(50));
        flow.getChildren().addAll(bMSFT, bAAPL, bAMZN, bFB, bGOOGL);
        flow.getChildren().add(lineChart);
        Scene scene = new Scene(flow, 1000, 800);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == bAAPL)
        {
            System.out.println(labelAAPL);
            Data hist = new Data();
        try {
            y_axis = hist.getHistoricalData("AAPL");
            x_axis = hist.getTimeInterval("AAPL");
            for(int i = 0;i<30;i++){
                series.getData().add(new XYChart.Data<String,Number>(x_axis[i],y_axis[i]));
            }
            lineChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
            }
     }
        else if (event.getSource() == bFB){
            System.out.println(labelFB);
            Data hist = new Data();
            try {
                y_axis = hist.getHistoricalData("FB");
                x_axis = hist.getTimeInterval("FB");
                for(int i = 0;i<30;i++){
                    series.getData().add(new XYChart.Data<String,Number>(x_axis[i],y_axis[i]));
                }
                lineChart.getData().add(series);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

