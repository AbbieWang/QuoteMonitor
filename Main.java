import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
This is a class displaying the real time data on a GUI platform.
 */
public class Main extends Application {
    static Data data = new Data();

    public static void main(String[] args) throws Exception {
        //data.insertRealTimeData("GOOGL");
        //data.insertHistoricalData("GOOGL");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("QuoteMonitorGUI.fxml"));
        primaryStage.setTitle("Quote Monitor");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}