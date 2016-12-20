package mukhtar.exapple.com.solutions_book;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreenActivity extends Activity {
    TextView tv;
    private final int SPLASH_DISPLAY_LENGTH = 2000;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashScreenActivity.this, Login.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();
    }
    private void StartAnimations() {
        ImageView tv = (ImageView) findViewById(R.id.textView2);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        AbsoluteLayout l = (AbsoluteLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.alpha2);
        anim2.reset();
        l.startAnimation(anim2);
        tv.startAnimation(anim3);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.translate2);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        ImageView iv2 = (ImageView) findViewById(R.id.logo2);
        iv.clearAnimation();
        iv2.clearAnimation();

        iv.startAnimation(anim);
        iv2.startAnimation(anim2);



    }


}