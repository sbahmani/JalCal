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

/**
 * This class contains some utility functions and constants used by other Persian Calendar classes.
 *
 * @author sjb
 */
public class PersianCalendarUtils {

    /**
     * Julian day corresponding to 1 Farvardin 1 A.H., corresponding to March 19, 622 A.D. by the Julian version of the Gregorian calendar.
     */
    public static final long EPOCH = 1948321;

    /**
     * A modulo function suitable for our purpose.
     *
     * @param a the dividend.
     * @param b the divisor.
     * @return the remainder of integer division.
     */
    public static long mod(double a, double b) {
        return (long) (a - b * Math.floor(a / b));
    }

    /**
     * An integer division function suitable for our purpose.
     *
     * @param a the dividend.
     * @param b the divisor.
     * @return the quotient of integer division.
     */
    public static long div(double a, double b) {
        return (long) Math.floor(a / b);
    }

    /**
     * Extracts the year from a packed long value.
     *
     * @param r the packed long value.
     * @return the year part of date.
     */
    public static long y(long r) {
        return r >> 16;
    }

    /**
     * Extracts the month from a packed long value.
     *
     * @param r the packed long value .
     * @return the month part of date.
     */
    public static int m(long r) {
        return (int) (r & 0xff00) >> 8;
    }

    /**
     * Extracts the day from a packed long value.
     *
     * @param r the packed long value.
     * @return the day part of date.
     */
    public static int d(long r) {
        return (int) (r & 0xff);
    }
}
