package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import java.sql.Blob;
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
    Image img ;
    Drawable draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        int imgint = getResources().getIdentifier("mukhtar.exapple.com.solutions_book.exercises:drawable/b", null, null);
        JSONObject res = null;
        //draw = getResources().getDrawable(imgint);
        tvGiven=(TextView)findViewById(R.id.given);
        lv=(ListView)findViewById(R.id.lv);
        Intent intent = getIntent();
        task_id=intent.getStringExtra("id");
        final String query = "SELECT data FROM tasks where _id='"+task_id+"'";
        final String querySolutions = "SELECT user_id,data,image FROM solutions where task_id='"+task_id+"'";
        getResponse(query,"task");
        getResponse(querySolutions,"solution");


        //tvGiven.setText(given);

        sa = new SimpleAdapter(this, dataSA, R.layout.item_solution,
                new String[]{"data","user_id","image"}, new int[]{R.id.item_solution_text,R.id.item_solution_login,R.id.imageViewTask});
        sa.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                switch (view.getId()){
                    case R.id.item_solution_text:
                        String str=(String)o;
                        ((TextView)view).setText(str);
                        break;
                    case R.id.item_solution_login:
                        String stl=(String)o;
                        Log.d("mylogs",(String)o);
                        ((TextView)view).setText(stl);
                        break;
                    case R.id.imageViewTask:
                        //BitmapDrawable btm = (BitmapDrawable) draw;
                        //.setImageBitmap(btm.getBitmap());
                        String l=(String)o;
                        Bitmap bm=  StringToBitMap(l);
                        ((ImageView)view).setImageBitmap(bm);
                        break;
                }
                return false;
            }
        });
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
                intent.putExtra("task_id",task_id);
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
                    tvGiven.setText(array.getString(0));
                    //Log.d("mylogs",given);
                }
                else if(type.equals("solution")){
                for(int i = 0; i<array.length();i++){
                    HashMap<String,String> book = new HashMap<>();
                    JSONArray information = array.getJSONArray(i);
                    book.put("user_id",information.getString(0));
                    book.put("data",information.getString(1));
                    book.put("image",information.getString(2));
                    Log.d("bit",information.getString(2));
                    dataSA.add(book);
                }}
                sa.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    void getResponse(final String query,final String type){
        String url="";
        if(type.equals("task")){
        url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_user_id.php";}
        else url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
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
    public Bitmap StringToBitMap(String encodedString){
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
    }

}
