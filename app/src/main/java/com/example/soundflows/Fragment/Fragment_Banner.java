package com.example.soundflows.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.soundflows.Adapter.BannerAdapter;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {

    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        mapping();
        GetData();
        return view;
    }

    private void mapping() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetData() {
        try {
            Dataservice dataservice = APIService.getService();
            Call<List<Banner>> callback = dataservice.GetDataBanner();
            callback.enqueue(new Callback<List<Banner>>() {
                @Override
                public void onResponse(@NonNull Call<List<Banner>> call,
                                       @NonNull Response<List<Banner>> response) {
                    ArrayList<Banner> banners = (ArrayList<Banner>) response.body();
                    bannerAdapter = new BannerAdapter(getActivity(), banners);
                    viewPager.setAdapter(bannerAdapter);
                    circleIndicator.setViewPager(viewPager);
                    handler = new Handler();
                    runnable = () -> {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;

                        if (currentItem >= viewPager.getCurrentItem()) {
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 3000);
                    };

                    handler.postDelayed(runnable, 3000);
                }

                @Override
                public void onFailure(@NonNull Call<List<Banner>> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.e("Banner", e.getMessage());
        }
    }
}
