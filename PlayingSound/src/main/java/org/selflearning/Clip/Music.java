package org.selflearning.Clip;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Music implements LineListener {
    public Music() {
    }

    private boolean playCompleted;

    public void play(String audioFilePath) {
        File auidoFile = new File(audioFilePath);


        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(auidoFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();
            long startTime = System.currentTimeMillis();
            long endTime;
            while(!playCompleted) {
                try {
                    Thread.sleep(1000);
                    endTime = System.currentTimeMillis();
                    if ((endTime-startTime) / 1000  >= 60) {
                        playCompleted = true;
                    }
                    System.out.println("startTime: " + startTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            audioClip.close();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started");
        } else if(type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed!!");
        }
    }

    public static void main(String[] args) {
        String audioFilePath = "/home/ledinhduy/developer/java/PlayingSound/playingwithfire.wav";
        Music player = new Music();
        player.play(audioFilePath);
    }
}
