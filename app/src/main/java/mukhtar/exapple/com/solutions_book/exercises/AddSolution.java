package mukhtar.exapple.com.solutions_book.exercises;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mukhtar.exapple.com.solutions_book.R;
import mukhtar.exapple.com.solutions_book.Utility;

public class AddSolution extends AppCompatActivity {
    EditText editText;
    Button button;
    ImageView image_view_solutions_image;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String task_id;
    String encodedImage = "DEFAULT";



    private String userChoosenTask;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);
        editText=(EditText)findViewById(R.id.text_of_solution);
        button= (Button)findViewById(R.id.btn_add_solution);
        image_view_solutions_image = (ImageView) findViewById(R.id.imageview_solutions_image);
        task_id = getIntent().getStringExtra("task_id");
    }

    public void addSolution(View v){
        String data = editText.getText().toString();
        String query = "INSERT INTO solutions  VALUES (DEFAULT,'"+encodedImage+"',"+task_id+",'"+data+"','"+encodedImage+"') ";

        for_query(query);

    }

    public void addImage(View v){
        selectImage();
    }


    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {


            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(userChoosenTask.equals("Take Photo"))

                        cameraIntent();

                    else if(userChoosenTask.equals("Choose from Library"))

                        galleryIntent();

                } else {

                    //code for deny

                }

                break;

        }

    }


    private void selectImage() {

        final CharSequence[] items = { "Take Photo", "Choose from Library",

                "Cancel" };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {


                boolean result=Utility.checkPermission(getBaseContext());


                if (items[item].equals("Take Photo")) {

                    userChoosenTask ="Take Photo";

                    if(result)

                        cameraIntent();


                } else if (items[item].equals("Choose from Library")) {

                    userChoosenTask ="Choose from Library";

                    if(result)

                        galleryIntent();


                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();
    }


    private void galleryIntent()

    {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

    }

    private void cameraIntent()
    {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE)

                onSelectFromGalleryResult(data);

            else if (requestCode == REQUEST_CAMERA)

                onCaptureImageResult(data);

        }

    }


    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


        File destination = new File(Environment.getExternalStorageDirectory(),

                System.currentTimeMillis() + ".jpg");


        FileOutputStream fo;

        try {

            destination.createNewFile();

            fo = new FileOutputStream(destination);

            fo.write(bytes.toByteArray());

            fo.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


        image_view_solutions_image.setImageBitmap(thumbnail);
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, boas ); //bm is the bitmap object
        byte[] byteArrayImage = boas .toByteArray();
        encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

    }





    @SuppressWarnings("deprecation")

    private void onSelectFromGalleryResult(Intent data) {


        Bitmap bm=null;

        if (data != null) {

            try {

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

            } catch (IOException e) {

                e.printStackTrace();

            }

        }


        image_view_solutions_image.setImageBitmap(bm);
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, boas ); //bm is the bitmap object
        byte[] byteArrayImage = boas .toByteArray();
        encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }
    public void for_query(final String query){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        "http://telegrambot.kz/android/Bimurat_Mukhtar/solutions_book/for_query.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response){
                                Log.d("mylogs",response);
                                try {
                                    JSONObject res = new JSONObject(response);
                                    if(res.getInt("success")==1){
                                        finish();
                                        Log.d("mylogs",res.toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
                ){
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

}
