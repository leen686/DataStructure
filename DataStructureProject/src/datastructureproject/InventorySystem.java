package datastructureproject;

import java.time.LocalDate;
import java.io.File;
import java.util.Scanner;

public class InventorySystem {
    private LinkedList<Customer> customers;
    private LinkedList<Order> orders;
    private LinkedList<Product> products;
    private LinkedList<Review> reviews;

    public InventorySystem() {
        this.customers = new LinkedList<>();
        this.orders = new LinkedList<>();
        this.products = new LinkedList<>();
        this.reviews = new LinkedList<>();
    }


    // CUSTOMER OPERATIONS
    // Requirement: "Register new customer"
    public boolean registerCustomer(Customer customer) {
        if (customer == null || !customer.isValidCustomer()) {
            System.out.println("Invalid customer data");
            return false;
        }
        
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

    // Linear Search
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
                removeCustomerOrders(customerId);
                System.out.println("Customer removed: " + customerId);
                return true;
            }
            if (customers.last()) break;
            customers.findNext();
        }
        return false;
    }

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

  
    // ORDER OPERATIONS
    //  Requirement: "Create order"
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

    //  Requirement: "Search order by ID"
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

    //  Requirement: "Cancel order"
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

    //  Requirement: "Update order status"
    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return false;
        }
        
        order.setStatus(newStatus);
        return true;
    }

    // Requirement: "All Orders between two dates"
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

    
    // PRODUCT OPERATIONS
    //  Requirement: "Add product"
    public boolean addProduct(Product product) {
        if (product == null || !product.isValidProduct()) {
            System.out.println("Invalid product data");
            return false;
        }
        
        if (!products.empty()) {
            products.findFirst();
            while (true) {
                if (products.retrieve().getProductId() == product.getProductId()) {
                    System.out.println("Product with ID " + product.getProductId() + " already exists");
                    return false;
                }
                if (products.last()) break;
                products.findNext();
            }
        }
        
        products.addLast(product);
        System.out.println("Product added successfully: " + product.getName());
        return true;
    }

    //  Requirement: "Search by ID" - Linear Search
    public Product findProductById(int productId) {
        if (products.empty()) {
            return null;
        }
        
        products.findFirst();
        while (true) {
            Product product = products.retrieve();
            if (product.getProductId() == productId) {
                return product;
            }
            if (products.last()) break;
            products.findNext();
        }
        return null;
    }

    //  Requirement: "Search by name" - Linear Search
    public Product findProductByName(String name) {
        if (products.empty()) {
            return null;
        }
        
        products.findFirst();
        while (true) {
            Product product = products.retrieve();
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
            if (products.last()) break;
            products.findNext();
        }
        return null;
    }

    //  Requirement: "Remove product"
    public boolean removeProduct(int productId) {
        if (products.empty()) {
            return false;
        }

        products.findFirst();
        while (true) {
            if (products.retrieve().getProductId() == productId) {
                products.remove();
                System.out.println("Product removed: " + productId);
                return true;
            }
            if (products.last()) break;
            products.findNext();
        }
        return false;
    }

    //  Requirement: "Update product"
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

    //  Requirement: "Track out-of-stock products"
    // Time Complexity: O(n)
    public LinkedList<Product> getOutOfStockProducts() {
        LinkedList<Product> outOfStock = new LinkedList<>();
        
        if (products.empty()) {
            return outOfStock;
        }
        
        products.findFirst();
        while (true) {
            Product product = products.retrieve();
            if (product.isOutOfStock()) {
                outOfStock.addLast(product);
            }
            if (products.last()) break;
            products.findNext();
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
            outOfStock.retrieve().display();
            System.out.println("---");
            if (outOfStock.last()) break;
            outOfStock.findNext();
        }
    }

    public void displayAllProducts() {
        if (products.empty()) {
            System.out.println("No products available");
            return;
        }
        
        System.out.println("=== All Products ===");
        products.findFirst();
        while (true) {
            products.retrieve().display();
            System.out.println("---");
            if (products.last()) break;
            products.findNext();
        }
    }

    // REVIEW OPERATIONS
    //  Requirement: "Add review"
    public boolean addReview(Review review) {
        if (review == null || !review.isValidReview()) {
            System.out.println("Invalid review data");
            return false;
        }
        
        // Verify customer exists
        Customer customer = findCustomer(review.getCustomerId());
        if (customer == null) {
            System.out.println("Customer not found: " + review.getCustomerId());
            return false;
        }
        
        // Verify product exists
        Product product = findProductById(review.getProductId());
        if (product == null) {
            System.out.println("Product not found: " + review.getProductId());
            return false;
        }
        
        // Check if review ID already exists
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
        System.out.println("Review added successfully");
        return true;
    }

    //  Requirement: "Edit review"
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
                System.out.println("Review updated successfully");
                return true;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        System.out.println("Review not found: " + reviewId);
        return false;
    }

    // Requirement: "Extract reviews from a specific customer"
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

 

    //  Requirement: "Suggest top 3 products by average rating"
    public LinkedList<Product> getTop3Products() {
        if (products.empty()) {
            return new LinkedList<>();
        }
        
        // Create array to store products with ratings
        Product[] productArray = new Product[products.size()];
        double[] ratings = new double[products.size()];
        
        int index = 0;
        products.findFirst();
        while (true) {
            Product product = products.retrieve();
            productArray[index] = product;
            ratings[index] = product.getAverageRating();
            index++;
            if (products.last()) break;
            products.findNext();
        }
        
        // Simple selection sort to find top 3
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
                top3.addLast(productArray[i]);
            }
        }
        
        return top3;
    }

    public void displayTop3Products() {
        LinkedList<Product> top3 = getTop3Products();
        
        if (top3.empty()) {
            System.out.println("No products with reviews available");
            return;
        }
        
        System.out.println("=== Top 3 Products by Rating ===");
        int rank = 1;
        top3.findFirst();
        while (true) {
            System.out.println("#" + rank + ":");
            top3.retrieve().display();
            System.out.println("---");
            rank++;
            if (top3.last()) break;
            top3.findNext();
        }
    }

    //  Requirement: "Common products reviewed by two customers "
    public LinkedList<Product> getCommonHighRatedProducts(int customer1Id, int customer2Id) {
        LinkedList<Product> commonProducts = new LinkedList<>();
        
        LinkedList<Review> customer1Reviews = getCustomerReviews(customer1Id);
        LinkedList<Review> customer2Reviews = getCustomerReviews(customer2Id);
        
        if (customer1Reviews.empty() || customer2Reviews.empty()) {
            return commonProducts;
        }
        
        // Find common products with rating > 4
        customer1Reviews.findFirst();
        while (true) {
            Review review1 = customer1Reviews.retrieve();
            
            if (review1.getRating() > 4) {
                // Check if customer2 also reviewed this product with rating > 4
                customer2Reviews.findFirst();
                while (true) {
                    Review review2 = customer2Reviews.retrieve();
                    
                    if (review2.getProductId() == review1.getProductId() && review2.getRating() > 4) {
                        // Check if not already added
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

  

    
    // STATISTICS METHODS
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
        System.out.println("========================================");
        System.out.println("       System Statistics");
        System.out.println("========================================");
        System.out.println("Total Customers: " + getTotalCustomers());
        System.out.println("Total Products: " + getTotalProducts());
        System.out.println("Total Orders: " + getTotalOrders());
        System.out.println("Total Reviews: " + getTotalReviews());
        System.out.println("Total Revenue: $" + calculateTotalRevenue());
        System.out.println("Out of Stock Products: " + getOutOfStockProducts().size());
        System.out.println("========================================");
    }


    // CSV FILE LOADING
    public void loadCustomersFromCSV(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading customers from: " + filename);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
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
                scanner.nextLine(); // Skip header
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
                scanner.nextLine(); // Skip header
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
                scanner.nextLine(); // Skip header
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
