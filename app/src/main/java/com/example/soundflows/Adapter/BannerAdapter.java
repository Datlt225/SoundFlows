package com.example.soundflows.Adapter;

import android.content.Context;
import android.content.Intent;
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
    } // trả về tất cả các page có trong viewpager

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    } // trả về view tuỳ theo object định hình

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_banner, null);

        ImageView imgbackgroundBanner = view.findViewById(R.id.imageBackgroundBanner);
        ImageView imgsongbanner = view.findViewById(R.id.imageviewBanner);
        TextView txtTitleBanner = view.findViewById(R.id.textviewTitleBanner);
        TextView txtContent = view.findViewById(R.id.textviewContent);

        Picasso.get().load(arrayListBanner.get(position).getImgAds()).into(imgbackgroundBanner);
        Picasso.get().load(arrayListBanner.get(position).getImgSong()).into(imgsongbanner);
        txtTitleBanner.setText(arrayListBanner.get(position).getNameSong());
        txtContent.setText(arrayListBanner.get(position).getContent());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlaylistActivity.class);
                intent.putExtra("banner", arrayListBanner.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
