package com.cowaine.corock.chapter03;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class PriceUnit {

    private final Locale locale;

    public PriceUnit(Locale locale) {
        if (Objects.isNull(locale)) {
            throw new IllegalArgumentException("locale argument is null");
        }

        this.locale = locale;
    }

    public String format(BigDecimal price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(Optional.ofNullable(price).orElse(BigDecimal.ZERO));
    }

    public void validate() {
        if (Objects.isNull(locale)) {
            throw new IllegalStateException("locale is null");
        }

        log.info("local is [{}]", locale);
    }

}