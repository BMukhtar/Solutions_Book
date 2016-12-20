package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class Chapters extends AppCompatActivity {



    ListView lv;
    Menu menu1;
    SimpleAdapter sa;
    public static ArrayList<Map<String, String>> dataSA=new ArrayList<>();
    String book_id;
    public static ArrayList<Integer>  chapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Intent intent =getIntent();
        book_id = intent.getStringExtra("book_id");

        lv = (ListView) findViewById(R.id.lvk);
        sa = new SimpleAdapter(this, dataSA, R.layout.item,
                new String[]{"chapter"}, new int[]{R.id.item});
        lv.setAdapter(sa);
        sa.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TextView tv= (TextView) adapterView.getItemAtPosition(i);
                //String str=tv.getText().toString();
                String str = (String) ((HashMap) lv.getItemAtPosition(i)).get("chapter");
                str=str.split("\\.")[1];

                Log.d("chapter",str);
                Intent intent = new Intent(getBaseContext(), Tasks.class);
                intent.putExtra("chapter",str);
                intent.putExtra("book_id",book_id);
                startActivity(intent);
            }
        });
    }

    public void addToLikedBooks(View v){
        v.setVisibility(View.GONE);
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        int user_id = getSharedPreferences("Username",MODE_PRIVATE).getInt("id",0);
        final String query_check  = "SELECT * FROM user_liked_books WHERE user_id="+user_id+" and book_id="+book_id+"";


        for_query(query_check,url,true);
    }
    void addToLiked(){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php";
        int user_id = getSharedPreferences("Username",MODE_PRIVATE).getInt("id",0);
        final String query = "INSERT INTO user_liked_books VALUES ("+user_id+","+book_id+")";
        for_query(query,url,false);
    }


    public void for_query(final String query, String url, final boolean is_check){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                try {
                                    JSONObject j = new JSONObject(response);
                                    Log.d("mylogs",response);
                                    if(j.getInt("success")==1){
                                        if(is_check){
                                            if(!j.isNull("products")){
                                                Toast.makeText(getBaseContext(),"your book exists as liked "+response,Toast.LENGTH_SHORT).show();
                                            }else{
                                                addToLiked();
                                            }
                                        }

                                        else
                                            Toast.makeText(getBaseContext(),"book added "+response,Toast.LENGTH_SHORT).show();

                                    }else{
                                        if(is_check){
                                            addToLiked();
                                        }else{
                                            Toast.makeText(getBaseContext(),"book not added "+response,Toast.LENGTH_SHORT).show();
                                        }


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



    @Override
    protected void onResume() {
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_user_id.php";
        final String query = "SELECT chapters FROM books WHERE _id="+book_id;
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("mylogs",response);
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
        chapters = new ArrayList();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu1 = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_chapter:
                Intent intent =new Intent(getBaseContext(),AddChapter.class);
                intent.putExtra("book_id",book_id);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeData(String response){

        try {
            JSONObject res = new JSONObject(response);
            String ja=res.getJSONArray("products").getString(0);
            JSONArray jarr = new JSONObject(ja).getJSONArray("chapters");
            Log.d( "mylogs",response);
            Log.d("mylogs","chapters"+jarr.toString());
            int o = res.getInt("success");
            String text = "";

            if (o == 1) {
                //JSONArray args = res.getJSONArray("data");
                dataSA.clear();
                for (int i = 0; i < jarr.length(); i++) {
                    Map<String, String> map = new HashMap();
                    map.put("chapter", "Chapter."+jarr.getInt(i));
                    Log.d("mylogs",jarr.getInt(i)+"");
                    dataSA.add(map);
                    chapters.add(jarr.getInt(i));
                }
                sa.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
