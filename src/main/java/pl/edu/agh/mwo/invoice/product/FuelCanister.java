package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class FuelCanister extends Product {
    private final int MARCH = 3;
    private final int MARCH_FIVE = 5;
    private final BigDecimal excise = new BigDecimal("5.56");

    public static Supplier<LocalDateTime> localDateTime = () -> LocalDateTime.now();

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        if (localDateTime.get().getDayOfMonth() == MARCH_FIVE && localDateTime.get().getMonthValue() == MARCH) {
            return getPrice().add(excise);
        } else {
            return super.getPriceWithTax().add(excise);
        }
    }
}
