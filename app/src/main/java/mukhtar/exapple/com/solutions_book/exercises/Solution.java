package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;

public class Solution extends AppCompatActivity {
    Menu menu1;
    String chapter,number,given;
    String task_id;
    TextView tvGiven;
    ListView lv;
    ArrayList<Map<String, String>> data = new ArrayList();
    SimpleAdapter sa;
    ArrayList<Map<String, String>> dataSA=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        JSONObject res = null;
        tvGiven=(TextView)findViewById(R.id.given);
        lv=(ListView)findViewById(R.id.lv);
        Intent intent = getIntent();
        task_id=intent.getStringExtra("id");
        final String query = "SELECT data FROM tasks where _id='"+task_id+"'";
        getResponse(query,"task");
        tvGiven.setText(given);

        sa = new SimpleAdapter(this, dataSA, R.layout.item_solution,
                new String[]{"text","login"}, new int[]{R.id.item_solution_text,R.id.item_solution_login});
        lv.setAdapter(sa);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_of_solution, menu);
        this.menu1 = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_solution:
                Intent intent = new Intent(getBaseContext(),AddSolution.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void changeData(String response,String type){

        try {
            JSONObject result = new JSONObject(response);
            if(result.getInt("success")==1){
                JSONArray array = result.getJSONArray("products");
                Log.d("mylogs",array.toString());
                if(type.equals("task")){
                    given= array.getString(0);
                }
                else{}
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
    void getResponse(final String query,final String type){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_user_id.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("mylogs","Response : "+response);
                                changeData(response,type);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getBaseContext(),"Can not load id",Toast.LENGTH_SHORT).show();
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
}
