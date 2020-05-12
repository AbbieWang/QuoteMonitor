import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//this class gets the data from url and puts ticker and stock price into database
public class Data {

    //get 5 min historical data from JSON and puts into an array
    public static double[] getHistoricalData(String company) throws Exception {
        double numbers[] = new double[12];
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/1hour/" + company);

        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        JSONArray body = (JSONArray) parser.parse(new InputStreamReader(in));

        for (int i = 0; i < 12; i++) {
            JSONObject obj = (JSONObject) body.get(i);
            numbers[i] = (double) obj.get("open");
        }
        return numbers;
    }

    //gets the real time price and returns it
    public static double getRealTimeData(String company) throws Exception {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/" + company);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject) body.get(0);
        return (double)obj.get("price");
    }

    public static String getChangesPercentage(String company) throws Exception {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/" + company);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject) body.get(0);
        if ((double)obj.get("changesPercentage") < 0) {
            return "" + (double)obj.get("changesPercentage");
        }
        else {
            return "+" + (double)obj.get("changesPercentage");
        }
    }


}