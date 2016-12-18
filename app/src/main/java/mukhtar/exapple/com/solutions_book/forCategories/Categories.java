package mukhtar.exapple.com.solutions_book.forCategories;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class Categories extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    //for main part:

    ProgressDialog pDialog;
    final String GROUP_CATEGORY_NAME = "groupName";
    final String CHILD_ID = "childId";
    final String GROUP_ID = "groupId";
    final String CHILD_CATEGORY_NAME = "childName";
    HashMap<String, String> current_child_hashmap = new HashMap<>();
    HashMap<String, String> current_group_hashmap = new HashMap<>();
    ArrayList<Map<String, String>> groupData = new ArrayList<>();
    ArrayList<Map<String, String>> childDataItem = new ArrayList<>();
    ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();
    ArrayList<String> counting_groups = new ArrayList<>();
    SimpleExpandableListAdapter adapter = null;
    ExpandableListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        //main part

        lv = (ExpandableListView) findViewById(R.id.elv_for_categories);
        sharedPreferences = getSharedPreferences("Username",MODE_PRIVATE);
        if(sharedPreferences.getString("categories","").equals("")){
            // main part:
            String url1 = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
            final String query1 = "SELECT * FROM categories;";

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Connecting with server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request =
                    new StringRequest(
                            Request.Method.POST,
                            url1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    getAdapter(response);
                                    if (adapter != null)
                                        lv.setAdapter(adapter);
                                    else{
                                        Toast.makeText(Categories.this, "interrupted due to time out Connection", Toast.LENGTH_SHORT).show();
                                    }
                                    if(pDialog.isShowing()){
                                        pDialog.dismiss();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(Categories.this, getResources().getString(R.string.volley_error), Toast.LENGTH_SHORT).show();
                                    if(pDialog.isShowing()){
                                        pDialog.dismiss();
                                    }
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap();
                            map.put("query", query1);
                            return map;
                        }
                    };
            request.setTag("POST");
            request.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            Log.d("mylogs",DefaultRetryPolicy.DEFAULT_MAX_RETRIES+" "+DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            queue.add(request);
        }else{
            getAdapter(sharedPreferences.getString("categories",""));
            lv.setAdapter(adapter);
        }





    }

    private void getAdapter(String response) {
        try {
            JSONObject res = new JSONObject(response);
            Log.d("mylogs",response);
            int o = res.getInt("success");
            if (o == 1) {
                JSONArray args = res.getJSONArray("products");
                for (int i = args.length()-1; i >= 0; i--) {
                    String child_id = ((JSONArray) args.get(i)).getString(0);
                    String child_name = ((JSONArray) args.get(i)).getString(1);
                    String group_id = ((JSONArray) args.get(i)).getString(2);
                    String group_name = ((JSONArray) args.get(i)).getString(3);
                    if (!counting_groups.contains(group_id) && !counting_groups.isEmpty()) {
                        counting_groups.add(group_id);
                        childData.add(childDataItem);
                        childDataItem = new ArrayList<>();

                        current_group_hashmap = new HashMap<>();
                        current_group_hashmap.put(GROUP_ID, group_id);
                        current_group_hashmap.put(GROUP_CATEGORY_NAME, group_name);
                        groupData.add(current_group_hashmap);
                    } else if (!counting_groups.contains(group_id) && counting_groups.isEmpty()) {
                        counting_groups.add(group_id);
                        current_group_hashmap = new HashMap<>();
                        current_group_hashmap.put(GROUP_ID, group_id);
                        current_group_hashmap.put(GROUP_CATEGORY_NAME, group_name);
                        groupData.add(current_group_hashmap);
                    }

                    current_child_hashmap = new HashMap<>();
                    current_child_hashmap.put(CHILD_CATEGORY_NAME, child_name);
                    childDataItem.add(current_child_hashmap);
                }
                childData.add(childDataItem);

                String groupFrom[] = new String[]{GROUP_CATEGORY_NAME};
                int groupTo[] = new int[]{R.id.name_of_category};
                String childFrom[] = new String[]{CHILD_CATEGORY_NAME};
                int childTo[] = new int[]{R.id.name_of_category};
                adapter = new SimpleExpandableListAdapter(
                        Categories.this,
                        groupData,
                        R.layout.item_of_category,
                        groupFrom,
                        groupTo,
                        childData,
                        R.layout.item_of_category,
                        childFrom,
                        childTo);
            } else {
                Toast.makeText(Categories.this, "Can not load result from server", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
