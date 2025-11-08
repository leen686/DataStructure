package datastructureproject;

import java.time.LocalDate;

public class InventorySystem {
    
    // Assume these LinkedLists are already declared:
    private LinkedList<Customer> customers;
    private LinkedList<Order> orders;
  //  private LinkedList<Product> products;
   // private LinkedList<Review> reviews;

    // Constructor
    public InventorySystem() {
        this.customers = new LinkedList<>();
        this.orders = new LinkedList<>();
      //  this.products = new LinkedList<>();
       // this.reviews = new LinkedList<>();
    }

    // ============ Customer Operations ============

    // University Requirement: "Register new customer"
    public boolean registerCustomer(Customer customer) {
        if (customer == null || !customer.isValidCustomer()) {
            System.out.println("Invalid customer data");
            return false;
        }
        
        // Check if customer ID already exists
        if (!customers.empty()) {
            customers.findFirst();
            while (true) {
                if (customers.retrieve().getCustomerId() == customer.getCustomerId()) {
                    System.out.println("Customer with ID " + customer.getCustomerId() + " already exists");
                    return false;
                }
                if (customers.last()) break;
                customers.findNext();
            }
        }
        
        customers.addLast(customer);
        System.out.println("Customer registered successfully: " + customer.getName());
        return true;
    }

    // Linear Search - as required by university
    public Customer findCustomer(int customerId) {
        if (customers.empty()) {
            return null;
        }
        
        customers.findFirst();
        while (true) {
            Customer customer = customers.retrieve();
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
            if (customers.last()) break;
            customers.findNext();
        }
        return null;
    }

    public boolean removeCustomer(int customerId) {
        if (customers.empty()) {
            return false;
        }

        customers.findFirst();
        while (true) {
            if (customers.retrieve().getCustomerId() == customerId) {
                customers.remove();
                
                // Remove customer's orders
                removeCustomerOrders(customerId);
                System.out.println("Customer removed: " + customerId);
                return true;
            }
            if (customers.last()) break;
            customers.findNext();
        }
        return false;
    }

