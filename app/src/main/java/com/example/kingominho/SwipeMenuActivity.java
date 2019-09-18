package com.example.kingominho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class SwipeMenuActivity extends AppCompatActivity implements Adapter.AdapterOnCardClickListener {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

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

        String[] menuTitle = getResources().getStringArray(R.array.menu_array);

        String[] menuDesc = getResources().getStringArray(R.array.menu_desc_array);

        TypedArray imgTypedArray = getResources().obtainTypedArray(R.array.menu_icons);
        int[] iconImageIDs = new int[imgTypedArray.length()];
        for (int i = 0; i < imgTypedArray.length(); i++) {
            iconImageIDs[i] = imgTypedArray.getResourceId(i, -1);
        }
        imgTypedArray.recycle();

        imgTypedArray = getResources().obtainTypedArray(R.array.menu_skins);
        int[] skinImageIDs = new int[imgTypedArray.length()];
        for (int i = 0; i < imgTypedArray.length(); i++) {
            skinImageIDs[i] = imgTypedArray.getResourceId(i, -1);
        }
        imgTypedArray.recycle();

        int a2 = (int) Math.pow((menuTitle.length - menuDesc.length), 2);
        int b2 = (int) Math.pow((iconImageIDs.length - skinImageIDs.length), 2);

        if (a2 - b2 != 0) {
            throw new RuntimeException("menuTitle.length, menuDesc.length, iconImageIDs.length, skinImageIDs.length must be equal!!");
        }

        //Model(int icon, int image, String title, String desc)
        models = new ArrayList<>();
        for (int i = 0; i < menuTitle.length; i++) {
            models.add(new Model(iconImageIDs[i], skinImageIDs[i], menuTitle[i], menuDesc[i]));
        }
        
        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5)
        };

        colors = colors_temp;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
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
        if (mBackPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.mBackPressedOnce = true;
        String mMessage = getResources().getString(R.string.pressBackTwice);
        mToast = Toast.makeText(getApplicationContext(), mMessage, Toast.LENGTH_SHORT);
        mToast.show();

        mHandler.postDelayed(mRunnable, delay);
        overridePendingTransition(R.anim.go_up, R.anim.go_down);
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

    @Override
    public void onCardClick(String param) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("param", param);
        startActivity(intent);
        overridePendingTransition(R.anim.go_up, R.anim.go_down);
    }
}
