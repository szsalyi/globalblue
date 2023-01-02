package com.github.szsalyi.globalblue.invoice;

import com.github.szsalyi.globalblue.calculator.GrossCalculator;
import com.github.szsalyi.globalblue.calculator.factory.CalculatorFactory;
import com.github.szsalyi.globalblue.dto.InvoiceDetailType;
import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.github.szsalyi.globalblue.TestUtil.MATH_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InvoiceServiceTest {

    private InvoiceService invoiceService;

    @Mock
    private CalculatorFactory factory;

    @BeforeEach
    void setup() {
        when(factory.getCalculator(any())).thenAnswer(i -> new GrossCalculator());
        this.invoiceService = new VatInvoiceService(factory);
        ReflectionTestUtils.setField(invoiceService, "bigDecimalPrecision", 34);
        ReflectionTestUtils.setField(invoiceService, "roundingMode", RoundingMode.HALF_UP.toString());
    }

    @Test
    void givenValidParameters_whenInvoiceServiceCalculate_thenReturnProperInvoiceDetails() {
        BigDecimal amount = new BigDecimal(120);
        BigDecimal vatRate = new BigDecimal(20);

        InvoiceDetails actual = invoiceService.calculate(
                InvoiceDetailType.GROSS,
                amount,
                vatRate
        );
        ScaledBigDecimal expectedGross = new ScaledBigDecimal(new BigDecimal("120"), MATH_CONTEXT);
        ScaledBigDecimal expectedNet = new ScaledBigDecimal(new BigDecimal("100.00"), MATH_CONTEXT);
        ScaledBigDecimal expectedVat = new ScaledBigDecimal(new BigDecimal("20.00"), MATH_CONTEXT);

        InvoiceDetails expected = new InvoiceDetails(expectedNet,expectedGross,expectedVat);
        assertEquals(expected, actual);
    }

    @Test
    void givenInValidType_whenInvoiceServiceCalculate_thenThrowIllegalArgumentException() {
        ReflectionTestUtils.setField(invoiceService, "roundingMode", "U");

        BigDecimal amount = new BigDecimal(120);
        BigDecimal vatRate = new BigDecimal(20);

        assertThrows(IllegalArgumentException.class ,
                () -> invoiceService.calculate(
                InvoiceDetailType.GROSS,
                amount,
                vatRate
        ));
    }

    @Test
    void givenNoAmount_whenInvoiceServiceCalculate_thenThrowIllegalArgumentException() {
        BigDecimal amount = null;
        BigDecimal vatRate = new BigDecimal(20);

        assertThrows(IllegalArgumentException.class ,
                () -> invoiceService.calculate(
                        InvoiceDetailType.GROSS,
                        amount,
                        vatRate
                ));
    }

    @Test
    void givenInvalidVatRate_whenInvoiceServiceCalculate_thenThrowIllegalArgumentException() {
        BigDecimal amount = new BigDecimal(20);
        BigDecimal vatRate = null;

        assertThrows(IllegalArgumentException.class ,
                () -> invoiceService.calculate(
                        InvoiceDetailType.GROSS,
                        amount,
                        vatRate
                ));
    }
}
