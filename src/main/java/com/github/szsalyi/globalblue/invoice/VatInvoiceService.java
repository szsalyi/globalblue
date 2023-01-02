package com.github.szsalyi.globalblue.invoice;

import com.github.szsalyi.globalblue.calculator.Calculator;
import com.github.szsalyi.globalblue.calculator.factory.CalculatorFactory;
import com.github.szsalyi.globalblue.dto.InvoiceDetailType;
import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import com.github.szsalyi.globalblue.validation.ValuesAllowed;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Validated
@RequiredArgsConstructor
@Service
public class VatInvoiceService implements InvoiceService {
    @Value("${scaled.bigdecimal.precision}")
    private Integer bigDecimalPrecision;
    @Value("${rounding.mode}")
    private String roundingMode;

    private final CalculatorFactory factory;

    @Override
    public InvoiceDetails calculate(
            @NonNull
            final InvoiceDetailType type,
            @NonNull
            final BigDecimal amount,
            @NonNull
            @ValuesAllowed
                    final BigDecimal vatRate) {

        MathContext context = new MathContext(bigDecimalPrecision, RoundingMode.valueOf(roundingMode));
        ScaledBigDecimal scaledAmount = new ScaledBigDecimal(amount, context);
        ScaledBigDecimal scaledVatRate = new ScaledBigDecimal(vatRate, context);

        Calculator calculator = factory.getCalculator(type);

        return calculator.calculateDetails(scaledAmount, scaledVatRate);
    }
}
