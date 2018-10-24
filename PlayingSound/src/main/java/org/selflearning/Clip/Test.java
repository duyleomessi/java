package org.selflearning.Clip;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread running...");

//        Tao thread alarm
        Thread thread = new Thread(new TestRunnable());
        thread.start();

//        ngu 5 giay
        Thread.sleep(5000);
        System.out.println("Main thread stopped");
    }
}
