package com.giffy;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {
    private static final String CURRENT_LEVEL = "CurrentLevel";
    private static final String SCORE = "Score";
    private static StorageManager INSTANCE;
    private static String PREF_NAME = "GiffyPref";

    private final Context context;

    public StorageManager(Context context) {
        this.context = context;
    }

    public static StorageManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StorageManager(context);
        }
        return INSTANCE;
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    int getCurrentLevel() {
        return getPreferences().getInt(CURRENT_LEVEL, 0);
    }


    void saveCurrentLevel(int level) {
        getPreferences()
                .edit()
                .putInt(CURRENT_LEVEL, level).apply();
    }

    int getScore() {
        return getPreferences().getInt(SCORE, 10);
    }

    void saveScore(int score) {
        getPreferences()
                .edit()
                .putInt(SCORE, score).apply();
    }

    void levelSolved(int level) {
        getPreferences().edit().putBoolean("Level" + level, false).apply();
    }

    boolean isLevelSolved(int level) {
        return getPreferences().contains("Level" + level);
    }

}
