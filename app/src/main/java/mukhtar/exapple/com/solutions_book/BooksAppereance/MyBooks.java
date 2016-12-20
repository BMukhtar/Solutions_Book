package mukhtar.exapple.com.solutions_book.BooksAppereance;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.RadioGroup;


import mukhtar.exapple.com.solutions_book.Fragments.AddBook;
import mukhtar.exapple.com.solutions_book.Fragments.ShowLikedBooks;
import mukhtar.exapple.com.solutions_book.Fragments.ShowMyBooks;
import mukhtar.exapple.com.solutions_book.R;

public class MyBooks extends AppCompatActivity {

    public static String current_id;
    public static String current_name;
    public static String current_author;
    public static String current_link;
    RadioGroup rg;
    public static FragmentTransaction fTrans;
    AddBook fragment_add_book;
    ShowMyBooks fragment_show_my_books;
    ShowLikedBooks fragment_show_liked_books;
    public static int id_of_frame_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        id_of_frame_layout = R.id.frame_layout_my_books;
        rg = (RadioGroup) findViewById(R.id.radio_group_show_my_books);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fTrans = getFragmentManager().beginTransaction();
                switch(checkedId){
                    case R.id.radio_button_add_new_book:
                        fragment_add_book = new AddBook();
                        fTrans.replace(id_of_frame_layout, fragment_add_book);
                        break;
                    case R.id.radio_button_choose_my_books:
                        fragment_show_my_books = new ShowMyBooks();
                        fTrans.replace(id_of_frame_layout, fragment_show_my_books);
                        break;
                    case R.id.radio_button_choose_liked_books:
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
