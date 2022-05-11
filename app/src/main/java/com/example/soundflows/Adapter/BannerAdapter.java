package com.example.soundflows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.soundflows.Activity.PlaylistActivity;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.RowBannerBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    Context context;
    ArrayList<Banner> arrayListBanner;

    public BannerAdapter(Context context, ArrayList<Banner> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_banner, null);

        ImageView ivBackgroundBanner = view.findViewById(R.id.imageBackgroundBanner);
        ImageView ivSongBanner = view.findViewById(R.id.imageviewBanner);
        TextView txtTitleBanner = view.findViewById(R.id.textviewTitleBanner);
        TextView txtContent = view.findViewById(R.id.textviewContent);

        Picasso.get().load(arrayListBanner.get(position).getImgAds()).into(ivBackgroundBanner);
        Picasso.get().load(arrayListBanner.get(position).getImgSong()).into(ivSongBanner);
        txtTitleBanner.setText(arrayListBanner.get(position).getNameSong());
        txtContent.setText(arrayListBanner.get(position).getContent());

        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlaylistActivity.class);
            intent.putExtra(UserConstant.KEY_PUT_BANNER_PLAYLIST, arrayListBanner.get(position));
            context.startActivity(intent);
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
