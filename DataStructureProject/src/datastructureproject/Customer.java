package datastructureproject;

// Customer class - implements Comparable for BST
class Customer implements Comparable<Customer> {
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orderIds;

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orderIds = new LinkedList<>();
    }

    // Getters
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LinkedList<Integer> getOrderIds() { return orderIds; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    // Place order
    public void placeOrder(int orderId) {
        orderIds.addLast(orderId);
    }

    // Validation
    public boolean isValidCustomer() {
        return customerId > 0 && name != null && !name.isEmpty() 
               && email != null && email.contains("@");
    }

    // Comparable implementation - compare by customerId
    @Override
    public int compareTo(Customer other) {
        return Integer.compare(this.customerId, other.customerId);
    }

    // Display methods
    public void displaySummary() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Total Orders: " + orderIds.size());
    }

    public void displayFullDetails() {
        System.out.println("========================================");
        System.out.println("Customer Details");
        System.out.println("========================================");
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Number of Orders: " + orderIds.size());
        
        if (!orderIds.empty()) {
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

    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name='" + name + 
               "', email='" + email + "', orders=" + orderIds.size() + "}";
    }
}
