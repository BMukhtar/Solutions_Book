package mukhtar.exapple.com.solutions_book;

import android.content.ContentValues;
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
import android.widget.ImageButton;
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
    ImageButton changeName;
    ImageButton changeSurname;
    ImageButton changePas;
    Button save;
    Button del;

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
        save = (Button) findViewById(R.id.save);
        del = (Button) findViewById(R.id.delete);
        sharedPref = getSharedPreferences("Username",this.MODE_PRIVATE);
        etName.setText(sharedPref.getString("name",""));
        etSurname.setText(sharedPref.getString("surname",""));
        etName.setEnabled(false);
        etSurname.setEnabled(false);
        etPas1.setVisibility(View.INVISIBLE);
        etPas2.setVisibility(View.INVISIBLE);
        changeName = (ImageButton) findViewById(R.id.changeName);
        changeSurname = (ImageButton) findViewById(R.id.changeSurname);
        changePas = (ImageButton) findViewById(R.id.changePas);
        changeName.setOnClickListener(onClickListener);
        changeSurname.setOnClickListener(onClickListener);
        changePas.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        del.setOnClickListener(onClickListener);

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
                case R.id.save:
                    if(!etName.getText().toString().equals(sharedPref.getString("name",""))){
                        SharedPreferences.Editor ed = sharedPref.edit();
                        ContentValues cv = new ContentValues();
                        cv.put("name",etName.getText().toString());
                        db.update("information",cv,"name=?",new String[]{sharedPref.getString("name","")});
                        cv.clear();
                        ed.putString("name",etName.getText().toString());
                        ed.commit();
                    }
                    if(!etSurname.getText().toString().equals(sharedPref.getString("surname",""))){
                        SharedPreferences.Editor ed = sharedPref.edit();
                        ContentValues cv = new ContentValues();
                        cv.put("name",etSurname.getText().toString());
                        db.update("information",cv,"surname=?",new String[]{sharedPref.getString("surname","")});
                        cv.clear();
                        ed.putString("surname",etSurname.getText().toString());
                        ed.commit();
                    }
                    if(etPas1.getText().toString().equals(etPas2.getText().toString())&&!etPas1.getText().toString().equals(sharedPref.getString("password",""))){
                        SharedPreferences.Editor ed = sharedPref.edit();
                        ContentValues cv = new ContentValues();
                        cv.put("name",etPas1.getText().toString());
                        db.update("users",cv,"password=?",new String[]{sharedPref.getString("password","")});
                        cv.clear();
                        ed.putString("surname",etPas1.getText().toString());
                        ed.commit();
                        db.close();
                    }
                    break;
                case R.id.delete:
                    SharedPreferences.Editor ed = sharedPref.edit();

                    db.delete("users","login=?",new String[]{sharedPref.getString("username","")});
                    ed.putString("username","");
                    ed.commit();
                    db.close();
                    Intent intent = new Intent(getBaseContext(),Login.class);
                    startActivity(intent);
                    finish();

            }
        }
    };


}
