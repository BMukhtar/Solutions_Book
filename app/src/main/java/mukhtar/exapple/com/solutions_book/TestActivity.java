package mukhtar.exapple.com.solutions_book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    Button add;
    Button show;
    Button clear;
    TextView result;
    EditText ed;
    JSONObject res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        add = (Button) findViewById(R.id.add_cat);
        show =(Button) findViewById(R.id.show_cat);
        clear = (Button) findViewById(R.id.clear_categories);
        clear.setOnClickListener(this);
        add.setOnClickListener(this);
        show.setOnClickListener(this);
        ed = (EditText) findViewById(R.id.editText);
        result = (TextView) findViewById(R.id.result_of_query);


    }

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
}
