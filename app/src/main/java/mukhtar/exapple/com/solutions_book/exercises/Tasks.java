package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import static mukhtar.exapple.com.solutions_book.R.id.lv;

public class Tasks extends AppCompatActivity {
    ListView lv;
    Menu menu1;
    SimpleAdapter sa;
    ArrayList<Map<String, String>> dataSA=new ArrayList<>();
    String chapter="";
    String book_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Intent intent=getIntent();
        chapter=intent.getStringExtra("chapter");
        book_id =intent.getStringExtra("book_id");
        Button bb = (Button)findViewById(R.id.button_add_to_liked_books);
        bb.setVisibility(View.GONE);
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        final String query = "SELECT number,_id FROM tasks WHERE chapter="+chapter+" order by number ";
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("query",response);
                                changeData(response);

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

        lv = (ListView)findViewById(R.id.lvk);
        sa = new SimpleAdapter(this, dataSA, R.layout.item,
                new String[]{"number"}, new int[]{R.id.item});
        lv.setAdapter(sa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = (String) ((HashMap) lv.getItemAtPosition(i)).get("id");
                Intent intent = new Intent(getBaseContext(), Solution.class);
                intent.putExtra("id",str);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_of_task, menu);
        this.menu1 = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_task:
                Intent intent=new Intent(getBaseContext(),AddTask.class);
                intent.putExtra("chapter",chapter);
                intent.putExtra("book_id",book_id);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void changeData(String response){

        try {
            JSONObject result = new JSONObject(response);
            if(result.getInt("success")==1){
                JSONArray array = result.getJSONArray("products");
                Log.d("mylogs",array.toString());
                for(int i = 0; i<array.length();i++){
                    HashMap<String,String> book = new HashMap<>();
                    JSONArray information = array.getJSONArray(i);
                    book.put("number",chapter+"."+information.getString(0));
                    book.put("id",information.getString(1));
                    dataSA.add(book);
                }
                sa.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
