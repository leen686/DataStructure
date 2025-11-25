package datastructureproject;

/**
 * Product Entity Class
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews;
    
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new LinkedList<>();
    }
    
    // ============ Getters ============
    
    public int getProductId() {
        return productId;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getStock() {
        return stock;
    }
    
    public LinkedList<Review> getReviews() {
        return reviews;
    }
    
    // ============ Setters ============
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    // ============ Stock Management ============
    
    public void updateStock(int newStock) {
        this.stock = newStock;
    }
    
    public void decreaseStock(int amount) {
        if (stock >= amount) {
            stock -= amount;
        }
    }
    
    public void increaseStock(int amount) {
        stock += amount;
    }
    
    public boolean isInStock() {
        return stock > 0;
    }
    
    public boolean isOutOfStock() {
        return stock <= 0;
    }
    
    public boolean hasEnoughStock(int required) {
        return stock >= required;
    }
    
    // ============ Review Management ============
    
    public void addReview(Review review) {
        reviews.addLast(review);
    }
    
    public void removeReview(int reviewId) {
        if (reviews.empty()) {
            return;
        }
        
        reviews.findFirst();
        while (true) {
            if (reviews.retrieve().getReviewId() == reviewId) {
                reviews.remove();
                return;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
    }
    
    public int getReviewCount() {
        return reviews.size();
    }
    
    public double getAverageRating() {
        if (reviews.empty()) {
            return 0.0;
        }
        
        double sum = 0;
        int count = 0;
        
        reviews.findFirst();
        while (true) {
            sum += reviews.retrieve().getRating();
            count++;
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        return sum / count;
    }
    
    public boolean hasReviews() {
        return !reviews.empty();
    }
    
    // ============ Validation ============
    
    public boolean isValidProduct() {
        return productId > 0 && 
               name != null && !name.trim().isEmpty() &&
               price >= 0 &&
               stock >= 0;
    }
    
    // ============ Display ============
    
    public void display() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock + (isOutOfStock() ? " (Out of Stock)" : ""));
        System.out.println("Average Rating: " + String.format("%.2f", getAverageRating()) + "/5.0");
        System.out.println("Total Reviews: " + getReviewCount());
    }
    
    public void displayDetails() {
        display();
        if (hasReviews()) {
            System.out.println("\nReviews:");
            reviews.findFirst();
            int count = 1;
            while (true) {
                Review review = reviews.retrieve();
                System.out.println("  " + count + ". Rating: " + review.getRating() + "/5");
                System.out.println("     Comment: " + review.getComment());
                count++;
                if (reviews.last()) break;
                reviews.findNext();
            }
        }
    }
    
    // ============ Comparison ============
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productId == product.productId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(productId);
    }
    
    @Override
    public String toString() {
        return "Product{id=" + productId + ", name='" + name + "', price=" + price + ", stock=" + stock + "}";
    }
}
