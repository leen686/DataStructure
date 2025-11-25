package datastructureproject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Order Entity Class
 */
public class Order {
    private int orderId;
    private int customerId;
    private String productIds;  // Stored as "id1;id2;id3"
    private double totalPrice;
    private LocalDate orderDate;
    private String status;      // "pending", "shipped", "delivered", "canceled"
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public Order(int orderId, int customerId, String productIds, double totalPrice, 
                 LocalDate orderDate, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productIds = productIds;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }
    
    // ============ Getters ============
    
    public int getOrderId() {
        return orderId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public String getProductIds() {
        return productIds;
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
    
    // ============ Setters ============
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // ============ Status Management ============
    
    public boolean isPending() {
        return status.equalsIgnoreCase("pending");
    }
    
    public boolean isShipped() {
        return status.equalsIgnoreCase("shipped");
    }
    
    public boolean isDelivered() {
        return status.equalsIgnoreCase("delivered");
    }
    
    public boolean isCanceled() {
        return status.equalsIgnoreCase("canceled");
    }
    
    public boolean canBeCanceled() {
        return isPending() || isShipped();
    }
    
    public void cancelOrder() {
        if (canBeCanceled()) {
            this.status = "canceled";
        }
    }
    
    public void shipOrder() {
        if (isPending()) {
            this.status = "shipped";
        }
    }
    
    public void deliverOrder() {
        if (isShipped()) {
            this.status = "delivered";
        }
    }
    
    // ============ Product Management ============
    
    public LinkedList<Integer> getProductIdsList() {
        LinkedList<Integer> productList = new LinkedList<>();
        if (productIds == null || productIds.trim().isEmpty()) {
            return productList;
        }
        
        String[] ids = productIds.split(";");
        for (String id : ids) {
            try {
                productList.addLast(Integer.parseInt(id.trim()));
            } catch (NumberFormatException e) {
                // Skip invalid IDs
            }
        }
        return productList;
    }
    
    public boolean containsProduct(int productId) {
        LinkedList<Integer> productList = getProductIdsList();
        if (productList.empty()) {
            return false;
        }
        
        productList.findFirst();
        while (true) {
            if (productList.retrieve() == productId) {
                return true;
            }
            if (productList.last()) break;
            productList.findNext();
        }
        return false;
    }
    
    public int getProductCount() {
        return getProductIdsList().size();
    }
    
    // ============ Date Operations ============
    
    public boolean isOrderedBetween(LocalDate startDate, LocalDate endDate) {
        return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
    }
    
    public boolean isOrderedAfter(LocalDate date) {
        return orderDate.isAfter(date);
    }
    
    public boolean isOrderedBefore(LocalDate date) {
        return orderDate.isBefore(date);
    }
    
    public String getFormattedDate() {
        return orderDate.format(DATE_FORMAT);
    }
    
    // ============ Validation ============
    
    public boolean isValidOrder() {
        return orderId > 0 && 
               customerId > 0 &&
               productIds != null && !productIds.trim().isEmpty() &&
               totalPrice >= 0 &&
               orderDate != null &&
               status != null && !status.trim().isEmpty();
    }
    
    // ============ Display ============
    
    public void display() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Product IDs: " + productIds);
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("Order Date: " + getFormattedDate());
        System.out.println("Status: " + status.toUpperCase());
    }
    
    public void displayDetails() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║          ORDER DETAILS                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        display();
        System.out.println("Number of Products: " + getProductCount());
    }
    
    // ============ Comparison ============
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return orderId == order.orderId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(orderId);
    }
    
    @Override
    public String toString() {
        return "Order{id=" + orderId + ", customer=" + customerId + 
               ", total=$" + totalPrice + ", status=" + status + "}";
    }
}
