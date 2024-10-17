package choiyh.musinsabackendassignment.util;

import java.text.DecimalFormat;

public class PriceUtil {

    public static String priceFormattingWithComma(Integer number) {
        if (number == null) {
            return null;
        }

        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

}
