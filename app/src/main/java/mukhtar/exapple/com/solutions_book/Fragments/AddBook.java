package mukhtar.exapple.com.solutions_book.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;
import mukhtar.exapple.com.solutions_book.forCategories.Categories;

public class AddBook extends Fragment {

    EditText name_of_new_book;
    EditText author_of_new_book;
    EditText link_of_new_book;
    Spinner spinner_categories_data;
    SharedPreferences sharedPreferences;
    int idOfUser;
    int id_of_category;
    Button add_book;
    String [] [] dataOfCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_book,container,false);
        add_book = (Button) view.findViewById(R.id.button_add_new_book);
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volleyQueue();
            }
        });
        name_of_new_book = (EditText) view.findViewById(R.id.edittext_book_name);
        author_of_new_book = (EditText) view.findViewById(R.id.edittext_book_author);
        link_of_new_book = (EditText) view.findViewById(R.id.edittext_book_link);
        spinner_categories_data = (Spinner) view.findViewById(R.id.spinner_categories_data);


        sharedPreferences = getActivity().getSharedPreferences("Username",Context.MODE_PRIVATE);
        String categories = sharedPreferences.getString("categories","no");

        //May occur errors
        idOfUser = sharedPreferences.getInt("id",0);


        ArrayAdapter<String> adapter;
        if(categories.equals("no")){
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,new String []{"Others"});

        }else{

            adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, getAdapter(categories));
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categories_data.setAdapter(adapter);
        spinner_categories_data.setPrompt("Others");
        spinner_categories_data.setSelection(0);
        spinner_categories_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_of_category = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }
    private void volleyQueue(){
        String url  = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php";
        String book_name = name_of_new_book.getText().toString();
        String book_author = author_of_new_book.getText().toString();
        String book_link = link_of_new_book.getText().toString();


        final String query = "INSERT INTO books values(default,'"+book_name+"','"+book_author+"','"+book_link+"','"+id_of_category+"'," +
                "'"+idOfUser+"');";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("mylogs",response+"");
                                    JSONObject res = new JSONObject(response);
                                    if(res.getInt("success")==1){
                                        Toast.makeText(getActivity(),"inserted successively",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getActivity(),"To Insert you book not allowed",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.volley_error), Toast.LENGTH_SHORT).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap();
                        map.put("query", query);
                        return map;
                    }
                };
        request.setTag("POST");
        queue.add(request);
    }

    private String [] getAdapter(String response){
        try {
            String [] result;
            JSONObject res = new JSONObject(response);
            int o = res.getInt("success");
            if (o == 1) {
                JSONArray args = res.getJSONArray("products");
                dataOfCategories = new String[args.length()][4];
                result = new String [args.length()];

                for (int i = 0; i < args.length(); i++) {
                    String child_id = ((JSONArray) args.get(i)).getString(0);
                    String child_name = ((JSONArray) args.get(i)).getString(1);
                    String group_id = ((JSONArray) args.get(i)).getString(2);
                    String group_name = ((JSONArray) args.get(i)).getString(3);
                    result[i] = child_name;
                    dataOfCategories[i] = new String []{child_id,child_name,group_id,group_name};
                }
            }else{
                result = new String[]{"Others"};
                dataOfCategories[0] = new String []{"1","Others","0","Others"};

            }return result;



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[]{"Others"};
    }

}
