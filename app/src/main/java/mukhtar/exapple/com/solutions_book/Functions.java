package mukhtar.exapple.com.solutions_book;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class Functions {
    Activity context;

    SharedPreferences user_information;

    Functions(Activity ctx){
        this.context = ctx;
        user_information = context.getSharedPreferences("Username", Context.MODE_PRIVATE);
    }
    Functions(){
        this.context = null;
    }

    protected void setCategoriesJsonOnSharedReferences(){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_result.php";
        final String query = "SELECT * FROM categories;";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                SharedPreferences.Editor ed = user_information.edit();
                                ed.putString("categories",response);
                                ed.commit();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(context,"Can not load categories",Toast.LENGTH_SHORT).show();
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(request);
    }


    void setInformationOnSharedReferences(String username, String password, final boolean isfirst){
        String url = "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_user_id.php";
        final String query = "SELECT * FROM users WHERE username = '"+username+"' and password = '"+password+"' ;";
        Log.d("mylogs",username+" "+password);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("mylogs",response);
                                updatePreferences(response,isfirst);


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(context,"Can not load id",Toast.LENGTH_SHORT).show();
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

    void updatePreferences(String response,boolean is_first){
        try {
            JSONObject result = new JSONObject(response);
            if(result.getInt("success")==0){
                Toast.makeText(context,"Change your username the same username exists",Toast.LENGTH_SHORT).show();
            }else{
                JSONArray array = result.getJSONArray("products");
                int id = Integer.parseInt(array.getString(0));
                String name = array.getString(1);
                String surname = array.getString(2);
                String username = array.getString(3);
                String password = array.getString(4);
                String image = array.getString(5);

                SharedPreferences.Editor ed = user_information.edit();
                ed.putInt("id",id);
                ed.putString("name",name);
                ed.putString("username",username);
                ed.putString("surname",surname);
                ed.putString("password",password);
                ed.putString("image",image);
                ed.commit();
                Toast.makeText(context,"sharedPreferences updated id is "+id+" name is "+name,Toast.LENGTH_SHORT).show();
                if(is_first){
                    Intent i = new Intent(context,MainPage.class);
                    i.putExtra("is_first","not");
                    context.startActivity(i);
                    Login.a.finish();
                    context.finish();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
