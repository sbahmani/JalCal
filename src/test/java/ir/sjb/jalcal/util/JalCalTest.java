/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.sjb.jalcal.util;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.fest.assertions.Assertions.*;

/**
 *
 * @author sjb
 */
public class JalCalTest {

    @Test
    public void testJalaliToGregorian() throws DateException {
        System.out.println(JalCal.jalaliToGregorian(1393, 5, 14, 10, 2, 4).getTime());
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 10, 2, 4).getTime()).isEqualTo(1407216724000l);
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 12, 1, 1).getTime()).isEqualTo(1407223861000l);
        assertThat(JalCal.jalaliToGregorian("1393/5/14").getTime()).isEqualTo(1407180600000l);
        assertThat(JalCal.jalaliToGregorian("14/5/1393").getTime()).isEqualTo(1407180600000l);
    }

    @Test
    public void testGregorianToJalali() throws DateException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 5);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 25);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 0);
        assertThat(JalCal.gregorianToJalali(cal.getTime(), true)).isEqualTo("14/4/1393   10:25:1");
    }

}
