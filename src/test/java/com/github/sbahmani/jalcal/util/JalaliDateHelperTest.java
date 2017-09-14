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
package com.github.sbahmani.jalcal.util;

import java.util.Date;
import org.fest.assertions.Assertions;

import org.junit.jupiter.api.Test;

/**
 *
 * @author sjb
 */
public class JalaliDateHelperTest {

    @Test
    public void testSomeMethod() {
        System.setProperty("user.timezone", "Asia/Tehran");
        Date now = new Date(1482148019498l);
        String date1 = JalaliDateHelper.convertToJalali4DigitSlashDateFormat(now);
        Assertions.assertThat(date1).isEqualTo("1395/09/29");
        String date2 = JalaliDateHelper.convertToTimeDigitalFormat(now);

        Assertions.assertThat(date2).isEqualTo("15:16:59");

    }

}
