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
import java.util.TimeZone;

/**
 *
 * @author SjB
 */
public class JalCal {

    private JalCal() {
    }

    /**
     *
     * @param year in jalali
     * @param month in jalali (not zero based)
     * @param day in jalali
     * @param hour in local time
     * @param min in local time
     * @param second in local time
     * @return
     * @throws DateException
     */
    public static Date jalaliToGregorian(int year, int month, int day, int hour, int min, int second) throws DateException {
        if (year < 1000 || month > 12 || day > 31) {
            throw new DateException();
        }
        SimplePersianCalendar c = new SimplePersianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        c.setDateFields(year, month - 1, day);
        int yearMiladi = c.get(SimplePersianCalendar.ERA) == SimplePersianCalendar.AD ? c.get(SimplePersianCalendar.YEAR) : -(c.get(SimplePersianCalendar.YEAR) - 1);
        int monthMiladi = c.get(SimplePersianCalendar.MONTH);
        int dayMiladi = c.get(SimplePersianCalendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, dayMiladi);
        cal.set(Calendar.MONTH, monthMiladi);
        cal.set(Calendar.YEAR, yearMiladi);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }

    /**
     * @param date
     * @param dayAtFirst if true on return value day/month/year else year/month/day
     * @return day/month/year or year/month/day
     */
    public static String gregorianToJalaliDate(Date date, boolean dayAtFirst) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimplePersianCalendar spc = new SimplePersianCalendar();
        spc.setTimeZone(TimeZone.getTimeZone("UTC"));
        spc.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (dayAtFirst) {
            return String.valueOf(
                    (spc.getDateFields().getDay() < 10 ? "0" : "") + String.valueOf(spc.getDateFields().getDay()) + "/"
                    + (spc.getDateFields().getMonth() + 1 < 10 ? "0" : "") + String.valueOf(spc.getDateFields().getMonth() + 1) + "/"
                    + spc.getDateFields().getYear()
            );
        } else {
            return String.valueOf(
                    spc.getDateFields().getYear() + "/"
                    + (spc.getDateFields().getMonth() + 1 < 10 ? "0" : "") + String.valueOf(spc.getDateFields().getMonth() + 1) + "/"
                    + (spc.getDateFields().getDay() < 10 ? "0" : "") + String.valueOf(spc.getDateFields().getDay())
            );
        }

    }

    /**
     *
     * @param date
     * @return hour:min:sec
     */
    public static String gregorianToJalaliTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + (calendar.get(Calendar.MINUTE) < 10 ? "0" : "") + String.valueOf(calendar.get(Calendar.MINUTE)) + ":"
                + (calendar.get(Calendar.SECOND) < 10 ? "0" : "") + String.valueOf(calendar.get(Calendar.SECOND));
    }

    /**
     *
     * @param date need to be converted
     * @param dayAtFirst if true on return value day/month/year else year/month/day
     * @return
     */
    public static String gregorianToJalali(Date date, boolean dayAtFirst) {
        return gregorianToJalaliDate(date, dayAtFirst) + "   " + gregorianToJalaliTime(date);
    }

    private static int[] slashDateTokenizer(String input) throws DateException {
        //21/2/1389
        StringTokenizer tokenizer = new StringTokenizer(input, "/");
        if (tokenizer.countTokens() != 3) {
            throw new DateException();
        }
        int[] retInt = new int[3];
        try {
            retInt[0] = Integer.valueOf(tokenizer.nextToken());
            retInt[1] = Integer.valueOf(tokenizer.nextToken());
            retInt[2] = Integer.valueOf(tokenizer.nextToken());
        } catch (NumberFormatException ex) {
            throw new DateException();
        }
        return retInt;
    }

    public static Date jalaliToGregorian(String input) throws DateException {
        int[] intArr = slashDateTokenizer(input);
        try {
            Date retDate = jalaliToGregorian(intArr[0], intArr[1], intArr[2], 0, 0, 0);
            return retDate;
        } catch (DateException ex) {
            Date retDate = jalaliToGregorian(intArr[2], intArr[1], intArr[0], 0, 0, 0);
            return retDate;
        }

    }
}
