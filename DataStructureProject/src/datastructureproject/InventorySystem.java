package datastructureproject;

import java.time.LocalDate;
import java.io.File;
import java.util.Scanner;

/**
 * E-Commerce Inventory System - Phase 2
 * Upgraded from LinkedList (Phase 1) to BST (Phase 2) for improved performance
 * 
 * Phase 1 Complexity: O(n) for search operations
 * Phase 2 Complexity: O(log n) for search operations
 */
public class InventorySystem {
    // Phase 2: BST-based storage for O(log n) operations
    private Customers customerManager;
    private Orders orderManager;
    private BST_int<Product> products;  // NEW: BST instead of LinkedList
    
    // Reviews remain LinkedList (makes sense for this use case)
    private LinkedList<Review> reviews;

    public InventorySystem() {
        this.customerManager = new Customers();
        this.orderManager = new Orders(customerManager);
        this.products = new BST_int<>();  // Phase 2 upgrade
        this.reviews = new LinkedList<>();
    }

    // ============================================
    // CUSTOMER OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    /**
     * Register new customer
     * Phase 1: O(n) - Linear search for duplicates
     * Phase 2: O(log n) - BST search
     */
    public boolean registerCustomer(Customer customer) {
        return customerManager.addCustomer(customer);
    }

    /**
     * Find customer by ID
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST search
     */
    public Customer findCustomer(int customerId) {
        return customerManager.findCustomerById(customerId);
    }

    /**
     * Remove customer and all associated orders
     * Phase 2: O(log n) for customer + O(n) for orders cleanup
     */
    public boolean removeCustomer(int customerId) {
        removeCustomerOrders(customerId);
        return customerManager.removeCustomer(customerId);
    }

    /**
     * Update customer information
     * Phase 2: O(log n)
     */
    public boolean updateCustomer(int customerId, Customer updatedCustomer) {
        return customerManager.updateCustomer(customerId, updatedCustomer);
    }

    /**
     * Display all customers (sorted by ID)
     * Phase 1: O(n) - Direct traversal
     * Phase 2: O(n) - In-order traversal (naturally sorted)
     */
    public void displayAllCustomers() {
        customerManager.displayAllCustomers();
    }

    /**
     * Search customer by name
     * O(n) - Requires full traversal as name is not the key
     */
    public Customer searchCustomerByName(String name) {
        return customerManager.searchByName(name);
    }

    /**
     * Search customer by email
     * O(n) - Requires full traversal
     */
    public Customer searchCustomerByEmail(String email) {
        return customerManager.searchByEmail(email);
    }

    // ============================================
    // ORDER OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    /**
     * Create new order
     * Phase 1: O(n) - Linear search for duplicate check
     * Phase 2: O(log n) - BST search and insert
     */
    public boolean createOrder(Order order) {
        Customer customer = findCustomer(order.getCustomerId());
        if (customer == null) {
            System.out.println("Error: Customer not found - " + order.getCustomerId());
            return false;
        }
        return orderManager.addOrder(order);
    }

    /**
     * Find order by ID
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST search
     */
    public Order findOrder(int orderId) {
        return orderManager.findOrderById(orderId);
    }

    /**
     * Cancel order
     * Phase 2: O(log n)
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
        return true;
    }

    /**
     * Update order status
     * Phase 2: O(log n)
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        return orderManager.updateOrderStatus(orderId, newStatus);
    }

    /**
     * Display all orders
     * Phase 2: O(n) - In-order traversal
     */
    public void displayAllOrders() {
        orderManager.displayAllOrders();
    }

    // ============================================
    // PRODUCT OPERATIONS
    // Phase 1: O(n) | Phase 2: O(log n)
    // ============================================
    
