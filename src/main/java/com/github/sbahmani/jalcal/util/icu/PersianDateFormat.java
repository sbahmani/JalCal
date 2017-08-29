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

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.ULocale;

/**
 * This class is used for formatting dates in the Persian calendar system. It uses the <code>PersianDateFormatSymbols</code> class for localized names of
 * Persian calendar eras and months.
 *
 * @author sjb
 */
public class PersianDateFormat extends SimpleDateFormat {

    /**
     * Creates a <code>PersianDateFormat</code> with the default pattern and locale.
     */
    public PersianDateFormat() {
        this("yyyy/MM/dd G HH:mm:ss z", ULocale.getDefault());
    }

    /**
     * Creates a <code>PersianDateFormat</code> with the specified pattern and the default locale.
     *
     * @param pattern the pattern to be used by this object.
     */
    public PersianDateFormat(String pattern) {
        this(pattern, ULocale.getDefault());
    }

    /**
     * Creates a <code>PersianDateFormat</code> with the specified pattern and locale.
     *
     * @param pattern the pattern to be used by this object.
     * @param loc the provided locale for this object.
     */
    public PersianDateFormat(String pattern, Locale loc) {
        this(pattern, ULocale.forLocale(loc));
    }

    /**
     * Creates a <code>PersianDateFormat</code> with the specified pattern and locale.
     *
     * @param pattern the pattern to be used by this object.
     * @param loc the provided locale for this object.
     */
    public PersianDateFormat(String pattern, ULocale loc) {
        super(pattern, loc);
        setDateFormatSymbols(new PersianDateFormatSymbols(loc));
		// Should we check if the locale is for Iran or Afghanistan?
        // Anyway, I don't think this class should be used for any other calendar type.
        setCalendar(new PersianCalendar(loc));
    }
}
