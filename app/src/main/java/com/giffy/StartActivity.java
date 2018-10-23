package com.giffy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.start)
    ImageView startImage;

    @BindView(R.id.howto)
    ImageView howtoImage;

    @BindView(R.id.share)
    ImageView shareImage;

    @BindView(R.id.rate)
    ImageView rateImage;

    @BindView(R.id.ad_view)
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    @OnClick(R.id.start)
    public void start() {
        startActivity(new Intent(this, PlayActivity.class));
    }

    @OnClick(R.id.howto)
    public void help() {
        startActivity(new Intent(this, HelpActivity.class));
    }


    @OnClick(R.id.share)
    public void share() {
        StorageManager storageManager = StorageManager.getInstance(this);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        final String appUrl = "http://play.google.com/store/apps/details?id=" + getPackageName();
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Giffy game, enjoy playing with gif images "
                + appUrl + ".  I got " + storageManager.getScore() + " stars!");
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    @OnClick(R.id.rate)
    public void rate() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}
