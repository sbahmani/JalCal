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
package ir.sjb.jalcal.util.icu;

import com.ibm.icu.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.text.DateFormat;

import static ir.sjb.jalcal.util.PersianCalendarUtils.*;
import static ir.sjb.jalcal.util.PersianCalendarHelper.*;
import ir.sjb.jalcal.util.PersianCalendarConstants;

/**
 * <p>
 * This is an <em>arithmetic</em> implementation of the Persian Calendar (also known as the Iranian or Jalali Calendar) based on the calendar framework of
 * <strong>ICU4J</strong> (IBM's International Components for Unicode for Java), version 3.2.</p>
 *
 * <p>
 * ICU4J is copyright International Business Machines Corporation (IBM). Please see the file <code>icu4j_3_2_license.html</code> included with this software
 * package for information about ICU4J license.</p>
 *
 * <p>
 * An astronomical version of the Persian Calendar may be implemented in a future release.</p>
 *
 * <p>
 * <strong><big>Usage Example</big></strong></p>
 * <p>
 * The following code snippet shows how to use the <code>{@link PersianCalendar}</code> class:
 * <pre>
 * import java.util.Date;
 * import com.ibm.icu.util.TimeZone;
 * import com.ibm.icu.util.ULocale;
 * import com.ibm.icu.text.DateFormat;
 * import ir.sjb.jalcal.util.icu.PersianCalendar;
 * // ...
 *
 * PersianCalendar pc = new PersianCalendar(TimeZone.getTimeZone("Asia/Tehran"));
 * DateFormat df = pc.getDateTimeFormat(DateFormat.FULL, DateFormat.DEFAULT, new ULocale("fa", "IR", ""));
 * String result = df.format(new Date());
 * // ...
 * </pre>
 * <p>
 * Some possible results are shown below:</p>
 * <table border="1">
 * <tr>
 * <th>Locale</th>
 * <th>Formatted String</th></tr>
 * <tr>
 * <td dir="rtl">&#x0641;&#x0627;&#x0631;&#x0633;&#x06CC;</td>
 * <td dir="rtl">&#1580;&#1605;&#1593;&#1607;&#1548; &#1783; &#1575;&#1587;&#1601;&#1606;&#1583; &#1777;&#1779;&#1784;&#1779;
 * &#1777;&#1778;:&#1776;&#1776;:&#1779;&#1785;</td></tr>
 * <tr>
 * <td dir="rtl">&#x0641;&#x0627;&#x0631;&#x0633;&#x06CC; (&#x0627;&#x0641;&#x063A;&#x0627;&#x0646;&#x0633;&#x062A;&#x0627;&#x0646;)</td>
 * <td dir="rtl">&#1580;&#1605;&#1593;&#1607;&#1548; &#1783; &#1581;&#1608;&#1578; &#1777;&#1779;&#1784;&#1779;
 * &#1777;&#1778;:&#1776;&#1776;:&#1779;&#1785;</td></tr>
 * <tr>
 * <td dir="rtl">&#x0627;&#x0644;&#x0639;&#x0631;&#x0628;&#x064A;&#x0629;</td>
 * <td dir="rtl">&#1575;&#1604;&#1580;&#1605;&#1593;&#1577;, &#1639; &#1573;&#1587;&#1601;&#1606;&#1583;, &#1633;&#1635;&#1640;&#1635;
 * &#1633;&#1634;:&#1632;&#1632;:&#1635;&#1641; &#1605;</td></tr>
 * <tr>
 * <td>English</td>
 * <td>Friday, Esfand 7, 1383 12:00:39 PM</td></tr>
 * <tr>
 * <td>Fran&#x00E7;ais</td>
 * <td>vendredi 7 Esfand 1383 12:00:39</td></tr>
 * <tr>
 * <td>Deutsch</td>
 * <td>Freitag, 7. Esfand 1383 12:00:39</td></tr>
 * <tr>
 * <td>&#x0420;&#x0443;&#x0441;&#x0441;&#x043A;&#x0438;&#x0439;</td>
 * <td>7 &#1069;&#1089;&#1092;&#1072;&#1085;&#1076; 1383 &#1075;. 12:00:39</td></tr>
 * <tr>
 * <td>T&#x00FC;rk&#x00E7;e</td>
 * <td>07 Isfend 1383 Cuma 12:00:39</td></tr>
 * <tr>
 * <td>Esperanto</td>
 * <td>vendredo, 7-a de esfando 1383 12:00:39</td></tr></table>
 *
 * @author sjb
 */
