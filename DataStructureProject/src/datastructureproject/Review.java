package datastructureproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reviews Management
 * Uses LinkedList (appropriate for reviews as they're typically accessed sequentially)
 */
public class Reviews {
    private LinkedList<Review> reviewList;
    
    public Reviews() {
        reviewList = new LinkedList<>();
    }
    
    // ============ Basic Operations ============
    
    /**
     * Add review
     * Phase 1 & 2: O(1) for LinkedList append
     */
    public boolean addReview(Review review) {
        if (review == null || !review.isValidReview()) {
            System.out.println("Error: Invalid review data");
            return false;
        }
        
        // Check for duplicate review ID
        if (!reviewList.empty()) {
            reviewList.findFirst();
            while (true) {
                if (reviewList.retrieve().getReviewId() == review.getReviewId()) {
                    System.out.println("✗ Review ID already exists: " + review.getReviewId());
                    return false;
                }
                if (reviewList.last()) break;
                reviewList.findNext();
            }
        }
        
        reviewList.addLast(review);
        System.out.println("✓ Review added successfully");
        return true;
    }
    
    /**
     * Find review by ID
     * O(n) - Linear search
     */
    public Review findReviewById(int reviewId) {
        if (reviewList.empty()) {
            return null;
        }
        
        reviewList.findFirst();
        while (true) {
            Review review = reviewList.retrieve();
            if (review.getReviewId() == reviewId) {
                return review;
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        return null;
    }
    
    /**
     * Update/Edit review
     * O(n) - Find review then update
     */
    public boolean editReview(int reviewId, int newRating, String newComment) {
        if (reviewList.empty()) {
            System.out.println("✗ No reviews in the system");
            return false;
        }
        
        reviewList.findFirst();
        while (true) {
            Review review = reviewList.retrieve();
            if (review.getReviewId() == reviewId) {
                if (newRating >= 1 && newRating <= 5) {
                    review.setRating(newRating);
                }
                if (newComment != null && !newComment.trim().isEmpty()) {
                    review.setComment(newComment);
                }
                System.out.println("✓ Review updated successfully");
                return true;
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        System.out.println("✗ Review not found: " + reviewId);
        return false;
    }
    
    /**
     * Remove review
     */
    public boolean removeReview(int reviewId) {
        if (reviewList.empty()) {
            return false;
        }
        
        reviewList.findFirst();
        while (true) {
            if (reviewList.retrieve().getReviewId() == reviewId) {
                reviewList.remove();
                System.out.println("✓ Review removed successfully");
                return true;
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        System.out.println("✗ Review not found: " + reviewId);
        return false;
    }
    
    public boolean isEmpty() {
        return reviewList.empty();
    }
    
    public LinkedList<Review> getReviewList() {
        return reviewList;
    }
    
    // ============ Display Operations ============
    
    /**
     * Display all reviews
     */
    public void displayAllReviews() {
        if (reviewList.empty()) {
            System.out.println("No reviews in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           ALL REVIEWS");
        System.out.println("========================================");
        int count = 0;
        reviewList.findFirst();
        while (true) {
            count++;
            System.out.println("Review #" + count + ":");
            reviewList.retrieve().display();
            System.out.println("----------------------------------------");
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        System.out.println("Total reviews: " + count);
        System.out.println("========================================\n");
    }
    
    public void displayReviewDetails(int reviewId) {
        Review review = findReviewById(reviewId);
        if (review == null) {
            System.out.println("Review not found: " + reviewId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         REVIEW DETAILS");
        System.out.println("========================================");
        review.display();
        System.out.println("========================================\n");
    }
    
    // ============ Query Operations ============
    
    /**
     * Get reviews by customer ID
     * O(n) - Must check all reviews
     */
    public LinkedList<Review> getReviewsByCustomer(int customerId) {
        LinkedList<Review> customerReviews = new LinkedList<>();
        
        if (reviewList.empty()) {
            return customerReviews;
        }
        
        reviewList.findFirst();
        while (true) {
            Review review = reviewList.retrieve();
            if (review.getCustomerId() == customerId) {
                customerReviews.addLast(review);
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        return customerReviews;
    }
    
    public void displayReviewsByCustomer(int customerId) {
        LinkedList<Review> customerReviews = getReviewsByCustomer(customerId);
        
        if (customerReviews.empty()) {
            System.out.println("No reviews found for customer: " + customerId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  REVIEWS BY CUSTOMER: " + customerId);
        System.out.println("========================================");
        int count = 0;
        customerReviews.findFirst();
        while (true) {
            count++;
            System.out.println("Review #" + count + ":");
            customerReviews.retrieve().display();
            System.out.println("----------------------------------------");
            if (customerReviews.last()) break;
            customerReviews.findNext();
        }
        System.out.println("Total: " + count + " reviews");
        System.out.println("========================================\n");
    }
    
    /**
     * Get reviews by product ID
     * O(n) - Must check all reviews
     */
    public LinkedList<Review> getReviewsByProduct(int productId) {
        LinkedList<Review> productReviews = new LinkedList<>();
        
        if (reviewList.empty()) {
            return productReviews;
        }
        
        reviewList.findFirst();
        while (true) {
            Review review = reviewList.retrieve();
            if (review.getProductId() == productId) {
                productReviews.addLast(review);
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        return productReviews;
    }
    
    public void displayReviewsByProduct(int productId) {
        LinkedList<Review> productReviews = getReviewsByProduct(productId);
        
        if (productReviews.empty()) {
            System.out.println("No reviews found for product: " + productId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  REVIEWS FOR PRODUCT: " + productId);
        System.out.println("========================================");
        int count = 0;
        productReviews.findFirst();
        while (true) {
            count++;
            System.out.println("Review #" + count + ":");
            productReviews.retrieve().display();
            System.out.println("----------------------------------------");
            if (productReviews.last()) break;
            productReviews.findNext();
        }
        System.out.println("Total: " + count + " reviews");
        System.out.println("========================================\n");
    }
    
    /**
     * Get reviews by rating
     */
    public LinkedList<Review> getReviewsByRating(int rating) {
        LinkedList<Review> ratingReviews = new LinkedList<>();
        
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Must be between 1 and 5.");
            return ratingReviews;
        }
        
        if (reviewList.empty()) {
            return ratingReviews;
        }
        
        reviewList.findFirst();
        while (true) {
            Review review = reviewList.retrieve();
            if (review.getRating() == rating) {
                ratingReviews.addLast(review);
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        return ratingReviews;
    }
    
    public void displayReviewsByRating(int rating) {
        LinkedList<Review> ratingReviews = getReviewsByRating(rating);
        
        if (ratingReviews.empty()) {
            System.out.println("No reviews with rating " + rating + " found");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  REVIEWS WITH RATING: " + rating + "/5");
        System.out.println("========================================");
        int count = 0;
        ratingReviews.findFirst();
        while (true) {
            count++;
            System.out.println("Review #" + count + ":");
            ratingReviews.retrieve().display();
            System.out.println("----------------------------------------");
            if (ratingReviews.last()) break;
            ratingReviews.findNext();
        }
        System.out.println("Total: " + count + " reviews");
        System.out.println("========================================\n");
    }
    
    /**
     * Get high-rated reviews (rating > threshold)
     */
    public LinkedList<Review> getHighRatedReviews(int minRating) {
        LinkedList<Review> highRated = new LinkedList<>();
        
        if (reviewList.empty()) {
            return highRated;
        }
        
        reviewList.findFirst();
        while (true) {
            Review review = reviewList.retrieve();
            if (review.getRating() >= minRating) {
                highRated.addLast(review);
            }
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        return highRated;
    }
    
    // ============ Statistics ============
    
    public int getTotalReviews() {
        return reviewList.size();
    }
    
    /**
     * Calculate average rating for all reviews
     */
    public double getAverageRating() {
        if (reviewList.empty()) {
            return 0.0;
        }
        
        double sum = 0;
        int count = 0;
        
        reviewList.findFirst();
        while (true) {
            sum += reviewList.retrieve().getRating();
            count++;
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        return sum / count;
    }
    
    /**
     * Get rating distribution
     */
    public void displayRatingDistribution() {
        if (reviewList.empty()) {
            System.out.println("No reviews available");
            return;
        }
        
        int[] distribution = new int[6]; // Index 0 unused, 1-5 for ratings
        
        reviewList.findFirst();
        while (true) {
            int rating = reviewList.retrieve().getRating();
            distribution[rating]++;
            if (reviewList.last()) break;
            reviewList.findNext();
        }
        
        System.out.println("\n========================================");
        System.out.println("       RATING DISTRIBUTION");
        System.out.println("========================================");
        for (int i = 5; i >= 1; i--) {
            System.out.print(i + " stars: ");
            for (int j = 0; j < distribution[i]; j++) {
                System.out.print("★");
            }
            System.out.println(" (" + distribution[i] + ")");
        }
        System.out.println("----------------------------------------");
        System.out.println("Average Rating: " + getAverageRating() + "/5.0");
        System.out.println("Total Reviews: " + getTotalReviews());
        System.out.println("========================================\n");
    }
    
    public void displayStatistics() {
        if (reviewList.empty()) {
            System.out.println("No review statistics available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("       REVIEW STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Reviews: " + getTotalReviews());
        System.out.println("Average Rating: " + getAverageRating() + "/5.0");
        System.out.println("========================================\n");
    }
    
    // ============ File Operations ============
    
    /**
     * Load reviews from CSV file
     */
    public void loadReviewsFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading reviews from: " + filename);
            
            // Skip header if exists
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    if (processReviewLine(line)) {
                        count++;
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " reviews successfully\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ Error: File not found - " + filename);
        } catch (Exception e) {
            System.out.println("✗ Error loading reviews: " + e.getMessage());
        }
    }
    
    private boolean processReviewLine(String line) {
        try {
            String[] parts = line.split(",", 5);
            if (parts.length >= 5) {
                int reviewId = Integer.parseInt(parts[0].trim());
                int productId = Integer.parseInt(parts[1].trim());
                int customerId = Integer.parseInt(parts[2].trim());
                int rating = Integer.parseInt(parts[3].trim());
                String comment = parts[4].trim();
                
                Review review = new Review(reviewId, productId, customerId, rating, comment);
                return addReview(review);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error parsing review data: " + line);
        }
        return false;
    }
}
