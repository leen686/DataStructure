package datastructureproject;

import java.time.LocalDate;

public class Order {
    private int orderId;
    private int customerId;
    private String productsData;
    private double totalPrice;
    private LocalDate orderDate;
    private String status;
    
    // Constructor
    public Order(int orderId, int customerId, String productsData, 
                 double totalPrice, LocalDate orderDate, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productsData = productsData;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }
    
    // Alternative constructor with default date and status
    public Order(int orderId, int customerId, String productsData, double totalPrice) {
        this(orderId, customerId, productsData, totalPrice, LocalDate.now(), "pending");
    }
    
    // Getters
    public int getOrderId() {
        return orderId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public String getProductsData() {
        return productsData;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setters
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setTotalPrice(double totalPrice) {
        if (totalPrice >= 0) {
            this.totalPrice = totalPrice;
        }
    }
    
    public void setProductsData(String productsData) {
        this.productsData = productsData;
    }
    
    // Methods
    public boolean isBetweenDates(LocalDate startDate, LocalDate endDate) {
        return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
    }
    
    public boolean isPending() {
        return status.equalsIgnoreCase("pending");
    }
    
    public boolean isCompleted() {
        return status.equalsIgnoreCase("completed");
    }
    
    public boolean isCanceled() {
        return status.equalsIgnoreCase("canceled");
    }
    
    public boolean canBeCanceled() {
        return isPending();
    }
    
    public void cancelOrder() {
        if (canBeCanceled()) {
            this.status = "canceled";
        }
    }
    
    public void completeOrder() {
        if (isPending()) {
            this.status = "completed";
        }
    }
    
    public boolean isValidOrder() {
        return orderId > 0 && 
               customerId > 0 && 
               productsData != null && !productsData.trim().isEmpty() && 
               totalPrice >= 0 && 
               orderDate != null && 
               status != null && !status.trim().isEmpty();
    }
    
    // Display methods
    public void displayInfo() {
        System.out.println("========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Products: " + productsData);
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("========================================");
    }
    
    public void displayBriefInfo() {
        System.out.println("Order #" + orderId + 
                         " - Customer: " + customerId + 
                         " - Total: $" + String.format("%.2f", totalPrice) + 
                         " - Status: " + status +
                         " - Date: " + orderDate);
    }
    
    public void displaySummary() {
        System.out.println("Order: " + orderId + " (Customer: " + customerId + ")");
        System.out.println("Total: $" + String.format("%.2f", totalPrice) + 
                         " | Status: " + status);
        System.out.println("Date: " + orderDate);
    }
    
    @Override
    public String toString() {
        return "Order{" +
               "ID=" + orderId +
               ", customerID=" + customerId +
               ", products='" + productsData + '\'' +
               ", total=" + totalPrice +
               ", date=" + orderDate +
               ", status='" + status + '\'' +
               '}';
    }
}
