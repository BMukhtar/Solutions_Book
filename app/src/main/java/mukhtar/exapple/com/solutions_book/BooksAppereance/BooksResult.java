package mukhtar.exapple.com.solutions_book.BooksAppereance;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
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
import mukhtar.exapple.com.solutions_book.exercises.Chapters;

public class BooksResult extends AppCompatActivity {
    EditText search;
    Button button_search;
    RadioGroup rg;
    String radioResult = null;
    Activity act;
    SimpleAdapter sa;
    ArrayList<Map<String, String>> data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_result);
        String query = getIntent().getStringExtra("id_of_category");
        ListView lv = (ListView) findViewById(R.id.book_result_lv);
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_books_result);
        act=this;
        sa = new SimpleAdapter(act, data, R.layout.item_my_book,
                new String[]{"name", "author"}, new int[]{R.id.textview_name_of_book, R.id.textview_author_of_book,
        });
        lv.setAdapter(sa);
        if (query.equals("search")) {
            LayoutInflater inflater = (this).getLayoutInflater();
            View view = inflater.inflate(R.layout.item_search, null, false);
            search = (EditText) view.findViewById(R.id.edittext_search_book);
            button_search = (Button) view.findViewById(R.id.button_search_books);
            rg = (RadioGroup) view.findViewById(R.id.radio_group_by);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch(rg.getCheckedRadioButtonId()){
                        case R.id.radio_book_by_name:
                            radioResult = "name";
                            break;
                        case R.id.radio_book_by_author:
                            radioResult = "author";
                            break;
                    }
                    Toast.makeText(getBaseContext(), "changed to by "+radioResult, Toast.LENGTH_SHORT).show();
                }
            });
            rg.check(R.id.radio_book_by_name);
            ll.addView(view,0);


        }else {
            update_data_category(query);
        }
    }

    View.OnClickListener button_search_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String text = search.getText().toString();
            String query = "SELECT * FROM books WHERE "+radioResult+" LIKE";
        }
    };

    void update_data_category(String cat){
        Log.d("mylogs",cat);
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        final String query = "SELECT * FROM books WHERE category_id='"+cat+"'";
        RequestQueue queue = Volley.newRequestQueue(act);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("mylogs",response);
                                set_data(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(act,"Can not load books",Toast.LENGTH_SHORT).show();
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

    void update_data_search(String cat){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        final String query = "SELECT * FROM books WHERE cat_id='"+cat+"'";
        RequestQueue queue = Volley.newRequestQueue(act);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("mylogs",response);
                                set_data(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(act,"Can not load books",Toast.LENGTH_SHORT).show();
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
    void set_data(String response){
        try {
            JSONObject result = new JSONObject(response);
            if(result.getInt("success")==1){
                JSONArray array = result.getJSONArray("products");
                for(int i = 0; i<array.length();i++){
                    HashMap<String,String> book = new HashMap<>();
                    JSONArray information = array.getJSONArray(i);
                    book.put("id",information.getString(0));
                    book.put("name",information.getString(1));
                    book.put("author",information.getString(2));
                    book.put("link",information.getString(3));
                    data.add(book);
                }
                sa.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
