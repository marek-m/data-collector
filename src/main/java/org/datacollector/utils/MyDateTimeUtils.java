package org.datacollector.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by Marek on 2016-03-03.
 */
public class MyDateTimeUtils {

    public static DateTime localDateStartOfDay(DateTime dateTime) {
        DateTime result = dateTime.millisOfDay().withMinimumValue();
        return result.withZone(DateTimeZone.forID("Europe/Warsaw"));
    }

    public static DateTime localDateNow() {
        DateTime result = DateTime.now();
        return result.withZone(DateTimeZone.forID("Europe/Warsaw"));
    }
    public static DateTime localDateEndOfDay(DateTime dateTime) {
        DateTime result = dateTime.millisOfDay().withMaximumValue();
        return result.withZone(DateTimeZone.forID("Europe/Warsaw"));
    }
}
