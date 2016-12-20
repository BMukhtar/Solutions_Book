package mukhtar.exapple.com.solutions_book.exercises;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import mukhtar.exapple.com.solutions_book.R;

/**
 * Created by Mukhtar on 12/20/2016.
 */

public class MyAdapter extends BaseAdapter {



    Context context;
    ArrayList<HashMap<String,String>> data;
    int xml ;

    public MyAdapter(Context ctx, ArrayList<HashMap<String,String>> data, int xml) {
        this.data = data;
        this.context = ctx;
        this.xml = xml;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(xml, viewGroup, false);
        }

        HashMap<String,String> text = data.get(position);

        TextView tv = (TextView) view.findViewById(R.id.textview_for_item_solution);
        tv.setText(text.get("descrip"));

        ImageView im = (ImageView) view.findViewById(R.id.imageview_for_item_solution);
        String imageString = text.get("imageString");
        im.setImageBitmap(StringToBitMap(imageString));
        im.setTag(position);


        return view;
    }

    View.OnClickListener play = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}


