package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products;

    public Invoice() {
        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cant be null");
        }
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity can't be 0 or negative.");
        }
        for (int i=0; i < quantity; i++) {
            this.products.add(product);
        }
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        if (products.isEmpty()) {
            return subtotal;
        }
        for (Product p : products) {
            subtotal = subtotal.add(p.getPrice());
        }
        return subtotal;
    }

    public BigDecimal getTax() {
        BigDecimal taxAmount = BigDecimal.ZERO;
        if (products.isEmpty()) {
            return taxAmount;
        }
        for (Product p : products) {
            taxAmount = taxAmount.add(p.getPrice().multiply(p.getTaxPercent()));
        }
        return taxAmount;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (products.isEmpty()) {
            return total;
        }
        for (Product p : products) {
            total = total.add(p.getPriceWithTax());
        }
        return  total;
    }
}
