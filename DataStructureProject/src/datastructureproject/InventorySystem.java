package datastructureproject;

import java.time.LocalDate;
import java.io.File;
import java.util.Scanner;

public class InventorySystem {
    // Using BSTree for main storage
    private BSTree<Integer, Customer> customers;   // Key: customerId
    private BSTree<Integer, Order> orders;         // Key: orderId
    private BSTree<Integer, Product> products;     // Key: productId
    private BSTree<Integer, Review> reviews;       // Key: reviewId

    public InventorySystem() {
        this.customers = new BSTree<>();
        this.orders = new BSTree<>();
        this.products = new BSTree<>();
        this.reviews = new BSTree<>();
    }


    // ============= CUSTOMER OPERATIONS =============
    
    public boolean registerCustomer(Customer customer) {
        if (customer == null || !customer.isValidCustomer()) {
            System.out.println("Invalid customer data");
            return false;
        }
        
        if (customers.find(customer.getCustomerId())) {
            System.out.println("Customer with ID " + customer.getCustomerId() + " already exists");
            return false;
        }
        
        customers.insert(customer.getCustomerId(), customer);
        System.out.println("Customer registered successfully: " + customer.getName());
        return true;
    }

    public Customer findCustomer(int customerId) {
        if (customers.find(customerId)) {
            return customers.retrieve();
        }
        return null;
    }

