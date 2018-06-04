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
import java.util.TimeZone;
import static org.fest.assertions.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author sjb
 */
public class JalCalTest {

    @Test
    public void testJalaliToGregorian() throws DateException {
        System.setProperty("user.timezone", "Asia/Tehran");
        Calendar expected1 = Calendar.getInstance(TimeZone.getDefault());
        expected1.set(2014, 7, 5, 10, 2, 4);
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 10, 2, 4).toString()).isEqualTo(expected1.getTime().toString());

        Calendar expected2 = Calendar.getInstance(TimeZone.getDefault());
        expected2.set(2014, 7, 5, 12, 1, 1);
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 12, 1, 1).toString()).isEqualTo(expected2.getTime().toString());

        Calendar expected3 = Calendar.getInstance(TimeZone.getDefault());
        expected3.set(2014, 7, 5, 23, 1, 1);
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 23, 1, 1).toString()).isEqualTo(expected3.getTime().toString());

        Calendar expected4 = Calendar.getInstance(TimeZone.getDefault());
        expected4.set(2014, 7, 5, 1, 23, 1);
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 1, 23, 1).toString()).isEqualTo(expected4.getTime().toString());

        Calendar expected5 = Calendar.getInstance(TimeZone.getDefault());
        expected5.set(2014, 7, 5, 0, 0, 0);
        assertThat(JalCal.jalaliToGregorian("1393/5/14").toString()).isEqualTo(expected5.getTime().toString());

        Calendar expected6 = Calendar.getInstance(TimeZone.getDefault());
        expected6.set(2014, 7, 5, 0, 0, 0);
        assertThat(JalCal.jalaliToGregorian("14/5/1393").toString()).isEqualTo(expected5.getTime().toString());
    }

    @Test
    public void testGregorianToJalali() throws DateException {
        System.setProperty("user.timezone", "Asia/Tehran");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 5);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 25);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 0);
        assertThat(JalCal.gregorianToJalali(cal.getTime(), true)).isEqualTo("14/04/1393   10:25:01");
        assertThat(JalCal.gregorianToJalali(new Date(1426883400000l), true)).isEqualTo("01/01/1394   00:00:00");

    }

    @Test
    public void testJalaliToGregorianWithHourMinSec() throws DateException {
        System.setProperty("user.timezone", "Asia/Tehran");
        Long time = 1520956290000l;
        String j1 = JalCal.gregorianToJalali(new Date(time), false);
        Date d1 = JalCal.JalaliToGregorianWithHourMinSec(j1);
        assertThat(d1.getTime()).isEqualTo(time);
        String j2 = JalCal.gregorianToJalali(new Date(time), true);
        Date d2 = JalCal.JalaliToGregorianWithHourMinSec(j2);
        assertThat(d2.getTime()).isEqualTo(time);

    }

}