    /**
     * Add product
     * Phase 1: O(n) - Linear search for duplicates
     * Phase 2: O(log n) - BST insert with duplicate check
     */
    public boolean addProduct(Product product) {
        if (product == null || !product.isValidProduct()) {
            System.out.println("Invalid product data");
            return false;
        }
        
        boolean added = products.add(product.getProductId(), product);
        if (added) {
            System.out.println("✓ Product added successfully: " + product.getName());
        } else {
            System.out.println("✗ Product with ID " + product.getProductId() + " already exists");
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
     * O(n) - Requires full traversal as name is not the key
     */
    public Product findProductByName(String name) {
        return findProductByNameHelper(products.getRoot(), name);
    }
    
    private Product findProductByNameHelper(BSTNode<Product> node, String name) {
        if (node == null) {
            return null;
        }
        
        if (node.data.getName().equalsIgnoreCase(name)) {
            return node.data;
        }
        
        Product left = findProductByNameHelper(node.left, name);
        if (left != null) {
            return left;
        }
        
        return findProductByNameHelper(node.right, name);
    }

    /**
     * Remove product
     * Phase 1: O(n) - Linear search and removal
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
     * Phase 1: O(n) search + O(1) update
     * Phase 2: O(log n) search + O(1) update
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
        
        System.out.println("✓ Product updated successfully");
        return true;
    }

    /**
     * Get out of stock products
     * O(n) - Must check all products
     */
    public LinkedList<Product> getOutOfStockProducts() {
        LinkedList<Product> outOfStock = new LinkedList<>();
        collectOutOfStock(products.getRoot(), outOfStock);
        return outOfStock;
    }
    
    private void collectOutOfStock(BSTNode<Product> node, LinkedList<Product> list) {
        if (node == null) {
            return;
        }
        collectOutOfStock(node.left, list);
        if (node.data.isOutOfStock()) {
            list.addLast(node.data);
        }
        collectOutOfStock(node.right, list);
    }

    public void displayOutOfStockProducts() {
        LinkedList<Product> outOfStock = getOutOfStockProducts();
        
        if (outOfStock.empty()) {
            System.out.println("All products are in stock");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("      OUT OF STOCK PRODUCTS");
        System.out.println("========================================");
        outOfStock.findFirst();
        while (true) {
            outOfStock.retrieve().display();
            System.out.println("----------------------------------------");
            if (outOfStock.last()) break;
            outOfStock.findNext();
        }
        System.out.println("========================================\n");
    }

    /**
     * Display all products
     * Phase 2: O(n) - In-order traversal (sorted by ID)
     */
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("          ALL PRODUCTS");
        System.out.println("========================================");
        displayProductsInOrder(products.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayProductsInOrder(BSTNode<Product> node) {
        if (node == null) {
            return;
        }
        displayProductsInOrder(node.left);
        node.data.display();
        System.out.println("----------------------------------------");
        displayProductsInOrder(node.right);
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
        
        // Check for duplicate review ID
        if (!reviews.empty()) {
            reviews.findFirst();
            while (true) {
                if (reviews.retrieve().getReviewId() == review.getReviewId()) {
                    System.out.println("Review with ID " + review.getReviewId() + " already exists");
                    return false;
                }
                if (reviews.last()) break;
                reviews.findNext();
            }
        }
        
        reviews.addLast(review);
        product.addReview(review);
        customer.addReview(review);
        System.out.println("✓ Review added successfully");
        return true;
    }

    public boolean editReview(int reviewId, int newRating, String newComment) {
        if (reviews.empty()) {
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

    public void displayAllReviews() {
        if (reviews.empty()) {
            System.out.println("No reviews found");
            return;
        }
        
        System.out.println("=== All Reviews ===");
        reviews.findFirst();
        while (true) {
            reviews.retrieve().display();
            System.out.println("---");
            if (reviews.last()) break;
            reviews.findNext();
        }
    }

    // ============================================
    // ADVANCED QUERIES (Phase 2 Requirements)
    // ============================================
    
    /**
     * ADVANCED QUERY 1: Find all orders between two dates
     * Uses in-order BST traversal
     * Phase 2: O(n) - Must check all orders
     */
    public void findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        orderManager.findOrdersBetweenDates(startDate, endDate);
    }

    /**
     * ADVANCED QUERY 2: List all products within a price range
     * Phase 2: O(n) - Must check all products
     * Uses in-order traversal for sorted output
     */
    public void findProductsInPriceRange(double minPrice, double maxPrice) {
        if (products.isEmpty()) {
            System.out.println("No products in the system");
            return;
        }
        
        if (minPrice > maxPrice) {
            System.out.println("Error: Min price cannot be greater than max price");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  PRODUCTS IN PRICE RANGE $" + minPrice + " - $" + maxPrice);
        System.out.println("========================================");
        
        LinkedList<Product> result = new LinkedList<>();
        collectProductsInPriceRange(products.getRoot(), minPrice, maxPrice, result);
        
        if (result.empty()) {
            System.out.println("No products found in this price range");
        } else {
            int count = 0;
            result.findFirst();
            while (true) {
                Product p = result.retrieve();
                System.out.println((++count) + ". " + p.getName() + 
                                 " | ID: " + p.getProductId() +
                                 " | Price: $" + p.getPrice() +
                                 " | Stock: " + p.getStock());
                if (result.last()) break;
                result.findNext();
            }
            System.out.println("----------------------------------------");
            System.out.println("Total products found: " + count);
        }
        System.out.println("========================================\n");
    }
    
    private void collectProductsInPriceRange(BSTNode<Product> node, double minPrice, 
                                            double maxPrice, LinkedList<Product> result) {
        if (node == null) {
            return;
        }
        
        collectProductsInPriceRange(node.left, minPrice, maxPrice, result);
        
        if (node.data.getPrice() >= minPrice && node.data.getPrice() <= maxPrice) {
            result.addLast(node.data);
        }
        
        collectProductsInPriceRange(node.right, minPrice, maxPrice, result);
    }

    /**
     * ADVANCED QUERY 3: Show top 3 most reviewed or highest rated products
     * Phase 2: O(n) - Collect all products and sort
     */
    public LinkedList<Product> getTop3ProductsByRating() {
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
        int size = 0;
        allProducts.findFirst();
        while (true) {
            size++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
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
            // Swap
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
                top3.insert(productArray[i]);
            }
        }
        
        return top3;
    }
    
    private void collectAllProducts(BSTNode<Product> node, LinkedList<Product> list) {
        if (node == null) {
            return;
        }
        collectAllProducts(node.left, list);
        list.addLast(node.data);
        collectAllProducts(node.right, list);
    }

    public void displayTop3Products() {
        LinkedList<Product> top3 = getTop3ProductsByRating();
        
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
     * ADVANCED QUERY 4: List all customers sorted alphabetically
     * Implemented in Customers class
     */
    public void displayCustomersAlphabetically() {
        customerManager.displayCustomersAlphabetically();
    }

    /**
     * ADVANCED QUERY 5: Display all customers who reviewed a product
     * Sorted by rating or customer ID
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
        
        // Collect all customers who reviewed this product
        if (!reviews.empty()) {
            reviews.findFirst();
            while (true) {
                Review review = reviews.retrieve();
                if (review.getProductId() == productId) {
                    Customer customer = findCustomer(review.getCustomerId());
                    if (customer != null) {
                        CustomerReviewPair pair = new CustomerReviewPair(customer, review);
                        pairs.addLast(pair);
                    }
                }
                if (reviews.last()) break;
                reviews.findNext();
            }
        }
        
        if (pairs.empty()) {
            System.out.println("No reviews found for this product");
        } else {
            // Sort by rating (descending) then by customer ID
            sortCustomerReviewPairs(pairs);
            
            int count = 0;
            pairs.findFirst();
            while (true) {
                CustomerReviewPair pair = pairs.retrieve();
                count++;
                System.out.println(count + ". Customer: " + pair.customer.getName() +
                                 " (ID: " + pair.customer.getCustomerId() + ")");
                System.out.println("   Rating: " + pair.review.getRating() + "/5");
                System.out.println("   Comment: " + pair.review.getComment());
                System.out.println("   ----------------------------------------");
                if (pairs.last()) break;
                pairs.findNext();
            }
            System.out.println("Total reviewers: " + count);
        }
        System.out.println("========================================\n");
    }
    
    // Helper class for query 5
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
                
                // Sort by rating (descending), then by customer ID (ascending)
                boolean shouldSwap = false;
                if (current.review.getRating() < next.review.getRating()) {
                    shouldSwap = true;
                } else if (current.review.getRating() == next.review.getRating() &&
                          current.customer.getCustomerId() > next.customer.getCustomerId()) {
                    shouldSwap = true;
                }
                
                if (shouldSwap) {
                    // Swap
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
        
        System.out.println("=== Common High-Rated Products (Rating > 4) ===");
        System.out.println("Between Customer " + customer1Id + " and Customer " + customer2Id);
        common.findFirst();
        while (true) {
            common.retrieve().display();
            System.out.println("---");
            if (common.last()) break;
            common.findNext();
        }
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
        return products.countNodes();
    }

    public int getTotalReviews() {
        return reviews.size();
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
        System.out.println("\n========================================");
        System.out.println("       SYSTEM STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Customers: " + getTotalCustomers());
        System.out.println("Total Products: " + getTotalProducts());
        System.out.println("Total Orders: " + getTotalOrders());
        System.out.println("Total Reviews: " + getTotalReviews());
        System.out.println("Total Revenue: $" + calculateTotalRevenue());
        System.out.println("Out of Stock Products: " + getOutOfStockProducts().size());
        System.out.println("========================================\n");
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
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading products from: " + filename);
            
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine().trim();
                if (!firstLine.matches("^\\d+.*")) {
                    // Header, already skipped
                } else {
                    // Process as data
                    processProductLine(firstLine);
                }
            }
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    if (processProductLine(line)) {
                        count++;
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " products successfully\n");
        } catch (Exception e) {
            System.out.println("✗ Error loading products: " + e.getMessage());
        }
    }
    
    private boolean processProductLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int stock = Integer.parseInt(parts[3].trim());
                Product product = new Product(id, name, price, stock);
                return addProduct(product);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error parsing product data: " + line);
        }
        return false;
    }

    public void loadReviewsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading reviews from: " + filename);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }
            
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
            System.out.println("✓ Loaded " + count + " reviews successfully\n");
        } catch (Exception e) {
            System.out.println("✗ Error loading reviews: " + e.getMessage());
        }
    }

    public void loadAllDataFromCSV(String customersFile, String productsFile, 
                                   String ordersFile, String reviewsFile) {
        System.out.println("\n========================================");
        System.out.println("  LOADING ALL DATA FROM CSV FILES");
        System.out.println("========================================\n");
        
        loadCustomersFromCSV(customersFile);
        loadProductsFromCSV(productsFile);
        loadOrdersFromCSV(ordersFile);
        loadReviewsFromCSV(reviewsFile);
        
        System.out.println("\n========================================");
        System.out.println("   ALL DATA LOADED SUCCESSFULLY!");
        System.out.println("========================================\n");
    }

    // ============================================
    // GETTERS
    // ============================================
    
    public Customers getCustomerManager() {
        return customerManager;
    }

    public Orders getOrderManager() {
        return orderManager;
    }

    public BST_int<Product> getProducts() {
        return products;
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }
}
