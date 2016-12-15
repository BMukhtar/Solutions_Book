package mukhtar.exapple.com.solutions_book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.BooksAppereance.BooksResult;
import mukhtar.exapple.com.solutions_book.BooksAppereance.MyBooks;
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

        sharedPref = getSharedPreferences("Additional",MODE_PRIVATE);

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

        setCategoriesJsonOnSharedReferences();

        //getting categories

    }

    View.OnClickListener for_main_page = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
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

                case R.id.my_books:
                    Intent my_books = new Intent(getBaseContext(), MyBooks.class);
                    startActivity(my_books);
                    break;
            }
        }
    };

    public void setCategoriesJsonOnSharedReferences(){
        String url1 = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        final String query1 = "SELECT * FROM categories;";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                SharedPreferences.Editor ed = sharedPref.edit();
                                ed.putString("categories",response);
                                ed.commit();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getBaseContext(),"Can not load categories",Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap();
                        map.put("query", query1);
                        return map;
                    }
                };
        request.setTag("POST");
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(request);

    }
}
