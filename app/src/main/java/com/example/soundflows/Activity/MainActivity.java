package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.soundflows.Adapter.MainViewPagerAdapter;
import com.example.soundflows.Fragment.Fragment_Home_Page;
import com.example.soundflows.Fragment.Fragment_Profile;
import com.example.soundflows.Fragment.Fragment_Searching;
import com.example.soundflows.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
    }

    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Home_Page(), "HomePage");
        mainViewPagerAdapter.addFragment(new Fragment_Searching(), "Searching");
        mainViewPagerAdapter.addFragment(new Fragment_Profile(), "Profile");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.iconhome);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconprofile);
    }


    private void mapping() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }
}