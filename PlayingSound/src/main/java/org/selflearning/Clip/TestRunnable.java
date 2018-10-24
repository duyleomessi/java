package org.selflearning.Clip;

public class TestRunnable implements Runnable {
   private Alarm alarm;
   private Music music;
    private volatile boolean isRunning;
    private boolean isTrigger;
    private String audioFilePath = "/home/ledinhduy/developer/java/PlayingSound/playingwithfire.wav";

    public TestRunnable() {
        alarm = new Alarm();
        music = new Music();
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {

                isTrigger = alarm.triggerAlarm(19, 52);
                if (isTrigger) {
                    isTrigger = false;
                    music.play(this.audioFilePath);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
