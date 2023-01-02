package com.github.szsalyi.globalblue.invoice;

import com.github.szsalyi.globalblue.dto.InvoiceDetailType;
import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = "/api/v1/invoice",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceDetails invoiceDetailsCalculation(
            @RequestParam
                    InvoiceDetailType type,
            @RequestParam
            @Digits(
                    integer=120,
                    fraction=2,
                    message = "Amount parameter representation is invalid. The valid value would be 120 digits and 2 fraction digits.")
                    BigDecimal amount,
            @RequestParam
                    BigDecimal vatRate) {
        log.info("Invoice detail calculation started.");
        validateAmountValue(amount);

        InvoiceDetails calculatedDetails = invoiceService.calculate(type, amount, vatRate);

        log.info("Invoice detail calculation finished.");
        return calculatedDetails;
    }

    private void validateAmountValue(BigDecimal amount) {
        if(Objects.equals(amount, BigDecimal.ZERO)) {
            throw new IllegalArgumentException("Input detail has to be bigger than zero");
        }
    }
}
