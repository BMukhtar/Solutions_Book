package mukhtar.exapple.com.solutions_book;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import mukhtar.exapple.com.solutions_book.DBHelper;
import mukhtar.exapple.com.solutions_book.Login;
import mukhtar.exapple.com.solutions_book.R;
public class MyAccount extends AppCompatActivity  {
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private Button btnSelect;

    private ImageView ivImage;

    private String userChoosenTask;
    SharedPreferences sharedPref;
    EditText etName;
    EditText etSurname;
    EditText etPas1;
    EditText etPas2;
    ImageButton changeName;
    ImageButton changeSurname;
    ImageView changePas;
    Button save;

    ImageButton upload;
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

        upload = (ImageButton) findViewById(R.id.upload);
        ivImage = (ImageView) findViewById(R.id.imageView);
        sharedPref = getSharedPreferences("Username",this.MODE_PRIVATE);
        etName.setText(sharedPref.getString("name",""));
        etSurname.setText(sharedPref.getString("surname",""));
        etName.setEnabled(false);
        etSurname.setEnabled(false);
        etPas1.setVisibility(View.INVISIBLE);
        etPas2.setVisibility(View.INVISIBLE);
        changePas = (ImageView) findViewById(R.id.changePas);
        changePas.setOnClickListener(onClickListener);
        save.setOnClickListener(onClickListener);
        save.setVisibility(View.INVISIBLE);
        upload.setOnClickListener(onClickListener);
        listView = new ListView(this);
        String img = sharedPref.getString("image","");
        Bitmap bitmap = StringToBitMap(img);
        ivImage.setImageBitmap(bitmap);







    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_account,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                etName.setEnabled(true);
                etSurname.setEnabled(true);
                save.setVisibility(View.VISIBLE);
                break;
            case R.id.logout:
                SharedPreferences.Editor ed = sharedPref.edit();
                ed.putString("username","");
                ed.commit();

                Intent intent = new Intent(getBaseContext(),Login.class);
                startActivity(intent);
                finish();
                break;
            case R.id.delAccount:
                break;
            case R.id.change_pas:
                etPas1.setVisibility(View.VISIBLE);
                etPas2.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.upload:
                    selectImage();
                    Functions f = new Functions(MyAccount.this);
                    f.setInformationOnSharedReferences(sharedPref.getString("username",""),sharedPref.getString("password",""),false);
                    break;

                case R.id.save:
                    break;

            }
        }
    };
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


        AlertDialog.Builder builder = new AlertDialog.Builder(MyAccount.this);

        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {


                boolean result=Utility.checkPermission(MyAccount.this);


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


        ivImage.setImageBitmap(thumbnail);
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, boas ); //bm is the bitmap object
        byte[] byteArrayImage = boas .toByteArray();
        final String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString("image",encodedImage);
        ed.commit();
        String query = "UPDATE `users` SET `image`='"+encodedImage+"' WHERE `username`='"+sharedPref.getString("username","")+"'";
        for_query(query,"image");

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


        ivImage.setImageBitmap(bm);

        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, boas ); //bm is the bitmap object
        byte[] byteArrayImage = boas .toByteArray();
        final String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString("image",encodedImage);
        ed.commit();
        String query = "UPDATE `users` SET `image`='"+encodedImage+"' WHERE `username`='"+sharedPref.getString("username","")+"'";
        for_query(query,"image");
    }
    public void for_query(final String query, String str){
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
