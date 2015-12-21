package pe.area51.repetitivetask;


import android.os.Handler;

public class RepetitiveTask {

    private final int delay;

    private final Handler handler;
    private final Runnable internalRunnable;

    private boolean isRunning;

    public RepetitiveTask(final int frequencyInMillis, final Runnable task) {
        this.delay = frequencyInMillis;
        isRunning = false;
        handler = new Handler();
        internalRunnable = new Runnable() {
            @Override
            public void run() {
                task.run();
                handler.postDelayed(this, delay);
            }
        };
    }

    public boolean start(final boolean executeImmediately) {
        if (!isRunning) {
            isRunning = true;
            if (executeImmediately) {
                handler.post(internalRunnable);
            } else {
                handler.postDelayed(internalRunnable, delay);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean stop() {
        if (isRunning) {
            isRunning = false;
            handler.removeCallbacks(internalRunnable);
            return true;
        } else {
            return false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

}
