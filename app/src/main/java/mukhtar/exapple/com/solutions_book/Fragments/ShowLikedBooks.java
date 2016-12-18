package mukhtar.exapple.com.solutions_book.Fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import static mukhtar.exapple.com.solutions_book.BooksAppereance.MyBooks.id_of_frame_layout;


public class ShowLikedBooks extends Fragment {
    ListView lv;
    SimpleAdapter sa;
    ArrayList<Map<String, String>> data = new ArrayList();
    String current_id;
    final int MENU_DELETE = 2;
    int user_id;
    SharedPreferences sharedPreferences;
    Activity act;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = getActivity();
        sharedPreferences = act.getSharedPreferences("Username", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt("id",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_my_books, container, false);
        lv = (ListView)view.findViewById(R.id.listview_my_books);

        registerForContextMenu(lv);
        sa = new SimpleAdapter(getActivity(), data, R.layout.item_my_book,
                new String[]{"name", "author","link"}, new int[]{R.id.textview_name_of_book, R.id.textview_author_of_book,
                R.id.textview_link_of_book});
        lv.setAdapter(sa);
        update_data();

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo ad=(AdapterView.AdapterContextMenuInfo)menuInfo;
        HashMap<String,String> inf = (HashMap)lv.getItemAtPosition(ad.position);
        current_id=inf.get("id");
        menu.add(0, MENU_DELETE, 0, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_DELETE:
                delete_data();
                break;
        }
        return super.onContextItemSelected(item);
    }

    void update_data(){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        final String query = "SELECT * FROM books WHERE _id IN(SELECT book_id FROM user_liked_books WHERE user_id = '"+user_id+"')";
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

    void delete_data(){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php";
        final String query = "DELETE FROM user_liked_books WHERE _id = "+current_id+"";
        RequestQueue queue = Volley.newRequestQueue(act);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                update_data();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(act,"Can not delete books",Toast.LENGTH_SHORT).show();
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
