package mukhtar.exapple.com.solutions_book;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageView imageView;
    ImageButton upload;
    final int DIALOG_EXIT = 1;
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        etName = (EditText) findViewById(R.id.nametext);
        etSurname = (EditText) findViewById(R.id.surnametext);
        etPas1 = (EditText) findViewById(R.id.changetext1);
        etPas2 = (EditText) findViewById(R.id.changetext2);
        save = (Button) findViewById(R.id.save);
        del = (Button) findViewById(R.id.delete);
        upload = (ImageButton) findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.imageView);
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
        upload.setOnClickListener(onClickListener);
        listView = new ListView(this);

        // Add data to the ListView

        String[] items={"Facebook","Google+","Twitter","Digg"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem,items);

        listView.setAdapter(adapter);

        // Perform action when an item is clicked

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {

                ViewGroup vg=(ViewGroup)view;

                TextView txt=(TextView)vg.findViewById(R.id.txtitem);
                Log.d("mylogs",txt.getText().toString());


            }

        });



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
                    AlertDialog.Builder builder=new
                            AlertDialog.Builder(MyAccount.this);
                    builder.setCancelable(true);
                    builder.setView(listView);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                    break;
                case R.id.changePas:
                    etPas1.setVisibility(View.VISIBLE);
                    etPas2.setVisibility(View.VISIBLE);
                    break;

                case R.id.save:

                    break;
                case R.id.delete:
                    SharedPreferences.Editor ed = sharedPref.edit();


                    ed.putString("username","");
                    ed.commit();

                    Intent intent = new Intent(getBaseContext(),Login.class);
                    startActivity(intent);
                    finish();

            }
        }
    };





}
