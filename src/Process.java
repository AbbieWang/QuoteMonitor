import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;



public class Process {

    /*
    This is the function to get information about companies.
    under the category, if you want to find the real stock prices, type in category to be "stock/real-time-price".
    If you want to find 5-minute duration historical stock prices, type in category to be "historical-chart/5min".
    To check multiple companies, use comma to separate them in companySymbol, example: "MSFT,AAPL,GOOG"/
     */
    public String getInfo(String companySymbol, String category) throws IOException, InterruptedException {

        URL url = new URL("https://financialmodelingprep.com/api/v3/"+category+"/"+companySymbol);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
     //   System.out.print(body);
        return body;
    }


    public static void main(String[] args) throws IOException, InterruptedException {

    }

}
