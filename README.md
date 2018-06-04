[![Build Status](https://travis-ci.org/sbahmani/JalCal.svg?branch=master)](https://travis-ci.org/sbahmani/JalCal)

# JalCal

Jalali(Persian) Calender Convertor in Java

## How to Use

```
<dependency>
    <groupId>com.github.sbahmani</groupId>
    <artifactId>jalcal</artifactId>
    <version>1.4</version>
</dependency>
```

## Example

- test Jalali to Gregorian

  ```
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
  ```

- test Gregorian to Jalali

  ```
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
  ```

- test Helper

  ```
    Date now = new Date(1482148019498l);
    String date1 = JalaliDateHelper.convertToJalali4DigitSlashDateFormat(now);
    Assertions.assertThat(date1).isEqualTo("1395/09/29");
    String date2 = JalaliDateHelper.convertToTimeDigitalFormat(now);
    Assertions.assertThat(date2).isEqualTo("15:16:59");
  ```

- test Jalali Convert with Hours and Minute and Second 
  ```
    Long time = 1520956290000l;
    String j1 = JalCal.gregorianToJalali(new Date(time), false);
    Date d1 = JalCal.JalaliToGregorianWithHourMinSec(j1);
    assertThat(d1.getTime()).isEqualTo(time);
    String j2 = JalCal.gregorianToJalali(new Date(time), true);
    Date d2 = JalCal.JalaliToGregorianWithHourMinSec(j2);
    assertThat(d2.getTime()).isEqualTo(time);
  ```
