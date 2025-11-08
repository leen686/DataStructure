package datastructureproject;
import java.io.File;
import java.util.Scanner;

public class Reviews {
    private static LinkedList<Review> reviews;    
    private Products allProducts;
    private Customers allCustomers;
    
    // ============ CONSTRUCTORS ============
    
    public Reviews(LinkedList<Review> reviews, LinkedList<Product> inputProducts, 
                   LinkedList<Customer> inputCustomers) {
        this.reviews = reviews;
        this.allProducts = new Products(inputProducts);
        this.allCustomers = new Customers(inputCustomers);
    }
    
    public Reviews() {
        this.reviews = new LinkedList<>();
        this.allProducts = new Products();
        this.allCustomers = new Customers();
    }
    
    // ============ GETTERS ============
    
    public LinkedList<Review> getReviews() {
        return reviews;
    }
    
    public Products getProducts() {
        return allProducts;
    }
    
    // ============ SEARCH OPERATIONS ============
    
    public Review findReviewById(int id) {
        if (reviews.empty()) {
            return null;
        }
        
        reviews.findFirst();
        while (!reviews.last()) {
            Review current = reviews.retrieve();
            if (current.getReviewId() == id) {
                return current;
            }
            reviews.findNext();
        }
        
        Review current = reviews.retrieve();
        return (current.getReviewId() == id) ? current : null;
    }
    
    // ============ ASSIGNMENT OPERATIONS ============
    
    public void assignToProduct(Review review) {
        Product product = allProducts.findProductById(review.getProductId());
        if (product != null) {
            product.addReview(review);
        }
    } 
    
    public void assignToCustomer(Review review) {
        Customer customer = allCustomers.searchById(review.getCustomerId());
        if (customer != null) {
            customer.addReview(review);
        }
    } 
    
    // ============ REVIEW MANAGEMENT OPERATIONS ============
    
    public void addReview(Review review) {
        if (findReviewById(review.getReviewId()) != null) {
            System.out.println("Review ID " + review.getReviewId() + " already exists");
            return;
        }
        
        reviews.addLast(review);
        assignToProduct(review);
        assignToCustomer(review);
    }    
    
    public void updateReview(int id, Review updatedReview) {
        Review existing = findReviewById(id);
        if (existing == null) {
            System.out.println("Review not found for update");
            return;
        }
        existing.UpdateReview(updatedReview);
    }
    
    // ============ DISPLAY OPERATIONS ============
    
    public void showAllReviews() {
        System.out.println("REVIEWS LISTING");
        
        if (reviews.empty()) {
            System.out.println("No reviews available");
            return;
        }
        
        reviews.findFirst();
        while (true) {
            Review current = reviews.retrieve();
            current.display();                  
            System.out.println("---------------------------");
            
            if (reviews.last()) break;
            reviews.findNext();
        }
    }
    
    // ============ DATA CONVERSION OPERATIONS ============
    
    public static Review stringToReview(String line) {
        String[] parts = line.split(",", 5);
        return new Review(
            Integer.parseInt(parts[0].trim()), 
            Integer.parseInt(parts[1].trim()),  
            Integer.parseInt(parts[2].trim()), 
            Integer.parseInt(parts[3].trim()),  
            parts[4]
        );
    }
    
    // ============ FILE OPERATIONS ============
    
    public void loadReviews(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading reviews from: " + fileName);
            System.out.println("-----------------------------------");
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                Review review = stringToReview(line);
                addReview(review);              
            }
            
            scanner.close();
            System.out.println("-----------------------------------");
            System.out.println("Reviews loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}