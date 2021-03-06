
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//this class gets the data from url and puts ticker and stock price into database
public class Data {


    //get 1 hour historical data from JSON and puts into an array
    public static double[] getHistoricalData(String company) throws Exception {
        double numbers[] = new double[30];
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/1hour/" + company);

        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        JSONArray body = (JSONArray) parser.parse(new InputStreamReader(in));

        for (int i = 0; i < 30; i++) {
            JSONObject obj = (JSONObject) body.get(i);
            numbers[i] = (double) obj.get("open");
        }
        return numbers;
    }

    //get the time and day for the historical data list chart xAxis
    public String[] getTimeInterval(String company) throws IOException, ParseException {
        String interval[] = new String[30];
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/1hour/" + company);

        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        JSONArray body = (JSONArray) parser.parse(new InputStreamReader(in));

        for (int i = 0; i < 30; i++) {
            JSONObject obj = (JSONObject) body.get(i);
            String str = obj.get("date").toString();
            interval[i] = str.substring(str.indexOf("-")+1, str.length()-3);
        }
        return interval;
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

    //gets the percent change and returns as a String
    public static String getChangesPercentage(String company) throws Exception {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/" + company);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject) body.get(0);
        //checks if the change is less than or more than zero
        if ((double)obj.get("changesPercentage") < 0) {
            return "" + obj.get("changesPercentage");
        }
        else {
            return "+" + obj.get("changesPercentage");
        }
    }

}