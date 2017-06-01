package org.bsuir.labs.spp.service.documents.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getFormattedDate(ZonedDateTime date){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(date);
    }
}
