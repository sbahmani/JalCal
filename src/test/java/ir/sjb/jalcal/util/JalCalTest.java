/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.sjb.jalcal.util;

import java.util.Calendar;
import org.junit.Test;
import static org.fest.assertions.Assertions.*;

/**
 *
 * @author sjb
 */
public class JalCalTest {

    @Test
    public void testJalaliToGregorian() throws DateException {
        assertThat(JalCal.jalaliToGregorian(1393, 5, 14, 10, 2, 4).toInstant().toString()).isEqualTo("2014-08-05T05:32:04Z");
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
        assertThat(JalCal.gregorianToJalali(cal.getTime())).isEqualTo("14/4/1393   10:25:1");
    }

}
