package com.example.soundflows.Activity;

import static android.app.Service.START_NOT_STICKY;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.soundflows.Adapter.ViewPagerPlaylistSong;
import com.example.soundflows.ForegroundService.NotificationService;
import com.example.soundflows.Fragment.Fragment_Disc;
import com.example.soundflows.Fragment.Fragment_Play_Playlist;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlaySongActivity extends AppCompatActivity{
    Toolbar toolbarPlaySong;
    TextView txtTimeSong, txtTotalTimeSong;
    SeekBar seekBar;
    ImageButton imgShuffer, imgPre, imgPlay, imgNext, imgRepeat;
    ViewPager viewPagerPlaySong;

    public static ArrayList<Song> arrayListSong = new ArrayList<>();
    public static ViewPagerPlaylistSong adapterSong;

    Fragment_Disc fragment_disc = new Fragment_Disc();
    Fragment_Play_Playlist fragment_list_song = new Fragment_Play_Playlist();
    MediaPlayer mediaPlayer;

    private final Handler mHandler = new Handler();

    int position = 0;
    boolean repeat = false, checkRandom = false, next = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        GetDataFromIntent();
        init();
        eventClick();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        PlaySongActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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

    /**
     * change image button
     */
    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!(adapterSong.getItem(1) != null)) {
                    if (arrayListSong.size() > 0) {
                        fragment_disc.PlaySong(arrayListSong.get(0).getImgSong());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);

        /**
         * handling button play song
         */
        playSong();

        /**
         * handling repeat button
         */
        repeatSong();

        /**
         * handling  button shuffle
         */
        shuffleSong();

        /**
         * handling button next
         */
        nextSong();

        /**
         * handling button previous
         */
        previousSong();
    }

    private void previousSong() {
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListSong.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    if (position < arrayListSong.size()) {
                        imgPlay.setImageResource(R.drawable.ic_pause);
                        position--;

                        if (position < 0) {
                            position = arrayListSong.size() - 1;
                        }

                        if (repeat == true) {
                            position += 1;
                        }

                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(arrayListSong.size());

                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }

                        playMusic(arrayListSong.get(position).getLinkSong());
                        fragment_disc.PlaySong(arrayListSong.get(position).getImgSong());
                        getSupportActionBar().setTitle(arrayListSong.get(position).getNameSong());
                        Update();
                    }
                }

                imgPre.setEnabled(false);
                imgNext.setEnabled(false);

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    imgPre.setEnabled(true);
                    imgNext.setEnabled(true);
                }, 2000);
            }
        });
    }

    private void nextSong() {
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListSong.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    if (position < arrayListSong.size()) {
                        imgPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = arrayListSong.size();
                            }

                            position -= 1;
                        }

                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(arrayListSong.size());

                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }

                        if (position > (arrayListSong.size() - 1)) {
                            position = 0;
                        }

                        playMusic(arrayListSong.get(position).getLinkSong());
                        fragment_disc.PlaySong(arrayListSong.get(position).getImgSong());
                        getSupportActionBar().setTitle(arrayListSong.get(position).getNameSong());
                        Update();
                    }
                }

                imgPre.setEnabled(false);
                imgNext.setEnabled(false);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setEnabled(true);
                        imgNext.setEnabled(true);
                    }
                }, 2000);
            }
        });
    }

    private void shuffleSong() {
        imgShuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRandom == false){
                    if (repeat == true) {
                        repeat = false;
                        imgShuffer.setImageResource(R.drawable.ic_shuffered);
                        imgRepeat.setImageResource(R.drawable.ic_repeat);
                    }

                    imgShuffer.setImageResource(R.drawable.ic_shuffered);
                    checkRandom = true;

                } else {
                    imgShuffer.setImageResource(R.drawable.ic_shuffer);
                    checkRandom = false;
                }
            }
        });
    }

    private void repeatSong() {
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!repeat){
                    if (checkRandom) {
                        checkRandom = false;
                        imgRepeat.setImageResource(R.drawable.ic_repeated);
                        imgShuffer.setImageResource(R.drawable.ic_shuffer);
                    }

                    imgRepeat.setImageResource(R.drawable.ic_repeated);
                    repeat = true;

                } else {
                    imgRepeat.setImageResource(R.drawable.ic_repeat);
                    repeat = false;
                }
            }
        });
    }

    public void playSong() {
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.ic_play);
                } else {
                    //sendNotify();
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });
    }

    private void sendNotify() {
        Intent intent = new Intent(getApplication(), NotificationService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(UserConstant.OBJECT_SONG, arrayListSong.get(position));
        intent.putExtras(bundle);

        startService(intent);
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        arrayListSong.clear();
        if (intent != null) {
            if (intent.hasExtra(UserConstant.KEY_SONG)) {
                Song song = intent.getParcelableExtra(UserConstant.KEY_SONG);
                arrayListSong.add(song);
            }

            if (intent.hasExtra(UserConstant.KEY_ARRAY_SONGS)) {
                ArrayList<Song> songArrayList = intent.getParcelableArrayListExtra(UserConstant.KEY_ARRAY_SONGS);
                arrayListSong = songArrayList;
            }
        }
    }

    private void init() {
        toolbarPlaySong = findViewById(R.id.toolBarPlaySong);
        txtTimeSong = findViewById(R.id.txtTimeSong);
        txtTotalTimeSong = findViewById(R.id.txtTotalTimeSong);
        seekBar = findViewById(R.id.seekBarSong);
        imgShuffer = findViewById(R.id.imgbtnShuffle);
        imgPre = findViewById(R.id.imgbtnPre);
        imgPlay = findViewById(R.id.imgbtnPlay);
        imgNext = findViewById(R.id.imgbtnNext);
        imgRepeat = findViewById(R.id.imgbtnRepeat);
        viewPagerPlaySong = findViewById(R.id.viewpagerPlaySong);
        setSupportActionBar(toolbarPlaySong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarPlaySong.setNavigationOnClickListener(v -> {
            finish();
            mediaPlayer.stop();
            arrayListSong.clear();
        });
        toolbarPlaySong.setTitleTextColor(Color.WHITE);

        adapterSong = new ViewPagerPlaylistSong(getSupportFragmentManager());
        adapterSong.AddFragment(fragment_disc);
        adapterSong.AddFragment(fragment_list_song);
        viewPagerPlaySong.setAdapter(adapterSong);

        try {
            if (arrayListSong.size() > 0) {
                Song currentSong = arrayListSong.get(0);
                getSupportActionBar().setTitle(currentSong.getNameSong());
                playMusic(currentSong.getLinkSong());
                fragment_disc.setSongThumbnail(currentSong.getImgSong());
                imgPlay.setImageResource(R.drawable.ic_pause);
            } else {
                Toast.makeText(this, "Danh sách bài hát trống", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Log.e("empty", e.getMessage());
        }
    }

    public void playMusic(String songUrl) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.setOnPreparedListener(mp -> {
                TimeSong();
                Update();

                mp.start();
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
    }

    private void Update() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setOnCompletionListener(mp -> {
                    next = true;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < (arrayListSong.size())) {
                        imgPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = arrayListSong.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(arrayListSong.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (arrayListSong.size() - 1)) {
                            position = 0;
                        }
                        playMusic(arrayListSong.get(position).getLinkSong());
                        fragment_disc.PlaySong(arrayListSong.get(position).getImgSong());
                        getSupportActionBar().setTitle(arrayListSong.get(position).getNameSong());
                    }


                    // tránh việc click quá nhiều gây crack app
                    imgPre.setClickable(false);
                    imgNext.setClickable(false);

                    Handler handler1 = new Handler();
                    handler1.postDelayed(() -> {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }, 2000); // thực thi sau 2s
                    next = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

}
