package com.giffy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class HelpActivity extends AppCompatActivity {


    @BindView(R.id.gif_image)
    GifImageView gifImageView;

    @BindView(R.id.attempts)
    TextView attemptsText;

    @BindView(R.id.score)
    TextView scoreText;

    @BindView(R.id.speed_0_5x)
    ImageView speed05X;

    @BindView(R.id.speed_0_25x)
    ImageView speed025X;

    @BindView(R.id.image)
    View image;

    @BindView(R.id.prev)
    ImageView prevImage;


    @BindView(R.id.next)
    ImageView nextImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ButterKnife.bind(this);


        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);

        sequence.setConfig(config);

        sequence.addSequenceItem(image,
                "The aim is to stop the gif at a right frame, multiple frames may be correct", "GOT IT");

        sequence.addSequenceItem(attemptsText,
                "Number of tries for each image", "GOT IT");
        sequence.addSequenceItem(scoreText,
                "Total score of the game, each successful try gives 10 stars", "GOT IT");

        sequence.addSequenceItem(speed05X,
                "Slow animation to 0.5X speed, costs 1 star", "GOT IT");

        sequence.addSequenceItem(speed025X,
                "Slow animation to 0.25X speed, costs 2 stars", "GOT IT");

        sequence.addSequenceItem(prevImage,
                "Replay already done levels", "GOT IT");

        sequence.addSequenceItem(nextImage,
                "Replay already done levels", "GOT IT");


        sequence.start();
    }
}
