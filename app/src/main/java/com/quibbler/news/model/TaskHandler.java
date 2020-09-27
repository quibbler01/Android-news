package com.quibbler.news.model;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskHandler {
    private static final int CPU = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, CPU, 8000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    public static void executeUpdateTask(Runnable updateNewsRunnable) {
        executor.execute(updateNewsRunnable);
    }

    public static void shutdownExecutor() {
        executor.shutdown();
    }

}
