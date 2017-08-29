package com.github.sbahmani.jalcal.util;

/*
 * Copyright 2016 sjb.
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
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sjb
 */
public class JalaliDateHelper {

    /**
     * hh:mm:ss
     */
    private static final Pattern TIME_PATTERN = Pattern.compile("([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})");
    /**
     * dd/mm/yyyy
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{1,2})/([0-9]{1,2})/([0-9]{1,4})");
    private static final Pattern SIX_DIGIT_PATTERN = Pattern.compile("([0-9]{2})([0-9]{2})([0-9]{2})");

    /**
     * yyyymmdd
     */
    private static final Pattern EIGHT_DATE_DIGIT_PATTERN = Pattern.compile("([0-9]{4})([0-9]{2})([0-9]{2})");

    /**
     *
     * @param date standard in java
     * @return yymmdd for example 930604 by 04/06/1393
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
     *
     * @param date standard in java
     * @return yyyy/mm/dd for example 1393/06/04 by 04/06/1393
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
     *
     * @param date standard in java
     * @return hhmmss
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
     *
     * @param date standard in java
     * @return hh:mm:ss
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
     *
     * @param date for example 930604 by 04/06/1393
     * @param time for example 154840 by 15:48:40
     * @return standard date in java
     * @throws DateException convert fail
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
        Date jalaliToGregorian = JalCal.jalaliToGregorian(
                Integer.valueOf("13" + datematcher.group(1)),
                Integer.valueOf(datematcher.group(2)),
                Integer.valueOf(datematcher.group(3)),
                Integer.valueOf(timematcher.group(1)),
                Integer.valueOf(timematcher.group(2)),
                Integer.valueOf(timematcher.group(3)));
        return jalaliToGregorian;
    }

    /**
     *
     * @param date for example 13930604 by 04/06/1393
     * @param time for example 154840 by 15:48:40
     * @return standard date in java
     * @throws DateException convert fail
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
        Date jalaliToGregorian = JalCal.jalaliToGregorian(
                Integer.valueOf(datematcher.group(1)),
                Integer.valueOf(datematcher.group(2)),
                Integer.valueOf(datematcher.group(3)),
                Integer.valueOf(timematcher.group(1)),
                Integer.valueOf(timematcher.group(2)),
                Integer.valueOf(timematcher.group(3)));
        return jalaliToGregorian;
    }

}
