package com.github.szsalyi.globalblue.calculator.factory;

import com.github.szsalyi.globalblue.calculator.Calculator;
import com.github.szsalyi.globalblue.calculator.GrossCalculator;
import com.github.szsalyi.globalblue.calculator.NetCalculator;
import com.github.szsalyi.globalblue.calculator.VatCalculator;
import com.github.szsalyi.globalblue.dto.InvoiceDetailType;
import org.springframework.stereotype.Service;

@Service
public class VatDetailCalculatorFactory implements CalculatorFactory {
    @Override
    public Calculator getCalculator(final InvoiceDetailType type) {
        return switch (type) {
            case VAT -> new VatCalculator();
            case GROSS -> new GrossCalculator();
            case NET -> new NetCalculator();
        };
    }
}
