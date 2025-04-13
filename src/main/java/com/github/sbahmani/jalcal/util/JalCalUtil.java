package com.github.sbahmani.jalcal.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for converting between Persian (Jalali) and Gregorian calendars.
 * Provides fast implementations of calendar conversion algorithms.
 */
public class JalCalUtil {

    /**
     * Private constructor to prevent instantiation of utility class.
     * This class provides only static methods and should not be instantiated.
     */
    private JalCalUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * The epoch date of the Persian calendar (precalculated)
     */
    private static final long PERSIAN_EPOCH = 226896L;  // Precalculated result from Calendrical Calculations

    /**
     * The epoch date of the Gregorian calendar
     */
    private static final long GREGORIAN_EPOCH = 1L;     // Fixed date of start of the Gregorian calendar

    /**
     * Set of years that are exceptions to the standard leap year rule in the Persian calendar.
     * These years are not leap years despite what the standard rule would indicate.
     */
    private static final Set<Integer> NON_LEAP_CORRECTION = new HashSet<>(Arrays.asList(
            1502,
            1601, 1634, 1667,
            1700, 1733, 1766, 1799,
            1832, 1865, 1898,
            1931, 1964, 1997,
            2030, 2059, 2063, 2096,
            2129, 2158, 2162, 2191, 2195,
            2224, 2228, 2257, 2261, 2290, 2294,
            2323, 2327, 2356, 2360, 2389, 2393,
            2422, 2426, 2455, 2459, 2488, 2492,
            2521, 2525, 2554, 2558, 2587, 2591,
            2620, 2624, 2653, 2657, 2686, 2690,
            2719, 2723, 2748, 2752, 2756, 2781, 2785, 2789,
            2818, 2822, 2847, 2851, 2855, 2880, 2884, 2888,
            2913, 2917, 2921, 2946, 2950, 2954, 2979, 2983, 2987
    ));

    /**
     * Calculate ceiling division of two numbers.
     *
     * @param a the dividend
     * @param b the divisor
     * @return ceiling of a/b
     */
    private static long divCeil(long a, long b) {
        return Math.ceilDiv(a, b);
    }

    /**
     * Converts a Persian date to a fixed date number.
     *
     * @param year  the Persian year
     * @param month the Persian month (1-12)
     * @param day   the day of month
     * @return the fixed date number
     */
    public static long fixedFromPersianFast(long year, int month, int day) {
        long newYear = PERSIAN_EPOCH - 1 + 365 * (year - 1) + (8 * year + 21) / 33;
        if (NON_LEAP_CORRECTION.contains((int) (year - 1))) {
            newYear -= 1;
        }
        return newYear - 1 +
                (month <= 7 ? 31L * (month - 1) : 30L * (month - 1) + 6) +
                day;
    }

    /**
     * Converts a fixed date number to a Persian date.
     *
     * @param date the fixed date number
     * @return array containing [year, month, day]
     */
    public static long[] persianFastFromFixed(long date) {
        long daysSinceEpoch = date - fixedFromPersianFast(1, 1, 1);
        long year = 1 + (33 * daysSinceEpoch + 3) / 12053;
        long dayOfYear = date - fixedFromPersianFast(year, 1, 1) + 1;

        if (dayOfYear == 366 && NON_LEAP_CORRECTION.contains((int) year)) {
            year += 1;
            dayOfYear = 1;
        }

        int month;
        if (dayOfYear <= 186) {
            month = (int) divCeil(dayOfYear, 31);
        } else {
            month = (int) divCeil(dayOfYear - 6, 30);
        }
        long day = date - fixedFromPersianFast(year, month, 1) + 1;
        return new long[]{year, month, day};
    }

    /**
     * Determines if a Persian year is a leap year.
     *
     * @param year the Persian year to check
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean persianFastLeapYear(long year) {
        if (NON_LEAP_CORRECTION.contains((int) year)) {
            return false;
        } else if (NON_LEAP_CORRECTION.contains((int) (year - 1))) {
            return true;
        } else {
            return (25 * year + 11) % 33 < 8;
        }
    }

    /**
     * Determines if a Gregorian year is a leap year.
     *
     * @param year the Gregorian year to check
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean gregorianLeapYear(long year) {
        return year % 4 == 0 && (year % 400 != 100 && year % 400 != 200 && year % 400 != 300);
    }

    /**
     * Converts a Gregorian date to a fixed date number.
     *
     * @param year  the Gregorian year
     * @param month the Gregorian month (1-12)
     * @param day   the day of month
     * @return the fixed date number
     */
    public static long fixedFromGregorian(long year, int month, int day) {
        return 365 * (year - 1) + (year - 1) / 4 -
                (year - 1) / 100 +
                (year - 1) / 400 +
                (367L * month - 362) / 12 +
                (month <= 2 ? 0 : (gregorianLeapYear(year) ? -1 : -2)) +
                day;
    }

    /**
     * Gets the fixed date for Gregorian New Year's Day of given year.
     *
     * @param year the Gregorian year
     * @return fixed date of January 1 of given year
     */
    public static long gregorianNewYear(long year) {
        return fixedFromGregorian(year, 1, 1);
    }

    /**
     * Calculates the Gregorian year containing a given fixed date.
     *
     * @param date the fixed date number
     * @return the Gregorian year
     */
    public static long gregorianYearFromFixed(long date) {
        long d0 = date - GREGORIAN_EPOCH;
        long n400 = d0 / 146097;
        long d1 = d0 % 146097;
        long n100 = d1 / 36524;
        long d2 = d1 % 36524;
        long n4 = d2 / 1461;
        long d3 = d2 % 1461;
        long n1 = d3 / 365;
        long year = 400 * n400 + 100 * n100 + 4 * n4 + n1;

        if (n100 == 4 || n1 == 4) {
            return year;
        } else {
            return year + 1;
        }
    }

    /**
     * Converts a fixed date number to a Gregorian date.
     *
     * @param date the fixed date number
     * @return array containing [year, month, day]
     */
    public static long[] gregorianFromFixed(long date) {
        long year = gregorianYearFromFixed(date);
        long priorDays = date - gregorianNewYear(year);

        int correction;
        if (date < fixedFromGregorian(year, 3, 1)) {
            correction = 0;
        } else if (gregorianLeapYear(year)) {
            correction = 1;
        } else {
            correction = 2;
        }

        int month = (int) ((12 * (priorDays + correction) + 373) / 367);
        int day = (int) (date - fixedFromGregorian(year, month, 1) + 1);

        return new long[]{year, month, day};
    }
}