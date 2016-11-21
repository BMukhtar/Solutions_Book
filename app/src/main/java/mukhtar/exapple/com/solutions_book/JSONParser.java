package mukhtar.exapple.com.solutions_book;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JSONParser {
    JSONObject jsonObject ;
    HttpURLConnection conn = null;
    public JSONParser() {
    }

    public JSONObject makeHttpRequest(String url,String method,String query){
        int timeout = 10000;
        try {

            URL urlUse = new URL(url);
            conn = (HttpURLConnection) urlUse.openConnection();
            conn.setRequestMethod(method);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            String data = URLEncoder.encode("query", "UTF-8")
                    + "=" + URLEncoder.encode(query, "UTF-8");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();
            jsonObject = new JSONObject(finalJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }

        }

        return jsonObject;
    }


}
