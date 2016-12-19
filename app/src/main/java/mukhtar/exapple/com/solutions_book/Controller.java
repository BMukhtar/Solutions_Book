package mukhtar.exapple.com.solutions_book;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 12/19/16.
 */

public class Controller extends AppCompatActivity {
    Activity activity;
    final int listViewId=R.id.lv;
    int book_id;
    int chapter;
    String number;
    int task_id;
    public Controller(Activity activity,int task_id){
        this.task_id=task_id;
        this.activity=activity;
    }
    public String getDataOfTask(int task_id){
        String data="";

        return data;
    }
    void getResponse(final String query,final String type,final SimpleAdapter sa){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_user_id.php";
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("mylogs","Response : "+response);
                                changeData(response,sa);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(activity,"Can not load id",Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap();
                        map.put("query", query);
                        return map;
                    }
                };
        request.setTag("POST");
        queue.add(request);
    }
    public void changeData(String response,SimpleAdapter sa){

        try {
            JSONObject result = new JSONObject(response);
            if(result.getInt("success")==1){
                JSONArray array = result.getJSONArray("products");
                Log.d("mylogs",array.toString());
                //for(int i = 0; i<array.length();i++){
//                    HashMap<String,String> book = new HashMap<>();
//                    JSONArray information = array.getJSONArray(i);
//                    book.put("number",chapter+"."+information.getString(0));
//                    book.put("id",information.getString(1));
//                    dataSA.add(book);
                //}
                sa.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
