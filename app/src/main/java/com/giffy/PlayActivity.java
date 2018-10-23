package com.giffy;

import android.app.ProgressDialog;
import android.arch.core.util.Function;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class PlayActivity extends AppCompatActivity {

    @BindView(R.id.gif_image)
    GifImageView gifImageView;

    @BindView(R.id.gif_text)
    TextView gifText;

    @BindView(R.id.attempts)
    TextView attemptsText;

    @BindView(R.id.score)
    TextView scoreText;

    @BindView(R.id.ad_view)
    AdView adView;

    @BindView(R.id.progress_bar)
    ProgressBar progress;

    @BindView(R.id.prev)
    ImageView prevImage;

    @BindView(R.id.next)
    ImageView nextImage;


    int level;
    Gif current;
    StorageManager storageManager;
    int attempts = 3;
    int score;
    boolean imageSolved;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        MobileAds.initialize(this, getString(R.string.admob_app_id));

        storageManager = StorageManager.getInstance(this);
        level = storageManager.getCurrentLevel();

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        load();
    }

    private void load() {
        progress.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> {
            progress.setVisibility(View.GONE);
            imageSolved = false;

            nextImage.setEnabled(storageManager.isLevelSolved(level) || storageManager.isLevelSolved(level + 1));
            prevImage.setEnabled(level != 0);
            prevImage.setVisibility(level == 0 ? View.GONE : View.VISIBLE);

            current = GifRepository.getGif(level);
            score = storageManager.getScore();
            scoreText.setText(String.valueOf(score));
            gifImageView.setImageResource(current.resourceId);
            if (current.text != null) {
                gifText.setText(current.text);
            } else {
                gifText.setText("");
            }
            updateAttempts(3);
        }, 500);
    }


    @OnClick(R.id.gif_image)
    public void play() {
        GifDrawable drawable = ((GifDrawable) gifImageView.getDrawable());
        if (drawable != null) {
            if (drawable.isPlaying() && !imageSolved) {
                drawable.pause();
                if (current.rightFrames.contains(drawable.getCurrentFrameIndex())) {
                    imageSolved = true;
                    storageManager.levelSolved(level);
                    updateScore((score) -> score + 10);
                    if (level == GifRepository.size() - 1) {
                        level = 0;
                        storageManager.saveCurrentLevel(0);
                    } else
                        storageManager.saveCurrentLevel(++level);
                    load();
                } else {
                    updateAttempts(--attempts);
                    if (attempts == 0) {
                        noTries(drawable);
                    }
                }
            } else {
                if (attempts == 0) {
                    noTries(drawable);
                    return;
                }
                drawable.start();
            }
        }
    }

    void noTries(GifDrawable drawable) {
        new AlertDialog.Builder(this)
                .setTitle("Restart")
                .setMessage("Restart for 3 stars?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateScore((score) -> score - 3);
                        updateAttempts(3);
                        drawable.setSpeed(1F);
                        drawable.start();
                    }
                })
                .show();
    }


    @OnClick(R.id.speed_0_5x)
    public void speed0_5x() {
        GifDrawable drawable = ((GifDrawable) gifImageView.getDrawable());
        if (drawable != null) {
            updateScore((score) -> score - 1);
            drawable.setSpeed(0.5F);
            drawable.start();
        }
    }

    @OnClick(R.id.speed_0_25x)
    public void speed0_25x() {
        GifDrawable drawable = ((GifDrawable) gifImageView.getDrawable());
        if (drawable != null) {
            updateScore((score) -> score - 2);
            drawable.setSpeed(0.25F);
            drawable.start();
        }
    }

    @OnClick(R.id.prev)
    public void prev() {
        updateLevel(level - 1);
        load();
    }

    @OnClick(R.id.next)
    public void next() {
        updateLevel(level + 1);
        load();
    }

    void updateLevel(int newValue) {
        level = newValue < 0 || newValue >= GifRepository.size() - 1 ? 0 : newValue;
        storageManager.saveCurrentLevel(level);
    }

    private void updateScore(Function<Integer, Integer> updateFunc) {
        score = updateFunc.apply(score);
        storageManager.saveScore(score);
        scoreText.setText(String.valueOf(score));
    }

    void updateAttempts(int tries) {
        attempts = tries;
        attemptsText.setText(String.valueOf(attempts));
    }
}
