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
                String log = login.getText().toString();
                String pass = password.getText().toString();

                Cursor c = db.query("users", null, "login=? and password=?",new String[]{log,pass},null,null,null);
                if(c.moveToFirst()){
                    long id = c.getLong(c.getColumnIndex("_id"));

                    sharedPref = getSharedPreferences("Username",getBaseContext().MODE_PRIVATE);
                    SharedPreferences.Editor et = sharedPref.edit();
                    et.putString("username",log);
                    et.putString("id",id+"");
                    et.commit();

                    Intent i = new Intent(getBaseContext(),MainPage.class);
                    i.putExtra("id",id);
                    i.putExtra("login",log);
                    startActivity(i);
                    finish();

                }else{
                    login.setText("");
                    password.setText("");
                    Toast.makeText(getBaseContext(),"Incorrect login or password!",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}