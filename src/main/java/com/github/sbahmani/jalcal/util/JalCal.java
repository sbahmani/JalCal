/*
 * Copyright 2014 sjb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.sbahmani.jalcal.util;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JalCal - Jalali Calendar Utility Class
 * This class provides utility methods for converting between Jalali (Persian)
 * and Gregorian calendar dates.
 *
 * @author SjB
 */
public class JalCal {

    /**
     * Regular expression pattern to match Jalali date-time strings in format:
     * yyyy/mm/dd hh:mm:ss or dd/mm/yyyy hh:mm:ss
     */
    private static final Pattern JALALI_DATE_PATTERN = Pattern.compile("(\\d*)/(\\d*)/(\\d*)\\s*(\\d*):(\\d*):(\\d*)");

    /**
     * Private constructor to prevent instantiation of utility class
     */
    private JalCal() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Converts a Jalali (Persian) date to a Gregorian date
     *
     * @param year   in jalali calendar (must be >= 1000)
     * @param month  in jalali calendar (1-12, not zero based)
     * @param day    in jalali calendar (1-31)
     * @param hour   in local time (0-23)
     * @param min    in local time (0-59)
     * @param second in local time (0-59)
     * @return java.util.Date object representing the Gregorian date
     * @throws DateException if the input parameters are invalid or conversion fails
     */
    public static Date jalaliToGregorian(int year, int month, int day, int hour, int min, int second) throws DateException {
        // Validate basic input parameters
        if (year < 1000 || month > 12 || day > 31) {
            throw new DateException();
        }
        // Convert Jalali date to Julian date
        long[] julian = JalCalUtil.gregorianFromFixed(JalCalUtil.fixedFromPersianFast(year, month, day));

        // Create Calendar instance and set all fields
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, (int) julian[2]);
        cal.set(Calendar.MONTH, (int) julian[1] - 1); // Calendar months are 0-based
        cal.set(Calendar.YEAR, (int) julian[0]);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * Converts a Gregorian date to Jalali (Persian) date string
     *
     * @param date       The Gregorian date to convert
     * @param dayAtFirst If true returns format dd/mm/yyyy, if false returns yyyy/mm/dd
     * @return Formatted string representation of Jalali date
     */
    public static String gregorianToJalaliDate(Date date, boolean dayAtFirst) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Convert to fixed day number then to Persian date
        long fixedFromGregorian = JalCalUtil.fixedFromGregorian(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        long[] persian = JalCalUtil.persianFastFromFixed(fixedFromGregorian);

        // Format based on dayAtFirst parameter
        if (dayAtFirst) {
            return (persian[2] < 10 ? "0" : "") + persian[2] + "/"
                    + (persian[1] < 10 ? "0" : "") + (persian[1]) + "/"
                    + persian[0];
        } else {
            return persian[0] + "/"
                    + (persian[1] < 10 ? "0" : "") + (persian[1]) + "/"
                    + (persian[2] < 10 ? "0" : "") + persian[2];
        }
    }

    /**
     * Extracts and formats time portion from a Gregorian date
     *
     * @param date The date containing the time to format
     * @return Formatted time string in HH:mm:ss format
     */
    public static String gregorianToJalaliTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + (calendar.get(Calendar.MINUTE) < 10 ? "0" : "") + calendar.get(Calendar.MINUTE) + ":"
                + (calendar.get(Calendar.SECOND) < 10 ? "0" : "") + calendar.get(Calendar.SECOND);
    }

    /**
     * Converts a Gregorian date to full Jalali date-time string
     *
     * @param date       The Gregorian date to convert
     * @param dayAtFirst If true returns format dd/mm/yyyy, if false returns yyyy/mm/dd
     * @return Combined Jalali date and time string
     */
    public static String gregorianToJalali(Date date, boolean dayAtFirst) {
        return gregorianToJalaliDate(date, dayAtFirst) + "   " + gregorianToJalaliTime(date);
    }

    /**
     * Returns the Persian (Farsi) name of the day of week
     *
     * @param date The date to get the day name for
     * @return Persian name of the day of week
     */
    public static String getPersianDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return switch (i) {
            case 1 -> "یکشنبه";
            case 2 -> "دوشنبه";
            case 3 -> "سه‌شنبه";
            case 4 -> "چهارشنبه";
            case 5 -> "پنجشنبه";
            case 6 -> "جمعه";
            case 7 -> "شنبه";
            default -> "هیچ شنبه";
        };
    }

    /**
     * Helper method to parse date strings in format dd/mm/yyyy or yyyy/mm/dd
     *
     * @param input Date string to parse
     * @return Array of [year/day, month, day/year] depending on input format
     * @throws DateException if parsing fails
     */
    private static int[] slashDateTokenizer(String input) throws DateException {
        StringTokenizer tokenizer = new StringTokenizer(input, "/");
        if (tokenizer.countTokens() != 3) {
            throw new DateException();
        }
        int[] retInt = new int[3];
        try {
            retInt[0] = Integer.parseInt(tokenizer.nextToken());
            retInt[1] = Integer.parseInt(tokenizer.nextToken());
            retInt[2] = Integer.parseInt(tokenizer.nextToken());
        } catch (NumberFormatException ex) {
            throw new DateException();
        }
        return retInt;
    }

    /**
     * Converts a Jalali date string to Gregorian date
     * Accepts both yyyy/mm/dd and dd/mm/yyyy formats
     *
     * @param input Jalali date string to convert
     * @return Gregorian date as java.util.Date
     * @throws DateException if conversion fails
     */
    public static Date jalaliToGregorian(String input) throws DateException {
        int[] intArr = slashDateTokenizer(input);
        try {
            // Try yyyy/mm/dd format first
            return jalaliToGregorian(intArr[0], intArr[1], intArr[2], 0, 0, 0);
        } catch (DateException ex) {
            // If that fails, try dd/mm/yyyy format
            return jalaliToGregorian(intArr[2], intArr[1], intArr[0], 0, 0, 0);
        }
    }

    /**
     * Converts a Jalali date-time string to Gregorian date
     * Accepts both yyyy/mm/dd hh:mm:ss and dd/mm/yyyy hh:mm:ss formats
     *
     * @param input Jalali date-time string to convert
     * @return Gregorian date as java.util.Date
     * @throws DateException if conversion fails or input format is invalid
     */
    public static Date JalaliToGregorianWithHourMinSec(String input) throws DateException {
        Matcher matcher = JALALI_DATE_PATTERN.matcher(input);
        if (matcher.matches()) {
            try {
                // Try yyyy/mm/dd hh:mm:ss format first
                return jalaliToGregorian(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6))
                );
            } catch (DateException ex) {
                // If that fails, try dd/mm/yyyy hh:mm:ss format
                return jalaliToGregorian(
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6))
                );
            }
        }
        throw new DateException();
    }
}