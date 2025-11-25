package datastructureproject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * E-Commerce Inventory System - Phase 2
 * Phase 1: Uses LinkedList directly - O(n) operations
 * Phase 2: Uses BST directly - O(log n) operations
 * 
 * Structure matches Phase 1 UML (no manager classes)
 */
public class InventorySystem {
    // Phase 2: BST-based storage (Phase 1 used LinkedList)
    private BST_int<Customer> customers;
    private BST_int<Order> orders;
    private BST_int<Product> products;
    private LinkedList<Review> reviews;  // Reviews stay LinkedList
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventorySystem() {
        this.customers = new BST_int<>();
        this.orders = new BST_int<>();
        this.products = new BST_int<>();
        this.reviews = new LinkedList<>();
    }

    // ============================================
    // CUSTOMER OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    /**
     * Register new customer
     * Phase 1: O(n) - LinkedList search
     * Phase 2: O(log n) - BST search
     */
    public boolean registerCustomer(Customer customer) {
        if (customer == null || !customer.isValidCustomer()) {
            System.out.println("Error: Invalid customer data");
            return false;
        }
        
        boolean added = customers.add(customer.getCustomerId(), customer);
        if (added) {
            System.out.println("✓ Customer registered: " + customer.getName());
        } else {
            System.out.println("✗ Customer ID already exists: " + customer.getCustomerId());
        }
        return added;
    }

    /**
     * Find customer by ID
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST search
     */
    public Customer findCustomer(int customerId) {
        return customers.getData(customerId);
    }

    /**
     * Remove customer
     * Phase 2: O(log n) + cleanup orders
     */
    public boolean removeCustomer(int customerId) {
        removeCustomerOrders(customerId);
        boolean removed = customers.delete(customerId);
        if (removed) {
            System.out.println("✓ Customer removed: " + customerId);
        } else {
            System.out.println("✗ Customer not found: " + customerId);
        }
        return removed;
    }

    /**
     * Display all customers
     * Phase 2: O(n) - In-order traversal (sorted)
     */
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("          ALL CUSTOMERS");
        System.out.println("========================================");
        displayCustomersInOrder(customers.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayCustomersInOrder(BSTNode<Customer> node) {
        if (node == null) return;
        displayCustomersInOrder(node.left);
        node.data.display();
        System.out.println("----------------------------------------");
        displayCustomersInOrder(node.right);
    }

    // ============================================
    // ORDER OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    /**
     * Create new order
     * Phase 1: O(n) - LinkedList search
     * Phase 2: O(log n) - BST search
     */
    public boolean createOrder(Order order) {
        if (order == null || !order.isValidOrder()) {
            System.out.println("Error: Invalid order data");
            return false;
        }
        
        Customer customer = findCustomer(order.getCustomerId());
        if (customer == null) {
            System.out.println("Error: Customer not found - " + order.getCustomerId());
            return false;
        }
        
        boolean added = orders.add(order.getOrderId(), order);
        if (added) {
            customer.placeOrder(order.getOrderId());
            System.out.println("✓ Order created: " + order.getOrderId());
        } else {
            System.out.println("✗ Order ID already exists: " + order.getOrderId());
        }
        return added;
    }

    /**
     * Find order by ID
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST search
     */
    public Order findOrder(int orderId) {
        return orders.getData(orderId);
    }

    /**
     * Cancel order
     */
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
        System.out.println("✓ Order canceled: " + orderId);
        return true;
    }

