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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;

import static mukhtar.exapple.com.solutions_book.R.id.lv;

public class Tasks extends AppCompatActivity {
    String response = "{\"success\":\"1\",\"data\":[\n" +
            "{\"id\":1,\"number\":\"2\"},\n" +
            "{\"id\":2,\"number\":\"4\"},\n" +
            "{\"id\":3,\"number\":\"10\"},\n" +
            "{\"id\":4,\"number\":\"4\"}\n" +
            "]\n" + "}";


    ListView lv;
    Menu menu1;
    ArrayAdapter aa;
    ArrayList<String> data;

    SimpleAdapter sa;
    ArrayList<Map<String, String>> dataSA;
    String chapter="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Intent intent=getIntent();
        chapter=intent.getStringExtra("chapter");


        lv = (ListView)findViewById(R.id.lv);
        JSONObject res = null;
        try {
            res = new JSONObject(response);

            int o = res.getInt("success");
            String text = "";
            if (o == 1) {
                JSONArray args = res.getJSONArray("data");
                dataSA = new ArrayList();
                for (int i = 0; i < args.length(); i++) {
                    Map<String, String> map = new HashMap();
                    map.put("number", chapter+"."+((JSONObject)args.get(i)).getString("number"));
                    dataSA.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sa = new SimpleAdapter(this, dataSA, R.layout.item,
                new String[]{"number"}, new int[]{R.id.item});
        lv.setAdapter(sa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = (String) ((HashMap) lv.getItemAtPosition(i)).get("number");
                Intent intent = new Intent(getBaseContext(), Solution.class);
                intent.putExtra("chapter",str.split("\\.")[0]);
                intent.putExtra("number",str.split("\\.")[1]);
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
