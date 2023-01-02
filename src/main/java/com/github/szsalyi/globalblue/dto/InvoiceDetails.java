package com.github.szsalyi.globalblue.dto;

import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InvoiceDetails {
    ScaledBigDecimal net;
    ScaledBigDecimal gross;
    ScaledBigDecimal vat;
}
