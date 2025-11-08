
package datastructureproject;

import java.io.File;
import java.util.Scanner;

public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews = new LinkedList<>();
    
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;

    }
    public void UpdateProduct(Product p) {
        this.productId = p.productId;
        this.name = p.name;
        this.price = p.price;
        this.stock = p.stock;
        this.reviews = p.reviews;
    }

    public int getProductId() { 
        return productId; }
    
    public String getName() { 
        return name; }
    
    public double getPrice() {
        return price; }
    
    public int getStock() { 
        return stock; }

    public void setPrice(double price) { 
        this.price = price; }
    
    public void setStock(int stock)  {
        this.stock = stock; }

    public void addReview(Review review) {        
        reviews.insert(review);
    }

    public double getAverageRating() {
        if (reviews.empty()) return 0 ;
        
        reviews.findFirst();
        double sum = 0;
        int count = 0;
        
        while (!reviews.last()) {
            sum = sum + reviews.retrieve().getRating();
            count++;
            reviews.findNext();
        }
        
         sum = sum + reviews.retrieve().getRating();
            count++ ; // for last node
        
        double avg = sum / count ;
        return avg ;
    }

    public void displayReviews() {
        
        System.out.println("Reviews for " + name + ":");
        if (reviews.empty()) {
            System.out.println(" No customer have review this product yet");}
        
        else { 
            
            reviews.findFirst();
            while (!reviews.last()) {
                reviews.retrieve().display();
                reviews.findNext();
            }
            
            reviews.retrieve().display(); // for last node 
            
        }
    }

    public void display() {
        
        System.out.println("Product ID:" + productId);
        System.out.println(" Product Name: " + name);
        System.out.println("Product Price:" + price);
        System.out.println("Product in Stock:" + stock);
        System.out.println("Average Rating for Product : " + getAverageRating() );
    } }
    
    

