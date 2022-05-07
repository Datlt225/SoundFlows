package com.example.soundflows.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.soundflows.Activity.MainActivity;
import com.example.soundflows.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Disc extends Fragment {
    private ImageView imageView;
    private View view;
    public ObjectAnimator objectAnimator;

    private String songThumbnail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_disc, container, false);
        this.imageView = this.view.findViewById(R.id.im_image_song);

        Picasso.get().load(this.songThumbnail).into(this.imageView);

//        objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
//        objectAnimator.setDuration(10000);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
//        objectAnimator.setInterpolator(new LinearInterpolator());
//        objectAnimator.start();
        return this.view;
    }

    public void setSongThumbnail(String link) {
        songThumbnail = link;
    }

    public void PlaySong(String link) {
        if (this.imageView != null)
            Picasso.get().load(link).into(this.imageView);
    }
}