    /**
     * Update order status
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("✗ Order not found: " + orderId);
            return false;
        }
        
        order.setStatus(newStatus);
        System.out.println("✓ Order status updated: " + orderId + " -> " + newStatus);
        return true;
    }

    /**
     * ADVANCED QUERY 1: Find orders between two dates
     * Phase 2: O(n) - Must check all orders
     */
    public LinkedList<Order> findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        LinkedList<Order> result = new LinkedList<>();
        collectOrdersBetweenDates(orders.getRoot(), startDate, endDate, result);
        return result;
    }
    
    private void collectOrdersBetweenDates(BSTNode<Order> node, LocalDate start, 
                                          LocalDate end, LinkedList<Order> result) {
        if (node == null) return;
        
        collectOrdersBetweenDates(node.left, start, end, result);
        
        if (node.data.isOrderedBetween(start, end)) {
            result.addLast(node.data);
        }
        
        collectOrdersBetweenDates(node.right, start, end, result);
    }

    /**
     * Display all orders
     * Phase 2: O(n) - In-order traversal (sorted)
     */
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           ALL ORDERS");
        System.out.println("========================================");
        displayOrdersInOrder(orders.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayOrdersInOrder(BSTNode<Order> node) {
        if (node == null) return;
        displayOrdersInOrder(node.left);
        node.data.display();
        System.out.println("----------------------------------------");
        displayOrdersInOrder(node.right);
    }

    // ============================================
    // PRODUCT OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    /**
     * Add product
     * Phase 1: O(n) - LinkedList search
     * Phase 2: O(log n) - BST insert
     */
    public boolean addProduct(Product product) {
        if (product == null || !product.isValidProduct()) {
            System.out.println("Error: Invalid product data");
            return false;
        }
        
        boolean added = products.add(product.getProductId(), product);
        if (added) {
            System.out.println("✓ Product added: " + product.getName());
        } else {
            System.out.println("✗ Product ID already exists: " + product.getProductId());
        }
        return added;
    }

    /**
     * Find product by ID
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST search
     */
    public Product findProductById(int productId) {
        return products.getData(productId);
    }

    /**
     * Find product by name
     * O(n) - Must traverse all nodes
     */
    public Product findProductByName(String name) {
        return findProductByNameHelper(products.getRoot(), name);
    }
    
    private Product findProductByNameHelper(BSTNode<Product> node, String name) {
        if (node == null) return null;
        
        if (node.data.getName().equalsIgnoreCase(name)) {
            return node.data;
        }
        
        Product left = findProductByNameHelper(node.left, name);
        if (left != null) return left;
        
        return findProductByNameHelper(node.right, name);
    }

    /**
     * Remove product
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST delete
     */
    public boolean removeProduct(int productId) {
        boolean removed = products.delete(productId);
        if (removed) {
            System.out.println("✓ Product removed: " + productId);
        } else {
            System.out.println("✗ Product not found: " + productId);
        }
        return removed;
    }

    /**
     * Update product
     */
    public boolean updateProduct(int productId, String newName, double newPrice, int newStock) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return false;
        }
        
        if (newName != null && !newName.trim().isEmpty()) {
            product.setName(newName);
        }
        if (newPrice >= 0) {
            product.setPrice(newPrice);
        }
        if (newStock >= 0) {
            product.setStock(newStock);
        }
        
        System.out.println("✓ Product updated: " + productId);
        return true;
    }

    /**
     * Get out of stock products
     */
    public LinkedList<Product> getOutOfStockProducts() {
        LinkedList<Product> outOfStock = new LinkedList<>();
        collectOutOfStock(products.getRoot(), outOfStock);
        return outOfStock;
    }
    
    private void collectOutOfStock(BSTNode<Product> node, LinkedList<Product> list) {
        if (node == null) return;
        collectOutOfStock(node.left, list);
        if (node.data.isOutOfStock()) {
            list.addLast(node.data);
        }
        collectOutOfStock(node.right, list);
    }

    /**
     * Display out of stock products
     */
    public void displayOutOfStockProducts() {
        LinkedList<Product> outOfStock = getOutOfStockProducts();
        
        if (outOfStock.empty()) {
            System.out.println("✓ All products are in stock");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("      OUT OF STOCK PRODUCTS");
        System.out.println("========================================");
        int count = 0;
        outOfStock.findFirst();
        while (true) {
            count++;
            Product p = outOfStock.retrieve();
            System.out.println(count + ". " + p.getName() + " (ID: " + p.getProductId() + ")");
            if (outOfStock.last()) break;
            outOfStock.findNext();
        }
        System.out.println("========================================\n");
    }

    /**
     * Display all products
     * Phase 2: O(n) - In-order traversal (sorted)
     */
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("          ALL PRODUCTS");
        System.out.println("========================================");
        displayProductsInOrder(products.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayProductsInOrder(BSTNode<Product> node) {
        if (node == null) return;
        displayProductsInOrder(node.left);
        node.data.display();
        System.out.println("----------------------------------------");
        displayProductsInOrder(node.right);
    }

    // ============================================
    // REVIEW OPERATIONS
    // ============================================
    
    /**
     * Add review
     */
    public boolean addReview(Review review) {
        if (review == null || !review.isValidReview()) {
            System.out.println("Error: Invalid review data");
            return false;
        }
        
        Customer customer = findCustomer(review.getCustomerId());
        if (customer == null) {
            System.out.println("Customer not found: " + review.getCustomerId());
            return false;
        }
        
        Product product = findProductById(review.getProductId());
        if (product == null) {
            System.out.println("Product not found: " + review.getProductId());
            return false;
        }
        
        reviews.addLast(review);
        product.addReview(review);
        customer.addReview(review.getReviewId());
        System.out.println("✓ Review added successfully");
        return true;
    }

    /**
     * Edit review
     */
    public boolean editReview(int reviewId, int newRating, String newComment) {
        if (reviews.empty()) {
            System.out.println("✗ No reviews in the system");
            return false;
        }
        
        reviews.findFirst();
        while (true) {
            Review review = reviews.retrieve();
            if (review.getReviewId() == reviewId) {
                if (newRating >= 1 && newRating <= 5) {
                    review.setRating(newRating);
                }
                if (newComment != null) {
                    review.setComment(newComment);
                }
                System.out.println("✓ Review updated successfully");
                return true;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        System.out.println("✗ Review not found: " + reviewId);
        return false;
    }

    /**
     * Get customer reviews
     */
    public LinkedList<Review> getCustomerReviews(int customerId) {
        LinkedList<Review> customerReviews = new LinkedList<>();
        
        if (reviews.empty()) {
            return customerReviews;
        }
        
        reviews.findFirst();
        while (true) {
            Review review = reviews.retrieve();
            if (review.getCustomerId() == customerId) {
                customerReviews.addLast(review);
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        return customerReviews;
    }

    /**
     * Display customer reviews
     */
    public void displayCustomerReviews(int customerId) {
        LinkedList<Review> customerReviews = getCustomerReviews(customerId);
        
        if (customerReviews.empty()) {
            System.out.println("No reviews found for customer: " + customerId);
            return;
        }
        
        System.out.println("=== Reviews by Customer " + customerId + " ===");
        customerReviews.findFirst();
        while (true) {
            customerReviews.retrieve().display();
            System.out.println("---");
            if (customerReviews.last()) break;
            customerReviews.findNext();
        }
    }

    // ============================================
    // ADVANCED QUERIES (Phase 2 Requirements)
    // ============================================
    
    /**
     * ADVANCED QUERY 2: Products within price range
     */
    public LinkedList<Product> findProductsInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> result = new LinkedList<>();
        collectProductsInPriceRange(products.getRoot(), minPrice, maxPrice, result);
        return result;
    }
    
    private void collectProductsInPriceRange(BSTNode<Product> node, double min, 
                                            double max, LinkedList<Product> result) {
        if (node == null) return;
        
        collectProductsInPriceRange(node.left, min, max, result);
        
        if (node.data.getPrice() >= min && node.data.getPrice() <= max) {
            result.addLast(node.data);
        }
        
        collectProductsInPriceRange(node.right, min, max, result);
    }

    /**
     * ADVANCED QUERY 3: Top 3 products by rating
     */
    public LinkedList<Product> getTop3Products() {
        if (products.isEmpty()) {
            return new LinkedList<>();
        }
        
        // Collect all products
        LinkedList<Product> allProducts = new LinkedList<>();
        collectAllProducts(products.getRoot(), allProducts);
        
        if (allProducts.empty()) {
            return new LinkedList<>();
        }
        
        // Convert to array for sorting
        int size = allProducts.size();
        Product[] productArray = new Product[size];
        double[] ratings = new double[size];
        
        int index = 0;
        allProducts.findFirst();
        while (true) {
            Product product = allProducts.retrieve();
            productArray[index] = product;
            ratings[index] = product.getAverageRating();
            index++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        // Selection sort to find top 3
        for (int i = 0; i < productArray.length && i < 3; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < productArray.length; j++) {
                if (ratings[j] > ratings[maxIndex]) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                Product tempProd = productArray[i];
                double tempRating = ratings[i];
                productArray[i] = productArray[maxIndex];
                ratings[i] = ratings[maxIndex];
                productArray[maxIndex] = tempProd;
                ratings[maxIndex] = tempRating;
            }
        }
        
        // Return top 3
        LinkedList<Product> top3 = new LinkedList<>();
        int limit = Math.min(3, productArray.length);
        for (int i = 0; i < limit; i++) {
            if (ratings[i] > 0) {
                top3.addLast(productArray[i]);
            }
        }
        
        return top3;
    }
    
    private void collectAllProducts(BSTNode<Product> node, LinkedList<Product> list) {
        if (node == null) return;
        collectAllProducts(node.left, list);
        list.addLast(node.data);
        collectAllProducts(node.right, list);
    }

    public void displayTop3Products() {
        LinkedList<Product> top3 = getTop3Products();
        
        if (top3.empty()) {
            System.out.println("No products with reviews available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("     TOP 3 PRODUCTS BY RATING");
        System.out.println("========================================");
        int rank = 1;
        top3.findFirst();
        while (true) {
            Product p = top3.retrieve();
            System.out.println("#" + rank + " - " + p.getName() + 
                             " (ID: " + p.getProductId() + 
                             ") | Rating: " + p.getAverageRating() + "/5.0");
            System.out.println("    Price: $" + p.getPrice() + " | Stock: " + p.getStock());
            rank++;
            if (top3.last()) break;
            top3.findNext();
        }
        System.out.println("========================================\n");
    }

    /**
     * ADVANCED QUERY 4: Customers alphabetically
     */
    public void displayCustomersAlphabetically() {
        if (customers.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        // Collect all customers
        LinkedList<Customer> allCustomers = new LinkedList<>();
        collectAllCustomers(customers.getRoot(), allCustomers);
        
        // Bubble sort by name
        sortCustomersByName(allCustomers);
        
        // Display
        System.out.println("\n========================================");
        System.out.println("  CUSTOMERS (ALPHABETICALLY)");
        System.out.println("========================================");
        int count = 0;
        allCustomers.findFirst();
        while (true) {
            count++;
            Customer c = allCustomers.retrieve();
            System.out.println(count + ". " + c.getName() + 
                             " (ID: " + c.getCustomerId() + 
                             ", Email: " + c.getEmail() + ")");
            if (allCustomers.last()) break;
            allCustomers.findNext();
        }
        System.out.println("========================================\n");
    }
    
    private void collectAllCustomers(BSTNode<Customer> node, LinkedList<Customer> list) {
        if (node == null) return;
        collectAllCustomers(node.left, list);
        list.addLast(node.data);
        collectAllCustomers(node.right, list);
    }
    
    private void sortCustomersByName(LinkedList<Customer> list) {
        if (list.empty()) return;
        
        boolean swapped;
        do {
            swapped = false;
            list.findFirst();
            
            while (!list.last()) {
                Customer current = list.retrieve();
                list.findNext();
                Customer next = list.retrieve();
                
                if (current.getName().compareToIgnoreCase(next.getName()) > 0) {
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

    /**
     * ADVANCED QUERY 5: Customers who reviewed a product
     */
    public void displayCustomersWhoReviewedProduct(int productId) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  CUSTOMERS WHO REVIEWED: " + product.getName());
        System.out.println("========================================");
        
        if (reviews.empty()) {
            System.out.println("No reviews found");
            System.out.println("========================================\n");
            return;
        }
        
        LinkedList<ReviewerPair> pairs = new LinkedList<>();
        
        reviews.findFirst();
        while (true) {
            Review review = reviews.retrieve();
            if (review.getProductId() == productId) {
                Customer customer = findCustomer(review.getCustomerId());
                if (customer != null) {
                    pairs.addLast(new ReviewerPair(customer, review));
                }
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        if (pairs.empty()) {
            System.out.println("No reviews for this product");
        } else {
            int count = 0;
            pairs.findFirst();
            while (true) {
                count++;
                ReviewerPair pair = pairs.retrieve();
                System.out.println(count + ". " + pair.customer.getName() +
                                 " (ID: " + pair.customer.getCustomerId() + ")");
                System.out.println("   Rating: " + pair.review.getRating() + "/5");
                System.out.println("   Comment: " + pair.review.getComment());
                System.out.println("   ----------------------------------------");
                if (pairs.last()) break;
                pairs.findNext();
            }
        }
        System.out.println("========================================\n");
    }
    
    // Helper class for Query 5
    private class ReviewerPair {
        Customer customer;
        Review review;
        
        ReviewerPair(Customer c, Review r) {
            this.customer = c;
            this.review = r;
        }
    }

    /**
     * Get common high-rated products between two customers
     */
    public LinkedList<Product> getCommonHighRatedProducts(int customer1Id, int customer2Id) {
        LinkedList<Product> commonProducts = new LinkedList<>();
        
        LinkedList<Review> customer1Reviews = getCustomerReviews(customer1Id);
        LinkedList<Review> customer2Reviews = getCustomerReviews(customer2Id);
        
        if (customer1Reviews.empty() || customer2Reviews.empty()) {
            return commonProducts;
        }
        
        customer1Reviews.findFirst();
        while (true) {
            Review review1 = customer1Reviews.retrieve();
            
            if (review1.getRating() >= 4) {
                customer2Reviews.findFirst();
                while (true) {
                    Review review2 = customer2Reviews.retrieve();
                    
                    if (review2.getProductId() == review1.getProductId() && review2.getRating() >= 4) {
                        Product product = findProductById(review1.getProductId());
                        if (product != null && !isProductInList(commonProducts, product.getProductId())) {
                            commonProducts.addLast(product);
                        }
                        break;
                    }
                    
                    if (customer2Reviews.last()) break;
                    customer2Reviews.findNext();
                }
            }
            
            if (customer1Reviews.last()) break;
            customer1Reviews.findNext();
        }
        
        return commonProducts;
    }

    public void displayCommonHighRatedProducts(int customer1Id, int customer2Id) {
        LinkedList<Product> common = getCommonHighRatedProducts(customer1Id, customer2Id);
        
        if (common.empty()) {
            System.out.println("No common high-rated products found");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  COMMON HIGH-RATED PRODUCTS (≥4 stars)");
        System.out.println("  Customer " + customer1Id + " & " + customer2Id);
        System.out.println("========================================");
        int count = 0;
        common.findFirst();
        while (true) {
            count++;
            Product p = common.retrieve();
            System.out.println(count + ". " + p.getName() + " (ID: " + p.getProductId() + ")");
            if (common.last()) break;
            common.findNext();
        }
        System.out.println("========================================\n");
    }

    // ============================================
    // STATISTICS
    // ============================================
    
    public int getTotalCustomers() {
        return customers.countNodes();
    }

    public int getTotalOrders() {
        return orders.countNodes();
    }

    public int getTotalProducts() {
        return products.countNodes();
    }

    public int getTotalReviews() {
        return reviews.size();
    }

    public double calculateTotalRevenue() {
        return calculateRevenueHelper(orders.getRoot());
    }

    private double calculateRevenueHelper(BSTNode<Order> node) {
        if (node == null) return 0.0;
        
        double revenue = 0.0;
        if (!node.data.getStatus().equalsIgnoreCase("canceled")) {
            revenue = node.data.getTotalPrice();
        }
        
        return revenue + calculateRevenueHelper(node.left) + calculateRevenueHelper(node.right);
    }

    public void displaySystemStatistics() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       SYSTEM STATISTICS                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Total Customers:  " + getTotalCustomers());
        System.out.println("Total Products:   " + getTotalProducts());
        System.out.println("Total Orders:     " + getTotalOrders());
        System.out.println("Total Reviews:    " + getTotalReviews());
        System.out.println("Total Revenue:    $" + String.format("%.2f", calculateTotalRevenue()));
        System.out.println("Out of Stock:     " + getOutOfStockProducts().size());
        System.out.println("════════════════════════════════════════\n");
    }

    // ============================================
    // HELPER METHODS
    // ============================================
    
    private void removeCustomerOrders(int customerId) {
        removeCustomerOrdersHelper(orders.getRoot(), customerId);
    }

    private void removeCustomerOrdersHelper(BSTNode<Order> node, int customerId) {
        if (node == null) return;
        
        removeCustomerOrdersHelper(node.left, customerId);
        
        if (node.data.getCustomerId() == customerId) {
            orders.delete(node.data.getOrderId());
        }
        
        removeCustomerOrdersHelper(node.right, customerId);
    }

    private boolean isProductInList(LinkedList<Product> list, int productId) {
        if (list.empty()) return false;
        
        list.findFirst();
        while (true) {
            if (list.retrieve().getProductId() == productId) {
                return true;
            }
            if (list.last()) break;
            list.findNext();
        }
        return false;
    }

    // ============================================
    // CSV FILE LOADING
    // ============================================
    
    public void loadCustomersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading customers from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String email = parts[2].trim();
                        
                        Customer customer = new Customer(id, name, email);
                        if (registerCustomer(customer)) {
                            count++;
                        }
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " customers\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ File not found: " + filename);
        }
    }

    public void loadProductsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading products from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        int stock = Integer.parseInt(parts[3].trim());
                        
                        Product product = new Product(id, name, price, stock);
                        if (addProduct(product)) {
                            count++;
                        }
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " products\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ File not found: " + filename);
        }
    }

    public void loadOrdersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading orders from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        int orderId = Integer.parseInt(parts[0].trim());
                        int customerId = Integer.parseInt(parts[1].trim());
                        String productIds = parts[2].trim();
                        double totalPrice = Double.parseDouble(parts[3].trim());
                        LocalDate orderDate = LocalDate.parse(parts[4].trim(), DATE_FORMAT);
                        String status = parts[5].trim();
                        
                        Order order = new Order(orderId, customerId, productIds, 
                                              totalPrice, orderDate, status);
                        if (createOrder(order)) {
                            count++;
                        }
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " orders\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ File not found: " + filename);
        }
    }

    public void loadReviewsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading reviews from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",", 5);
                    if (parts.length >= 5) {
                        int reviewId = Integer.parseInt(parts[0].trim());
                        int productId = Integer.parseInt(parts[1].trim());
                        int customerId = Integer.parseInt(parts[2].trim());
                        int rating = Integer.parseInt(parts[3].trim());
                        String comment = parts[4].trim();
                        
                        Review review = new Review(reviewId, productId, customerId, rating, comment);
                        if (addReview(review)) {
                            count++;
                        }
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " reviews\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ File not found: " + filename);
        }
    }

    public void loadAllDataFromCSV(String customersFile, String productsFile, 
                                   String ordersFile, String reviewsFile) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    LOADING ALL DATA FROM CSV FILES     ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        loadCustomersFromCSV(customersFile);
        loadProductsFromCSV(productsFile);
        loadOrdersFromCSV(ordersFile);
        loadReviewsFromCSV(reviewsFile);
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   ALL DATA LOADED SUCCESSFULLY! ✓      ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
}