public class PersianCalendar extends Calendar implements PersianCalendarConstants {

    private static String copyright = "Copyright \u00a9 2005, Ghasem Kiani. All Rights Reserved.";
    /**
     * Before Hijra Era.
     */
    public static final int BH = 0;
    /**
     * After Hijra Era.
     */
    public static final int AH = 1;

    /**
     * Constructs a Persian calendar with the default time zone and locale.
     */
    public PersianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault());
    }

    /**
     * Constructs a Persian calendar with the specified time zone and the default locale.
     *
     * @param zone the desired timezone.
     */
    public PersianCalendar(TimeZone zone) {
        this(zone, ULocale.getDefault());
    }

    /**
     * Constructs a Persian calendar with the default time zone and the specified locale.
     *
     * @param aLocale the desired locale.
     */
    public PersianCalendar(Locale aLocale) {
        this(ULocale.forLocale(aLocale));
    }

    /**
     * Constructs a Persian calendar with the default time zone and the specified locale.
     *
     * @param locale the desired locale.
     */
    public PersianCalendar(ULocale locale) {
        this(TimeZone.getDefault(), locale);
    }

    /**
     * Constructs a Persian calendar with the specified time zone and locale.
     *
     * @param zone the desired timezone.
     * @param aLocale the desired locale.
     */
    public PersianCalendar(TimeZone zone, Locale aLocale) {
        this(zone, ULocale.forLocale(aLocale));
    }

    /**
     * Constructs a Persian calendar with the specified time zone and locale.
     *
     * @param zone the desired timezone.
     * @param locale the desired locale.
     */
    public PersianCalendar(TimeZone zone, ULocale locale) {
        super(zone, locale);
        setTimeInMillis(System.currentTimeMillis());
    }

    /**
     * Constructs a Persian calendar with the default time zone and locale and sets its time to the specified date-time.
     *
     * @param date the date of this calendar object.
     */
    public PersianCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault());
        setTime(date);
    }

    /**
     * Constructs a Persian calendar with the default time zone and locale and sets its time to the specified date.
     *
     * @param year the Persian year.
     * @param month the Persian month (zero-based).
     * @param date the Persian day of month.
     */
    public PersianCalendar(int year, int month, int date) {
        super(TimeZone.getDefault(), ULocale.getDefault());
        set(ERA, AH);
        set(YEAR, year);
        set(MONTH, month);
        set(DATE, date);
    }

    /**
     * Constructs a Persian calendar with the default time zone and locale and sets its time to the specified time.
     *
     * @param year the Persian year.
     * @param month the Persian month (zero-based).
     * @param date the Persian day of month.
     * @param hour the hours part of time.
     * @param minute the minutes part of time.
     * @param second the seconds part of time.
     */
    public PersianCalendar(int year, int month, int date, int hour, int minute, int second) {
        super(TimeZone.getDefault(), ULocale.getDefault());
        set(ERA, AH);
        set(YEAR, year);
        set(MONTH, month);
        set(DATE, date);
        set(HOUR_OF_DAY, hour);
        set(MINUTE, minute);
        set(SECOND, second);
    }
    private static final int LIMITS[][]
            = {
                // Minimum, GreatestMinimum, LeastMaximum, Maximum
                {0, 0, 1, 1}, // ERA
                {1, 1, 5000000, 5000000}, // YEAR
                {0, 0, 11, 11}, // MONTH
                {1, 1, 51, 52}, // WEEK_OF_YEAR
                {0, 0, 5, 6}, // WEEK_OF_MONTH
                {1, 1, 29, 31}, // DAY_OF_MONTH
                {1, 1, 365, 366}, // DAY_OF_YEAR
                { /* */}, // DAY_OF_WEEK
                {-1, -1, 4, 5}, // DAY_OF_WEEK_IN_MONTH
                { /* */}, // AM_PM
                { /* */}, // HOUR
                { /* */}, // HOUR_OF_DAY
                { /* */}, // MINUTE
                { /* */}, // SECOND
                { /* */}, // MILLISECOND
                { /* */}, // ZONE_OFFSET
                { /* */}, // DST_OFFSET
                {-5000001, -5000001, 5000001, 5000001}, // YEAR_WOY
                { /* */}, // DOW_LOCAL
                {-5000000, -5000000, 5000000, 5000000}, // EXTENDED_YEAR
                { /* */}, // JULIAN_DAY
                { /* */}, // MILLISECONDS_IN_DAY
            };

    @Override
    protected int handleGetLimit(int field, int limitType) {
        return LIMITS[field][limitType];
    }

    @Override
    protected int handleGetMonthLength(int extendedYear, int month) {
        if (month < 6) {
            return 31;
        }
        if (month < 11) {
            return 30;
        }
        return isLeapYear(extendedYear) ? 30 : 29;
    }

    @Override
    protected int handleGetYearLength(int extendedYear) {
        return isLeapYear(extendedYear) ? 366 : 365;
    }

    @Override
    protected int handleComputeMonthStart(int extendedYear, int month, boolean useMonth) {
        return (int) pj(extendedYear, month, 0);
    }

    @Override
    protected int handleGetExtendedYear() {
        int year;
        if (newerField(EXTENDED_YEAR, YEAR) == EXTENDED_YEAR) {
            year = internalGet(EXTENDED_YEAR, 1);
        } else {
            int era = internalGet(ERA, AH);
            if (era == BH) {
                year = 1 - internalGet(YEAR, 1); // Convert to extended year
            } else {
                year = internalGet(YEAR, 1);
            }
        }
        return year;
    }

    @Override
    protected void handleComputeFields(int julianDay) {
        long r = jp(julianDay);
        int year = (int) y(r);
        int month = m(r);
        int day = d(r);
        internalSet(ERA, year > 0 ? AH : BH);
        internalSet(YEAR, year > 0 ? year : 1 - year);
        internalSet(EXTENDED_YEAR, year);
        internalSet(MONTH, month);
        internalSet(DAY_OF_MONTH, day);
        internalSet(DAY_OF_YEAR, day + month * 30 + Math.min(6, month));
    }

    @Override
    protected DateFormat handleGetDateFormat(String pattern, ULocale locale) {
        return new PersianDateFormat(pattern, locale);
    }

    /**
     * Adds the specified amount to the specified field of this calendar. This is overriden to correct the behavior at the end of the leap years.
     *
     * @param field the field index.
     * @param amount the amount to add.
     */
    @Override
    public void add(int field, int amount) {
        switch (field) {
            case MONTH: {
                int month = get(MONTH);
                int extendedYear = get(EXTENDED_YEAR);
                if (amount > 0) {
                    extendedYear += amount / 12;
                    month += amount % 12;
                    if (month > 11) {
                        month -= 12;
                        extendedYear++;
                    }
                } else {
                    amount = -amount;
                    extendedYear -= amount / 12;
                    month -= amount % 12;
                    if (month < 0) {
                        month += 12;
                        extendedYear--;
                    }
                }
                set(EXTENDED_YEAR, extendedYear);
                set(MONTH, month);
                pinField(DAY_OF_MONTH);
                break;
            }
            default:
                super.add(field, amount);
                break;
        }
    }

    /**
     * <p>
     * Type of this calendar.</p>
     * <p>
     * Type is used for loading resources. Since there is no calendar data for this type ("persian"), the <code>CalendarData</code> will use the fallback type
     * ("gregorian"). This is fine, just the month names and era names must be changed. This is taken care of by {@link PersianDateFormatSymbols}, which uses a
     * java resource bundle in its turn.</p>
     *
     * @return type of this calendar ("persian").
     */
    @Override
    public String getType() {
        return "persian";
    }
}
