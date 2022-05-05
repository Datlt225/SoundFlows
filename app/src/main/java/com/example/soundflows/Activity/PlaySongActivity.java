package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.example.soundflows.Adapter.MainViewPagerAdapter;
import com.example.soundflows.Adapter.ViewPagerPlaylistSong;
import com.example.soundflows.Fragment.Fragment_Disc;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityPlaySongBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlaySongActivity extends AppCompatActivity {
    private ActivityPlaySongBinding binding;
    private static final ArrayList<Song> songArrayList = new ArrayList<>();
    private static ViewPagerPlaylistSong viewPagerPlaylistSong;
    private final Fragment_Disc fragment_disc = new Fragment_Disc();
    private MediaPlayer mediaPlayer;
    private final Handler mHandler = new Handler();

    int position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaySongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent.hasExtra(UserConstant.KEY_SONG)) {
            Song song = intent.getParcelableExtra(UserConstant.KEY_SONG);
            songArrayList.add(song);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        init();
        eventClick();

        if (intent.hasExtra(UserConstant.KEY_ARRAY_SONGS)) {
            ArrayList<Song> songs = intent.getParcelableArrayListExtra(UserConstant.KEY_ARRAY_SONGS);
            for (int i = 0; i < songs.size(); i++) {
                songs.add(songs.get(i));
                Log.d("BBB", songs.get(i).getNameSong());
            }
        }

        PlaySongActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    binding.seekbarSong.setProgress(mCurrentPosition);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

                    binding.tvSongTimeSong.setText(
                            simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                }

                mHandler.postDelayed(this, 1000);
            }
        });

        binding.seekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
        });
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPlaylistSong.getCount() > 0) {
                    viewPagerPlaylistSong.getItem(0);
                    if (songArrayList.size() > 0) {
                        fragment_disc.PlaySong(songArrayList.get(0).getImgSong());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 3000);
                    }
                }
            }
        }, 500);


        // ---Bắt sự kiện mấy cái nút nhạc

        //-- Nuts Play
        binding.ibPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                binding.ibPlay.setImageResource(R.drawable.ic_play);
//                if (fragment_disc.objectAnimator != null) {
//                    fragment_disc.objectAnimator.pause();
//                }
            } else {
                mediaPlayer.start();
                binding.ibPlay.setImageResource(R.drawable.ic_pause);
//                if (fragment_disc.objectAnimator != null) {
//                    fragment_disc.objectAnimator.resume();
//                }
            }
        });
        // -- Nút lặp
        binding.ibRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if (repeat == false) {
                        if (checkRandom == true) {
                            checkRandom = false;
                            binding.ibRepeat.setImageResource(R.drawable.ic_repeated);
                            binding.ibSuffering.setImageResource(R.drawable.ic_shuffer);
                        }

                        binding.ibRepeat.setImageResource(R.drawable.ic_repeated);
                        repeat = true;
                    } else {
                        binding.ibRepeat.setImageResource(R.drawable.ic_repeat);
                        repeat = false;
                    }
                }
            }
        });

        // -- Nút chạy ngẫu nhiên
        binding.ibSuffering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        binding.ibSuffering.setImageResource(R.drawable.ic_shuffered);
                        binding.ibRepeat.setImageResource(R.drawable.ic_repeat);
                    }

                    binding.ibSuffering.setImageResource(R.drawable.ic_shuffered);
                    checkRandom = true;
                } else {
                    binding.ibSuffering.setImageResource(R.drawable.ic_shuffer);
                    checkRandom = false;
                }
            }
        });

        // -- cập nhật thanh seekbar
        binding.seekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        // Nút next
        binding.ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songArrayList.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (songArrayList.size())) {
                        binding.ibPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = songArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1; // để index đừng vượt qua giá trị của mảng
                            }
                            position = index;
                        }
                        if (position > (songArrayList.size() - 1)) {
                            position = 0;
                        }
                        new PlayMp3().execute(songArrayList.get(position).getLinkSong());
                        fragment_disc.PlaySong(songArrayList.get(position).getImgSong());
                        getSupportActionBar().setTitle(songArrayList.get(position).getNameSong());
                        Update();
                    }
                }

                // tránh việc click quá nhiều gây crack app
                binding.ibPre.setClickable(false);
                binding.ibNext.setClickable(false);

                Handler handler1 = new Handler(); // dùng để thực thi
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.ibPre.setClickable(true);
                        binding.ibNext.setClickable(true);
                    }
                }, 2000); // thực thi sau 2s
            }
        });

        // -- Nút Previous bài hát

        binding.ibPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position < (songArrayList.size())) {
                    binding.ibPlay.setImageResource(R.drawable.ic_pause);
                    position--;

                    // trường hợp position đang ở bài đầu tiên
                    if (position < 0) {
                        position = songArrayList.size() - 1;
                    }

                    if (repeat == true) {
                        position += 1;
                    }
                    if (checkRandom == true) {
                        Random random = new Random();
                        int index = random.nextInt(songArrayList.size());
                        if (index == position) {
                            position = index - 1;
                        }
                        position = index;
                    }

                    new PlayMp3().execute(songArrayList.get(position).getLinkSong());
                    fragment_disc.PlaySong(songArrayList.get(position).getImgSong());
                    getSupportActionBar().setTitle(songArrayList.get(position).getNameSong());
                    Update();
                }
                binding.ibPre.setClickable(false);
                binding.ibNext.setClickable(false);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.ibPre.setClickable(true);
                        binding.ibNext.setClickable(true);
                    }
                }, 2000);
            }
        });
    }

    private void init() {
        setSupportActionBar(binding.toolbarPlaySong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarPlaySong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                songArrayList.clear();
            }
        });
        binding.toolbarPlaySong.setTitleTextColor(Color.WHITE);

        viewPagerPlaylistSong = new ViewPagerPlaylistSong(getSupportFragmentManager());
        viewPagerPlaylistSong.AddFragment(fragment_disc);

        if (songArrayList.size() > 0) {
            getSupportActionBar().setTitle(songArrayList.get(0).getNameSong());
            new PlayMp3().execute(songArrayList.get(0).getLinkSong());
            binding.ibPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            Update();
        }
    }

    private void Update() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        next = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < (songArrayList.size())) {
                        binding.ibPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = songArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1; // để index đừng vượt qua giá trị của mảng
                            }
                            position = index;
                        }
                        if (position > (songArrayList.size() - 1)) {
                            position = 0;
                        }
                        new PlayMp3().execute(songArrayList.get(position).getLinkSong());
                        fragment_disc.PlaySong(songArrayList.get(position).getImgSong());
                        getSupportActionBar().setTitle(songArrayList.get(position).getNameSong());
                    }


                    // tránh việc click quá nhiều gây crack app
                    binding.ibPre.setClickable(false);
                    binding.ibNext.setClickable(false);

                    Handler handler1 = new Handler(); // dùng để thực thi
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.ibPre.setClickable(true);
                            binding.ibNext.setClickable(true);
                        }
                    }, 2000); // thực thi sau 2s
                    next = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        binding.tvSongTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        binding.seekbarSong.setMax(mediaPlayer.getDuration() / 1000);
    }
}