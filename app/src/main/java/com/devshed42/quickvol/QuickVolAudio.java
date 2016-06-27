package com.devshed42.quickvol;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by doug on 2016-06-25.
 */
public class QuickVolAudio {

    public static final void setRingerMode(Context context, int ringerMode) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(ringerMode);
    }

    public static final int getRingerMode(Context context) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audio.getRingerMode();
    }

}
