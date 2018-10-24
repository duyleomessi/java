package org.selflearning.Clip;

public class HelloThread extends Thread {
    // Code trong hàm run() sẽ được thực thi khi
    // thread được chạy (start)
    @Override
    public void run() {
        int index = 1;

        for (int i = 0; i < 10; i++) {
            System.out.println("  - HelloThread running " + index++);

            try {
                // Ngủ 1030 milli giây.
                Thread.sleep(1030);
            } catch (InterruptedException e) {
            }

        }
        System.out.println("  - ==> HelloThread stopped");
    }
}
