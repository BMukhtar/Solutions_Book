package mukhtar.exapple.com.solutions_book.MyTasks;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import mukhtar.exapple.com.solutions_book.Fragments.AddBook;
import mukhtar.exapple.com.solutions_book.Fragments.ShowLikedBooks;
import mukhtar.exapple.com.solutions_book.Fragments.ShowMyBooks;
import mukhtar.exapple.com.solutions_book.R;

public class MyTasks extends AppCompatActivity {

    RadioGroup rg;
    FragmentTransaction fTrans;

    ShowMyBooks fragment_show_my_books;
    ShowLikedBooks fragment_show_liked_books;
    SharedPreferences sharedPref;
    public static int id_of_frame_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        sharedPref = getSharedPreferences("Username",this.MODE_PRIVATE);
        TextView userName = (TextView) findViewById(R.id.textview_username);
        userName.setText(sharedPref.getString("username",""));

        id_of_frame_layout = R.id.frame_layout_my_tasks;
        rg = (RadioGroup) findViewById(R.id.radio_group_show_my_books);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fTrans = getFragmentManager().beginTransaction();
                switch(checkedId){

                    case R.id.radio_button_choose_my_tasks:
                        fragment_show_my_books = new ShowMyBooks();
                        fTrans.replace(id_of_frame_layout, fragment_show_my_books);
                        break;
                    case R.id.radio_button_choose_liked_tasks:
                        fragment_show_liked_books = new ShowLikedBooks();
                        fTrans.replace(id_of_frame_layout, fragment_show_liked_books);
                        break;
                }
                fTrans.commit();
            }
        });
        rg.check(R.id.radio_button_choose_my_books);
    }
}
