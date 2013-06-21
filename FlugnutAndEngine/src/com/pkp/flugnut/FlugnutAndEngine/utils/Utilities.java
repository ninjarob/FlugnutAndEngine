package com.pkp.flugnut.FlugnutAndEngine.utils;

import com.pkp.flugnut.FlugnutAndEngine.game.Settings;
import org.andengine.audio.music.Music;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 6/6/13
 * Time: 8:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utilities {

    public static void playMusic(Music mMusic) {
        if (Settings.musicEnabled && mMusic != null && !mMusic.isPlaying()) {
            mMusic.play();
        }
    }

}
