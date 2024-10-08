package com.app.utils;

import com.app.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public final class DateUtils {
    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, AppConstant.DATE_TIME_FORMAT);
    }

    public static Date converDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            log.error("Error converting date", e);
            return null;
        }
    }

    public static Date converDate(String date) {
        return converDate(date, AppConstant.DATE_TIME_FORMAT);
    }

    public static boolean isInRangeXMinutesAgo(Date date, int minutes) {
        return Instant.now().minus(Duration.ofMinutes(minutes)).isBefore(date.toInstant());
    }

    public static boolean isAtLeastXSecondsAgo(Date date, int seconds) {
        return date.toInstant().isBefore(Instant.now().minus(Duration.ofSeconds(seconds)));
    }

    public static Date startOfDay(Date date) {
        return Date.from(date.toInstant().atOffset(ZoneOffset.UTC)
                .withHour(0).withMinute(0).withSecond(0).withNano(0)
                .toInstant());
    }

    public static Date endOfDay(Date date) {
        return Date.from(date.toInstant().atOffset(ZoneOffset.UTC)
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999)
                .toInstant());
    }

    public static Date convertLocalDate2Date(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertDate2LocalDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date startOfDayUTC(Date sourceDate, TimeZone timeZone) {
        return convertToUTC(sourceDate, timeZone, "00:00:00");
    }

    public static Date endOfDayUTC(Date sourceDate, TimeZone timeZone) {
        return convertToUTC(sourceDate, timeZone, "23:59:59");
    }

    private static Date convertToUTC(Date sourceDate, TimeZone timeZone, String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConstant.DATE_TIME_FORMAT);
            sdf.setTimeZone(timeZone);
            return sdf.parse(sdf.format(sourceDate).split(" ")[0] + " " + time);
        } catch (Exception e) {
            log.error("Error converting to UTC", e);
            return null;
        }
    }

    public static Date getCurrentStoreDate(TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstant.DATE_TIME_FORMAT);
        sdf.setTimeZone(timeZone);
        return converDate(sdf.format(new Date()), AppConstant.DATE_TIME_FORMAT);
    }

    public static String getOffset(TimeZone tz) {
        int offsetInMillis = tz.getOffset(System.currentTimeMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        return (offsetInMillis >= 0 ? "+" : "-") + offset;
    }

    public static Date convertUTCToVN(Date utcDate) {
        return Date.from(utcDate.toInstant().atZone(ZoneOffset.UTC).plusHours(7).toInstant());
    }
}

