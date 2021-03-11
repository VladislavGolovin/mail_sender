package com.ss.mailsender.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ThreadsList {

    private static final int MAX_CACHE_TIME_SEC = 100;    // in seconds

    private static final List<ThreadSender> threads = new ArrayList<>();

    public static void addThread(ThreadSender thread) {
        clearCache();
        threads.add(thread);
    }


    public static void clearCache() {
//        // clear threads list
//        Calendar calendar = Calendar.getInstance();
//        Calendar now = Calendar.getInstance();
//        for (int i = 0; i < threads.size(); i++) {
//            ThreadSender object = threads.get(i);
//            if (object.getFinishDate() == null)
//                continue;
//
//            calendar.setTime(object.getFinishDate());
//            calendar.add(Calendar.SECOND, MAX_CACHE_TIME_SEC);
//            if (now.after(calendar)) {
//                threads.remove(i);
//                i--;
//            }
//
//        }
//        // clear threads list ##
    }

    public static List<ThreadSender> getThreads() {
        return Collections.unmodifiableList(threads);
    }
}
