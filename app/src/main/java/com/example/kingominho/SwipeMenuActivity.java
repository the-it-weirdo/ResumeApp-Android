package com.example.kingominho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SwipeMenuActivity extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private static final String webViewFragmentTag = "WEB_FRAGMENT";
    private boolean mBackPressedOnce;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mBackPressedOnce = false;
        }
    };
    private int delay = 2000;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_menu);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.ic_home, R.drawable.ic_home,"Home", "Brochure is an informative paper document (often also used for advertising) that can be folded into a template"));
        models.add(new Model(R.drawable.ic_address_card, R.drawable.ic_address_card, "Contact", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side"));
        models.add(new Model(R.drawable.ic_skills, R.drawable.ic_skills,"Skills", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
        models.add(new Model(R.drawable.ic_project, R.drawable.skin_project,"Projects", "Business cards are cards bearing business information about a company or individual."));
        models.add(new Model(R.drawable.ic_info_outline_black_24dp, R.drawable.ic_project, "About", "Business cards are cards bearing business information about a company or individual."));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        WebViewFragment fragment = (WebViewFragment) getSupportFragmentManager().findFragmentByTag(webViewFragmentTag);
        if (fragment != null && fragment.isVisible()) {
            fragment.webNavigation();
        }  else {
            if (mBackPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.mBackPressedOnce = true;
            String mMessage = getResources().getString(R.string.pressBackTwice);
            mToast = Toast.makeText(getApplicationContext(), mMessage, Toast.LENGTH_SHORT);
            mToast.show();

            mHandler.postDelayed(mRunnable, delay);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mToast != null) {
            mToast.cancel();
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
