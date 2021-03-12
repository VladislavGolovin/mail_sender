package com.ss.mailsender.services;

import com.ss.mailsender.libs.DateTimeUtil;
import com.ss.mailsender.model.UploadingProcess;

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
        // clear threads list
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < threads.size(); i++) {
            ThreadSender thread = threads.get(i);
            UploadingProcess process = thread.getProcess();
            if (process.getFinishDateTime() == null)
                continue;

            calendar.setTime(DateTimeUtil.convertLocalDateTimeToDate(process.getFinishDateTime()));
            calendar.add(Calendar.SECOND, MAX_CACHE_TIME_SEC);
            if (now.after(calendar)) {
                threads.remove(i);
                i--;
            }

        }
        // clear threads list ##
    }

    public static List<ThreadSender> getThreads() {
        return Collections.unmodifiableList(threads);
    }
}
