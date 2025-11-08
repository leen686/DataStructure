package datastructureproject;

import java.io.File;
import java.util.Scanner;

public class Review {
    
    private String comment;
    private int reviewId;
    private int productId; // for linking with product class
    private int customerId; // for linking with customer class
    private int rating; 
    
   // constructor
    public Review(String c ,int reviewId,int productId,  int customerId, int rating) {
       
        comment = c;
        this.reviewId = reviewId;
        this. productId =  productId;
        this.customerId = customerId;
        this.rating = rating;
       
    }
    
    // for editing the review
public void editReview (Review p) {
         this.reviewId = p.reviewId;
        this. productId = p. productId;
        this.customerId = p.customerId;
        this.rating = p.rating;
        this.comment = p.comment;
    }

    // setters and getters
    public int getReviewId() { return reviewId; }
     public int getProductId() { return productId; }
     public int getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    

    public void setRating(int r) { this.rating = r; }
    public void setComment(String c) { this.comment = c; }

    
    
    // display info 
    public void display() {
        System.out.println("Review ID: " + reviewId);
         System.out.println("product ID: " + productId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Rating: " + rating + "/5");
        System.out.println("Comment: " + comment);
        System.out.println("---------------------------------");
    }

}
