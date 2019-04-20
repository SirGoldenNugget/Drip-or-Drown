package com.minhvu.dripordrown.essentials;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
    public Clip SAMPLE;

    private InputStream inputstream;
    private AudioInputStream audioinputstream;

    public Sound() {
//        SAMPLE = getAudioClip("/sounds/sample.wav");
    }

    private Clip getAudioClip(String path) {
        Clip clip = null;

        inputstream = getClass().getResourceAsStream(path);

        try {
            audioinputstream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputstream));

            try {
                clip = AudioSystem.getClip();
                clip.open(audioinputstream);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return clip;
    }
}