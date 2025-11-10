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
            System.out.println("5. Other Operations");
            System.out.println("6. Exit");
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
                    otherMenu(input, system);
                    break;
                case 6:
                    System.out.println("Thank you!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    public static void customerMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*          Customer Management Menu             *");
            System.out.println("**************************************************");
            System.out.println("1. Register New Customer");
            System.out.println("2. Find Customer by ID");
            System.out.println("3. Display All Customers");
            System.out.println("4. Remove Customer");
            System.out.println("5. Back to Main Menu");
            System.out.println("**************************************************");
            System.out.print("Enter your choice: ");
            
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
                    else System.out.println("Not found!");
                    break;
                case 3:
                    system.displayAllCustomers();
                    break;
                case 4:
                    System.out.print("Customer ID: ");
                    system.removeCustomer(input.nextInt());
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid!");
            }
        }
    }
    
    public static void productMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*          Product Management Menu              *");
            System.out.println("**************************************************");
            System.out.println("1. Add Product");
            System.out.println("2. Search by ID");
            System.out.println("3. Search by Name");
            System.out.println("4. Display All");
            System.out.println("5. Update Product");
            System.out.println("6. Remove Product");
            System.out.println("7. Out of Stock");
            System.out.println("8. Back");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("ID: ");
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
                    System.out.print("ID: ");
                    Product p = system.findProductById(input.nextInt());
                    if (p != null) p.display();
                    else System.out.println("Not found!");
                    break;
                case 3:
                    System.out.print("Name: ");
                    Product p2 = system.findProductByName(input.nextLine());
                    if (p2 != null) p2.display();
                    else System.out.println("Not found!");
                    break;
                case 4:
                    system.displayAllProducts();
                    break;
                case 5:
                    System.out.print("ID: ");
                    int upId = input.nextInt();
                    input.nextLine();
                    System.out.print("New Name: ");
                    String upName = input.nextLine();
                    System.out.print("New Price: ");
                    double upPrice = input.nextDouble();
                    System.out.print("New Stock: ");
                    int upStock = input.nextInt();
                    system.updateProduct(upId, upName, upPrice, upStock);
                    break;
                case 6:
                    System.out.print("ID: ");
                    system.removeProduct(input.nextInt());
                    break;
                case 7:
                    system.displayOutOfStockProducts();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid!");
            }
        }
    }
    
    public static void orderMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*           Order Management Menu               *");
            System.out.println("**************************************************");
            System.out.println("1. Create Order");
            System.out.println("2. Search Order");
            System.out.println("3. Display All");
            System.out.println("4. Update Status");
            System.out.println("5. Cancel Order");
            System.out.println("6. Orders by Date");
            System.out.println("7. Back");
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
                    System.out.print("Products (separated by ;): ");
                    String prods = input.nextLine();
                    System.out.print("Total Price: ");
                    double total = input.nextDouble();
                    system.createOrder(new Order(oid, cid, prods, total, LocalDate.now(), "pending"));
                    break;
                case 2:
                    System.out.print("Order ID: ");
                    Order o = system.findOrder(input.nextInt());
                    if (o != null) o.displayOrderDetails();
                    else System.out.println("Not found!");
                    break;
                case 3:
                    system.displayAllOrders();
                    break;
                case 4:
                    System.out.print("Order ID: ");
                    int upId = input.nextInt();
                    input.nextLine();
                    System.out.print("Status: ");
                    system.updateOrderStatus(upId, input.nextLine());
                    break;
                case 5:
                    System.out.print("Order ID: ");
                    system.cancelOrder(input.nextInt());
                    break;
                case 6:
                    System.out.print("Start Date (YYYY-MM-DD): ");
                    LocalDate start = LocalDate.parse(input.nextLine());
                    System.out.print("End Date (YYYY-MM-DD): ");
                    LocalDate end = LocalDate.parse(input.nextLine());
                    LinkedList<Order> orders = system.findOrdersBetweenDates(start, end);
                    if (orders.empty()) System.out.println("No orders found.");
                    else {
                        orders.findFirst();
                        while (true) {
                            orders.retrieve().displayBriefInfo();
                            if (orders.last()) break;
                            orders.findNext();
                        }
                    }
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid!");
            }
        }
    }
    
    public static void reviewMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*           Review Management Menu              *");
            System.out.println("**************************************************");
            System.out.println("1. Add Review");
            System.out.println("2. Edit Review");
            System.out.println("3. Customer Reviews");
            System.out.println("4. Back");
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
                case 2:
                    System.out.print("Review ID: ");
                    int erid = input.nextInt();
                    System.out.print("New Rating: ");
                    int erating = input.nextInt();
                    input.nextLine();
                    System.out.print("New Comment: ");
                    String ecomment = input.nextLine();
                    system.editReview(erid, erating, ecomment);
                    break;
                case 3:
                    System.out.print("Customer ID: ");
                    system.displayCustomerReviews(input.nextInt());
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid!");
            }
        }
    }
    
    public static void otherMenu(Scanner input, InventorySystem system) {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*           Other Operations Menu               *");
            System.out.println("**************************************************");
            System.out.println("1. System Statistics");
            System.out.println("2. Top 3 Products");
            System.out.println("3. Common Products");
            System.out.println("4. Back");
            System.out.println("**************************************************");
            System.out.print("Choice: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            switch (choice) {
                case 1:
                    system.displaySystemStatistics();
                    break;
                case 2:
                    system.displayTop3Products();
                    break;
                case 3:
                    System.out.print("First Customer ID: ");
                    int c1 = input.nextInt();
                    System.out.print("Second Customer ID: ");
                    int c2 = input.nextInt();
                    system.displayCommonHighRatedProducts(c1, c2);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid!");
            }
        }
    }
}
