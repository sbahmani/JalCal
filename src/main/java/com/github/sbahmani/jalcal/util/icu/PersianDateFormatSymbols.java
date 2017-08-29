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
package com.github.sbahmani.jalcal.util.icu;

import java.util.*;

import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.util.ULocale;

/**
 * This class uses a resource bundle to extract localized names of the Persian calendar eras and months.
 *
 * @author sjb
 */
public final class PersianDateFormatSymbols extends DateFormatSymbols {

    private static final String BUNDLE = "com.ghasemkiani.util.icu.Resources";

    /**
     * Creates a <code>PersianDateFormatSymbols</code> for the default locale.
     */
    public PersianDateFormatSymbols() {
        this(Locale.getDefault());
    }

    /**
     * Creates a <code>PersianDateFormatSymbols</code> for the specified locale.
     *
     * @param uLocale the provided locale for this object.
     */
    public PersianDateFormatSymbols(ULocale uLocale) {
        this(uLocale.toLocale());
    }

    /**
     * Creates a <code>PersianDateFormatSymbols</code> for the specified locale.
     *
     * @param locale the provided locale for this object.
     */
    public PersianDateFormatSymbols(Locale locale) {
        super(locale);
        initializePersianData(locale);
    }

    protected void initializePersianData(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle(BUNDLE, locale);
        setEras(new String[]{
            rb.getString("persianCalendar.era0"),
            rb.getString("persianCalendar.era1"),}
        );
        setMonths(new String[]{
            rb.getString("persianCalendar.month00"),
            rb.getString("persianCalendar.month01"),
            rb.getString("persianCalendar.month02"),
            rb.getString("persianCalendar.month03"),
            rb.getString("persianCalendar.month04"),
            rb.getString("persianCalendar.month05"),
            rb.getString("persianCalendar.month06"),
            rb.getString("persianCalendar.month07"),
            rb.getString("persianCalendar.month08"),
            rb.getString("persianCalendar.month09"),
            rb.getString("persianCalendar.month10"),
            rb.getString("persianCalendar.month11"),}
        );
        setShortMonths(new String[]{
            rb.getString("persianCalendar.monthShort00"),
            rb.getString("persianCalendar.monthShort01"),
            rb.getString("persianCalendar.monthShort02"),
            rb.getString("persianCalendar.monthShort03"),
            rb.getString("persianCalendar.monthShort04"),
            rb.getString("persianCalendar.monthShort05"),
            rb.getString("persianCalendar.monthShort06"),
            rb.getString("persianCalendar.monthShort07"),
            rb.getString("persianCalendar.monthShort08"),
            rb.getString("persianCalendar.monthShort09"),
            rb.getString("persianCalendar.monthShort10"),
            rb.getString("persianCalendar.monthShort11"),}
        );
    }
}
