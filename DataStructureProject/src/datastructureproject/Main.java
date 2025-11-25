package datastructureproject;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        InventorySystem system = new InventorySystem();
        
        System.out.println("Loading data from CSV files...");
        system.loadAllDataFromCSV("customers.csv", "products.csv", "orders.csv", "reviews.csv"); 
        
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*   E-Commerce Management System - Main Menu   *");
            System.out.println("**************************************************");
            System.out.println("1. Customer Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
            System.out.println("4. Review Management");
            System.out.println("5. Advanced Queries (Phase 2)");
            System.out.println("6. System Statistics");
            System.out.println("7. Exit");
            System.out.println("**************************************************");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    customerMenu(input, system);
                    break;
                case 2:
                    productMenu(input, system);
                    break;
                case 3:
                    orderMenu(input, system);
                    break;
                case 4:
                    reviewMenu(input, system);
                    break;
                case 5:
                    advancedQueriesMenu(input, system);
                    break;
                case 6:
                    system.displaySystemStatistics();
                    break;
                case 7:
                    System.out.println("Thank you for using our system!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
  
    // CUSTOMER MENU
   
    public static void customerMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*          Customer Management Menu             *");
            System.out.println("**************************************************");
            System.out.println("1. Register New Customer");
            System.out.println("2. Find Customer by ID");
            System.out.println("3. Display All Customers");
            System.out.println("4. Remove Customer");
            System.out.println("5. Place order for specific customer");
            System.out.println("6. View Customer Order History");
            System.out.println("7. Back to Main Menu");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Customer ID: ");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    System.out.print("Email: ");
                    String email = input.nextLine();
                    system.registerCustomer(new Customer(id, name, email));
                    break;
                case 2:
                    System.out.print("Customer ID: ");
                    Customer c = system.findCustomer(input.nextInt());
                    if (c != null) c.displayDetailedInfo(system);
                    else System.out.println("Customer not found!");
                    break;
                case 3:
                    system.displayAllCustomers();
                    break;
                case 4:
                    System.out.print("Customer ID: ");
                    if (!system.removeCustomer(input.nextInt())) {
                        System.out.println("Customer not found!");
                    }
                    break;
            case 5: 
             System.out.println("=== Place Order ===");
             System.out.print("Customer ID: ");
             int custId = input.nextInt();
            Customer customer = system.findCustomer(custId);
    
           if (customer == null) {
          System.out.println("Customer not found!");
          break;
           }
    
           System.out.print("Order ID to add: ");
           int orderId = input.nextInt();
    
  
          Order existingOrder = system.findOrder(orderId);
          if (existingOrder == null) {
        System.out.println("Order " + orderId + " does not exist in the system!");
        System.out.println("Please create the order first using 'Create Order'");
        break;
          }
        customer.placeOrder(orderId);
       break;
                    
                case 6:
                    System.out.print("Customer ID: ");
                    int histId = input.nextInt();
                    LinkedList<Order> history = system.getCustomerOrderHistory(histId);
                    if (history.empty()) {
                        System.out.println("No orders found!");
                    } else {
                        System.out.println("=== Order History ===");
                        history.findFirst();
                        while (true) {
                            history.retrieve().displayBriefInfo();
                            if (history.last()) break;
                            history.findNext();
                        }
                    }
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
   
    // PRODUCT MENU
   
    public static void productMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*          Product Management Menu              *");
            System.out.println("**************************************************");
            System.out.println("1. Add Product");
            System.out.println("2. Search Product by ID");
            System.out.println("3. Search Product by Name");
            System.out.println("4. Display All Products");
            System.out.println("5. Update Product");
            System.out.println("6. Remove Product");
            System.out.println("7. Display Out of Stock Products");
            System.out.println("8. product average rating ");
            System.out.println("9. Back to Main Menu");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Product ID: ");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    System.out.print("Price: ");
                    double price = input.nextDouble();
                    System.out.print("Stock: ");
                    int stock = input.nextInt();
                    system.addProduct(new Product(id, name, price, stock));
                    break;
                case 2:
                    System.out.print("Product ID: ");
                    Product p = system.findProductById(input.nextInt());
                    if (p != null) p.display();
                    else System.out.println("Product not found!");
                    break;
                case 3:
                    System.out.print("Product Name: ");
                    Product p2 = system.findProductByName(input.nextLine());
                    if (p2 != null) p2.display();
                    else System.out.println("Product not found!");
                    break;
                case 4:
                    system.displayAllProducts();
                    break;
                case 5:
                    System.out.print("Product ID: ");
                    int upId = input.nextInt();
                    input.nextLine();
                    System.out.print("New Name (or Enter to skip): ");
                    String upName = input.nextLine();
                    System.out.print("New Price (-1 to skip): ");
                    double upPrice = input.nextDouble();
                    System.out.print("New Stock (-1 to skip): ");
                    int upStock = input.nextInt();
                    system.updateProduct(upId, upName, upPrice, upStock);
                    break;
                case 6:
                    System.out.print("Product ID: ");
                    if (!system.removeProduct(input.nextInt())) {
                        System.out.println("Product not found!");
                    }
                    break;
                case 7:
                    system.displayOutOfStockProducts();
                    break;
                     case 8:
                    System.out.print("Product ID: ");
                    Product pr = system.findProductById(input.nextInt());
                    if (pr != null) {
                        System.out.println("Product: " + pr.getName());
                        System.out.println("Average Rating: " + pr.getAverageRating() + "/5");
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
   
    // ORDER MENU

    public static void orderMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*           Order Management Menu               *");
            System.out.println("**************************************************");
            System.out.println("1. Create Order");
            System.out.println("2. Search Order by ID");
            System.out.println("3. Display All Orders");
            System.out.println("4. Update Order Status");
            System.out.println("5. Cancel Order");
            System.out.println("6. Back to Main Menu");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Order ID: ");
                    int oid = input.nextInt();
                    System.out.print("Customer ID: ");
                    int cid = input.nextInt();
                    input.nextLine();
                    System.out.print("Product IDs (separated by ;): ");
                    String prods = input.nextLine();
                    
                    // Validate products
                    String[] productIds = prods.split(";");
                    boolean valid = true;
                    for (String pid : productIds) {
                        int productId = Integer.parseInt(pid.trim());
                        if (system.findProductById(productId) == null) {
                            System.out.println("Product not found: " + productId);
                            System.out.println("Order cannot be created!");
                            valid = false;
                            break;
                        }
                    }
                    
                    if (valid) {
                        System.out.print("Total Price: ");
                        double total = input.nextDouble();
                        system.createOrder(new Order(oid, cid, prods, total, 
                                         LocalDate.now(), "pending"));
                    }
                    break;
                case 2:
                    System.out.print("Order ID: ");
                    Order o = system.findOrder(input.nextInt());
                    if (o != null) o.displayFullDetails();
                    else System.out.println("Order not found!");
                    break;
                case 3:
                    system.displayAllOrders();
                    break;
                case 4:
                    System.out.print("Order ID: ");
                    int upId = input.nextInt();
                    input.nextLine();
                    System.out.print("New Status (pending/shipped/delivered/canceled): ");
                    system.updateOrderStatus(upId, input.nextLine());
                    break;
                case 5:
                    System.out.print("Order ID: ");
                    system.cancelOrder(input.nextInt());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    

    // REVIEW MENU
  
    public static void reviewMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*           Review Management Menu              *");
            System.out.println("**************************************************");
            System.out.println("1. Add Review");
            System.out.println("2. Edit Review");
            System.out.println("3. Customer Reviews");
            System.out.println("4. Display All Reviews");
            System.out.println("5. Back to Main Menu");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Review ID: ");
                    int rid = input.nextInt();
                    System.out.print("Product ID: ");
                    int pid = input.nextInt();
                    System.out.print("Customer ID: ");
                    int cid = input.nextInt();
                    System.out.print("Rating (1-5): ");
                    int rating = input.nextInt();
                    input.nextLine();
                    System.out.print("Comment: ");
                    String comment = input.nextLine();
                    system.addReview(new Review(rid, pid, cid, rating, comment));
                    break;
                case 4:
                    system.displayAllReviews();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
 
    // ADVANCED QUERIES MENU (PHASE 2 REQUIREMENTS)
    
    public static void advancedQueriesMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*     Advanced Queries Menu (Phase 2)           *");
            System.out.println("**************************************************");
            System.out.println("1. Find Orders Between Two Dates");
            System.out.println("2. List Products Within Price Range");
            System.out.println("3. Show Top 3 Most Reviewed Products");
            System.out.println("4. Show Top 3 Highest Rated Products");
            System.out.println("5. List Customers Sorted Alphabetically");
            System.out.println("6. Show Customers Who Reviewed a Product");
            System.out.println("7. Back to Main Menu");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                   
                    System.out.print("Start Date (YYYY-MM-DD): ");
                    LocalDate start = LocalDate.parse(input.nextLine());
                    System.out.print("End Date (YYYY-MM-DD): ");
                    LocalDate end = LocalDate.parse(input.nextLine());
                    
                    LinkedList<Order> orders = system.findOrdersBetweenDates(start, end);
                    if (orders.empty()) {
                        System.out.println("No orders found between these dates.");
                    } else {
                        System.out.println("\n=== Orders Between " + start + " and " + end + " ===");
                        orders.findFirst();
                        while (true) {
                            orders.retrieve().displayBriefInfo();
                            if (orders.last()) break;
                            orders.findNext();
                        }
                    }
                    break;
                    
                case 2:
                   
                    System.out.print("Minimum Price: ");
                    double minPrice = input.nextDouble();
                    System.out.print("Maximum Price: ");
                    double maxPrice = input.nextDouble();
                    system.displayProductsInPriceRange(minPrice, maxPrice);
                    break;
                    
                case 3:

                    system.displayTop3MostReviewedProducts();
                    break;
                    
                case 4:
              
                    system.displayTop3HighestRatedProducts();
                    break;
                    
                case 5:
                  
                    system.displayCustomersAlphabetically();
                    break;
                    
                case 6:
                   
                    System.out.print("Product ID: ");
                    int productId = input.nextInt();
                    system.displayCustomersWhoReviewedProduct(productId);
                    break;
                    
                case 7:
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
