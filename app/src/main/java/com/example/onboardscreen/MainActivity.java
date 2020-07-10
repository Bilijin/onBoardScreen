package com.example.onboardscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dots;
    SliderAdapter sliderAdapter;
    TextView[] mDots;
    ScrollingPagerIndicator pagerIndicator;
    public int[] layouts;
    LinearLayout btnNext;
    TextView txtNxt;
    ImageView imgNxt;
    Button startBtn;
    TextView skp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        //initializes the views
        pagerIndicator = findViewById(R.id.indicator);
        pagerIndicator.setSelectedDotColor(getColor(R.color.colorPrimary));
        pagerIndicator.setDotColor(getColor(R.color.grey));
        pagerIndicator.attachToPager(viewPager);
        btnNext = findViewById(R.id.nxt_btn);
        imgNxt = findViewById(R.id.next_img);
        txtNxt = findViewById(R.id.next_txt);
        startBtn = findViewById(R.id.start_btn);

        //initializes the Get Started as gone so it doesn't show
        startBtn.setVisibility(View.GONE);
        skp = findViewById(R.id.skip);

        //adds all the layouts to an array list of integers and uses the array list to populate the
        //layout inflater
        layouts = new int[]{
                R.layout.onboard1,
                R.layout.onboard2,
                R.layout.onboard3
        };



        //attaches and onClick listener to the next arrow image view
        imgNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }


    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(MainActivity.this, HelloActivity.class));
        finish();
    }

    //implements an onPageChange listener for the viewPager
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //checks which page is currently on the screen
        @Override
        public void onPageSelected(int position) {
            int current = getItem(+1);

            //if the current layout is the last, it removes the next button
            //and removes the skip textView
            if (current == layouts.length) {
                btnNext.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                skp.setVisibility(View.GONE);
            }
            //if it isn't the last, it removes the getStarted button
            //and sets the skip textView to visible
            else {
                btnNext.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);
                skp.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    //opens the Hello Activity when the method is called
    public void openHome(View view){
        startActivity(new Intent(MainActivity.this, HelloActivity.class));
    }

    public class SliderAdapter extends PagerAdapter {
        Context context;
        LayoutInflater inflater;

        public SliderAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            View view = (View) object;
            container.removeView(view);
        }
    }
}