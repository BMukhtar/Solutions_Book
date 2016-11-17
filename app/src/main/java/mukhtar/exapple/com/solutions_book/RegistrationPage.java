package mukhtar.exapple.com.solutions_book;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationPage extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button reg;
    EditText etName;
    EditText etSurname;
    EditText etPassword;
    EditText conPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        etName = (EditText) findViewById(R.id.name);
        etSurname = (EditText) findViewById(R.id.surname);
        etPassword = (EditText) findViewById(R.id.password);
        conPassword = (EditText) findViewById(R.id.confirm);
        reg = (Button) findViewById(R.id.registr);
        db = dbHelper.getWritableDatabase();
    }
    public void registrate(){
        if(etName.getText().toString().isEmpty()){
            Toast.makeText(this,"Name is empty, please enter your name",Toast.LENGTH_SHORT);
        }
        else if(etSurname.getText().toString().isEmpty()){
            Toast.makeText(this,"Surname is empty, please enter your surname",Toast.LENGTH_SHORT);
        }
        else if(etPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"password can not be empty",Toast.LENGTH_SHORT);
        }
        else if(!etPassword.getText().toString().equals(conPassword.getText().toString())||conPassword.getText().toString().isEmpty()){
            Toast.makeText(this,"Please chek your password",Toast.LENGTH_SHORT);
        }
        ContentValues cv = new ContentValues();
        cv.put("surname",etSurname.getText().toString());
        cv.put("name",etName.getText().toString());
        db.insert("information",null,cv);
        cv.clear();
        
    }
}
