package mukhtar.exapple.com.solutions_book;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button sign,registr;
    EditText login,password;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("Username",this.MODE_PRIVATE);
        String loginn = sharedPref.getString("username","");
        String id = sharedPref.getString("id","");
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        if(loginn!=""){
            Intent in = new Intent(this,MainPage.class);
            startActivity(in);
            finish();
        }
        else{
            sign = (Button) findViewById(R.id.sign);
            registr = (Button) findViewById(R.id.registr);
            login = (EditText) findViewById(R.id.login);
            password = (EditText) findViewById(R.id.password);



            sign.setOnClickListener(this);
            registr.setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registr:
                Intent in = new Intent(getBaseContext(),RegistrationPage.class);
                startActivity(in);
                break;
            case R.id.sign:
                final String log = login.getText().toString();
                final String pass = password.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest request =
                        new StringRequest(
                                Request.Method.POST,
                                "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response){
                                        try {
                                            JSONObject res = new JSONObject(response);
                                            JSONArray args = res.getJSONArray("products");
                                            String logS = "";
                                            String passS="";
                                            for (int i = 0; i < args.length(); i++) {
                                                logS = ((JSONArray) args.get(i)).getString(0);
                                                passS = ((JSONArray) args.get(i)).getString(1);
                                            }

                                            if(log.equals(logS) && pass.equals(passS)){
                                                sharedPref = getSharedPreferences("Username",getBaseContext().MODE_PRIVATE);
                                                SharedPreferences.Editor et = sharedPref.edit();
                                                et.putString("username",log);
                                                et.commit();

                                                Intent i = new Intent(getBaseContext(),MainPage.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(getBaseContext(),"Incorrect login or password",Toast.LENGTH_SHORT).show();
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
                                map.put("query", "SELECT username, password FROM users WHERE name='"+log+"'");


                                return map;
                            }
                        };
                request.setTag("POST");
                queue.add(request);

                break;

        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


}