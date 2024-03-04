class Invoice {
    private String businessName;
    private String address;
    private java.util.List<LineItem> lineItems;

    public Invoice(String businessName, String address) {
        this.businessName = businessName;
        this.address = address;
        this.lineItems = new java.util.ArrayList<>();
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    public java.util.List<LineItem> getLineItems() {
        return lineItems;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getAddress() {
        return address;
    }

    public double calculateTotalAmountDue() {
        return lineItems.stream().mapToDouble(LineItem::calculateTotal).sum();
    }
}
