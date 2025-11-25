package datastructureproject;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orderIds;
    
    // Constructor
    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orderIds = new LinkedList<>();
    }
    
    // Getters
    public int getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public LinkedList<Integer> getOrderIds() {
        return orderIds;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Methods
    public void placeOrder(int orderId) {
        orderIds.addLast(orderId);
    }
    
    public boolean hasOrders() {
        return !orderIds.empty();
    }
    
    public int getOrderCount() {
        return orderIds.size();
    }
    
    public boolean isValidCustomer() {
        return customerId > 0 && 
               name != null && !name.trim().isEmpty() && 
               email != null && !email.trim().isEmpty();
    }
    
    // Display methods
    public void displayInfo() {
        System.out.println("========================================");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Total Orders: " + getOrderCount());
        
        if (hasOrders()) {
            System.out.print("Order IDs: ");
            orderIds.findFirst();
            while (true) {
                System.out.print(orderIds.retrieve());
                if (orderIds.last()) break;
                System.out.print(", ");
                orderIds.findNext();
            }
            System.out.println();
        }
        System.out.println("========================================");
    }
    
    public void displaySummary() {
        System.out.println("Customer: " + name + " (ID: " + customerId + ")");
        System.out.println("Email: " + email);
        System.out.println("Orders: " + getOrderCount());
    }
    
    @Override
    public String toString() {
        return "Customer{" +
               "ID=" + customerId +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", orders=" + getOrderCount() +
               '}';
    }
}