    public boolean updateCustomer(int customerId, String newName, String newEmail) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return false;
        }
        
        if (newName != null && !newName.trim().isEmpty()) {
            customer.setName(newName);
        }
        if (newEmail != null && newEmail.contains("@")) {
            customer.setEmail(newEmail);
        }
        
        System.out.println("Customer updated successfully");
        return true;
    }

    // ============ Order Operations ============

    // University Requirement: "Create order"
    public boolean createOrder(Order order) {
        if (order == null || !order.isValidOrder()) {
            System.out.println("Invalid order data");
            return false;
        }
        
        // Verify customer exists
        Customer customer = findCustomer(order.getCustomerId());
        if (customer == null) {
            System.out.println("Customer not found: " + order.getCustomerId());
            return false;
        }
        
        // Check if order ID already exists
        if (!orders.empty()) {
            orders.findFirst();
            while (true) {
                if (orders.retrieve().getOrderId() == order.getOrderId()) {
                    System.out.println("Order with ID " + order.getOrderId() + " already exists");
                    return false;
                }
                if (orders.last()) break;
                orders.findNext();
            }
        }
        
        orders.addLast(order);
        customer.placeOrder(order.getOrderId());
        System.out.println("Order created successfully: " + order.getOrderId());
        return true;
    }

    // University Requirement: "Search order by ID" - Linear Search
    public Order findOrder(int orderId) {
        if (orders.empty()) {
            return null;
        }
        
        orders.findFirst();
        while (true) {
            Order order = orders.retrieve();
            if (order.getOrderId() == orderId) {
                return order;
            }
            if (orders.last()) break;
            orders.findNext();
        }
        return null;
    }

    // University Requirement: "Cancel order"
    public boolean cancelOrder(int orderId) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return false;
        }
        
        if (!order.canBeCanceled()) {
            System.out.println("Order cannot be canceled: " + orderId);
            return false;
        }
        
        order.cancelOrder();
        return true;
    }

    public boolean removeOrder(int orderId) {
        if (orders.empty()) {
            return false;
        }

        orders.findFirst();
        while (true) {
            if (orders.retrieve().getOrderId() == orderId) {
                orders.remove();
                
                // Remove order reference from customer
                removeOrderReference(orderId);
                System.out.println("Order removed: " + orderId);
                return true;
            }
            if (orders.last()) break;
            orders.findNext();
        }
        return false;
    }

    // University Requirement: "Update order status"
    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return false;
        }
        
        order.setStatus(newStatus);
        return true;
    }

    // ============ University Query Requirements ============

    // University Requirement: "All Orders between two dates"
    // Time Complexity: O(n) where n is number of orders
    // Space Complexity: O(k) where k is number of matching orders
    public LinkedList<Order> findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        LinkedList<Order> result = new LinkedList<>();
        
        if (orders.empty()) {
            return result;
        }
        
        orders.findFirst();
        while (true) {
            Order order = orders.retrieve();
            if (order.isBetweenDates(startDate, endDate)) {
                result.addLast(order);
            }
            if (orders.last()) break;
            orders.findNext();
        }
        
        return result;
    }

    public void displayOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        LinkedList<Order> result = findOrdersBetweenDates(startDate, endDate);
        
        if (result.empty()) {
            System.out.println("No orders found between " + startDate + " and " + endDate);
            return;
        }
        
        System.out.println("=== Orders between " + startDate + " and " + endDate + " ===");
        result.findFirst();
        while (true) {
            result.retrieve().displayBriefInfo();
            if (result.last()) break;
            result.findNext();
        }
    }

    // ============ Helper Methods ============

    private void removeCustomerOrders(int customerId) {
        if (!orders.empty()) {
            orders.findFirst();
            while (true) {
                if (orders.retrieve().getCustomerId() == customerId) {
                    orders.remove();
                }
                if (orders.last()) break;
                orders.findNext();
            }
        }
    }

    private void removeOrderReference(int orderId) {
        if (!customers.empty()) {
            customers.findFirst();
            while (true) {
                customers.retrieve().removeOrder(orderId);
                if (customers.last()) break;
                customers.findNext();
            }
        }
    }

    // ============ Display Methods ============

    public void displayAllCustomers() {
        if (customers.empty()) {
            System.out.println("No customers registered");
            return;
        }
        
        System.out.println("=== All Customers ===");
        customers.findFirst();
        while (true) {
            customers.retrieve().displaySummary();
            System.out.println("---");
            if (customers.last()) break;
            customers.findNext();
        }
    }

    public void displayAllOrders() {
        if (orders.empty()) {
            System.out.println("No orders found");
            return;
        }
        
        System.out.println("=== All Orders ===");
        orders.findFirst();
        while (true) {
            orders.retrieve().displayBriefInfo();
            if (orders.last()) break;
            orders.findNext();
        }
    }

    public void displayOrdersByStatus(String status) {
        if (orders.empty()) {
            System.out.println("No orders found");
            return;
        }
        
        System.out.println("=== Orders with status: " + status + " ===");
        boolean found = false;
        orders.findFirst();
        while (true) {
            Order order = orders.retrieve();
            if (order.getStatus().equalsIgnoreCase(status)) {
                order.displayBriefInfo();
                found = true;
            }
            if (orders.last()) break;
            orders.findNext();
        }
        
        if (!found) {
            System.out.println("No orders found with status: " + status);
        }
    }

    // ============ Statistics Methods ============

    public int getTotalCustomers() {
        int count = 0;
        if (!customers.empty()) {
            customers.findFirst();
            while (true) {
                count++;
                if (customers.last()) break;
                customers.findNext();
            }
        }
        return count;
    }

    public int getTotalOrders() {
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

    public double calculateTotalRevenue() {
        double total = 0.0;
        if (!orders.empty()) {
            orders.findFirst();
            while (true) {
                Order order = orders.retrieve();
                if (!order.getStatus().equalsIgnoreCase("canceled")) {
                    total += order.getTotalPrice();
                }
                if (orders.last()) break;
                orders.findNext();
            }
        }
        return total;
    }

    public void displaySystemStatistics() {
        System.out.println("=== System Statistics ===");
        System.out.println("Total Customers: " + getTotalCustomers());
        System.out.println("Total Orders: " + getTotalOrders());
        System.out.println("Total Revenue: $" + String.format("%.2f", calculateTotalRevenue()));
        System.out.println("========================");
    }
}