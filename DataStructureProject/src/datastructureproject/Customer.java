package datastructureproject;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orders;
    private LinkedList<Review> reviews;

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orders = new LinkedList<>();
        this.reviews = new LinkedList<>();
    }

    // ============ Getters ============
    public int getCustomerId() { 
        return customerId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public LinkedList<Integer> getOrders() { 
        return orders; 
    }
    
    public LinkedList<Review> getReviews() {
        return reviews;
    }

    // ============ Setters ============
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    // ============ Order Management ============
    
    public void placeOrder(int orderId) {
        if (!orders.empty()) {
            orders.findFirst();
            while (true) {
                if (orders.retrieve() == orderId) {
                    System.out.println("Order already exists: " + orderId);
                    return;
                }
                if (orders.last()) break;
                orders.findNext();
            }
        }
        orders.addLast(orderId);
        System.out.println("Order " + orderId + " placed successfully for customer " + customerId);
    }

    public boolean removeOrder(int orderId) {
        if (orders.empty()) {
            return false;
        }

        orders.findFirst();
        while (true) {
            if (orders.retrieve() == orderId) {
                orders.remove();
                return true;
            }
            if (orders.last()) break;
            orders.findNext();
        }
        return false;
    }

   public void viewOrderHistory(InventorySystem system) {
    if (orders.empty()) {                                // Line 1
        System.out.println("No orders found");           // Line 2
        return;                                          // Line 3
    }

    System.out.println("=== Order History ===");        // Line 4
    orders.findFirst();                                  // Line 5
    while (true) {                                       // Line 6
        int orderId = orders.retrieve();                 // Line 7
        Order order = system.findOrder(orderId);         // Line 8: O(o)
        if (order != null) {                             // Line 9
            order.displayBriefInfo();                    // Line 10
        }
        
        if (orders.last()) break;                        // Line 11
        orders.findNext();                               // Line 12
    }
}

    public int countOrders() {
        int count = 0;
        if (!orders.empty()) {
            orders.findFirst();
            while (true) {
                count++;
                if (orders.last()) break;
                orders.findNext();
            }
        }
        return count;
    }

    public double calculateTotalSpending(InventorySystem system) {
        double total = 0.0;
        if (!orders.empty()) {
            orders.findFirst();
            while (true) {
                int orderId = orders.retrieve();
                Order order = system.findOrder(orderId);
                if (order != null) {
                    total += order.getTotalPrice();
                }
                
                if (orders.last()) break;
                orders.findNext();
            }
        }
        return total;
    }

    // ============ Review Management ============
    
    public void addReview(Review review) {
        reviews.insert(review);
    }
    
    public LinkedList<Review> getAllReviews() {
        return reviews;
    }

    // ============ Validation ============
    
    public boolean isValidCustomer() {
        return customerId > 0 && 
               name != null && !name.trim().isEmpty() &&
               email != null && email.contains("@");
    }

    // ============ Display ============
    
    public void displaySummary() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Total Orders: " + countOrders());
    }

    public void displayDetailedInfo(InventorySystem system) {
        System.out.println("========================================");
        System.out.println("Customer Details");
        System.out.println("========================================");
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Total Orders: " + countOrders());
        System.out.println("Total Spending: $" + String.format("%.2f", calculateTotalSpending(system)));
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return String.format("Customer[ID:%d, Name:%s, Email:%s, Orders:%d]", 
                customerId, name, email, countOrders());
    }
}