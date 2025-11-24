package datastructureproject;

import java.time.LocalDate;

// Order class - implements Comparable for BST
class Order implements Comparable<Order> {
    private int orderId;
    private int customerId;
    private String productsData; // Can be product IDs or description
    private double totalPrice;
    private LocalDate orderDate;
    private String status; // "pending", "processing", "shipped", "delivered", "canceled"

    public Order(int orderId, int customerId, String productsData, 
                 double totalPrice, LocalDate orderDate, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productsData = productsData;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public String getProductsData() { return productsData; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getStatus() { return status; }

    // Setters
    public void setStatus(String status) { 
        this.status = status;
        System.out.println("Order " + orderId + " status updated to: " + status);
    }
    
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    // Order operations
    public boolean canBeCanceled() {
        return !status.equalsIgnoreCase("delivered") && 
               !status.equalsIgnoreCase("canceled");
    }

    public void cancelOrder() {
        if (canBeCanceled()) {
            this.status = "canceled";
            System.out.println("Order " + orderId + " has been canceled");
        } else {
            System.out.println("Order " + orderId + " cannot be canceled");
        }
    }

    // Validation
    public boolean isValidOrder() {
        return orderId > 0 && customerId > 0 && totalPrice >= 0 
               && orderDate != null && status != null;
    }

    // Date range check
    public boolean isBetweenDates(LocalDate startDate, LocalDate endDate) {
        return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
    }

    // Comparable implementation - compare by orderId
    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.orderId, other.orderId);
    }

    // Display methods
    public void displayBriefInfo() {
        System.out.printf("Order #%d | Customer: %d | Total: $%.2f | Date: %s | Status: %s%n",
                         orderId, customerId, totalPrice, orderDate, status);
    }

    public void displayFullDetails() {
        System.out.println("========================================");
        System.out.println("Order Details");
        System.out.println("========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Products: " + productsData);
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("Order Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", customerId=" + customerId + 
               ", total=$" + totalPrice + ", date=" + orderDate + 
               ", status='" + status + "'}";
    }
}

// Product class - implements Comparable for BST
class Product implements Comparable<Product> {
    private int productId;
    private String name;
    private double price;
    private int stock;

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    // Stock operations
    public boolean isOutOfStock() {
        return stock <= 0;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public boolean reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    public void increaseStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }

    // Validation
    public boolean isValidProduct() {
        return productId > 0 && name != null && !name.isEmpty() 
               && price >= 0 && stock >= 0;
    }

    // Price range check
    public boolean isInPriceRange(double minPrice, double maxPrice) {
        return price >= minPrice && price <= maxPrice;
    }

    // Comparable implementation - compare by productId
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }

    // Display methods
    public void displaySummary() {
        System.out.printf("Product #%d: %s | Price: $%.2f | Stock: %d%n",
                         productId, name, price, stock);
    }

    public void displayFullDetails() {
        System.out.println("========================================");
        System.out.println("Product Details");
        System.out.println("========================================");
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Stock: " + stock);
        System.out.println("Status: " + (isOutOfStock() ? "OUT OF STOCK" : "In Stock"));
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", name='" + name + 
               "', price=$" + price + ", stock=" + stock + "}";
    }
}

// Review class - implements Comparable for BST  
class Review implements Comparable<Review> {
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating; // 1-5
    private String comment;

    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = Math.max(1, Math.min(5, rating)); // Ensure 1-5 range
        this.comment = comment;
    }

    // Getters
    public int getReviewId() { return reviewId; }
    public int getProductId() { return productId; }
    public int getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    // Setters
    public void setRating(int rating) { 
        this.rating = Math.max(1, Math.min(5, rating));
    }
    public void setComment(String comment) { this.comment = comment; }

    // Validation
    public boolean isValidReview() {
        return reviewId > 0 && productId > 0 && customerId > 0 
               && rating >= 1 && rating <= 5;
    }

    // Comparable implementation - compare by reviewId
    @Override
    public int compareTo(Review other) {
        return Integer.compare(this.reviewId, other.reviewId);
    }

    // Display methods
    public void displayReview() {
        System.out.println("Review #" + reviewId);
        System.out.println("Product ID: " + productId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Rating: " + rating + "/5");
        System.out.println("Comment: " + comment);
        System.out.println("---");
    }

    @Override
    public String toString() {
        return "Review{id=" + reviewId + ", productId=" + productId + 
               ", customerId=" + customerId + ", rating=" + rating + "/5}";
    }
}
