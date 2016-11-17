package mukhtar.exapple.com.solutions_book;

import android.content.ContentValues;
import android.content.Intent;
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
    DBHelper helper;
    SQLiteDatabase db;
    Button sign,registr;
    EditText login,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        sign = (Button) findViewById(R.id.sign);
        registr = (Button) findViewById(R.id.registr);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        sign.setOnClickListener(this);
        registr.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registr:
                Intent in = new Intent(getBaseContext(),RegistrationPage.class);
                startActivity(in);
            case R.id.sign:
                String log = login.getText().toString();
                String pass = password.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("login","zhorik");
                cv.put("password","123");
                db.insert("users",null,cv);
                cv.clear();
                Cursor c = db.query("users", null, "login=? and password=?",new String[]{log,pass},null,null,null);
                if(c.moveToFirst()){
                    long id = c.getLong(c.getColumnIndex("_id"));
                    Intent i = new Intent(getBaseContext(),MainPage.class);
                    i.putExtra("id",id);
                    i.putExtra("login",log);
                    startActivity(i);
                }else{
                    login.setText("");
                    password.setText("");
                    Toast.makeText(getBaseContext(),"Incorrect login or password!",Toast.LENGTH_SHORT).show();
                }

        }
    }
}