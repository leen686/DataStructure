package datastructureproject;

public class Review {
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;
    private String comment;
    
    // Constructor
    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }
    
    // Getters
    public int getReviewId() {
        return reviewId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public int getRating() {
        return rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    // Setters
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    // Methods
    public boolean isValidReview() {
        return reviewId > 0 && 
               productId > 0 && 
               customerId > 0 && 
               rating >= 1 && rating <= 5 && 
               comment != null && !comment.trim().isEmpty();
    }
    
    public boolean isPositive() {
        return rating >= 4;
    }
    
    public boolean isNegative() {
        return rating <= 2;
    }
    
    public boolean isNeutral() {
        return rating == 3;
    }
    
    public String getRatingStars() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("★");
        }
        for (int i = rating; i < 5; i++) {
            stars.append("☆");
        }
        return stars.toString();
    }
    
    // Display methods
    public void displayReview() {
        System.out.println("========================================");
        System.out.println("Review ID: " + reviewId);
        System.out.println("Product ID: " + productId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Rating: " + rating + "/5 " + getRatingStars());
        System.out.println("Comment: " + comment);
        System.out.println("========================================");
    }
    
    public void displaySummary() {
        System.out.println("Review #" + reviewId + 
                         " (Product: " + productId + 
                         ", Customer: " + customerId + ")");
        System.out.println("Rating: " + getRatingStars() + 
                         " (" + rating + "/5)");
        System.out.println("Comment: " + 
                         (comment.length() > 50 ? 
                          comment.substring(0, 50) + "..." : 
                          comment));
    }
    
    public void displayBrief() {
        System.out.println("Review #" + reviewId + 
                         " - Rating: " + rating + "/5 - " + 
                         "\"" + (comment.length() > 30 ? 
                                comment.substring(0, 30) + "..." : 
                                comment) + "\"");
    }
    
    @Override
    public String toString() {
        return "Review{" +
               "ID=" + reviewId +
               ", productID=" + productId +
               ", customerID=" + customerId +
               ", rating=" + rating +
               ", comment='" + comment + '\'' +
               '}';
    }
}
