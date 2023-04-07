package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class FuelCanister extends Product {
    private final int march = 3;
    private final int day = 5;
    private final BigDecimal excise = new BigDecimal("5.56");

    public static Supplier<LocalDateTime> localDateTime = () -> LocalDateTime.now();

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        if (localDateTime.get().getDayOfMonth() == day
                && localDateTime.get().getMonthValue() == march) {
            return getPrice().add(excise);
        } else {
            return super.getPriceWithTax().add(excise);
        }
    }
}
