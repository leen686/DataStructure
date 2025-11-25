package datastructureproject;

/**
 * Customer Entity Class
 */
public class Customer {
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orders;      // Order IDs
    private LinkedList<Integer> reviews;     // Review IDs
    
    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.orders = new LinkedList<>();
        this.reviews = new LinkedList<>();
    }
    
    // ============ Getters ============
    
    public int getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public LinkedList<Integer> getOrders() {
        return orders;
    }
    
    public LinkedList<Integer> getReviews() {
        return reviews;
    }
    
    // ============ Setters ============
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // ============ Order Management ============
    
    public void placeOrder(int orderId) {
        orders.addLast(orderId);
    }
    
    public void removeOrder(int orderId) {
        if (orders.empty()) {
            return;
        }
        
        orders.findFirst();
        while (true) {
            if (orders.retrieve() == orderId) {
                orders.remove();
                return;
            }
            if (orders.last()) break;
            orders.findNext();
        }
    }
    
    public int countOrders() {
        return orders.size();
    }
    
    public boolean hasOrders() {
        return !orders.empty();
    }
    
    // ============ Review Management ============
    
    public void addReview(Review review) {
        reviews.addLast(review.getReviewId());
    }
    
    public void addReview(int reviewId) {
        reviews.addLast(reviewId);
    }
    
    public void removeReview(int reviewId) {
        if (reviews.empty()) {
            return;
        }
        
        reviews.findFirst();
        while (true) {
            if (reviews.retrieve() == reviewId) {
                reviews.remove();
                return;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
    }
    
    public int countReviews() {
        return reviews.size();
    }
    
    // ============ Validation ============
    
    public boolean isValidCustomer() {
        return customerId > 0 && 
               name != null && !name.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() && email.contains("@");
    }
    
    // ============ Display ============
    
    public void display() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Total Orders: " + countOrders());
        System.out.println("Total Reviews: " + countReviews());
    }
    
    // ============ Comparison ============
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return customerId == customer.customerId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(customerId);
    }
    
    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name='" + name + "', email='" + email + "'}";
    }
}
