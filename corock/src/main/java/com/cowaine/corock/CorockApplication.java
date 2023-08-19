package com.cowaine.corock;

import com.cowaine.corock.chapter03.Formatter;
import com.cowaine.corock.chapter03.PriceUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.IntStream;

@Slf4j
@SpringBootApplication
public class CorockApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(CorockApplication.class, args);

        // CorockApplication.p99(ctx);
        // CorockApplication.p110(ctx);

        Formatter<LocalDateTime> formatter = ctx.getBean("localDateTimeFormatter", Formatter.class);
        String date = formatter.of(LocalDateTime.of(2020, 12, 24, 23, 59, 59));

        log.info("Date: " + date);
        ctx.close();
    }

    private static void p99(ConfigurableApplicationContext ctx) {
        Environment env = ctx.getBean(Environment.class);
        String port = env.getProperty("server.port");
        log.info("Customized Port: {}", port);

        String[] beanNames = ctx.getBeanDefinitionNames();
        IntStream.range(0, beanNames.length)
                .forEach(i -> log.info("Bean Name #{}: {}", i + 1, beanNames[i]));
    }

    private static void p110(ConfigurableApplicationContext ctx) {
        PriceUnit defaultPriceUnit = ctx.getBean("priceUnit", PriceUnit.class);
        log.info("Price #1: {}", defaultPriceUnit.format(BigDecimal.valueOf(10.2)));

        PriceUnit wonPriceUnit = ctx.getBean("wonPriceUnit", PriceUnit.class);
        log.info("Price #2: {}", wonPriceUnit.format(BigDecimal.valueOf(1_000)));

        // Exception in thread "main" o.s.b.f.NoSuchBeanDefinitionException: No bean named 'testPriceUnit' available
        // PriceUnit testPriceUnit = ctx.getBean("testPriceUnit", PriceUnit.class);
    }

    @Bean(name = "priceUnit")
    public PriceUnit dollarPriceUnit() {
        return new PriceUnit(Locale.US);
    }

    @Bean
    public PriceUnit wonPriceUnit() {
        return new PriceUnit(Locale.KOREA);
    }

}
