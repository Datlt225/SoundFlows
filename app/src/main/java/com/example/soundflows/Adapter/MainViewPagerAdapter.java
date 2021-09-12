package com.example.soundflows.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrayfragment = new ArrayList<>();
    private  ArrayList<String> arrayTittle = new ArrayList<>();
    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayfragment.get(position);
    }

    @Override
    public int getCount() {
        return arrayfragment.size();
    }

    public  void addFragment (Fragment fragment, String title) {
        arrayfragment.add(fragment);
        arrayTittle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  arrayTittle.get(position);
    }
}
