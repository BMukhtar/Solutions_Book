package mukhtar.exapple.com.solutions_book;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationPage extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;

    EditText etName;
    EditText etSurname;
    EditText etPassword;
    EditText conPassword;
    EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        etName = (EditText) findViewById(R.id.name);
        etSurname = (EditText) findViewById(R.id.surname);
        etPassword = (EditText) findViewById(R.id.password);
        conPassword = (EditText) findViewById(R.id.confirm);
        etUsername = (EditText) findViewById(R.id.username);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
    }
    public void registrate(View v){
        if(etName.getText().toString().isEmpty()){
            Toast.makeText(this,"Name is empty, please enter your name",Toast.LENGTH_SHORT);return;
        }
        else if(etSurname.getText().toString().isEmpty()){
            Toast.makeText(this,"Surname is empty, please enter your surname",Toast.LENGTH_SHORT);return;
        }
        else if(etUsername.getText().toString().isEmpty()){
            Toast.makeText(this,"Username can not be empty",Toast.LENGTH_SHORT);return;
        }
        else if(etPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"password can not be empty",Toast.LENGTH_SHORT);return;
        }
        else if(!etPassword.getText().toString().equals(conPassword.getText().toString())||conPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"Please chek your password",Toast.LENGTH_SHORT);return;
        }
        ContentValues cv = new ContentValues();
        cv.put("surname",etSurname.getText().toString());
        cv.put("name",etName.getText().toString());
        db.insert("information",null,cv);
        cv.clear();
        cv.put("login",etUsername.getText().toString());
        cv.put("password",etPassword.getText().toString());
        db.insert("users",null,cv);
        cv.clear();
        Intent i = new Intent(this,MainPage.class);
        startActivity(i);

    }
}
