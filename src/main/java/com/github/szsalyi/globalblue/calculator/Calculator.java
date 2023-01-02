package com.github.szsalyi.globalblue.calculator;

import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import jakarta.validation.constraints.NotNull;

public interface Calculator {
    InvoiceDetails calculateDetails(
            @NotNull
            final ScaledBigDecimal requestDetail,
            @NotNull
            final ScaledBigDecimal selectedVat);
}
