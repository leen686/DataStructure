package datastructureproject;

public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    
    // Constructor
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters
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
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }
    
    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }
    
    // Methods
    public boolean isInStock() {
        return stock > 0;
    }
    
    public boolean isOutOfStock() {
        return stock == 0;
    }
    
    public boolean isInPriceRange(double minPrice, double maxPrice) {
        return price >= minPrice && price <= maxPrice;
    }
    
    public boolean reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
            return true;
        }
        return false;
    }
    
    public void addStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }
    
    public boolean isValidProduct() {
        return productId > 0 && 
               name != null && !name.trim().isEmpty() && 
               price >= 0 && 
               stock >= 0;
    }
    
    // Display methods
    public void displayInfo() {
        System.out.println("========================================");
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Stock: " + stock);
        System.out.println("Status: " + (isInStock() ? "In Stock" : "Out of Stock"));
        System.out.println("========================================");
    }
    
    public void displaySummary() {
        System.out.println("Product: " + name + " (ID: " + productId + ")");
        System.out.println("Price: $" + String.format("%.2f", price) + 
                         " | Stock: " + stock);
    }
    
    public void displayBrief() {
        System.out.println(productId + " - " + name + 
                         " - $" + String.format("%.2f", price) + 
                         " (Stock: " + stock + ")");
    }
    
    @Override
    public String toString() {
        return "Product{" +
               "ID=" + productId +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", stock=" + stock +
               '}';
    }
}
