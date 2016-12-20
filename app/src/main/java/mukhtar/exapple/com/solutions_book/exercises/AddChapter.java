package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import mukhtar.exapple.com.solutions_book.R;

import static mukhtar.exapple.com.solutions_book.exercises.Chapters.chapters;
import static mukhtar.exapple.com.solutions_book.exercises.Chapters.dataSA;

public class AddChapter extends AppCompatActivity {
    EditText editText;
    String book_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        editText=(EditText)findViewById(R.id.edittext_add_chapter);
        Intent intent=getIntent();
        book_id=intent.getStringExtra("book_id");
    }
    public void save(View v){
        String url="http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php";
        final String query = "UPDATE books SET chapters='"+getRes()+"' where _id="+book_id;
        for_query(query,url);
    }
    public void for_query(final String query,String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                Toast.makeText(getBaseContext(),"chapter added succesfully  "+response,Toast.LENGTH_SHORT).show();

                                finish();
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
    public String getRes(){
        int currentChapter = Integer.parseInt(editText.getText().toString());
        try {
            JSONObject result = new JSONObject("{}");
            JSONArray array = new JSONArray();
            if(!chapters.contains(currentChapter)){
                chapters.add(currentChapter);
            }
            for(int c:chapters){
                array.put(c);
            }
            result.put("chapters",array);
            Log.d("mylogs",result.toString());
            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "{chapters:["+currentChapter+"]}";





    }
}
