class LineItem {
    private int quantity;
    private Product product;

    public LineItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double calculateTotal() {
        return quantity * product.getUnitPrice();
    }

    @Override
    public String toString() {
        return String.format("Product: %s, Quantity: %d, Total: $%.2f",
                product.getName(), quantity, calculateTotal());
    }
}
