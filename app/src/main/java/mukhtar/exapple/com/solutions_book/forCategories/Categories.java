package mukhtar.exapple.com.solutions_book.forCategories;

import android.app.ProgressDialog;
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
    //for temporary part:
    Button add;
    Button show;
    Button clear;
    TextView result;
    EditText ed;

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

        // temporary part:

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View addView  = layoutInflater.inflate(R.layout.activity_test,null,false);
        add = (Button) addView.findViewById(R.id.add_cat);
        show =(Button) addView.findViewById(R.id.show_cat);
        clear = (Button) addView.findViewById(R.id.clear_categories);
        clear.setOnClickListener(temporary);
        add.setOnClickListener(temporary);
        show.setOnClickListener(temporary);
        ed = (EditText) addView.findViewById(R.id.editText);
        result = (TextView) addView.findViewById(R.id.result_of_query);

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_categories);
        ll.addView(addView,0);




        // main part:

        String url1 = "https://arcane-peak-68343.herokuapp.com/for_result.php";
        final String query1 = "SELECT * FROM categories;";

        lv = (ExpandableListView) findViewById(R.id.elv_for_categories);
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
                                try {
                                    JSONObject res = new JSONObject(response);
                                    getAdapter(res);
                                    if (adapter != null)
                                        lv.setAdapter(adapter);
                                    else{
                                        Toast.makeText(Categories.this, "interrupted due to time out Connection", Toast.LENGTH_SHORT).show();
                                    }
                                    if(pDialog.isShowing()){
                                        pDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
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
    }

    private void getAdapter(JSONObject res) {
        try {
            int o = res.getInt("success");
            String text = "";
            if (o == 1) {
                JSONArray args = res.getJSONArray("products");
                for (int i = 0; i < args.length(); i++) {
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
                    current_child_hashmap.put(CHILD_ID, child_id);
                    current_child_hashmap.put(CHILD_CATEGORY_NAME, child_name);
                    childDataItem.add(current_child_hashmap);
                }
                childData.add(childDataItem);

                String groupFrom[] = new String[]{GROUP_CATEGORY_NAME};
                int groupTo[] = new int[]{R.id.name_of_category};
                String childFrom[] = new String[]{CHILD_ID, CHILD_CATEGORY_NAME};
                int childTo[] = new int[]{R.id.ID, R.id.name_of_category};
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

    private View.OnClickListener temporary = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_cat:
                /*String line = ed.getText().toString();
                int id = Integer.parseInt(line.split(" ")[1]);
                String pcatName = line.split(" ")[2];
                String catName = line.split(" ")[0];

                String url = "https://arcane-peak-68343.herokuapp.com/for_query.php";
                String query = "INSERT INTO categories VALUES (DEFAULT,'"+catName+"',"+id+",'"+pcatName+"');";
                String method = "POST";
                DatabaseInteraction add = new DatabaseInteraction(this);
                add.execute(url,method,query);
                try {
                    res = add.get();
                    result.setText("success res"+res.getInt("success"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                    break;
                case R.id.show_cat:
                /*String url1 = "https://arcane-peak-68343.herokuapp.com/for_result.php";
                String query1 = "SELECT * FROM categories;";
                String method1 = "POST";
                DatabaseInteraction add1 = new DatabaseInteraction(this);
                add1.execute(url1,method1,query1);
                try {
                    res = add1.get();
                    JSONArray args = res.getJSONArray("products");
                    int o = res.getInt("success");
                    String text = "";
                    for(int i = 0; i<args.length();i++){
                        text+=((JSONArray)args.get(i)).getString(0);
                        text+=((JSONArray)args.get(i)).getString(1);
                        text+=((JSONArray)args.get(i)).getString(2);
                        text+=((JSONArray)args.get(i)).getString(3)+"\n";
                    }
                    result.setText(text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                    break;
                case R.id.clear_categories:
                /*String url2 = "https://arcane-peak-68343.herokuapp.com/for_query.php";
                String query2 = "DELETE FROM categories;";
                String method2 = "POST";
                DatabaseInteraction add2 = new DatabaseInteraction(this);
                add2.execute(url2,method2,query2);
                try{
                    res = add2.get();
                    int o = res.getInt("success");
                    Log.d("mylogs",o+" - success");

                }catch (Exception e){
                    Log.d("mylogs","delete canceled");
                }*/

                    break;
            }
        }
    };
}
