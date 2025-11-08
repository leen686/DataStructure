package datastructureproject;

import java.io.File;
import java.util.Scanner;

public class Products {
    private LinkedList<Product> productsList;    
    
    // ============ CONSTRUCTORS ============
    
    public Products(LinkedList<Product> inputProducts) {
        productsList = inputProducts;
    }
    
    public Products() {
        productsList = new LinkedList<>();
    }
    
    // ============ GETTERS ============
    
    public LinkedList<Product> getProducts() {
        return productsList;
    }
    
    // ============ SEARCH OPERATIONS ============
    
    public Product findProductById(int id) {
        if (productsList.empty()) {
            return null;
        }
        
        productsList.findFirst();
        while (true) {
            Product current = productsList.retrieve();
            if (current.getProductId() == id) {
                return current;
            }
            if (productsList.last()) {
                break;
            }
            productsList.findNext();
        }
        return null;
    }
    
    // ============ PRODUCT MANAGEMENT OPERATIONS ============
    
    public void addProduct(Product product) {
        if (findProductById(product.getProductId()) == null) {
            productsList.addLast(product);
            System.out.println("Added product: " + product.getName());
        } else {
            System.out.println("Product ID " + product.getProductId() + " already exists");
        }
    }

    public void removeProduct(int id) {
        Product toRemove = findProductById(id);
        if (toRemove != null) {
            productsList.remove();
            System.out.println("Removed product: " + id);
        } else {
            System.out.println("Product ID not found: " + id);
        }
    }
    
    public void updateProduct(int id, Product newProduct) {
        Product existing = findProductById(id);
        if (existing == null) {
            System.out.println("Cannot update - product not found");
        } else {
            existing.UpdateProduct(newProduct);
        }
    }
    
    // ============ STOCK MANAGEMENT OPERATIONS ============
    
    public void showOutOfStock() {      
        System.out.println("OUT OF STOCK PRODUCTS:"); 
        
        if (productsList.empty()) {
            System.out.println("No products available");        
        } else {
            boolean found = false;
            productsList.findFirst();
            while (true) {
                Product current = productsList.retrieve();
                if (current.getStock() == 0) {
                    current.display();
                    found = true;
                }
                if (productsList.last()) {
                    break;
                } else {
                    productsList.findNext();
                }
            }
            if (!found) {
                System.out.println("All products are in stock");
            }
        }
    }
    
    // ============ DISPLAY OPERATIONS ============
    
    public void showAllProducts() {
        System.out.println("PRODUCT LISTING");
        if (productsList.empty()) {
            System.out.println("No products to display");
            return;
        }
        
        productsList.findFirst();
        while (true) {
            Product product = productsList.retrieve();
            product.display(); 
            product.displayReviews();
            System.out.println("---------------------------");
            
            if (productsList.last()) {
                break;
            } else {
                productsList.findNext();
            }
        }
    }
    
    // ============ REVIEW MANAGEMENT OPERATIONS ============
    
    public void assignReviewToAll(Review review) {
        if (!productsList.empty()) {
            productsList.findFirst();
            while (true) {
                Product product = productsList.retrieve();
                if (product.getProductId() == review.getProductId()) {
                    product.addReview(review);
                }
                if (productsList.last()) {
                    break;
                } else {
                    productsList.findNext();
                }
            }              
        }
    }
    
    public void assignReview(Review review) {
        Product product = findProductById(review.getProductId());
        if (product == null) {
            System.out.println("Cannot assign review - product ID " + review.getProductId() + " not found");
        } else {
            product.addReview(review);
        }
    }
    
    // ============ DATA CONVERSION OPERATIONS ============
    
    public static Product stringToProduct(String data) {
        String[] fields = data.split(",");
        if (fields.length < 4) {
            System.out.println("Invalid product data format");
            return null;
        }
        
        try {
            return new Product(
                Integer.parseInt(fields[0]), 
                fields[1],
                Double.parseDouble(fields[2]),
                Integer.parseInt(fields[3])
            );
        } catch (NumberFormatException e) {
            System.out.println("Error converting product data");
            return null;
        }
    }
    
    // ============ FILE OPERATIONS ============
    
    public void loadProducts(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            System.out.println("Loading products from: " + filename);
            System.out.println("----------------------");
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Product product = stringToProduct(line);
                    if (product != null) {
                        addProduct(product);
                    }
                }
            }

            scanner.close();
            System.out.println("----------------------");
            System.out.println("Products loaded successfully");
        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}