package datastructureproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Orders {
    private BST_int<Order> orderTree;
    private Customers customerManager;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public Orders() {
        orderTree = new BST_int<>();
        customerManager = null;
    }
    
    public Orders(Customers customers) {
        orderTree = new BST_int<>();
        this.customerManager = customers;
    }
    
    public void setCustomerManager(Customers customers) {
        this.customerManager = customers;
    }
    
    // ============ Basic Operations ============
    
    public boolean addOrder(Order order) {
        if (order == null || !order.isValidOrder()) {
            System.out.println("Error: Invalid order data");
            return false;
        }
        
        boolean added = orderTree.add(order.getOrderId(), order);
        if (added) {
            System.out.println("✓ Order added successfully: " + order.getOrderId());
            
            // Link order to customer if customer manager is available
            if (customerManager != null) {
                Customer customer = customerManager.findCustomerById(order.getCustomerId());
                if (customer != null) {
                    customer.placeOrder(order.getOrderId());
                } else {
                    System.out.println("Warning: Customer not found for order: " + order.getCustomerId());
                }
            }
        } else {
            System.out.println("✗ Order ID already exists: " + order.getOrderId());
        }
        return added;
    }
    
    public Order findOrderById(int orderId) {
        return orderTree.getData(orderId);
    }
    
    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order order = findOrderById(orderId);
        if (order == null) {
            System.out.println("✗ Order not found: " + orderId);
            return false;
        }
        
        order.setStatus(newStatus);
        return true;
    }
    
    public boolean removeOrder(int orderId) {
        // Remove from customer's order list first
        if (customerManager != null) {
            Order order = findOrderById(orderId);
            if (order != null) {
                Customer customer = customerManager.findCustomerById(order.getCustomerId());
                if (customer != null) {
                    customer.removeOrder(orderId);
                }
            }
        }
        
        boolean removed = orderTree.delete(orderId);
        if (removed) {
            System.out.println("✓ Order removed successfully");
        } else {
            System.out.println("✗ Order not found: " + orderId);
        }
        return removed;
    }
    
    public boolean isEmpty() {
        return orderTree.isEmpty();
    }
    
    public BST_int<Order> getOrderTree() {
        return orderTree;
    }
    
    // ============ Display Operations ============
    
    public void displayAllOrders() {
        if (orderTree.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           ALL ORDERS");
        System.out.println("========================================");
        displayOrdersInOrder(orderTree.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayOrdersInOrder(BSTNode<Order> node) {
        if (node == null) {
            return;
        }
        displayOrdersInOrder(node.left);
        node.data.displayOrderDetails();
        System.out.println("----------------------------------------");
        displayOrdersInOrder(node.right);
    }
    
    public void displayOrderDetails(int orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         ORDER DETAILS");
        System.out.println("========================================");
        order.displayOrderDetails();
        System.out.println("========================================\n");
    }
    
    // ============ Advanced Queries ============
    
    // Query: Find all orders between two dates
    public void findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        if (orderTree.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        if (startDate.isAfter(endDate)) {
            System.out.println("Error: Start date must be before or equal to end date");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  ORDERS BETWEEN " + startDate + " AND " + endDate);
        System.out.println("========================================");
        
        LinkedList<Order> result = new LinkedList<>();
        findOrdersInDateRange(orderTree.getRoot(), startDate, endDate, result);
        
        if (result.empty()) {
            System.out.println("No orders found in this date range");
        } else {
            double totalRevenue = 0.0;
            int count = 0;
            result.findFirst();
            while (true) {
                Order order = result.retrieve();
                System.out.println("Order #" + order.getOrderId() + 
                                 " | Customer: " + order.getCustomerId() +
                                 " | Date: " + order.getOrderDate() +
                                 " | Total: $" + order.getTotalPrice() +
                                 " | Status: " + order.getStatus());
                totalRevenue += order.getTotalPrice();
                count++;
                if (result.last()) break;
                result.findNext();
            }
            System.out.println("----------------------------------------");
            System.out.println("Total Orders: " + count);
            System.out.println("Total Revenue: $" + totalRevenue);
        }
        System.out.println("========================================\n");
    }
    
    private void findOrdersInDateRange(BSTNode<Order> node, LocalDate startDate, 
                                      LocalDate endDate, LinkedList<Order> result) {
        if (node == null) {
            return;
        }
        
        findOrdersInDateRange(node.left, startDate, endDate, result);
        
        if (node.data.isBetweenDates(startDate, endDate)) {
            result.addLast(node.data);
        }
        
        findOrdersInDateRange(node.right, startDate, endDate, result);
    }
    
    // Query: Find orders by status
    public void findOrdersByStatus(String status) {
        if (orderTree.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  ORDERS WITH STATUS: " + status.toUpperCase());
        System.out.println("========================================");
        
        LinkedList<Order> result = new LinkedList<>();
        findOrdersByStatusHelper(orderTree.getRoot(), status, result);
        
        if (result.empty()) {
            System.out.println("No orders found with status: " + status);
        } else {
            int count = 0;
            result.findFirst();
            while (true) {
                Order order = result.retrieve();
                order.displayBriefInfo();
                count++;
                if (result.last()) break;
                result.findNext();
            }
            System.out.println("----------------------------------------");
            System.out.println("Total: " + count + " orders");
        }
        System.out.println("========================================\n");
    }
    
    private void findOrdersByStatusHelper(BSTNode<Order> node, String status, 
                                         LinkedList<Order> result) {
        if (node == null) {
            return;
        }
        
        findOrdersByStatusHelper(node.left, status, result);
        if (node.data.getStatus().equalsIgnoreCase(status)) {
            result.addLast(node.data);
        }
        findOrdersByStatusHelper(node.right, status, result);
    }
    
    // Query: Find orders by customer
    public void findOrdersByCustomer(int customerId) {
        if (orderTree.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  ORDERS FOR CUSTOMER: " + customerId);
        System.out.println("========================================");
        
        LinkedList<Order> result = new LinkedList<>();
        findOrdersByCustomerHelper(orderTree.getRoot(), customerId, result);
        
        if (result.empty()) {
            System.out.println("No orders found for customer: " + customerId);
        } else {
            double totalSpent = 0.0;
            int count = 0;
            result.findFirst();
            while (true) {
                Order order = result.retrieve();
                order.displayOrderDetails();
                System.out.println("----------------------------------------");
                totalSpent += order.getTotalPrice();
                count++;
                if (result.last()) break;
                result.findNext();
            }
            System.out.println("Total Orders: " + count);
            System.out.println("Total Spent: $" + totalSpent);
        }
        System.out.println("========================================\n");
    }
    
    private void findOrdersByCustomerHelper(BSTNode<Order> node, int customerId, 
                                           LinkedList<Order> result) {
        if (node == null) {
            return;
        }
        
        findOrdersByCustomerHelper(node.left, customerId, result);
        if (node.data.getCustomerId() == customerId) {
            result.addLast(node.data);
        }
        findOrdersByCustomerHelper(node.right, customerId, result);
    }
    
    // Query: Find orders containing specific product
    public void findOrdersWithProduct(int productId) {
        if (orderTree.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  ORDERS CONTAINING PRODUCT: " + productId);
        System.out.println("========================================");
        
        LinkedList<Order> result = new LinkedList<>();
        findOrdersWithProductHelper(orderTree.getRoot(), productId, result);
        
        if (result.empty()) {
            System.out.println("No orders found containing product: " + productId);
        } else {
            int count = 0;
            result.findFirst();
            while (true) {
                Order order = result.retrieve();
                System.out.println("Order #" + order.getOrderId() + 
                                 " | Customer: " + order.getCustomerId() +
                                 " | Date: " + order.getOrderDate() +
                                 " | Status: " + order.getStatus());
                count++;
                if (result.last()) break;
                result.findNext();
            }
            System.out.println("Total: " + count + " orders");
        }
        System.out.println("========================================\n");
    }
    
    private void findOrdersWithProductHelper(BSTNode<Order> node, int productId, 
                                            LinkedList<Order> result) {
        if (node == null) {
            return;
        }
        
        findOrdersWithProductHelper(node.left, productId, result);
        if (node.data.containsProduct(productId)) {
            result.addLast(node.data);
        }
        findOrdersWithProductHelper(node.right, productId, result);
    }
    
    // Query: Find orders above certain price
    public void findOrdersAbovePrice(double minPrice) {
        if (orderTree.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  ORDERS ABOVE $" + minPrice);
        System.out.println("========================================");
        
        LinkedList<Order> result = new LinkedList<>();
        findOrdersAbovePriceHelper(orderTree.getRoot(), minPrice, result);
        
        if (result.empty()) {
            System.out.println("No orders found above $" + minPrice);
        } else {
            result.findFirst();
            while (true) {
                Order order = result.retrieve();
                System.out.println("Order #" + order.getOrderId() + 
                                 " | Total: $" + order.getTotalPrice() +
                                 " | Customer: " + order.getCustomerId());
                if (result.last()) break;
                result.findNext();
            }
        }
        System.out.println("========================================\n");
    }
    
    private void findOrdersAbovePriceHelper(BSTNode<Order> node, double minPrice, 
                                           LinkedList<Order> result) {
        if (node == null) {
            return;
        }
        
        findOrdersAbovePriceHelper(node.left, minPrice, result);
        if (node.data.getTotalPrice() >= minPrice) {
            result.addLast(node.data);
        }
        findOrdersAbovePriceHelper(node.right, minPrice, result);
    }
    
    // ============ Statistics ============
    
    public int getTotalOrders() {
        return orderTree.countNodes();
    }
    
    public void displayStatistics() {
        if (orderTree.isEmpty()) {
            System.out.println("No order statistics available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("        ORDER STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Orders: " + getTotalOrders());
        
        double totalRevenue = calculateTotalRevenue(orderTree.getRoot());
        System.out.println("Total Revenue: $" + totalRevenue);
        
        if (getTotalOrders() > 0) {
            System.out.println("Average Order Value: $" + (totalRevenue / getTotalOrders()));
        }
        
        // Count by status
        int pending = countOrdersByStatus(orderTree.getRoot(), "pending");
        int shipped = countOrdersByStatus(orderTree.getRoot(), "shipped");
        int delivered = countOrdersByStatus(orderTree.getRoot(), "delivered");
        int canceled = countOrdersByStatus(orderTree.getRoot(), "canceled");
        
        System.out.println("\nOrders by Status:");
        System.out.println("  Pending: " + pending);
        System.out.println("  Shipped: " + shipped);
        System.out.println("  Delivered: " + delivered);
        System.out.println("  Canceled: " + canceled);
        
        System.out.println("========================================\n");
    }
    
    private double calculateTotalRevenue(BSTNode<Order> node) {
        if (node == null) {
            return 0.0;
        }
        return node.data.getTotalPrice() + 
               calculateTotalRevenue(node.left) + 
               calculateTotalRevenue(node.right);
    }
    
    private int countOrdersByStatus(BSTNode<Order> node, String status) {
        if (node == null) {
            return 0;
        }
        int count = node.data.getStatus().equalsIgnoreCase(status) ? 1 : 0;
        return count + countOrdersByStatus(node.left, status) + 
               countOrdersByStatus(node.right, status);
    }
    
    // ============ File Operations ============
    
    public void loadOrdersFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading orders from: " + filename);
            
            // Skip header if exists
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine().trim();
                if (!firstLine.matches("^\\d+.*")) {
                    // It's a header, already skipped
                } else {
                    // It's data, process it
                    processOrderLine(firstLine);
                }
            }
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    if (processOrderLine(line)) {
                        count++;
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " orders successfully\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ Error: File not found - " + filename);
        } catch (Exception e) {
            System.out.println("✗ Error loading orders: " + e.getMessage());
        }
    }
    
    private boolean processOrderLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                int orderId = Integer.parseInt(parts[0].trim().replace("\"", ""));
                int customerId = Integer.parseInt(parts[1].trim().replace("\"", ""));
                String products = parts[2].trim().replace("\"", "");
                double totalPrice = Double.parseDouble(parts[3].trim());
                LocalDate orderDate = LocalDate.parse(parts[4].trim(), DATE_FORMAT);
                String status = parts[5].trim();
                
                Order order = new Order(orderId, customerId, products, 
                                       totalPrice, orderDate, status);
                return addOrder(order);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("✗ Error parsing order data: " + line);
        }
        return false;
    }
}
