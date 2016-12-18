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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;

public class Solution extends AppCompatActivity {
    String response = "{\"success\":\"1\",\"data\":[\n" +
            "{\"id\":1,\"login\":\"login1\",\"text\":\"There is some text , the solution , I steel don't have connection to database,fuck.But I should keep work.Fuck.\"},\n" +
            "{\"id\":2,\"login\":\"login2\",\"text\":\"There is some text , the solution , I steel don't have connection to database,fuck.But I should keep work.Fuck.\"},\n" +
            "{\"id\":3,\"login\":\"login3\",\"text\":\"There is some text , the solution , I steel don't have connection to database,fuck.But I should keep work.Fuck.\"},\n" +
            "{\"id\":4,\"login\":\"login4\",\"text\":\"There is some text , the solution , I steel don't have connection to database,fuck.But I should keep work.Fuck.\"}\n" +
            "]\n" +
            "}";

    Menu menu1;
    String chapter,number,given;
    TextView tvGiven;
    ListView lv;
    ArrayList<Map<String, String>> data = new ArrayList();
    SimpleAdapter sa;
    ArrayList<Map<String, String>> dataSA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        JSONObject res = null;
        tvGiven=(TextView)findViewById(R.id.given);
        lv=(ListView)findViewById(R.id.lv);
        Intent intent = getIntent();
        chapter=intent.getStringExtra("chapter");
        number=intent.getStringExtra("number");
        given="There is should be text which we get from database. But I don't have any text from so \n " +
                "There is should be text which we get from database. But I don't have any text from so \n" +
                "There is should be text which we get from database. But I don't have any text from so ";
        tvGiven.setText(given);

        try {
            res = new JSONObject(response);

            int o = res.getInt("success");
            String text = "";
            if (o == 1) {
                JSONArray args = res.getJSONArray("data");
                dataSA = new ArrayList();
                for (int i = 0; i < args.length(); i++) {
                    Map<String, String> map = new HashMap();
                    map.put("login",((JSONObject)args.get(i)).getString("login"));
                    map.put("text", ((JSONObject)args.get(i)).getString("text"));
                    dataSA.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}
