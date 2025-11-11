package datastructureproject;

public class Review {
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;
    private String comment;
    
    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    //  Getters 
    
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

    //  Setters 
    
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
        } else {
            System.out.println("Rating must be between 1 and 5");
        }
    }
    
    public void setComment(String comment) { 
        this.comment = comment; 
    }

    //  Validation 
    
    public boolean isValidReview() { 
        return reviewId > 0 &&
               productId > 0 &&
               customerId > 0 &&
               rating >= 1 && rating <= 5 &&
               comment != null;
    }

    //  Display 
    
    public void display() { 
        System.out.println("  Review ID: " + reviewId);
        System.out.println("  Customer ID: " + customerId);
        System.out.println("  Rating: " + rating + "/5");
        System.out.println("  Comment: " + comment);
    }

    @Override
    public String toString() {
        return String.format("Review[ID:%d, Product:%d, Customer:%d, Rating:%d/5]",
                reviewId, productId, customerId, rating);
    }
}

