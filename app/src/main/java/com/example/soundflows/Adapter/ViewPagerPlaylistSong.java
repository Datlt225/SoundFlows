package com.example.soundflows.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerPlaylistSong extends FragmentPagerAdapter {

    public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    public ViewPagerPlaylistSong(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentArrayList.size() > 0)
            return fragmentArrayList.size();

        return 0;
    }

    public void AddFragment(Fragment fragment) {
        fragmentArrayList.add(fragment);
    }
}
