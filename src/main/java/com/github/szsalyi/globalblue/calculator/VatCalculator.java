package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import lombok.NonNull;
import java.math.BigDecimal;

import static com.github.szsalyi.globalblue.math.ScaledBigDecimal.ONE;
import static com.github.szsalyi.globalblue.math.ScaledBigDecimal.ONE_HUNDRED;

public class VatCalculator implements Calculator {

    @Override
    public InvoiceDetails calculateDetails(
            @NonNull
            final ScaledBigDecimal requestDetail,
            @NonNull
            final ScaledBigDecimal selectedVat) {
        ScaledBigDecimal selectedVatPercentage = selectedVat.divide(ONE_HUNDRED);
        ScaledBigDecimal selectedVatNumerator = selectedVatPercentage.add(ONE);
        ScaledBigDecimal grossMultiplier = selectedVatNumerator.divide(selectedVatPercentage);
        BigDecimal netMultiplier = ONE.divide(selectedVatPercentage);

        ScaledBigDecimal gross = requestDetail.multiply(grossMultiplier);
        ScaledBigDecimal net = requestDetail.multiply(netMultiplier);

        return new InvoiceDetails(
               net, gross, requestDetail);
    }
}
