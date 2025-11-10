package datastructureproject;

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

    // ============ Review Management ============
    
    public void addReview(Review review) {        
        reviews.insert(review);
    }

    public double getAverageRating() {
        if (reviews.empty()) {
            return 0.0;
        }
        
        reviews.findFirst();
        double sum = 0;
        int count = 0;
        
        while (true) {
            sum += reviews.retrieve().getRating();
            count++;
            if (reviews.last()) break;
            reviews.findNext();
        }
        
        return sum / count;
    }

    // ============ Stock Management ============
    
    public boolean isOutOfStock() {
        return stock == 0;
    }
    
    public void updateStock(int newStock) {
        this.stock = newStock;
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
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Stock: " + stock);
        System.out.println("Average Rating: " + String.format("%.2f", getAverageRating()));
    }
    
    public void displayReviews() { 
        System.out.println("Reviews for " + name + ":");
        if (reviews.empty()) {
            System.out.println("  No reviews yet");
            return;
        }
        
        reviews.findFirst();
        while (true) {
            reviews.retrieve().display();
            if (reviews.last()) break;
            reviews.findNext();
        }
    }

    @Override
    public String toString() {
        return String.format("Product[ID:%d, Name:%s, Price:$%.2f, Stock:%d, Avg Rating:%.2f]",
                productId, name, price, stock, getAverageRating());
    }
}

