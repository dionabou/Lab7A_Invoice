import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceGUI extends JFrame {
    private Invoice invoice;

    private JTextField businessNameField, addressField;
    private JTextField productNameField, unitPriceField, quantityField;
    private JButton addLineItemButton, calculateTotalButton, displayInvoiceButton;
    private JTextArea invoiceTextArea;

    private boolean businessInfoEntered = false;

    public InvoiceGUI() {
        invoice = new Invoice("", ""); // Empty strings as placeholders

        businessNameField = new JTextField(15);
        addressField = new JTextField(15);
        productNameField = new JTextField(15);
        unitPriceField = new JTextField(15);
        quantityField = new JTextField(15);
        addLineItemButton = new JButton("Add Line Item");
        calculateTotalButton = new JButton("Calculate Total");
        displayInvoiceButton = new JButton("Display Invoice");
        invoiceTextArea = new JTextArea(15, 30);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1;
        add(businessNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        add(productNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Unit Price ($):"), gbc);
        gbc.gridx = 1;
        add(unitPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(addLineItemButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(calculateTotalButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(displayInvoiceButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(new JScrollPane(invoiceTextArea), gbc);

        addLineItemButton.addActionListener(new AddLineItemListener());
        calculateTotalButton.addActionListener(new CalculateTotalListener());
        displayInvoiceButton.addActionListener(new DisplayInvoiceListener());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setTitle("Invoice Application");
        setVisible(true);
    }

    private class AddLineItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!businessInfoEntered) {
                String businessName = businessNameField.getText();
                String address = addressField.getText();
                invoice = new Invoice(businessName, address);
                businessInfoEntered = true;
            }

            try {
                String productName = productNameField.getText();
                double unitPrice = Double.parseDouble(unitPriceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Product product = new Product(productName, unitPrice);
                LineItem lineItem = new LineItem(quantity, product);
                invoice.addLineItem(lineItem);

                invoiceTextArea.append(lineItem.toString() + "\n");

                productNameField.setText("");
                unitPriceField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(InvoiceGUI.this, "Invalid input. Please enter numeric values.");
            }
        }
    }

    private class CalculateTotalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double totalAmountDue = invoice.calculateTotalAmountDue();
            invoiceTextArea.append(String.format("\nTotal Amount Due: $%.2f\n", totalAmountDue));
        }
    }

    private class DisplayInvoiceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!businessInfoEntered) {
                JOptionPane.showMessageDialog(InvoiceGUI.this, "Please enter business information first.");
                return;
            }

            invoiceTextArea.setText("");
            invoiceTextArea.append("                                      INVOICE\n");
            invoiceTextArea.append("Business Name: " + invoice.getBusinessName() + "\n");
            invoiceTextArea.append("Address: " + invoice.getAddress() + "\n");
            invoiceTextArea.append("\n");
            invoiceTextArea.append("==========================================================================================================\n");
            invoiceTextArea.append(String.format("%-30s%-15s%-15s%-15s\n", "ITEM", "QTY", "PRICE", "Total"));
            invoiceTextArea.append("\n");

            for (LineItem lineItem : invoice.getLineItems()) {
                invoiceTextArea.append(String.format("%-32s%-18s$%-18s$%-14s\n",
                        lineItem.getProduct().getName(), lineItem.getQuantity(),
                        lineItem.getProduct().getUnitPrice(), lineItem.calculateTotal()));
            }

            invoiceTextArea.append("====================================================================================================================\n");
        }
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(InvoiceGUI::new);
    }
}
