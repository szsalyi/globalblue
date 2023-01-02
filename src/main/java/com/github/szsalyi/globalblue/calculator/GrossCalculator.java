package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import lombok.NonNull;

import static com.github.szsalyi.globalblue.math.ScaledBigDecimal.ONE;
import static com.github.szsalyi.globalblue.math.ScaledBigDecimal.ONE_HUNDRED;

public class GrossCalculator implements Calculator {
    @Override
    public InvoiceDetails calculateDetails(
            @NonNull
            final ScaledBigDecimal requestDetail,
            @NonNull
            final ScaledBigDecimal selectedVat) {
        ScaledBigDecimal selectedVatPercentage = selectedVat.divide(ONE_HUNDRED);

        ScaledBigDecimal selectedVatNumerator = ONE.add(selectedVatPercentage);

        ScaledBigDecimal vat = requestDetail.divide(selectedVatNumerator.divide(selectedVatPercentage));
        ScaledBigDecimal net = requestDetail.divide(selectedVatNumerator);

        return new InvoiceDetails(net, requestDetail, vat);
    }
}
