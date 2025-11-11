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
    if (customer == null || !customer.isValidCustomer()) { // Line 1
        System.out.println("Invalid customer data");       // Line 2
        return false;                                      // Line 3
    }
    
    if (!customers.empty()) {                              // Line 4
        customers.findFirst();                             // Line 5
        while (true) {                                     // Line 6
            if (customers.retrieve().getCustomerId() == 
                customer.getCustomerId()) {                // Line 7
                System.out.println("Customer already exists"); // Line 8
                return false;                              // Line 9
            }
            if (customers.last()) break;                   // Line 10
            customers.findNext();                          // Line 11
        }
    }
    
    customers.addLast(customer);                           // Line 12
    System.out.println("Customer registered");             // Line 13
    return true;                                           // Line 14
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
    if (order == null || !order.isValidOrder()) {        // Line 1
        System.out.println("Invalid order data");        // Line 2
        return false;                                    // Line 3
    }
    
    Customer customer = findCustomer(order.getCustomerId()); // Line 4: O(c)
    if (customer == null) {                              // Line 5
        System.out.println("Customer not found");        // Line 6
        return false;                                    // Line 7
    }
    
    if (!orders.empty()) {                               // Line 8
        orders.findFirst();                              // Line 9
        while (true) {                                   // Line 10
            if (orders.retrieve().getOrderId() == 
                order.getOrderId()) {                    // Line 11
                System.out.println("Order already exists"); // Line 12
                return false;                            // Line 13
            }
            if (orders.last()) break;                    // Line 14
            orders.findNext();                           // Line 15
        }
    }
    
    orders.addLast(order);                               // Line 16
    customer.placeOrder(order.getOrderId());             // Line 17: O(o)
    System.out.println("Order created successfully");    // Line 18
    return true;                                         // Line 19
}
    //  Requirement: "Search order by ID"
   public Order findOrder(int orderId) {
    if (orders.empty()) {                                // Line 1
        return null;                                     // Line 2
    }
    
    orders.findFirst();                                  // Line 3
    while (true) {                                       // Line 4
        Order order = orders.retrieve();                 // Line 5
        if (order.getOrderId() == orderId) {             // Line 6
            return order;                                // Line 7
        }
        if (orders.last()) break;                        // Line 8
        orders.findNext();                               // Line 9
    }
    return null;                                         // Line 10
}
    //  Requirement: "Cancel order"
   public boolean cancelOrder(int orderId) {
    Order order = findOrder(orderId);                    // Line 1: O(o)
    if (order == null) {                                 // Line 2
        System.out.println("Order not found");           // Line 3
        return false;                                    // Line 4
    }
    
    if (!order.canBeCanceled()) {                        // Line 5
        System.out.println("Order cannot be canceled");  // Line 6
        return false;                                    // Line 7
    }
    
    order.cancelOrder();                                 // Line 8
    return true;                                         // Line 9
}

    //  Requirement: "Update order status"
    public boolean updateOrderStatus(int orderId, String newStatus) {
    Order order = findOrder(orderId);                    // Line 1: O(o)
    if (order == null) {                                 // Line 2
        System.out.println("Order not found");           // Line 3
        return false;                                    // Line 4
    }
    
    order.setStatus(newStatus);                          // Line 5
    return true;                                         // Line 6
}

    // Requirement: "All Orders between two dates"
   public LinkedList<Order> findOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
    LinkedList<Order> result = new LinkedList<>();       // Line 1
    
    if (orders.empty()) {                                // Line 2
        return result;                                   // Line 3
    }
    
    orders.findFirst();                                  // Line 4
    while (true) {                                       // Line 5
        Order order = orders.retrieve();                 // Line 6
        if (order.isBetweenDates(startDate, endDate)) {  // Line 7
            result.addLast(order);                       // Line 8
        }
        if (orders.last()) break;                        // Line 9
        orders.findNext();                               // Line 10
    }
    
    return result;                                       // Line 11
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

    //  Requirement: "Search by ID" 
    public Product findProductById(int productId) {
    if (products.empty()) {                              // Line 1
        return null;                                     // Line 2
    }
    
    products.findFirst();                                // Line 3
    while (true) {                                       // Line 4
        Product product = products.retrieve();           // Line 5
        if (product.getProductId() == productId) {       // Line 6
            return product;                              // Line 7
        }
        if (products.last()) break;                      // Line 8
        products.findNext();                             // Line 9
    }
    return null;                                         // Line 10
}

    //  Requirement: "Search by name" 
   public Product findProductByName(String name) {
    if (products.empty()) {                              // Line 1
        return null;                                     // Line 2
    }
    
    products.findFirst();                                // Line 3
    while (true) {                                       // Line 4
        Product product = products.retrieve();           // Line 5
        if (product.getName().equalsIgnoreCase(name)) {  // Line 6
            return product;                              // Line 7
        }
        if (products.last()) break;                      // Line 8
        products.findNext();                             // Line 9
    }
    return null;                                         // Line 10
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
    public boolean updateProduct(int productId, String newName, 
                            double newPrice, int newStock) {
    Product product = findProductById(productId);        // Line 1: O(n)
    if (product == null) {                               // Line 2
        System.out.println("Product not found");         // Line 3
        return false;                                    // Line 4
    }
    
    if (newName != null && !newName.trim().isEmpty()) {  // Line 5
        product.setName(newName);                        // Line 6
    }
    if (newPrice >= 0) {                                 // Line 7
        product.setPrice(newPrice);                      // Line 8
    }
    if (newStock >= 0) {                                 // Line 9
        product.setStock(newStock);                      // Line 10
    }
    
    System.out.println("Product updated successfully");  // Line 11
    return true;                                         // Line 12
}
    //  Requirement: "Track out-of-stock products"
    // Time Complexity: O(n)
    public LinkedList<Product> getOutOfStockProducts() {
    LinkedList<Product> outOfStock = new LinkedList<>(); // Line 1
    
    if (products.empty()) {                              // Line 2
        return outOfStock;                               // Line 3
    }
    
    products.findFirst();                                // Line 4
    while (true) {                                       // Line 5
        Product product = products.retrieve();           // Line 6
        if (product.isOutOfStock()) {                    // Line 7
            outOfStock.addLast(product);                 // Line 8
        }
        if (products.last()) break;                      // Line 9
        products.findNext();                             // Line 10
    }
    
    return outOfStock;                                   // Line 11
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
    if (review == null || !review.isValidReview()) {    // Line 1
        System.out.println("Invalid review data");       // Line 2
        return false;                                    // Line 3
    }
    
    Customer customer = findCustomer(review.getCustomerId()); // Line 4: O(c)
    if (customer == null) {                              // Line 5
        System.out.println("Customer not found");        // Line 6
        return false;                                    // Line 7
    }
    
    Product product = findProductById(review.getProductId()); // Line 8: O(p)
    if (product == null) {                               // Line 9
        System.out.println("Product not found");         // Line 10
        return false;                                    // Line 11
    }
    
    if (!reviews.empty()) {                              // Line 12
        reviews.findFirst();                             // Line 13
        while (true) {                                   // Line 14
            if (reviews.retrieve().getReviewId() == 
                review.getReviewId()) {                  // Line 15
                System.out.println("Review already exists"); // Line 16
                return false;                            // Line 17
            }
            if (reviews.last()) break;                   // Line 18
            reviews.findNext();                          // Line 19
        }
    }
    
    reviews.addLast(review);                             // Line 20
    product.addReview(review);                           // Line 21
    customer.addReview(review);                          // Line 22
    System.out.println("Review added successfully");     // Line 23
    return true;                                         // Line 24
}
    //  Requirement: "Edit review"
    public boolean editReview(int reviewId, int newRating, String newComment) {
    if (reviews.empty()) {                               // Line 1
        return false;                                    // Line 2
    }
    
    reviews.findFirst();                                 // Line 3
    while (true) {                                       // Line 4
        Review review = reviews.retrieve();              // Line 5
        if (review.getReviewId() == reviewId) {          // Line 6
            if (newRating >= 1 && newRating <= 5) {      // Line 7
                review.setRating(newRating);             // Line 8
            }
            if (newComment != null) {                    // Line 9
                review.setComment(newComment);           // Line 10
            }
            System.out.println("Review updated");        // Line 11
            return true;                                 // Line 12
        }
        if (reviews.last()) break;                       // Line 13
        reviews.findNext();                              // Line 14
    }
    
    System.out.println("Review not found");              // Line 15
    return false;                                        // Line 16
}
    // Requirement: "Extract reviews from a specific customer"
   public LinkedList<Review> getCustomerReviews(int customerId) {
    LinkedList<Review> customerReviews = new LinkedList<>();  // Line 1
    
    if (reviews.empty()) {                                    // Line 2
        return customerReviews;                               // Line 3
    }
    
    reviews.findFirst();                                      // Line 4
    while (true) {                                            // Line 5
        Review review = reviews.retrieve();                   // Line 6
        if (review.getCustomerId() == customerId) {           // Line 7
            customerReviews.addLast(review);                  // Line 8
        }
        if (reviews.last()) break;                            // Line 9
        reviews.findNext();                                   // Line 10
    }
    
    return customerReviews;                                   // Line 11
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
    if (products.empty()) {                              // Line 1
        return new LinkedList<>();                       // Line 2
    }
    
    // Create arrays for sorting
    Product[] productArray = new Product[products.size()]; // Line 3: O(p)
    double[] ratings = new double[products.size()];        // Line 4: O(p)
    
    int index = 0;                                       // Line 5
    products.findFirst();                                // Line 6
    while (true) {                                       // Line 7
        Product product = products.retrieve();           // Line 8
        productArray[index] = product;                   // Line 9
        ratings[index] = product.getAverageRating();     // Line 10: O(r_i)
        index++;                                         // Line 11
        if (products.last()) break;                      // Line 12
        products.findNext();                             // Line 13
    }
    
    // Selection sort for top 3
    for (int i = 0; i < productArray.length && i < 3; i++) {  // Line 14: 3 iterations
        int maxIndex = i;                                // Line 15
        for (int j = i + 1; j < productArray.length; j++) {   // Line 16: p-i iterations
            if (ratings[j] > ratings[maxIndex]) {        // Line 17
                maxIndex = j;                            // Line 18
            }
        }
        // Swap products
        if (maxIndex != i) {                             // Line 19
            Product tempProd = productArray[i];          // Line 20
            double tempRating = ratings[i];              // Line 21
            productArray[i] = productArray[maxIndex];    // Line 22
            ratings[i] = ratings[maxIndex];              // Line 23
            productArray[maxIndex] = tempProd;           // Line 24
            ratings[maxIndex] = tempRating;              // Line 25
        }
    }
    
    // Create result list
    LinkedList<Product> top3 = new LinkedList<>();       // Line 26
    int limit = Math.min(3, productArray.length);        // Line 27
    for (int i = 0; i < limit; i++) {                    // Line 28: 3 iterations max
        if (ratings[i] > 0) {                            // Line 29
            top3.addLast(productArray[i]);               // Line 30
        }
    }
    
    return top3;                                         // Line 31
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
