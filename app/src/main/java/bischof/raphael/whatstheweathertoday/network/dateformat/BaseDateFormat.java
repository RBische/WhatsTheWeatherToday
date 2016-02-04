package bischof.raphael.whatstheweathertoday.network.dateformat;

import java.text.SimpleDateFormat;

/**
 * Inheritance of SimpleDateFormat that formats dates in a readable way
 * Created by rbischof on 04/02/2016.
 */
public class BaseDateFormat extends SimpleDateFormat {
    public BaseDateFormat() {
        super("dd/MM/yyyy");
    }
}
