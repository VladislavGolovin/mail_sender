package com.ss.mailsender.libs;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility methods for date conversion
 */
public class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(dateTime.getYear(), dateTime.getMonthValue()-1, dateTime.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
        return calendar.getTime();
    }
}
