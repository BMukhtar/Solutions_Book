package mukhtar.exapple.com.solutions_book;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationPage extends AppCompatActivity {

    SharedPreferences sharedPref;
    EditText etName;
    EditText etSurname;
    EditText etPassword;
    EditText conPassword;
    EditText etUsername;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        etName = (EditText) findViewById(R.id.name);
        etSurname = (EditText) findViewById(R.id.surname);
        etPassword = (EditText) findViewById(R.id.password);
        conPassword = (EditText) findViewById(R.id.confirm);
        etUsername = (EditText) findViewById(R.id.username);


    }

    public void registrate(View v){
        if(etName.getText().toString().isEmpty()){
            Toast.makeText(this,"Name is empty, please enter your name",Toast.LENGTH_SHORT).show();return;
        }
        else if(etSurname.getText().toString().isEmpty()){
            Toast.makeText(this,"Surname is empty, please enter your surname",Toast.LENGTH_SHORT).show();return;
        }
        else if(etUsername.getText().toString().isEmpty()){
            Toast.makeText(this,"Username can not be empty",Toast.LENGTH_SHORT).show();return;
        }
        else if(etPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"password can not be empty",Toast.LENGTH_SHORT).show();return;
        }
        else if(!etPassword.getText().toString().equals(conPassword.getText().toString())||conPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"Please chek your password",Toast.LENGTH_SHORT).show();return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                Log.d("mylogs",response);
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
                        map.put("query", "INSERT INTO users(name, surname, username, password, image) VALUES('"+etName.getText().toString()+
                                "', '"+etSurname.getText().toString()+"', '"+etUsername.getText().toString()+"','"+
                                etPassword.getText().toString()+"',"+"'null')");


                        return map;
                    }
                };
        request.setTag("POST");
        queue.add(request);

        sharedPref = getSharedPreferences("Username",getBaseContext().MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString("username", etUsername.getText().toString());
        ed.putString("password", etPassword.getText().toString());
        ed.putString("name",etName.getText().toString());
        ed.putString("surname",etSurname.getText().toString());
        ed.commit();
        Intent i = new Intent(this,MainPage.class);
        startActivity(i);
        Login.a.finish();
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
