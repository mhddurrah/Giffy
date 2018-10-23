package com.giffy;

import java.util.List;

public class Gif {
    public String text;
    public final int resourceId;
    public final List<Integer> rightFrames;

    public Gif(int resourceId, List<Integer> rightFrames) {
        text = null;
        this.resourceId = resourceId;
        this.rightFrames = rightFrames;
    }


    public Gif(String text, int resourceId, List<Integer> rightFrames) {
        this(resourceId, rightFrames);
        this.text = text;
    }
}
