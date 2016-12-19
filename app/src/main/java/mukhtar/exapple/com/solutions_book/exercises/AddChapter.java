package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;

public class AddChapter extends AppCompatActivity {
    EditText editText;
    String book_id;
    String s;
    ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        editText=(EditText)findViewById(R.id.edittext_add_chapter);
        Intent intent=getIntent();
        book_id=intent.getStringExtra("book_id");
    }
    public void save(View v){
       s =editText.getText().toString();
        String url="http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_user_id.php";
        String url1="http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php";
        final String query = "SELECT chapters FROM books WHERE _id="+book_id;
        final String query1 = "UPDATE books set  chapters={chapters:["+list.toString()+"]} where _id="+book_id;
        for_query(query,url,true);
        for_query(query1,url1,false);
    }
    public void for_query(final String query,String url,final boolean t){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                Log.d("mylogs",response);
                                if(t){
                                    changeData(response);}
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
                ){
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
    public void changeData(String response){

        try {
            JSONObject res = new JSONObject(response);
            String ja=res.getJSONArray("products").getString(0);
            JSONArray jarr = new JSONObject(ja).getJSONArray("chapters");
            int size = jarr.length();
            for (int i=0;i<size;i++){
                list.add(jarr.get(i).toString());
            }
            list.add(s);
            Collections.sort(list);
            Log.d( "mylogs",list.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
