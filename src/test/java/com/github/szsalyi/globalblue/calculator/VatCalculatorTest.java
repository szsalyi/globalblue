package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.szsalyi.globalblue.TestUtil.MATH_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VatCalculatorTest {
    private VatCalculator calculator;

    @BeforeEach
    void setup() {
        this.calculator = new VatCalculator();
    }

    @Test
    void givenVatAmountWithValidVatRate_whenCalculateDetails_thenReturnInvoiceDetails() {
        ScaledBigDecimal vatAmount = new ScaledBigDecimal(new BigDecimal(20.5), MATH_CONTEXT);
        ScaledBigDecimal vatRate = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);

        InvoiceDetails actual = calculator.calculateDetails(vatAmount, vatRate);
        ScaledBigDecimal expectedGross = new ScaledBigDecimal(new BigDecimal("123.00"), MATH_CONTEXT);
        ScaledBigDecimal expectedNet = new ScaledBigDecimal(new BigDecimal("102.50"), MATH_CONTEXT);

        InvoiceDetails expected = new InvoiceDetails(expectedNet,expectedGross, vatAmount);
        assertEquals(expected, actual);
    }

    @Test()
    void givenValidVatRateWithoutVatAmount_whenCalculateDetails_thenThrowIllegalArgumentException() {
        ScaledBigDecimal vatAmount = null;
        ScaledBigDecimal vatRate = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateDetails(vatAmount, vatRate));
    }

    @Test()
    void givenValidVatAmountWithoutVatRate_whenCalculateDetails_thenThrowIllegalArgumentException() {
        ScaledBigDecimal vatAmount = new ScaledBigDecimal(new BigDecimal(20), MATH_CONTEXT);;
        ScaledBigDecimal vatRate = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateDetails(vatAmount, vatRate));
    }
}
