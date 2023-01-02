package com.github.szsalyi.globalblue.calculator.factory;

import com.github.szsalyi.globalblue.calculator.Calculator;
import com.github.szsalyi.globalblue.dto.InvoiceDetailType;

public interface CalculatorFactory {
    Calculator getCalculator(final InvoiceDetailType type);
}
