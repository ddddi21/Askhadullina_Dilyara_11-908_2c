package HW_2;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import sun.misc.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Get_Post {
    private final String USER__AGENT = "Mozilla/5.0";

    public static void main(String[]args) throws Exception {

        Get_Post http = new Get_Post();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

//        public static String getHTML(String urlToRead) throws Exception {
//            StringBuilder result = new StringBuilder();
//            URL url = new URL(urlToRead);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//            rd.close();
//            return result.toString();
//        }
//
//        public static void main(String[] args) throws Exception
//        {
//            System.out.println(getHTML(args[0]));
//        }

    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER__AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
     void sendPost() throws Exception {

        // url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "https://httpbin.org/post";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            System.out.println(response.toString());

        }

    }
//        String url = "http://www.google.com/";
//
//        URL obj = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
//
//        connection.setRequestMethod("GET");
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//    }
////System.out.println(response.toString());
//URL url = new URL("http://www.example.com/");
//URLConnection con = url.openConnection();
//    InputStream in = con.getInputStream();
//    String encoding = con.getContentEncoding();
//    encoding = encoding == null ? "UTF-8" : encoding;
//    String body = IOUtils.toString(in, encoding);
//System.out.println(body);
}
