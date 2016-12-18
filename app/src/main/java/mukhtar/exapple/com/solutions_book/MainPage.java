package mukhtar.exapple.com.solutions_book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mukhtar.exapple.com.solutions_book.BooksAppereance.BooksResult;
import mukhtar.exapple.com.solutions_book.exercises.Chapters;
import mukhtar.exapple.com.solutions_book.forCategories.Categories;

public class MainPage extends AppCompatActivity {
    ImageView my_account;
    ImageView categories;
    ImageView my_books;
    ImageView my_solutions;
    ImageView search_image;
    ImageView exit_image;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        my_account = (ImageView) findViewById(R.id.my_account);
        categories = (ImageView) findViewById(R.id.categories_image);
        my_books = (ImageView) findViewById(R.id.my_books);
        my_solutions = (ImageView) findViewById(R.id.my_solution);
        search_image = (ImageView) findViewById(R.id.search_image);
        exit_image = (ImageView) findViewById(R.id.exit_image);

        my_account.setOnClickListener(for_main_page);
        categories.setOnClickListener(for_main_page);
        my_books.setOnClickListener(for_main_page);
        my_solutions.setOnClickListener(for_main_page);
        search_image.setOnClickListener(for_main_page);
        exit_image.setOnClickListener(for_main_page);

    }

    View.OnClickListener for_main_page = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.my_solution:
                    Intent my_sol = new Intent(getBaseContext(), Chapters.class);
                    startActivity(my_sol);
                    //Log.d("mylogs","Madik");
                    break;
                case R.id.my_account:
                    Intent intent = new Intent(getBaseContext(), MyAccount.class);
                    startActivity(intent);
                    break;

                case R.id.categories_image:
                    Intent cats = new Intent(getBaseContext(),Categories.class);
                    startActivity(cats);

                    break;
                case R.id.search_image:
                    Intent test = new Intent(getBaseContext(), BooksResult.class);
                    test.putExtra("id_of_category","search");
                    startActivity(test);

                    break;
            }
        }
    };
}
