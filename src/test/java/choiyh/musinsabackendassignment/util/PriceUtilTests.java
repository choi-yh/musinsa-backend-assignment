package choiyh.musinsabackendassignment.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PriceUtilTests {

    @Test
    public void priceFormattingWithComma() {
        assertEquals("34,100", PriceUtil.priceFormattingWithComma(34100));
        assertEquals("123", PriceUtil.priceFormattingWithComma(123));
        assertEquals("12,345,678", PriceUtil.priceFormattingWithComma(12345678));
        assertNull(PriceUtil.priceFormattingWithComma(null));
    }

}
