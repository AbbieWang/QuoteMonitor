import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class DataTest {
    Data data = new Data();

    @Test
    void gettingUrl() throws IOException {
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/5min/GOOG");
        System.out.print(url.openConnection());
    }

    @Test
    void gettingJsonArray() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/GOOG");
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        JSONArray body = (JSONArray) parser.parse(new InputStreamReader(in));
        System.out.print(body);
    }

    @Test
    void gettingJsonObject() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/historical-chart/1hour/GOOG");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject)body.get(0);
        System.out.print(obj);
    }

    @Test
    void gettingJsonObjectKeyValue() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/GOOG");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        JSONArray body = (JSONArray) parser.parse(reader);
        JSONObject obj = (JSONObject)body.get(0);
        System.out.print(obj.get("price"));
    }

    @Test
    void gettingHistoricalDataArray() throws Exception {
        System.out.println(data.getHistoricalData("GOOG")[0]);
        System.out.println(data.getHistoricalData("GOOG")[1]);
        System.out.println(data.getHistoricalData("GOOG")[2]);
    }


}
