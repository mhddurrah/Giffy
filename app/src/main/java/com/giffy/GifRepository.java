package com.giffy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GifRepository {
    private static List<Gif> GIFs = new ArrayList<>();

    static {
        GIFs.add(new Gif(R.drawable.bike, Collections.singletonList(15)));
        GIFs.add(new Gif(R.drawable.cake, Arrays.asList(9, 26)));
        GIFs.add(new Gif(R.drawable.elephant, Collections.singletonList(4)));
        GIFs.add(new Gif(R.drawable.flappy, Collections.singletonList(6)));
        GIFs.add(new Gif(R.drawable.people, Collections.singletonList(5)));
        GIFs.add(new Gif("Stop on White!", R.drawable.rubic, Collections.singletonList(15)));
        GIFs.add(new Gif(R.drawable.sun, Collections.singletonList(1)));
        GIFs.add(new Gif(R.drawable.trump, Collections.singletonList(6)));
        GIFs.add(new Gif(R.drawable.park, Collections.singletonList(3)));
        GIFs.add(new Gif(R.drawable.fall, Collections.singletonList(6)));
    }

    static Gif getGif(int index) {
        if (index < 0) index = 0;
        if (index >= GIFs.size()) index = GIFs.size() - 1;
        return GIFs.get(index);
    }

    public static int size() {
        return GIFs.size();
    }
}
