package com.example.soundflows.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.soundflows.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Disc extends Fragment {
    private ImageView imageView;
    private View view;
    public ObjectAnimator objectAnimator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disc, container, false);
        imageView = view.findViewById(R.id.im_image_song);
//        objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
//        objectAnimator.setDuration(10000);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
//        objectAnimator.setInterpolator(new LinearInterpolator());
//        objectAnimator.start();
        return view;
    }

    public void PlaySong(String imgSong) {
        Picasso.get().load(imgSong).into(imageView);
    }
}
