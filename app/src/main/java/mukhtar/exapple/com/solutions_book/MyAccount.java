package mukhtar.exapple.com.solutions_book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import mukhtar.exapple.com.solutions_book.DBHelper;
import mukhtar.exapple.com.solutions_book.Login;
import mukhtar.exapple.com.solutions_book.R;

public class MyAccount extends AppCompatActivity {
    SQLiteDatabase db;
    DBHelper dbHelper;
    SharedPreferences sharedPref;
    EditText etName;
    EditText etSurname;
    EditText etPas1;
    EditText etPas2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        etName = (EditText) findViewById(R.id.nametext);
        etSurname = (EditText) findViewById(R.id.surnametext);
        etPas1 = (EditText) findViewById(R.id.changetext1);
        etPas2 = (EditText) findViewById(R.id.changetext2);
        sharedPref = getSharedPreferences("Username",this.MODE_PRIVATE);
        etName.setText(sharedPref.getString("name",""));
        etSurname.setText(sharedPref.getString("surname",""));
        etName.setEnabled(false);
        etSurname.setEnabled(false);
        etPas1.setVisibility(View.INVISIBLE);
        etPas2.setVisibility(View.INVISIBLE);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.changeName:
                    etName.setEnabled(true);break;
                case R.id.changeSurname:
                    etSurname.setEnabled(true);break;
                case R.id.upload:
                    break;
                case R.id.changePas:
                    etPas1.setVisibility(View.VISIBLE);
                    etPas2.setVisibility(View.VISIBLE);
                    break;


            }
        }
    };


}
