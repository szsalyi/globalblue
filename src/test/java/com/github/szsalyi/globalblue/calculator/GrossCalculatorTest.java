package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.szsalyi.globalblue.TestUtil.MATH_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GrossCalculatorTest {

    private GrossCalculator grossCalculator;

    @BeforeEach
    void setup() {
        grossCalculator = new GrossCalculator();
    }

    @Test
    void givenGrossAmountWithValidVatRate_whenCalculateDetails_thenReturnInvoiceDetails() {
        ScaledBigDecimal grossAmount = new ScaledBigDecimal(new BigDecimal(120.5), MATH_CONTEXT);
        ScaledBigDecimal vatRate = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);

        InvoiceDetails actual = grossCalculator.calculateDetails(grossAmount, vatRate);
        ScaledBigDecimal expectedNet = new ScaledBigDecimal(new BigDecimal("100.42"), MATH_CONTEXT);
        ScaledBigDecimal expectedVat = new ScaledBigDecimal(new BigDecimal("20.08"), MATH_CONTEXT);

        InvoiceDetails expected = new InvoiceDetails(expectedNet,grossAmount, expectedVat);
        assertEquals(expected, actual);
    }

    @Test()
    void givenValidVatRateWithoutGrossAmount_whenCalculateDetails_thenTrhowIllegalArgumentException() {
        ScaledBigDecimal grossAmount = null;
        ScaledBigDecimal vatRate = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);

        assertThrows(
                IllegalArgumentException.class,
                () -> grossCalculator.calculateDetails(grossAmount, vatRate));
    }

    @Test()
    void givenValidGrossAmountWithoutVatRate_whenCalculateDetails_thenTrhowIllegalArgumentException() {
        ScaledBigDecimal grossAmount = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);;
        ScaledBigDecimal vatRate = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> grossCalculator.calculateDetails(grossAmount, vatRate));
    }
}
