package datastructureproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Customers {
    private BST_int<Customer> customerTree;
    
    public Customers() {
        customerTree = new BST_int<>();
    }
    
    // ============ Basic Operations ============
    
    public boolean addCustomer(Customer customer) {
        if (customer == null || !customer.isValidCustomer()) {
            System.out.println("Error: Invalid customer data");
            return false;
        }
        
        boolean added = customerTree.add(customer.getCustomerId(), customer);
        if (added) {
            System.out.println("✓ Customer added successfully: " + customer.getName());
        } else {
            System.out.println("✗ Customer ID already exists: " + customer.getCustomerId());
        }
        return added;
    }
    
    public Customer findCustomerById(int customerId) {
        return customerTree.getData(customerId);
    }
    
    public boolean updateCustomer(int customerId, Customer updatedCustomer) {
        if (updatedCustomer == null || !updatedCustomer.isValidCustomer()) {
            System.out.println("Error: Invalid customer data");
            return false;
        }
        
        boolean updated = customerTree.update(customerId, updatedCustomer);
        if (updated) {
            System.out.println("✓ Customer updated successfully");
        } else {
            System.out.println("✗ Customer not found: " + customerId);
        }
        return updated;
    }
    
    public boolean removeCustomer(int customerId) {
        boolean removed = customerTree.delete(customerId);
        if (removed) {
            System.out.println("✓ Customer removed successfully");
        } else {
            System.out.println("✗ Customer not found: " + customerId);
        }
        return removed;
    }
    
    public boolean isEmpty() {
        return customerTree.isEmpty();
    }
    
    public BST_int<Customer> getCustomerTree() {
        return customerTree;
    }
    
    // ============ Display Operations ============
    
    public void displayAllCustomers() {
        if (customerTree.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("          ALL CUSTOMERS");
        System.out.println("========================================");
        displayCustomersInOrder(customerTree.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayCustomersInOrder(BSTNode<Customer> node) {
        if (node == null) {
            return;
        }
        displayCustomersInOrder(node.left);
        node.data.displaySummary();
        System.out.println("----------------------------------------");
        displayCustomersInOrder(node.right);
    }
    
    public void displayCustomerDetails(int customerId, InventorySystem system) {
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return;
        }
        customer.displayDetailedInfo(system);
    }
    
    // ============ Search Operations ============
    
    public Customer searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return searchByNameHelper(customerTree.getRoot(), name.toLowerCase().trim());
    }
    
    private Customer searchByNameHelper(BSTNode<Customer> node, String name) {
        if (node == null) {
            return null;
        }
        
        if (node.data.getName().toLowerCase().contains(name)) {
            return node.data;
        }
        
        Customer leftResult = searchByNameHelper(node.left, name);
        if (leftResult != null) {
            return leftResult;
        }
        
        return searchByNameHelper(node.right, name);
    }
    
    public Customer searchByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return searchByEmailHelper(customerTree.getRoot(), email.toLowerCase().trim());
    }
    
    private Customer searchByEmailHelper(BSTNode<Customer> node, String email) {
        if (node == null) {
            return null;
        }
        
        if (node.data.getEmail().toLowerCase().equals(email)) {
            return node.data;
        }
        
        Customer leftResult = searchByEmailHelper(node.left, email);
        if (leftResult != null) {
            return leftResult;
        }
        
        return searchByEmailHelper(node.right, email);
    }
    
    // ============ Advanced Queries ============
    
    // Query: List all customers sorted alphabetically by name
    public void displayCustomersAlphabetically() {
        if (customerTree.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("    CUSTOMERS (ALPHABETICALLY)");
        System.out.println("========================================");
        
        LinkedList<Customer> sortedCustomers = new LinkedList<>();
        collectAllCustomers(customerTree.getRoot(), sortedCustomers);
        
        // Sort alphabetically using bubble sort
        sortCustomersByName(sortedCustomers);
        
        // Display sorted customers
        if (!sortedCustomers.empty()) {
            sortedCustomers.findFirst();
            int count = 1;
            while (true) {
                Customer c = sortedCustomers.retrieve();
                System.out.println(count + ". " + c.getName() + " (ID: " + 
                                 c.getCustomerId() + ", Email: " + c.getEmail() + ")");
                if (sortedCustomers.last()) break;
                sortedCustomers.findNext();
                count++;
            }
        }
        System.out.println("========================================\n");
    }
    
    private void collectAllCustomers(BSTNode<Customer> node, LinkedList<Customer> list) {
        if (node == null) {
            return;
        }
        collectAllCustomers(node.left, list);
        list.addLast(node.data);
        collectAllCustomers(node.right, list);
    }
    
    private void sortCustomersByName(LinkedList<Customer> list) {
        if (list.empty()) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            list.findFirst();
            
            while (!list.last()) {
                Customer current = list.retrieve();
                list.findNext();
                Customer next = list.retrieve();
                
                if (current.getName().compareToIgnoreCase(next.getName()) > 0) {
                    // Swap by updating data
                    String tempName = current.getName();
                    String tempEmail = current.getEmail();
                    int tempId = current.getCustomerId();
                    
                    current.setName(next.getName());
                    current.setEmail(next.getEmail());
                    current.setCustomerId(next.getCustomerId());
                    
                    next.setName(tempName);
                    next.setEmail(tempEmail);
                    next.setCustomerId(tempId);
                    
                    swapped = true;
                }
            }
        } while (swapped);
    }
    
    // Query: Get customer's complete order history
    public void displayCustomerOrderHistory(int customerId, InventorySystem system) {
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("   ORDER HISTORY FOR: " + customer.getName());
        System.out.println("========================================");
        customer.viewOrderHistory(system);
        System.out.println("Total Spending: $" + customer.calculateTotalSpending(system));
        System.out.println("========================================\n");
    }
    
    // Query: Find customers with specific number of orders
    public void findCustomersByOrderCount(int minOrders) {
        if (customerTree.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  CUSTOMERS WITH " + minOrders + "+ ORDERS");
        System.out.println("========================================");
        
        LinkedList<Customer> result = new LinkedList<>();
        findCustomersByOrderCountHelper(customerTree.getRoot(), minOrders, result);
        
        if (result.empty()) {
            System.out.println("No customers found with " + minOrders + "+ orders");
        } else {
            result.findFirst();
            while (true) {
                Customer c = result.retrieve();
                System.out.println(c.getName() + " (ID: " + c.getCustomerId() + 
                                 ") - Orders: " + c.countOrders());
                if (result.last()) break;
                result.findNext();
            }
        }
        System.out.println("========================================\n");
    }
    
    private void findCustomersByOrderCountHelper(BSTNode<Customer> node, 
                                                 int minOrders, LinkedList<Customer> result) {
        if (node == null) {
            return;
        }
        findCustomersByOrderCountHelper(node.left, minOrders, result);
        if (node.data.countOrders() >= minOrders) {
            result.addLast(node.data);
        }
        findCustomersByOrderCountHelper(node.right, minOrders, result);
    }
    
    // ============ Statistics ============
    
    public int getTotalCustomers() {
        return customerTree.countNodes();
    }
    
    public void displayStatistics(InventorySystem system) {
        if (customerTree.isEmpty()) {
            System.out.println("No customer statistics available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("       CUSTOMER STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Customers: " + getTotalCustomers());
        
        int totalOrders = calculateTotalOrders(customerTree.getRoot());
        System.out.println("Total Orders Placed: " + totalOrders);
        
        if (getTotalCustomers() > 0) {
            System.out.println("Average Orders per Customer: " + 
                             (double)totalOrders / getTotalCustomers());
        }
        
        double totalSpending = calculateTotalSpending(customerTree.getRoot(), system);
        System.out.println("Total Revenue: $" + totalSpending);
        
        System.out.println("========================================\n");
    }
    
    private int calculateTotalOrders(BSTNode<Customer> node) {
        if (node == null) {
            return 0;
        }
        return node.data.countOrders() + 
               calculateTotalOrders(node.left) + 
               calculateTotalOrders(node.right);
    }
    
    private double calculateTotalSpending(BSTNode<Customer> node, InventorySystem system) {
        if (node == null) {
            return 0.0;
        }
        return node.data.calculateTotalSpending(system) + 
               calculateTotalSpending(node.left, system) + 
               calculateTotalSpending(node.right, system);
    }
    
    // ============ File Operations ============
    
    public void loadCustomersFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading customers from: " + filename);
            
            // Skip header if exists
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine().trim();
                // Check if it's a header
                if (!firstLine.matches("^\\d+.*")) {
                    // It's a header, already skipped
                } else {
                    // It's data, process it
                    processCustomerLine(firstLine);
                }
            }
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    if (processCustomerLine(line)) {
                        count++;
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " customers successfully\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ Error: File not found - " + filename);
        } catch (Exception e) {
            System.out.println("✗ Error loading customers: " + e.getMessage());
        }
    }
    
    private boolean processCustomerLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String email = parts[2].trim();
                
                Customer customer = new Customer(id, name, email);
                return addCustomer(customer);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error parsing customer data: " + line);
        }
        return false;
    }
}
