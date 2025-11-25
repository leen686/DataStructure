package datastructureproject;

import java.time.LocalDate;

/**
 * E-Commerce Inventory System - Phase 2 COMPLETE
 * All Phase 1 methods preserved with Phase 2 BST upgrades
 * 
 * Phase 1: LinkedList - O(n) operations
 * Phase 2: BST - O(log n) operations
 */
public class InventorySystem {
    // Phase 2: Manager-based architecture with BST
    private Customers customerManager;
    private Orders orderManager;
    private Products productManager;
    private Reviews reviewManager;

    public InventorySystem() {
        this.customerManager = new Customers();
        this.orderManager = new Orders(customerManager);
        this.productManager = new Products();
        this.reviewManager = new Reviews();
    }

    // ============================================
    // CUSTOMER OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    public boolean registerCustomer(Customer customer) {
        return customerManager.addCustomer(customer);
    }

    public Customer findCustomer(int customerId) {
        return customerManager.findCustomerById(customerId);
    }

    public boolean removeCustomer(int customerId) {
        // Remove all customer's orders first
        removeCustomerOrders(customerId);
        return customerManager.removeCustomer(customerId);
    }

    public boolean updateCustomer(int customerId, Customer updatedCustomer) {
        return customerManager.updateCustomer(customerId, updatedCustomer);
    }

    public void displayAllCustomers() {
        customerManager.displayAllCustomers();
    }

    public Customer searchCustomerByName(String name) {
        return customerManager.searchByName(name);
    }

    public Customer searchCustomerByEmail(String email) {
        return customerManager.searchByEmail(email);
    }

    // ============================================
    // ORDER OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    public boolean createOrder(Order order) {
        Customer customer = findCustomer(order.getCustomerId());
        if (customer == null) {
            System.out.println("Error: Customer not found - " + order.getCustomerId());
            return false;
        }
        return orderManager.addOrder(order);
    }

    public Order findOrder(int orderId) {
        return orderManager.findOrderById(orderId);
    }

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

    public boolean updateOrderStatus(int orderId, String newStatus) {
        return orderManager.updateOrderStatus(orderId, newStatus);
    }

    public void displayAllOrders() {
        orderManager.displayAllOrders();
    }

    // ============================================
    // PRODUCT OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    public boolean addProduct(Product product) {
        return productManager.addProduct(product);
    }

    public Product findProductById(int productId) {
        return productManager.findProductById(productId);
    }

    public Product findProductByName(String name) {
        return productManager.findProductByName(name);
    }

    public boolean removeProduct(int productId) {
        return productManager.removeProduct(productId);
    }

    public boolean updateProduct(int productId, String newName, double newPrice, int newStock) {
        return productManager.updateProductFields(productId, newName, newPrice, newStock);
    }

    public LinkedList<Product> getOutOfStockProducts() {
        return productManager.getOutOfStockProducts();
    }

    public void displayOutOfStockProducts() {
        productManager.displayOutOfStockProducts();
    }

    public void displayAllProducts() {
        productManager.displayAllProducts();
    }

    // ============================================
    // REVIEW OPERATIONS
    // ============================================
    
    public boolean addReview(Review review) {
        if (review == null || !review.isValidReview()) {
            System.out.println("Invalid review data");
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
        
        boolean added = reviewManager.addReview(review);
        if (added) {
            product.addReview(review);
            customer.addReview(review);
        }
        return added;
    }

    public boolean editReview(int reviewId, int newRating, String newComment) {
        return reviewManager.editReview(reviewId, newRating, newComment);
    }

    public LinkedList<Review> getCustomerReviews(int customerId) {
        return reviewManager.getReviewsByCustomer(customerId);
    }

    public void displayCustomerReviews(int customerId) {
        reviewManager.displayReviewsByCustomer(customerId);
    }

    public void displayAllReviews() {
        reviewManager.displayAllReviews();
    }

    // ============================================
    // ADVANCED QUERIES (Phase 2 Requirements)
    // ============================================
    
    /**
     * ADVANCED QUERY 1: Find all orders between two dates
     */
    public void findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        orderManager.findOrdersBetweenDates(startDate, endDate);
    }

    /**
     * ADVANCED QUERY 2: List all products within a price range
     */
    public void findProductsInPriceRange(double minPrice, double maxPrice) {
        productManager.findProductsInPriceRange(minPrice, maxPrice);
    }

    /**
     * ADVANCED QUERY 3: Show top 3 most reviewed or highest rated products
     */
    public void displayTop3Products() {
        productManager.displayTop3Products();
    }

    public LinkedList<Product> getTop3ProductsByRating() {
        return productManager.getTopProductsByRating(3);
    }

    /**
     * ADVANCED QUERY 4: List all customers sorted alphabetically
     */
    public void displayCustomersAlphabetically() {
        customerManager.displayCustomersAlphabetically();
    }

    /**
     * ADVANCED QUERY 5: Display all customers who reviewed a product
     * Sorted by rating (desc) then customer ID (asc)
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
        
        LinkedList<CustomerReviewPair> pairs = new LinkedList<>();
        LinkedList<Review> allReviews = reviewManager.getReviewsByProduct(productId);
        
        if (allReviews.empty()) {
            System.out.println("No reviews found for this product");
            System.out.println("========================================\n");
            return;
        }
        
        // Collect customers with their reviews
        allReviews.findFirst();
        while (true) {
            Review review = allReviews.retrieve();
            Customer customer = findCustomer(review.getCustomerId());
            if (customer != null) {
                CustomerReviewPair pair = new CustomerReviewPair(customer, review);
                pairs.addLast(pair);
            }
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        
        // Sort by rating (desc) then customer ID (asc)
        sortCustomerReviewPairs(pairs);
        
        // Display sorted results
        int count = 0;
        pairs.findFirst();
        while (true) {
            CustomerReviewPair pair = pairs.retrieve();
            count++;
            System.out.println(count + ". Customer: " + pair.customer.getName() +
                             " (ID: " + pair.customer.getCustomerId() + ")");
            System.out.println("   Email: " + pair.customer.getEmail());
            System.out.println("   Rating: " + pair.review.getRating() + "/5");
            System.out.println("   Comment: " + pair.review.getComment());
            System.out.println("   ----------------------------------------");
            if (pairs.last()) break;
            pairs.findNext();
        }
        System.out.println("Total reviewers: " + count);
        System.out.println("========================================\n");
    }
    
    // Helper class for Query 5
    private class CustomerReviewPair {
        Customer customer;
        Review review;
        
        CustomerReviewPair(Customer c, Review r) {
            this.customer = c;
            this.review = r;
        }
    }
    
    private void sortCustomerReviewPairs(LinkedList<CustomerReviewPair> list) {
        if (list.empty()) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            list.findFirst();
            
            while (!list.last()) {
                CustomerReviewPair current = list.retrieve();
                list.findNext();
                CustomerReviewPair next = list.retrieve();
                
                boolean shouldSwap = false;
                if (current.review.getRating() < next.review.getRating()) {
                    shouldSwap = true;
                } else if (current.review.getRating() == next.review.getRating() &&
                          current.customer.getCustomerId() > next.customer.getCustomerId()) {
                    shouldSwap = true;
                }
                
                if (shouldSwap) {
                    Customer tempC = current.customer;
                    Review tempR = current.review;
                    current.customer = next.customer;
                    current.review = next.review;
                    next.customer = tempC;
                    next.review = tempR;
                    swapped = true;
                }
            }
        } while (swapped);
    }

    // ============================================
    // ADDITIONAL PHASE 1 FEATURES
    // ============================================
    
    /**
     * Common high-rated products between two customers
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
            
            if (review1.getRating() > 4) {
                customer2Reviews.findFirst();
                while (true) {
                    Review review2 = customer2Reviews.retrieve();
                    
                    if (review2.getProductId() == review1.getProductId() && review2.getRating() > 4) {
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
            System.out.println("No common products with rating > 4 found");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  COMMON HIGH-RATED PRODUCTS (>4 stars)");
        System.out.println("  Between Customer " + customer1Id + " & " + customer2Id);
        System.out.println("========================================");
        int count = 0;
        common.findFirst();
        while (true) {
            count++;
            Product p = common.retrieve();
            System.out.println(count + ". " + p.getName() + 
                             " (ID: " + p.getProductId() + 
                             ") | Price: $" + p.getPrice());
            if (common.last()) break;
            common.findNext();
        }
        System.out.println("========================================\n");
    }

    /**
     * Customer order history
     */
    public void displayCustomerOrderHistory(int customerId) {
        customerManager.displayCustomerOrderHistory(customerId, this);
    }

    /**
     * Additional query methods
     */
    public void findOrdersByStatus(String status) {
        orderManager.findOrdersByStatus(status);
    }

    public void findOrdersByCustomer(int customerId) {
        orderManager.findOrdersByCustomer(customerId);
    }

    public void findOrdersWithProduct(int productId) {
        orderManager.findOrdersWithProduct(productId);
    }

    public void findOrdersAbovePrice(double minPrice) {
        orderManager.findOrdersAbovePrice(minPrice);
    }

    public void findCustomersByOrderCount(int minOrders) {
        customerManager.findCustomersByOrderCount(minOrders);
    }

    // ============================================
    // STATISTICS
    // ============================================
    
    public int getTotalCustomers() {
        return customerManager.getTotalCustomers();
    }

    public int getTotalOrders() {
        return orderManager.getTotalOrders();
    }

    public int getTotalProducts() {
        return productManager.getTotalProducts();
    }

    public int getTotalReviews() {
        return reviewManager.getTotalReviews();
    }

    public double calculateTotalRevenue() {
        return calculateRevenueHelper(orderManager.getOrderTree().getRoot());
    }

    private double calculateRevenueHelper(BSTNode<Order> node) {
        if (node == null) {
            return 0.0;
        }
        
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
        System.out.println("Average Rating:   " + String.format("%.2f", reviewManager.getAverageRating()) + "/5.0");
        System.out.println("════════════════════════════════════════\n");
    }

    public void displayDetailedStatistics() {
        displaySystemStatistics();
        customerManager.displayStatistics(this);
        orderManager.displayStatistics();
        productManager.displayStatistics();
        reviewManager.displayStatistics();
    }

    // ============================================
    // HELPER METHODS
    // ============================================
    
    private void removeCustomerOrders(int customerId) {
        removeCustomerOrdersHelper(orderManager.getOrderTree().getRoot(), customerId);
    }

    private void removeCustomerOrdersHelper(BSTNode<Order> node, int customerId) {
        if (node == null) {
            return;
        }
        
        removeCustomerOrdersHelper(node.left, customerId);
        
        if (node.data.getCustomerId() == customerId) {
            orderManager.removeOrder(node.data.getOrderId());
        }
        
        removeCustomerOrdersHelper(node.right, customerId);
    }

    private boolean isProductInList(LinkedList<Product> list, int productId) {
        if (list.empty()) {
            return false;
        }
        
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
        customerManager.loadCustomersFromFile(filename);
    }

    public void loadOrdersFromCSV(String filename) {
        orderManager.loadOrdersFromFile(filename);
    }

    public void loadProductsFromCSV(String filename) {
        productManager.loadProductsFromFile(filename);
    }

    public void loadReviewsFromCSV(String filename) {
        reviewManager.loadReviewsFromFile(filename);
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

    // ============================================
    // GETTERS (for integration)
    // ============================================
    
    public Customers getCustomerManager() {
        return customerManager;
    }

    public Orders getOrderManager() {
        return orderManager;
    }

    public Products getProductManager() {
        return productManager;
    }

    public Reviews getReviewManager() {
        return reviewManager;
    }
}
