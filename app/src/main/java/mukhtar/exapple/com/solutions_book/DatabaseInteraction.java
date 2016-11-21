package mukhtar.exapple.com.solutions_book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Mukhtar on 11/20/2016.
 */

public class DatabaseInteraction extends AsyncTask<Object,Void,JSONObject> {
    Context ctx;
    String url;
    String method;
    String query;
    ProgressDialog pDialog;
    public DatabaseInteraction(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Connecting with server...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }


    @Override
    protected JSONObject doInBackground(Object[] settings) {
        url = (String)settings[0];
        method = (String)settings[1];
        query = (String)settings[2];
        JSONParser jp = new JSONParser();
        JSONObject js = jp.makeHttpRequest(url, method, query);
        return js;
    }

    @Override
    protected void onPostExecute(JSONObject j) {
        pDialog.dismiss();
        super.onPostExecute(j);
    }
}
