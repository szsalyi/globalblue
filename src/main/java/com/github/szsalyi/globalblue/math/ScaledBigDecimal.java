package com.github.szsalyi.globalblue.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ScaledBigDecimal extends BigDecimal {
    public static final int DEFAULT_SCALE = 2;
    public static final int DEFAULT_PRECISION = 34;
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final ScaledBigDecimal ONE_HUNDRED = new ScaledBigDecimal(new BigDecimal(100));
    public static final ScaledBigDecimal ONE = new ScaledBigDecimal(new BigDecimal(1));

    private final MathContext mathContext = new MathContext(DEFAULT_PRECISION, DEFAULT_ROUNDING_MODE);

    public ScaledBigDecimal(BigDecimal bd) {
        super(String.valueOf(bd));
    }

    public ScaledBigDecimal(BigDecimal bd, MathContext mathContext) {
        super(String.valueOf(bd), mathContext);
    }

    @Override
    public ScaledBigDecimal add(final BigDecimal augend) {
        return new ScaledBigDecimal(
                super.add(augend)
                        .setScale(DEFAULT_SCALE, mathContext.getRoundingMode()),
                this.getMathContext()
        );
    }

    @Override
    public ScaledBigDecimal multiply(BigDecimal multiplicand) {
        return new ScaledBigDecimal(
                super.multiply(multiplicand).
                        setScale(DEFAULT_SCALE, mathContext.getRoundingMode()),
                this.getMathContext()
        );
    }

    @Override
    public ScaledBigDecimal divide(BigDecimal divisor) {
        return new ScaledBigDecimal(
                super.divide(divisor, this.getMathContext())
                        .setScale(DEFAULT_SCALE, mathContext.getRoundingMode()),
                this.getMathContext()
        );
    }
}
