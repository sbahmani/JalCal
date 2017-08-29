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

import static com.github.sbahmani.jalcal.util.PersianCalendarUtils.*;

/**
 * This class contains algorithms for converting Julian days to the Persian calendar system, and vice versa. The algorithms have been rewritten and some bugs in
 * the Java code have been corrected, but, originally, they have been based on the "<a href="http://couprie.docspages.com/calmath/">Calendar Math Add-In for
 * Excel</a>" by <a href="mailto:kees@couprie.org">Kees Couprie</a>, whose contribution is gratefully acknowledged.
 *
 * @author sjb
 */
public class PersianCalendarHelper {

    /**
     * Determines if the specified year is a leap year in the Persian calendar.
     *
     * @param year the "Persian" year.
     * @return <code>true</code> if <code>year</code> is a leap year, <code>false</code> otherwise.
     */
    public static boolean isLeapYear(long year) {
        long a = year - 474L;
        long b = mod(a, 2820L) + 474L;
        return mod((b + 38D) * 682D, 2816D) < 682L;
    }

    /**
     * Returns the Julian day corresponding to the specified date in the Persian calendar.
     *
     * @param y the Persian year.
     * @param m the Persian month.
     * @param d the Persian day.
     * @return the Julian day corresponding to the specified date in the Persian calendar.
     */
    public static long pj(long y, int m, int d) {
        long a = y - 474L;
        long b = mod(a, 2820D) + 474L;
        return (EPOCH - 1L) + 1029983L * div(a, 2820D) + 365L * (b - 1L) + div(682L * b - 110L, 2816D) + (long) (m > 6 ? 30 * m + 6 : 31 * m) + (long) d;
    }

    /**
     * Returns the date in the Persian calendar corresponding to the specified Julian day. The date fields (year, month, and day) are packed into a long value.
     * See <code>{@link PersianCalendarUtils}</code> class for extraction of fields from the packed long value.
     *
     * @param j the Julian day.
     * @return a packed long value containing the corresponding Persian year, month, and day.
     */
    public static long jp(long j) {
        long a = j - pj(475L, 0, 1);
        long b = div(a, 1029983D);
        long c = mod(a, 1029983D);
        long d = c != 1029982L ? div(2816D * (double) c + 1031337D, 1028522D) : 2820L;
        long year = 474L + 2820L * b + d;
        long f = (1L + j) - pj(year, 0, 1);
        int month = (int) (f > 186L ? Math.ceil((double) (f - 6L) / 30D) - 1 : Math.ceil((double) f / 31D) - 1);
        int day = (int) (j - (pj(year, month, 1) - 1L));
        return (year << 16) | (month << 8) | day;
    }
}
