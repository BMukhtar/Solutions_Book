package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;

public class AddTask extends AppCompatActivity {
    EditText editText;
    EditText numberEditText;
    Button button;
    ImageView image_view_solutions_image;
    String book_id;
    String chapter;


    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        numberEditText=(EditText)findViewById(R.id.number_of_task);
        editText=(EditText)findViewById(R.id.text_of_task);
        button= (Button)findViewById(R.id.btn_add_task);
        sharedPref = getSharedPreferences("Username",this.MODE_PRIVATE);
        Intent intent = getIntent();
        book_id=intent.getStringExtra("book_id");
        chapter=intent.getStringExtra("chapter");
    }

    public void addTask(View v){
        String number=numberEditText.getText().toString();
        String data = editText.getText().toString();
        String quer="INSERT INTO tasks(book_id,chapter, number,data,user_id) VALUES ("+book_id+","+chapter+",'"+number+"','"+data+"','"+sharedPref.getInt("id",0)+"')";
        Log.d("btn",quer);
        for_query(quer);

    }






    public void for_query(final String query){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                Log.d("mylogs",response);
                                try {
                                    JSONObject res = new JSONObject(response);
                                    if(res.getInt("success")==1){
                                        Log.d("madi","madik");
                                        finish();

                                        Log.d("mylogs",res.toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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

}
