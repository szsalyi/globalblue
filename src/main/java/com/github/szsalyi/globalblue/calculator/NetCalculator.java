package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import lombok.NonNull;

import static com.github.szsalyi.globalblue.math.ScaledBigDecimal.ONE;
import static com.github.szsalyi.globalblue.math.ScaledBigDecimal.ONE_HUNDRED;

public class NetCalculator implements Calculator {

    @Override
    public InvoiceDetails calculateDetails(
            @NonNull
            final ScaledBigDecimal requestDetail,
            @NonNull
            final ScaledBigDecimal selectedVat) {
        ScaledBigDecimal selectedVatPercentage = selectedVat.divide(ONE_HUNDRED);
        ScaledBigDecimal gross = requestDetail.multiply(ONE.add(selectedVatPercentage));
        ScaledBigDecimal vat = requestDetail.multiply(selectedVatPercentage);

        return new InvoiceDetails(requestDetail, gross, vat);
    }
}
