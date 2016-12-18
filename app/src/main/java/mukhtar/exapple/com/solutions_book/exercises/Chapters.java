package mukhtar.exapple.com.solutions_book.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;

public class Chapters extends AppCompatActivity {

    String response = "{\"success\":\"1\",\"data\":[\n" +
            "{\"id\":1,\"chapter\":\"1\"},\n" +
            "{\"id\":2,\"chapter\":\"2\"},\n" +
            "{\"id\":3,\"chapter\":\"5\"},\n" +
            "{\"id\":4,\"chapter\":\"10\"}\n" +
            "]\n" +
            "}";


    ListView lv;
    Menu menu1;
    SimpleAdapter sa;
    ArrayList<Map<String, String>> dataSA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        JSONObject res = null;
        lv = (ListView) findViewById(R.id.lv);


        try {
            res = new JSONObject(response);
            Log.d( "mylogs",response);
            int o = res.getInt("success");
            String text = "";
            if (o == 1) {
                JSONArray args = res.getJSONArray("data");
                dataSA = new ArrayList();
                for (int i = 0; i < args.length(); i++) {
                    Map<String, String> map = new HashMap();
                    //map.put("number","."+((JSONObject)args.get(i)).getString("number"));
                    //map.put("id", ((JSONObject)args.get(i)).getString("id"));
                    map.put("chapter", "Chapter."+((JSONObject)args.get(i)).getString("chapter"));
                    // map.put("chapter", "Item" + i);
                    //map.put("id",i+"");
                    dataSA.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sa = new SimpleAdapter(this, dataSA, R.layout.item,
                new String[]{"chapter"}, new int[]{R.id.item});
        lv.setAdapter(sa);
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
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu1 = menu;
        return super.onCreateOptionsMenu(menu);
    }
}