    public boolean removeCustomer(int customerId) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return false;
        }
        
        customers.remove(customerId);
        removeCustomerOrders(customerId);
        System.out.println("Customer removed: " + customerId);
        return true;
    }

    public void displayAllCustomers() {
        LinkedList<Customer> customerList = customers.getAllElements();
        
        if (customerList.empty()) {
            System.out.println("No customers registered");
            return;
        }
        
        System.out.println("=== All Customers ===");
        customerList.findFirst();
        while (true) {
            customerList.retrieve().displaySummary();
            System.out.println("---");
            if (customerList.last()) break;
            customerList.findNext();
        }
    }

    public void displayCustomersAlphabetically() {
        LinkedList<Customer> customerList = customers.getAllElements();
        
        if (customerList.empty()) {
            System.out.println("No customers found");
            return;
        }
        
        // Convert to array for sorting
        int size = customerList.size();
        Customer[] customerArray = new Customer[size];
        
        customerList.findFirst();
        int index = 0;
        while (true) {
            customerArray[index++] = customerList.retrieve();
            if (customerList.last()) break;
            customerList.findNext();
        }
        
        // Bubble sort by name
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (customerArray[j].getName().compareTo(customerArray[j + 1].getName()) > 0) {
                    Customer temp = customerArray[j];
                    customerArray[j] = customerArray[j + 1];
                    customerArray[j + 1] = temp;
                }
            }
        }
        
        System.out.println("=== Customers Sorted Alphabetically ===");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + customerArray[i].getName() + 
                             " (ID: " + customerArray[i].getCustomerId() + ")");
        }
    }

  
    // ============= ORDER OPERATIONS =============
    
    public boolean createOrder(Order order) {
        if (order == null || !order.isValidOrder()) {
            System.out.println("Invalid order data");
            return false;
        }
        
        Customer customer = findCustomer(order.getCustomerId());
        if (customer == null) {
            System.out.println("Customer not found: " + order.getCustomerId());
            return false;
        }
        
        if (orders.find(order.getOrderId())) {
            System.out.println("Order with ID " + order.getOrderId() + " already exists");
            return false;
        }
        
        orders.insert(order.getOrderId(), order);
        customer.placeOrder(order.getOrderId());
        System.out.println("Order created successfully: " + order.getOrderId());
        return true;
    }

    public Order findOrder(int orderId) {
        if (orders.find(orderId)) {
            return orders.retrieve();
        }
        return null;
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
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return false;
        }
        
        order.setStatus(newStatus);
        return true;
    }

    public LinkedList<Order> findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        LinkedList<Order> result = new LinkedList<>();
        LinkedList<Order> allOrders = orders.getAllElements();
        
        if (allOrders.empty()) {
            return result;
        }
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            if (order.isBetweenDates(startDate, endDate)) {
                result.addLast(order);
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return result;
    }

    public LinkedList<Order> getCustomerOrderHistory(int customerId) {
        LinkedList<Order> result = new LinkedList<>();
        Customer customer = findCustomer(customerId);
        
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return result;
        }
        
        LinkedList<Integer> orderIds = customer.getOrderIds();
        if (orderIds.empty()) {
            return result;
        }
        
        orderIds.findFirst();
        while (true) {
            int orderId = orderIds.retrieve();
            Order order = findOrder(orderId);
            if (order != null) {
                result.addLast(order);
            }
            if (orderIds.last()) break;
            orderIds.findNext();
        }
        
        return result;
    }

    public void displayAllOrders() {
        LinkedList<Order> orderList = orders.getAllElements();
        
        if (orderList.empty()) {
            System.out.println("No orders found");
            return;
        }
        
        System.out.println("=== All Orders ===");
        orderList.findFirst();
        while (true) {
            orderList.retrieve().displayBriefInfo();
            if (orderList.last()) break;
            orderList.findNext();
        }
    }

    
    // ============= PRODUCT OPERATIONS =============
    
    public boolean addProduct(Product product) {
        if (product == null || !product.isValidProduct()) {
            System.out.println("Invalid product data");
            return false;
        }
        
        if (products.find(product.getProductId())) {
            System.out.println("Product with ID " + product.getProductId() + " already exists");
            return false;
        }
        
        products.insert(product.getProductId(), product);
        System.out.println("Product added successfully: " + product.getName());
        return true;
    }

    public Product findProductById(int productId) {
        if (products.find(productId)) {
            return products.retrieve();
        }
        return null;
    }

    public Product findProductByName(String name) {
        LinkedList<Product> productList = products.getAllElements();
        
        if (productList.empty()) {
            return null;
        }
        
        productList.findFirst();
        while (true) {
            Product product = productList.retrieve();
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
            if (productList.last()) break;
            productList.findNext();
        }
        return null;
    }

    public boolean removeProduct(int productId) {
        if (!products.find(productId)) {
            System.out.println("Product not found: " + productId);
            return false;
        }
        
        products.remove(productId);
        System.out.println("Product removed: " + productId);
        return true;
    }

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
        
        System.out.println("Product updated successfully");
        return true;
    }

    public LinkedList<Product> findProductsByPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> result = new LinkedList<>();
        LinkedList<Product> allProducts = products.getAllElements();
        
        if (allProducts.empty()) {
            return result;
        }
        
        allProducts.findFirst();
        while (true) {
            Product product = allProducts.retrieve();
            if (product.isInPriceRange(minPrice, maxPrice)) {
                result.addLast(product);
            }
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        return result;
    }

    public void displayProductsInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> productsInRange = findProductsByPriceRange(minPrice, maxPrice);
        
        if (productsInRange.empty()) {
            System.out.println("No products found in price range $" + minPrice + " - $" + maxPrice);
            return;
        }
        
        System.out.println("=== Products in Price Range $" + minPrice + " - $" + maxPrice + " ===");
        productsInRange.findFirst();
        while (true) {
            productsInRange.retrieve().displaySummary();
            if (productsInRange.last()) break;
            productsInRange.findNext();
        }
    }

    public LinkedList<Product> getOutOfStockProducts() {
        LinkedList<Product> outOfStock = new LinkedList<>();
        LinkedList<Product> allProducts = products.getAllElements();
        
        if (allProducts.empty()) {
            return outOfStock;
        }
        
        allProducts.findFirst();
        while (true) {
            Product product = allProducts.retrieve();
            if (product.isOutOfStock()) {
                outOfStock.addLast(product);
            }
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        return outOfStock;
    }

    public void displayOutOfStockProducts() {
        LinkedList<Product> outOfStock = getOutOfStockProducts();
        
        if (outOfStock.empty()) {
            System.out.println("All products are in stock");
            return;
        }
        
        System.out.println("=== Out of Stock Products ===");
        outOfStock.findFirst();
        while (true) {
            outOfStock.retrieve().displaySummary();
            if (outOfStock.last()) break;
            outOfStock.findNext();
        }
    }

    public void displayAllProducts() {
        LinkedList<Product> productList = products.getAllElements();
        
        if (productList.empty()) {
            System.out.println("No products available");
            return;
        }
        
        System.out.println("=== All Products ===");
        productList.findFirst();
        while (true) {
            productList.retrieve().displaySummary();
            if (productList.last()) break;
            productList.findNext();
        }
    }


    // ============= REVIEW OPERATIONS =============
    
    public boolean addReview(Review review) {
        if (review == null || !review.isValidReview()) {
            System.out.println("Invalid review data");
            return false;
        }
        
        if (!products.find(review.getProductId())) {
            System.out.println("Product not found: " + review.getProductId());
            return false;
        }
        
        if (!customers.find(review.getCustomerId())) {
            System.out.println("Customer not found: " + review.getCustomerId());
            return false;
        }
        
        if (reviews.find(review.getReviewId())) {
            System.out.println("Review with ID " + review.getReviewId() + " already exists");
            return false;
        }
        
        reviews.insert(review.getReviewId(), review);
        System.out.println("Review added successfully");
        return true;
    }

    public Review findReview(int reviewId) {
        if (reviews.find(reviewId)) {
            return reviews.retrieve();
        }
        return null;
    }

    public LinkedList<Review> getProductReviews(int productId) {
        LinkedList<Review> productReviews = new LinkedList<>();
        LinkedList<Review> allReviews = reviews.getAllElements();
        
        if (allReviews.empty()) {
            return productReviews;
        }
        
        allReviews.findFirst();
        while (true) {
            Review review = allReviews.retrieve();
            if (review.getProductId() == productId) {
                productReviews.addLast(review);
            }
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        
        return productReviews;
    }

    public LinkedList<Product> getTop3MostReviewedProducts() {
        LinkedList<Product> allProducts = products.getAllElements();
        
        if (allProducts.empty()) {
            return new LinkedList<>();
        }
        
        Product[] top3 = new Product[3];
        int[] reviewCounts = new int[3];
        
        for (int i = 0; i < 3; i++) {
            reviewCounts[i] = -1;
        }
        
        allProducts.findFirst();
        while (true) {
            Product product = allProducts.retrieve();
            int reviewCount = getProductReviews(product.getProductId()).size();
            
            for (int i = 0; i < 3; i++) {
                if (reviewCount > reviewCounts[i]) {
                    for (int j = 2; j > i; j--) {
                        reviewCounts[j] = reviewCounts[j-1];
                        top3[j] = top3[j-1];
                    }
                    reviewCounts[i] = reviewCount;
                    top3[i] = product;
                    break;
                }
            }
            
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        LinkedList<Product> result = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            if (top3[i] != null) {
                result.addLast(top3[i]);
            }
        }
        
        return result;
    }

    public void displayTop3MostReviewedProducts() {
        LinkedList<Product> top3 = getTop3MostReviewedProducts();
        
        if (top3.empty()) {
            System.out.println("No products with reviews found");
            return;
        }
        
        System.out.println("=== Top 3 Most Reviewed Products ===");
        int rank = 1;
        top3.findFirst();
        while (true) {
            Product product = top3.retrieve();
            int reviewCount = getProductReviews(product.getProductId()).size();
            System.out.println(rank + ". " + product.getName() + 
                             " - " + reviewCount + " reviews");
            rank++;
            if (top3.last()) break;
            top3.findNext();
        }
    }

    public LinkedList<Product> getTop3HighestRatedProducts() {
        LinkedList<Product> allProducts = products.getAllElements();
        
        if (allProducts.empty()) {
            return new LinkedList<>();
        }
        
        Product[] top3 = new Product[3];
        double[] ratings = new double[3];
        
        for (int i = 0; i < 3; i++) {
            ratings[i] = -1;
        }
        
        allProducts.findFirst();
        while (true) {
            Product product = allProducts.retrieve();
            double avgRating = calculateAverageRating(product.getProductId());
            
            if (avgRating > 0) {
                for (int i = 0; i < 3; i++) {
                    if (avgRating > ratings[i]) {
                        for (int j = 2; j > i; j--) {
                            ratings[j] = ratings[j-1];
                            top3[j] = top3[j-1];
                        }
                        ratings[i] = avgRating;
                        top3[i] = product;
                        break;
                    }
                }
            }
            
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        LinkedList<Product> result = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            if (top3[i] != null) {
                result.addLast(top3[i]);
            }
        }
        
        return result;
    }

    public void displayTop3HighestRatedProducts() {
        LinkedList<Product> top3 = getTop3HighestRatedProducts();
        
        if (top3.empty()) {
            System.out.println("No products with ratings found");
            return;
        }
        
        System.out.println("=== Top 3 Highest Rated Products ===");
        int rank = 1;
        top3.findFirst();
        while (true) {
            Product product = top3.retrieve();
            double avgRating = calculateAverageRating(product.getProductId());
            System.out.printf("%d. %s - %.2f/5.0%n", rank, product.getName(), avgRating);
            rank++;
            if (top3.last()) break;
            top3.findNext();
        }
    }

    private double calculateAverageRating(int productId) {
        LinkedList<Review> productReviews = getProductReviews(productId);
        
        if (productReviews.empty()) {
            return 0.0;
        }
        
        int total = 0;
        int count = 0;
        
        productReviews.findFirst();
        while (true) {
            total += productReviews.retrieve().getRating();
            count++;
            if (productReviews.last()) break;
            productReviews.findNext();
        }
        
        return (double) total / count;
    }

    public LinkedList<Customer> findCustomersWhoReviewedProduct(int productId) {
        LinkedList<Customer> result = new LinkedList<>();
        
        if (!products.find(productId)) {
            System.out.println("Product not found: " + productId);
            return result;
        }
        
        LinkedList<Review> productReviews = getProductReviews(productId);
        
        if (productReviews.empty()) {
            return result;
        }
        
        productReviews.findFirst();
        while (true) {
            int customerId = productReviews.retrieve().getCustomerId();
            Customer customer = findCustomer(customerId);
            if (customer != null) {
                result.addLast(customer);
            }
            if (productReviews.last()) break;
            productReviews.findNext();
        }
        
        // Sort by customerId
        if (!result.empty()) {
            int size = 0;
            result.findFirst();
            while (true) {
                size++;
                if (result.last()) break;
                result.findNext();
            }
            
            Customer[] customerArray = new Customer[size];
            result.findFirst();
            int index = 0;
            while (true) {
                customerArray[index++] = result.retrieve();
                if (result.last()) break;
                result.findNext();
            }
            
            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - i - 1; j++) {
                    if (customerArray[j].getCustomerId() > customerArray[j + 1].getCustomerId()) {
                        Customer temp = customerArray[j];
                        customerArray[j] = customerArray[j + 1];
                        customerArray[j + 1] = temp;
                    }
                }
            }
            
            LinkedList<Customer> sortedResult = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                sortedResult.addLast(customerArray[i]);
            }
            
            return sortedResult;
        }
        
        return result;
    }

    public void displayCustomersWhoReviewedProduct(int productId) {
        LinkedList<Customer> reviewers = findCustomersWhoReviewedProduct(productId);
        
        if (reviewers.empty()) {
            System.out.println("No customers have reviewed this product");
            return;
        }
        
        Product product = findProductById(productId);
        System.out.println("=== Customers Who Reviewed: " + product.getName() + " ===");
        reviewers.findFirst();
        while (true) {
            Customer customer = reviewers.retrieve();
            System.out.println("- " + customer.getName() + " (ID: " + customer.getCustomerId() + ")");
            if (reviewers.last()) break;
            reviewers.findNext();
        }
    }

    public void displayAllReviews() {
        LinkedList<Review> reviewList = reviews.getAllElements();
        
        if (reviewList.empty()) {
            System.out.println("No reviews found");
            return;
        }
        
        System.out.println("=== All Reviews ===");
        reviewList.findFirst();
        while (true) {
            reviewList.retrieve().displayReview();
            if (reviewList.last()) break;
            reviewList.findNext();
        }
    }


    // ============= HELPER METHODS =============
    
    private void removeCustomerOrders(int customerId) {
        LinkedList<Order> allOrders = orders.getAllElements();
        
        if (allOrders.empty()) {
            return;
        }
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            if (order.getCustomerId() == customerId) {
                orders.remove(order.getOrderId());
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
    }


    // ============= STATISTICS METHODS =============
    
    public int getTotalCustomers() {
        return customers.size();
    }

    public int getTotalOrders() {
        return orders.size();
    }

    public int getTotalProducts() {
        return products.size();
    }

    public int getTotalReviews() {
        return reviews.size();
    }

    public double calculateTotalRevenue() {
        double total = 0.0;
        LinkedList<Order> allOrders = orders.getAllElements();
        
        if (allOrders.empty()) {
            return total;
        }
        
        allOrders.findFirst();
        while (true) {
            Order order = allOrders.retrieve();
            if (!order.getStatus().equalsIgnoreCase("canceled")) {
                total += order.getTotalPrice();
            }
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        
        return total;
    }

    public void displaySystemStatistics() {
        System.out.println("========================================");
        System.out.println("       System Statistics");
        System.out.println("========================================");
        System.out.println("Total Customers: " + getTotalCustomers());
        System.out.println("Total Products: " + getTotalProducts());
        System.out.println("Total Orders: " + getTotalOrders());
        System.out.println("Total Reviews: " + getTotalReviews());
        System.out.println("Total Revenue: $" + String.format("%.2f", calculateTotalRevenue()));
        System.out.println("Out of Stock Products: " + getOutOfStockProducts().size());
        System.out.println("========================================");
    }


    // ============= CSV FILE LOADING =============
    
    public void loadCustomersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading customers from: " + filename);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String email = parts[2].trim();
                        Customer customer = new Customer(id, name, email);
                        registerCustomer(customer);
                    }
                }
            }
            
            scanner.close();
            System.out.println("Customers loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    public void loadProductsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading products from: " + filename);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
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
                        addProduct(product);
                    }
                }
            }
            
            scanner.close();
            System.out.println("Products loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public void loadOrdersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading orders from: " + filename);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        int orderId = Integer.parseInt(parts[0].trim());
                        int customerId = Integer.parseInt(parts[1].trim());
                        String productsData = parts[2].trim();
                        double totalPrice = Double.parseDouble(parts[3].trim());
                        LocalDate orderDate = LocalDate.parse(parts[4].trim());
                        String status = parts[5].trim();
                        Order order = new Order(orderId, customerId, productsData, 
                                              totalPrice, orderDate, status);
                        createOrder(order);
                    }
                }
            }
            
            scanner.close();
            System.out.println("Orders loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    public void loadReviewsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading reviews from: " + filename);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
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
                        addReview(review);
                    }
                }
            }
            
            scanner.close();
            System.out.println("Reviews loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading reviews: " + e.getMessage());
        }
    }

    public void loadAllDataFromCSV(String customersFile, String productsFile, 
                                   String ordersFile, String reviewsFile) {
        System.out.println("========================================");
        System.out.println("Loading All Data from CSV Files");
        System.out.println("========================================");
        
        loadCustomersFromCSV(customersFile);
        loadProductsFromCSV(productsFile);
        loadOrdersFromCSV(ordersFile);
        loadReviewsFromCSV(reviewsFile);
        
        System.out.println("========================================");
        System.out.println("All Data Loaded Successfully!");
        System.out.println("========================================");
    }
}
