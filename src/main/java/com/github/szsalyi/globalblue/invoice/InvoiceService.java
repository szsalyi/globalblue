package com.github.szsalyi.globalblue.invoice;

import com.github.szsalyi.globalblue.dto.InvoiceDetailType;
import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.validation.ValuesAllowed;
import lombok.NonNull;

import java.math.BigDecimal;

public interface InvoiceService {
    InvoiceDetails calculate(
            @NonNull
            final InvoiceDetailType type,
            @NonNull
            final BigDecimal amount,
            @NonNull
            @ValuesAllowed
                    final BigDecimal vatRate
    );
}
