package datastructureproject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InventorySystem {
    
    // ============ DATA STORAGE (AVL TREES) ============
    
    private AVL_int<Customer> customers;
    private AVL_int<Order> orders;
    private AVL_int<Product> products;
    private AVLString<Product> productsByName;
    private LinkedList<Review> reviews;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventorySystem() {
        this.customers = new AVL_int<>();
        this.orders = new AVL_int<>();
        this.products = new AVL_int<>();
        this.productsByName = new AVLString<>();
        this.reviews = new LinkedList<>();
    }

    // CUSTOMER OPERATIONS 
   
    
    
   
     
    public boolean registerCustomer(Customer customer) {
        if (customer == null || !customer.isValidCustomer()) {
            System.out.println("Error: Invalid customer data");
            return false;
        }
        
        boolean added = customers.add(customer.getCustomerId(), customer);
        if (added) {
            System.out.println("Customer registered: " + customer.getName());
        } else {
            System.out.println("Customer ID already exists: " + customer.getCustomerId());
        }
        return added;
    }

    
    public Customer findCustomer(int customerId) {
        return customers.getData(customerId);
    }

    
    public boolean removeCustomer(int customerId) {
        removeCustomerOrders(customerId);
        boolean removed = customers.delete(customerId);
        if (removed) {
            System.out.println("Customer removed: " + customerId);
        } else {
            System.out.println("Customer not found: " + customerId);
        }
        return removed;
    }

   
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        System.out.println("\nALL CUSTOMERS");
        System.out.println("================");
        displayCustomersInOrder(customers.getRoot());
        System.out.println("================\n");
    }
    
    private void displayCustomersInOrder(AVLNode<Customer> node) {
        if (node == null) return;
        displayCustomersInOrder(node.left);
        node.data.display();
        System.out.println("----------------");
        displayCustomersInOrder(node.right);
    }

   
    // PRODUCT OPERATIONS 

    
   
    public boolean addProduct(Product product) {
        if (product == null || !product.isValidProduct()) {
            System.out.println("Error: Invalid product data");
            return false;
        }
        
        boolean addedById = products.add(product.getProductId(), product);
        boolean addedByName = productsByName.add(product.getName(), product);
        
        if (addedById && addedByName) {
            System.out.println("Product added: " + product.getName());
            return true;
        } else {
            if (addedById) products.delete(product.getProductId());
            System.out.println("Failed to add product: " + product.getProductId());
            return false;
        }
    }

   
    public Product findProductById(int productId) {
        return products.getData(productId);
    }


    public Product findProductByName(String name) {
        return productsByName.getData(name);
    }


    public LinkedList<Product> findProductsByNames(String[] names) {
        LinkedList<Product> result = new LinkedList<>();
        for (String name : names) {
            Product product = findProductByName(name.trim());
            if (product != null) {
                result.addLast(product);
            }
        }
        return result;
    }

    
    public boolean removeProduct(int productId) {
        Product product = findProductById(productId);
        if (product != null) {
            productsByName.delete(product.getName());
            boolean removed = products.delete(productId);
            if (removed) {
                System.out.println("Product removed: " + productId);
            }
            return removed;
        }
        System.out.println("Product not found: " + productId);
        return false;
    }

    
    public boolean updateProduct(int productId, String newName, double newPrice, int newStock) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return false;
        }
        
        if (newName != null && !newName.trim().isEmpty()) {
            productsByName.delete(product.getName());
            product.setName(newName);
            productsByName.add(newName, product);
        }
        if (newPrice >= 0) {
            product.setPrice(newPrice);
        }
        if (newStock >= 0) {
            product.setStock(newStock);
        }
        
        System.out.println("Product updated: " + productId);
        return true;
    }


    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products in the system");
            return;
        }
        
        System.out.println("\nALL PRODUCTS");
        System.out.println("================");
        displayProductsInOrder(products.getRoot());
        System.out.println("================\n");
    }
    
    private void displayProductsInOrder(AVLNode<Product> node) {
        if (node == null) return;
        displayProductsInOrder(node.left);
        node.data.display();
        System.out.println("----------------");
        displayProductsInOrder(node.right);
    }

    
    public void displayProductsByName() {
        if (productsByName.isEmpty()) {
            System.out.println("No products in the system");
            return;
        }
        
        System.out.println("\nALL PRODUCTS (BY NAME)");
        System.out.println("================");
        displayProductsByNameInOrder(productsByName.getRoot());
        System.out.println("================\n");
    }
    
    private void displayProductsByNameInOrder(AVLStringNode<Product> node) {
        if (node == null) return;
        displayProductsByNameInOrder(node.left);
        node.data.display();
        System.out.println("----------------");
        displayProductsByNameInOrder(node.right);
    }

   
    // ORDER OPERATIONS 
    
    
   
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
            System.out.println("Order created: " + order.getOrderId());
        } else {
            System.out.println("Order ID already exists: " + order.getOrderId());
        }
        return added;
    }

    
    public Order findOrder(int orderId) {
        return orders.getData(orderId);
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
        System.out.println("Order canceled: " + orderId);
        return true;
    }

   
    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return false;
        }
        
        order.setStatus(newStatus);
        System.out.println("Order status updated: " + orderId + " -> " + newStatus);
        return true;
    }

    
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders in the system");
            return;
        }
        
        System.out.println("\nALL ORDERS");
        System.out.println("================");
        displayOrdersInOrder(orders.getRoot());
        System.out.println("================\n");
    }
    
    private void displayOrdersInOrder(AVLNode<Order> node) {
        if (node == null) return;
        displayOrdersInOrder(node.left);
        node.data.display();
        System.out.println("----------------");
        displayOrdersInOrder(node.right);
    }

  
    // REVIEW OPERATIONS
   
    
    
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
        System.out.println("Review added successfully");
        return true;
    }

  
    public boolean editReview(int reviewId, int newRating, String newComment) {
        if (reviews.empty()) {
            System.out.println("No reviews in the system");
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
                System.out.println("Review updated successfully");
                return true;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        System.out.println("Review not found: " + reviewId);
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

 
    // ADVANCED QUERIES (PHASE 2 REQUIREMENTS)
    
    
    

    
  
    public LinkedList<Order> findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        LinkedList<Order> result = new LinkedList<>();
        collectOrdersBetweenDates(orders.getRoot(), startDate, endDate, result);
        return result;
    }
    
    private void collectOrdersBetweenDates(AVLNode<Order> node, LocalDate start, 
                                          LocalDate end, LinkedList<Order> result) {
        if (node == null) return;
        
        collectOrdersBetweenDates(node.left, start, end, result);
        
        if (node.data.isOrderedBetween(start, end)) {
            result.addLast(node.data);
        }
        
        collectOrdersBetweenDates(node.right, start, end, result);
    }

  
    public LinkedList<Product> findProductsInPriceRange(double minPrice, double maxPrice) {
        LinkedList<Product> result = new LinkedList<>();
        collectProductsInPriceRange(products.getRoot(), minPrice, maxPrice, result);
        return result;
    }
    
    private void collectProductsInPriceRange(AVLNode<Product> node, double min, 
                                            double max, LinkedList<Product> result) {
        if (node == null) return;
        
        collectProductsInPriceRange(node.left, min, max, result);
        
        if (node.data.getPrice() >= min && node.data.getPrice() <= max) {
            result.addLast(node.data);
        }
        
        collectProductsInPriceRange(node.right, min, max, result);
    }

    
    public LinkedList<Product> getTop3Products() {
        if (products.isEmpty()) {
            return new LinkedList<>();
        }
        
        LinkedList<Product> allProducts = new LinkedList<>();
        collectAllProducts(products.getRoot(), allProducts);
        
        if (allProducts.empty()) {
            return new LinkedList<>();
        }
        
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
        
        LinkedList<Product> top3 = new LinkedList<>();
        int limit = Math.min(3, productArray.length);
        for (int i = 0; i < limit; i++) {
            if (ratings[i] > 0) {
                top3.addLast(productArray[i]);
            }
        }
        
        return top3;
    }
    
    private void collectAllProducts(AVLNode<Product> node, LinkedList<Product> list) {
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
        
        System.out.println("\nTOP 3 PRODUCTS BY RATING");
        System.out.println("====================");
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
        System.out.println("====================\n");
    }

    
    public void displayCustomersAlphabetically() {
        if (customers.isEmpty()) {
            System.out.println("No customers in the system");
            return;
        }
        
        LinkedList<Customer> allCustomers = new LinkedList<>();
        collectAllCustomers(customers.getRoot(), allCustomers);
        
        sortCustomersByName(allCustomers);
        
        System.out.println("\nCUSTOMERS (ALPHABETICALLY)");
        System.out.println("================");
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
        System.out.println("================\n");
    }
    
    private void collectAllCustomers(AVLNode<Customer> node, LinkedList<Customer> list) {
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

   
    public void displayCustomersWhoReviewedProduct(int productId) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return;
        }
        
        System.out.println("\nCUSTOMERS WHO REVIEWED: " + product.getName());
        System.out.println("================");
        
        if (reviews.empty()) {
            System.out.println("No reviews found");
            System.out.println("================\n");
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
                System.out.println("   ----------------");
                if (pairs.last()) break;
                pairs.findNext();
            }
        }
        System.out.println("================\n");
    }
    
    private class ReviewerPair {
        Customer customer;
        Review review;
        
        ReviewerPair(Customer c, Review r) {
            this.customer = c;
            this.review = r;
        }
    }
    
   
   public LinkedList<Order> getCustomerOrderHistory(int customerId) {
    Customer customer = findCustomer(customerId);
    if (customer == null) {
        return new LinkedList<>();
    }
    
    LinkedList<Order> customerOrders = new LinkedList<>();
    LinkedList<Integer> orderIds = customer.getOrders();
    
    if (orderIds.empty()) {
        return customerOrders;
    }
    
    orderIds.findFirst();
    while (true) {
        int orderId = orderIds.retrieve();
        Order order = findOrder(orderId);
        if (order != null) {
            customerOrders.addLast(order);
        }
        if (orderIds.last()) break;
        orderIds.findNext();
    }
    
    return customerOrders;
}
    
   
    // RANGE QUERY OPERATIONS 
    
    
    
    public LinkedList<Product> findProductsInIdRange(int minId, int maxId) {
        return products.getRange(minId, maxId);
    }

   
    public LinkedList<Product> findProductsInNameRange(String startName, String endName) {
        return productsByName.getRange(startName, endName);
    }


    // STOCK MANAGEMENT OPERATIONS

    
   
    public LinkedList<Product> getOutOfStockProducts() {
        LinkedList<Product> outOfStock = new LinkedList<>();
        collectOutOfStock(products.getRoot(), outOfStock);
        return outOfStock;
    }
    
    private void collectOutOfStock(AVLNode<Product> node, LinkedList<Product> list) {
        if (node == null) return;
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
        
        System.out.println("\nOUT OF STOCK PRODUCTS");
        System.out.println("================");
        int count = 0;
        outOfStock.findFirst();
        while (true) {
            count++;
            Product p = outOfStock.retrieve();
            System.out.println(count + ". " + p.getName() + " (ID: " + p.getProductId() + ")");
            if (outOfStock.last()) break;
            outOfStock.findNext();
        }
        System.out.println("================\n");
    }

   
    // STATISTICS OPERATIONS
   
    
    
   
    
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

    private double calculateRevenueHelper(AVLNode<Order> node) {
        if (node == null) return 0.0;
        
        double revenue = 0.0;
        if (!node.data.getStatus().equalsIgnoreCase("canceled")) {
            revenue = node.data.getTotalPrice();
        }
        
        return revenue + calculateRevenueHelper(node.left) + calculateRevenueHelper(node.right);
    }

   
    public void displayAVLStatistics() {
        System.out.println("\nAVL TREE STATISTICS");
        System.out.println("====================");
        System.out.println("Customers Tree Height: " + customers.getHeight());
        System.out.println("Products Tree Height:  " + products.getHeight());
        System.out.println("Orders Tree Height:    " + orders.getHeight());
        System.out.println("Products by Name Tree Height: " + productsByName.getHeight());
        System.out.println("Total Products by Name: " + productsByName.countNodes() + " nodes");
        System.out.println("====================\n");
    }

   
    public void displaySystemStatistics() {
        System.out.println("\nSYSTEM STATISTICS (FULL AVL)");
        System.out.println("====================");
        System.out.println("Total Customers:  " + getTotalCustomers());
        System.out.println("Total Products:   " + getTotalProducts());
        System.out.println("Total Orders:     " + getTotalOrders());
        System.out.println("Total Reviews:    " + getTotalReviews());
        System.out.println("Total Revenue:    $" + String.format("%.2f", calculateTotalRevenue()));
        System.out.println("Out of Stock:     " + getOutOfStockProducts().size());
        
        displayAVLStatistics();
    }

   
    // HELPER METHODS
  
    
    private void removeCustomerOrders(int customerId) {
        removeCustomerOrdersHelper(orders.getRoot(), customerId);
    }

    private void removeCustomerOrdersHelper(AVLNode<Order> node, int customerId) {
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


    // CSV FILE LOADING OPERATIONS
    public void loadCustomersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading customers from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine();
            
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
            System.out.println("Loaded " + count + " customers\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public void loadProductsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading products from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine();
            
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
            System.out.println("Loaded " + count + " products\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public void loadOrdersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading orders from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine();
            
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
            System.out.println("Loaded " + count + " orders\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public void loadReviewsFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading reviews from: " + filename);
            
            if (scanner.hasNextLine()) scanner.nextLine();
            
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
            System.out.println("Loaded " + count + " reviews\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public void loadAllDataFromCSV(String customersFile, String productsFile, 
                                   String ordersFile, String reviewsFile) {
        System.out.println("\nLOADING ALL DATA FROM CSV FILES");
        System.out.println("================================\n");
        
        loadCustomersFromCSV(customersFile);
        loadProductsFromCSV(productsFile);
        loadOrdersFromCSV(ordersFile);
        loadReviewsFromCSV(reviewsFile);
        
        System.out.println("\nALL DATA LOADED SUCCESSFULLY!");
        System.out.println("================================\n");
    }
}
