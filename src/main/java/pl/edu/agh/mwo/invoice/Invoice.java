package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    private final long invoiceNumber;
    static long nextNumber = 1;

    public Invoice() {
        this.invoiceNumber = nextNumber++;
        this.numberOfPositions = calculateNumberOfPositions();
    }

    private int numberOfPositions;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (checkIfInvoiceHasProduct(product)) {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product foundProduct = entry.getKey();
                Integer foundQuantity = entry.getValue();
                if (foundProduct.equals(product)) {
                    entry.setValue(foundQuantity + quantity);
                }
            }
        } else {
            products.put(product, quantity);
        }
        this.numberOfPositions = calculateNumberOfPositions();
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }
    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    public int getNumberOfPositions() {
        numberOfPositions = calculateNumberOfPositions();
        return numberOfPositions;
    }

    private int calculateNumberOfPositions() {
        return this.products.size();
    }

    public String printInvoice() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice number: " + this.getInvoiceNumber() + "\n");
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            sb.append(product.getName() + " " + quantity + " " + product.getPrice() + "zl\n");
        }
        sb.append("Number of positions: " + getNumberOfPositions());
        return sb.toString();
    }

    public int getProductQuantity(Product product) {
        int quantity = 0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product foundProduct = entry.getKey();
            Integer foundQuantity = entry.getValue();
            if (foundProduct.equals(product)) {
                quantity = foundQuantity;
            }
        }
        return quantity;
    }

    public boolean checkIfInvoiceHasProduct(Product product) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product foundProduct = entry.getKey();
            if (product.equals(foundProduct)) {
                return true;
            }
        }
        return false;
    }
}
