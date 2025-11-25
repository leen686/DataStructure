package datastructureproject;

/**
 * Review Entity Class
 */
public class Review {
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;         // 1-5 stars
    private String comment;
    
    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }
    
    // ============ Getters ============
    
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
    
    // ============ Setters ============
    
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    // ============ Rating Operations ============
    
    public boolean isHighRated() {
        return rating >= 4;
    }
    
    public boolean isLowRated() {
        return rating <= 2;
    }
    
    public boolean isMediumRated() {
        return rating == 3;
    }
    
    public String getRatingDescription() {
        switch (rating) {
            case 5: return "Excellent";
            case 4: return "Good";
            case 3: return "Average";
            case 2: return "Poor";
            case 1: return "Very Poor";
            default: return "Unknown";
        }
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
    
    // ============ Validation ============
    
    public boolean isValidReview() {
        return reviewId > 0 && 
               productId > 0 &&
               customerId > 0 &&
               rating >= 1 && rating <= 5 &&
               comment != null && !comment.trim().isEmpty();
    }
    
    public boolean isValidRating() {
        return rating >= 1 && rating <= 5;
    }
    
    // ============ Display ============
    
    public void display() {
        System.out.println("Review ID: " + reviewId);
        System.out.println("Product ID: " + productId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Rating: " + getRatingStars() + " (" + rating + "/5) - " + getRatingDescription());
        System.out.println("Comment: " + comment);
    }
    
    public void displayCompact() {
        System.out.println("Review #" + reviewId + " | " + getRatingStars() + 
                         " | Customer " + customerId + " | \"" + comment + "\"");
    }
    
    // ============ Comparison ============
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Review review = (Review) obj;
        return reviewId == review.reviewId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(reviewId);
    }
    
    @Override
    public String toString() {
        return "Review{id=" + reviewId + ", product=" + productId + 
               ", customer=" + customerId + ", rating=" + rating + "/5}";
    }
}
