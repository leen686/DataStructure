package datastructureproject;

import java.time.LocalDate;

public class Order {
    private int orderId;
    private int customerId;
    private LinkedList<Integer> products;
    private double totalPrice;
    private LocalDate orderDate;
    private String status;

    public Order(int orderId, int customerId, String productsData, 
                 double totalPrice, LocalDate orderDate, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.products = new LinkedList<>();
        processProducts(productsData);
    }

    // Getters 
    public int getOrderId() { 
        return orderId; 
    }
    
    public int getCustomerId() { 
        return customerId; 
    }
    
    public LinkedList<Integer> getProducts() { 
        return products; 
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
    public void setOrderId(int orderId) { 
        this.orderId = orderId; 
    }

    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }

    public void setTotalPrice(double totalPrice) { 
        this.totalPrice = totalPrice; 
    }

    public void setOrderDate(LocalDate orderDate) { 
        this.orderDate = orderDate; 
    }

    public void setStatus(String status) { 
        if (isValidStatus(status)) {
            this.status = status;
            System.out.println("Order " + orderId + " status updated to: " + status);
        } else {
            System.out.println("Invalid status: " + status);
        }
    }

    //  Product Management
    
    private void processProducts(String productsData) {
        if (productsData != null && !productsData.trim().isEmpty()) {
            String[] productsArray = productsData.split(";");
            for (String product : productsArray) {
                try {
                    int productId = Integer.parseInt(product.trim());
                    addProduct(productId);
                } catch (NumberFormatException e) {
                    System.out.println("Error processing product: " + product);
                }
            }
        }
    }

    public void addProduct(int productId) {
        if (!products.empty()) {
            products.findFirst();
            while (true) {
                if (products.retrieve() == productId) {
                    System.out.println("Product already in order: " + productId);
                    return;
                }
                if (products.last()) break;
                products.findNext();
            }
        }
        products.addLast(productId);
    }

    public boolean removeProduct(int productId) {
        if (products.empty()) {
            return false;
        }

        products.findFirst();
        while (true) {
            if (products.retrieve() == productId) {
                products.remove();
                return true;
            }
            if (products.last()) break;
            products.findNext();
        }
        return false;
    }

    public boolean containsProduct(int productId) {
        if (products.empty()) {
            return false;
        }

        products.findFirst();
        while (true) {
            if (products.retrieve() == productId) {
                return true;
            }
            if (products.last()) break;
            products.findNext();
        }
        return false;
    }

    // Status Operations 
    
    private boolean isValidStatus(String status) {
        String lower = status.toLowerCase();
        return lower.equals("pending") || 
               lower.equals("shipped") || 
               lower.equals("delivered") || 
               lower.equals("canceled");
    }

    public boolean canBeCanceled() {
        return status.equalsIgnoreCase("pending") || 
               status.equalsIgnoreCase("shipped");
    }

    public void cancelOrder() {
        if (canBeCanceled()) {
            status = "canceled";
            System.out.println("Order " + orderId + " has been canceled");
        } else {
            System.out.println("Order cannot be canceled in current status: " + status);
        }
    }

    //  Validation 
    
    public boolean isValidOrder() {
        return orderId > 0 && 
               customerId > 0 && 
               totalPrice >= 0 && 
               orderDate != null &&
               status != null && !status.trim().isEmpty() &&
               isValidStatus(status);
    }

    //  Statistics 
    
    public int countProducts() {
        int count = 0;
        if (!products.empty()) {
            products.findFirst();
            while (true) {
                count++;
                if (products.last()) break;
                products.findNext();
            }
        }
        return count;
    }

    public boolean isBetweenDates(LocalDate startDate, LocalDate endDate) {
        return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
    }

    //  Display 
    
    public void displayOrderDetails() { 
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Products: " + formatProductsList());
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("Number of Products: " + countProducts());
    }

    public void displayBriefInfo() {
        System.out.println(orderId + " - Customer:" + customerId + " - $" + 
                          String.format("%.2f", totalPrice) + " - " + status);
    }

    private String formatProductsList() {
        if (products.empty()) {
            return "No products";
        }

        StringBuilder result = new StringBuilder();
        products.findFirst();
        while (true) {
            result.append(products.retrieve());
            if (products.last()) break;
            result.append(", ");
            products.findNext();
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return String.format("Order[ID:%d, Customer:%d, Products:%d, Total:$%.2f, Date:%s, Status:%s]",
                orderId, customerId, countProducts(), totalPrice, orderDate, status);
    }
}

