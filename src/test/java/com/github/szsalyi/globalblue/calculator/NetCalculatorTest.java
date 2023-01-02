package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.szsalyi.globalblue.TestUtil.MATH_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NetCalculatorTest {

    private NetCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new NetCalculator();
    }

    @Test
    void givenNetAmountWithValidVatRate_whenCalculateDetails_thenReturnInvoiceDetails() {
        ScaledBigDecimal netAmount = new ScaledBigDecimal(new BigDecimal(100.5), MATH_CONTEXT);
        ScaledBigDecimal vatRate = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);

        InvoiceDetails actual = calculator.calculateDetails(netAmount, vatRate);
        ScaledBigDecimal expectedGross = new ScaledBigDecimal(new BigDecimal("120.60"), MATH_CONTEXT);
        ScaledBigDecimal expectedVat = new ScaledBigDecimal(new BigDecimal("20.10"), MATH_CONTEXT);

        InvoiceDetails expected = new InvoiceDetails(netAmount,expectedGross, expectedVat);
        assertEquals(expected, actual);
    }

    @Test()
    void givenValidVatRateWithoutNetAmount_whenCalculateDetails_thenThrowIllegalArgumentException() {
        ScaledBigDecimal netAmount = null;
        ScaledBigDecimal vatRate = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateDetails(netAmount, vatRate));
    }

    @Test()
    void givenValidNetAmountWithoutVatRate_whenCalculateDetails_thenThrowIllegalArgumentException() {
        ScaledBigDecimal netAmount = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);;
        ScaledBigDecimal vatRate = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateDetails(netAmount, vatRate));
    }
}
