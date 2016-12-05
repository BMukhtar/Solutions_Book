package mukhtar.exapple.com.solutions_book.BooksAppereance;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.zip.Inflater;

import mukhtar.exapple.com.solutions_book.R;

public class BooksResult extends AppCompatActivity {
    EditText search;
    Button button_search;
    RadioGroup rg;
    String radioResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_result);
        String query = getIntent().getStringExtra("id_of_category");
        ListView lv = (ListView) findViewById(R.id.book_result_lv);
        if (query == "search") {
            LayoutInflater inflater = (this).getLayoutInflater();
            View view = inflater.inflate(R.layout.item_search, null, false);
            search = (EditText) view.findViewById(R.id.edittext_search_book);
            button_search = (Button) view.findViewById(R.id.button_search_books);
            rg = (RadioGroup) findViewById(R.id.radio_group_by);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch(rg.getCheckedRadioButtonId()){
                        case R.id.radio_book_by_name:
                            radioResult = "name";
                            break;
                        case R.id.radio_book_by_author:
                            radioResult = "author";
                            break;
                    }
                    Toast.makeText(getBaseContext(), "changed to by "+radioResult, Toast.LENGTH_SHORT).show();
                }
            });

        }else{

        }



    }
}
