package com.github.sbahmani.jalcal.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JalaliDateHelper - A utility class for converting between Gregorian and Jalali (Persian) calendar dates
 * This class provides methods to convert dates between different formats and calendar systems.
 * It supports various date and time formats including slash-separated dates and digital time formats.
 *
 * @author sjb
 * @version 1.0
 */
public class JalaliDateHelper {

    /**
     * Constructor for JalaliDateHelper
     * This is a utility class with only static methods, so the constructor is private
     * to prevent instantiation.
     */
    private JalaliDateHelper() {
        // Private constructor to prevent instantiation
        // This is a utility class that should not be instantiated
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Regular expression pattern for matching time in format hh:mm:ss
     * Examples: "23:59:59", "01:30:00"
     */
    private static final Pattern TIME_PATTERN = Pattern.compile("([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})");

    /**
     * Regular expression pattern for matching date in format dd/mm/yyyy
     * Examples: "13/07/1399", "1/1/1400"
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{1,2})/([0-9]{1,2})/([0-9]{1,4})");

    /**
     * Regular expression pattern for matching six-digit numbers
     * Used for parsing shortened date/time formats
     * Examples: "931215" (yymmdd), "235959" (hhmmss)
     */
    private static final Pattern SIX_DIGIT_PATTERN = Pattern.compile("([0-9]{2})([0-9]{2})([0-9]{2})");

    /**
     * Regular expression pattern for matching eight-digit date format yyyymmdd
     * Example: "13991230" represents 30th day of 12th month of year 1399
     */
    private static final Pattern EIGHT_DATE_DIGIT_PATTERN = Pattern.compile("([0-9]{4})([0-9]{2})([0-9]{2})");

    /**
     * Converts a Gregorian date to Jalali date in six-digit format (yymmdd)
     *
     * @param date The Gregorian date to convert (java.util.Date)
     * @return String A six-digit string representing date in format yymmdd (e.g., "990415" for 15/04/1399)
     * Returns null if conversion fails
     */
    public static String convertToJalaliDateFormat(Date date) {
        String retVal = null;
        String nowgregorianToJalaliDate = JalCal.gregorianToJalaliDate(date, true);
        Matcher matcher = DATE_PATTERN.matcher(nowgregorianToJalaliDate);
        if (matcher.find()) {
            retVal = matcher.group(3).substring(2, 4);
            retVal += (matcher.group(2).length() == 2) ? matcher.group(2) : "0" + matcher.group(2);
            retVal += (matcher.group(1).length() == 2) ? matcher.group(1) : "0" + matcher.group(1);
        }
        return retVal;
    }

    /**
     * Converts a Gregorian date to Jalali date in slash-separated format with 4-digit year
     *
     * @param date The Gregorian date to convert (java.util.Date)
     * @return String Date in format yyyy/mm/dd (e.g., "1399/06/31")
     * Returns null if conversion fails
     */
    public static String convertToJalali4DigitSlashDateFormat(Date date) {
        String retVal = null;
        String nowgregorianToJalaliDate = JalCal.gregorianToJalaliDate(date, true);
        Matcher matcher = DATE_PATTERN.matcher(nowgregorianToJalaliDate);
        if (matcher.find()) {
            retVal = matcher.group(3).substring(0, 4) + "/";
            retVal += (matcher.group(2).length() == 2) ? matcher.group(2) : "0" + matcher.group(2);
            retVal += "/";
            retVal += (matcher.group(1).length() == 2) ? matcher.group(1) : "0" + matcher.group(1);
        }
        return retVal;
    }

    /**
     * Converts a Date object's time component to six-digit time format
     *
     * @param date The date containing the time to convert
     * @return String Time in format hhmmss (e.g., "235959" for 23:59:59)
     * Returns null if conversion fails
     */
    public static String convertToTimeFormat(Date date) {
        String retVal = null;
        String nowgregorianToJalaliDate = JalCal.gregorianToJalaliTime(date);
        Matcher matcher = TIME_PATTERN.matcher(nowgregorianToJalaliDate);
        if (matcher.find()) {
            retVal = (matcher.group(1).length() == 2) ? matcher.group(1) : "0" + matcher.group(1);
            retVal += (matcher.group(2).length() == 2) ? matcher.group(2) : "0" + matcher.group(2);
            retVal += (matcher.group(3).length() == 2) ? matcher.group(3) : "0" + matcher.group(3);
        }
        return retVal;
    }

    /**
     * Converts a Date object's time component to colon-separated digital format
     *
     * @param date The date containing the time to convert
     * @return String Time in format hh:mm:ss (e.g., "23:59:59")
     * Returns null if conversion fails
     */
    public static String convertToTimeDigitalFormat(Date date) {
        String retVal = null;
        String nowgregorianToJalaliDate = JalCal.gregorianToJalaliTime(date);
        Matcher matcher = TIME_PATTERN.matcher(nowgregorianToJalaliDate);
        if (matcher.find()) {
            retVal = (matcher.group(1).length() == 2) ? matcher.group(1) : "0" + matcher.group(1);
            retVal += ":";
            retVal += (matcher.group(2).length() == 2) ? matcher.group(2) : "0" + matcher.group(2);
            retVal += ":";
            retVal += (matcher.group(3).length() == 2) ? matcher.group(3) : "0" + matcher.group(3);
        }
        return retVal;
    }

    /**
     * Extracts a Java Date object from Jalali date and time strings (assuming 1300s Persian calendar)
     * This method automatically prepends "13" to the year, making it suitable for dates in the 1300s Jalali calendar
     *
     * @param date Six-digit Jalali date string in format yymmdd (e.g., "990631" for 31/06/1399)
     * @param time Six-digit time string in format hhmmss (e.g., "235959" for 23:59:59)
     * @return Date A standard Java Date object representing the given Jalali datetime
     * @throws DateException         If conversion fails or input format is invalid
     * @throws IllegalStateException If the date or time format is invalid
     */
    public static Date extractDateFromJalaliDateTimeIn1300(String date, String time) throws DateException {
        Matcher timematcher = SIX_DIGIT_PATTERN.matcher(time);
        if (!timematcher.find()) {
            throw new IllegalStateException("time format invalid");
        }
        Matcher datematcher = SIX_DIGIT_PATTERN.matcher(date);
        if (!datematcher.find()) {
            throw new IllegalStateException("D format invalid");
        }
        return JalCal.jalaliToGregorian(
                Integer.parseInt("13" + datematcher.group(1)),
                Integer.parseInt(datematcher.group(2)),
                Integer.parseInt(datematcher.group(3)),
                Integer.parseInt(timematcher.group(1)),
                Integer.parseInt(timematcher.group(2)),
                Integer.parseInt(timematcher.group(3)));
    }

    /**
     * Extracts a Java Date object from Jalali date and time strings
     * Uses full four-digit year format for dates
     *
     * @param date Eight-digit Jalali date string in format yyyymmdd (e.g., "13990631" for 31/06/1399)
     * @param time Six-digit time string in format hhmmss (e.g., "235959" for 23:59:59)
     * @return Date A standard Java Date object representing the given Jalali datetime
     * @throws DateException         If conversion fails or input format is invalid
     * @throws IllegalStateException If the date or time format is invalid
     */
    public static Date extractDateFromJalaliDateTime(String date, String time) throws DateException {
        Matcher timematcher = SIX_DIGIT_PATTERN.matcher(time);
        if (!timematcher.find()) {
            throw new IllegalStateException("time format invalid");
        }
        Matcher datematcher = EIGHT_DATE_DIGIT_PATTERN.matcher(date);
        if (!datematcher.find()) {
            throw new IllegalStateException("date format invalid");
        }
        return JalCal.jalaliToGregorian(
                Integer.parseInt(datematcher.group(1)),
                Integer.parseInt(datematcher.group(2)),
                Integer.parseInt(datematcher.group(3)),
                Integer.parseInt(timematcher.group(1)),
                Integer.parseInt(timematcher.group(2)),
                Integer.parseInt(timematcher.group(3)));
    }
}