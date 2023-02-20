package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.*;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private HashMap<Product, Integer> products;

    public Invoice() {
        this.products = new HashMap<Product, Integer>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cant be null");
        }
        this.products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity can't be 0 or negative");
        }
        this.products.put(product, quantity);
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            BigDecimal quantity = BigDecimal.valueOf(entry.getValue());
            subtotal = subtotal.add(product.getPrice().multiply(quantity));
        }
        return subtotal;
    }

    public BigDecimal getTax() {
        BigDecimal tax = BigDecimal.ZERO;
        Iterator i = this.products.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<Product, Integer> me = (Map.Entry) i.next();
            Product product = me.getKey();
            BigDecimal quantity = BigDecimal.valueOf(me.getValue());

            BigDecimal taxValue = product.getPrice().multiply(product.getTaxPercent());
            tax = tax.add(taxValue.multiply(quantity));
        }
        return tax;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            BigDecimal quantity = BigDecimal.valueOf(entry.getValue());

            total = total.add(product.getPriceWithTax().multiply(quantity));
        }
        return total;
    }
}
