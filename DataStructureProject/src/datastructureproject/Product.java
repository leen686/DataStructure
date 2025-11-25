package datastructureproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Products Management using BST
 * Phase 1: LinkedList - O(n) search
 * Phase 2: BST - O(log n) search
 */
public class Products {
    private BST_int<Product> productTree;
    
    public Products() {
        productTree = new BST_int<>();
    }
    
    // ============ Basic Operations ============
    
    /**
     * Add product
     * Phase 1: O(n) - Linear search for duplicates
     * Phase 2: O(log n) - BST insert
     */
    public boolean addProduct(Product product) {
        if (product == null || !product.isValidProduct()) {
            System.out.println("Error: Invalid product data");
            return false;
        }
        
        boolean added = productTree.add(product.getProductId(), product);
        if (added) {
            System.out.println("✓ Product added successfully: " + product.getName());
        } else {
            System.out.println("✗ Product ID already exists: " + product.getProductId());
        }
        return added;
    }
    
    /**
     * Find product by ID
     * Phase 1: O(n) - Linear search
     * Phase 2: O(log n) - BST search
     */
    public Product findProductById(int productId) {
        return productTree.getData(productId);
    }
    
    /**
     * Update product
     * Phase 1: O(n) search + O(1) update
     * Phase 2: O(log n) search + O(1) update
     */
    public boolean updateProduct(int productId, Product updatedProduct) {
        if (updatedProduct == null || !updatedProduct.isValidProduct()) {
            System.out.println("Error: Invalid product data");
            return false;
        }
        
        boolean updated = productTree.update(productId, updatedProduct);
        if (updated) {
            System.out.println("✓ Product updated successfully");
        } else {
            System.out.println("✗ Product not found: " + productId);
        }
        return updated;
    }
    
    /**
     * Update product fields
     * Phase 2: O(log n) search
     */
    public boolean updateProductFields(int productId, String newName, double newPrice, int newStock) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("✗ Product not found: " + productId);
            return false;
        }
        
        if (newName != null && !newName.trim().isEmpty()) {
            product.setName(newName);
        }
        if (newPrice >= 0) {
            product.setPrice(newPrice);
        }
        if (newStock >= 0) {
            product.setStock(newStock);
        }
        
        System.out.println("✓ Product updated successfully");
        return true;
    }
    
    /**
     * Remove product
     * Phase 1: O(n) - Linear search and removal
     * Phase 2: O(log n) - BST delete
     */
    public boolean removeProduct(int productId) {
        boolean removed = productTree.delete(productId);
        if (removed) {
            System.out.println("✓ Product removed: " + productId);
        } else {
            System.out.println("✗ Product not found: " + productId);
        }
        return removed;
    }
    
    public boolean isEmpty() {
        return productTree.isEmpty();
    }
    
    public BST_int<Product> getProductTree() {
        return productTree;
    }
    
    // ============ Display Operations ============
    
    /**
     * Display all products
     * Phase 1: O(n) - LinkedList traversal
     * Phase 2: O(n) - BST in-order traversal (naturally sorted)
     */
    public void displayAllProducts() {
        if (productTree.isEmpty()) {
            System.out.println("No products in the system");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("          ALL PRODUCTS");
        System.out.println("========================================");
        displayProductsInOrder(productTree.getRoot());
        System.out.println("========================================\n");
    }
    
    private void displayProductsInOrder(BSTNode<Product> node) {
        if (node == null) {
            return;
        }
        displayProductsInOrder(node.left);
        node.data.display();
        System.out.println("----------------------------------------");
        displayProductsInOrder(node.right);
    }
    
    public void displayProductDetails(int productId) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         PRODUCT DETAILS");
        System.out.println("========================================");
        product.display();
        System.out.println("========================================\n");
    }
    
    // ============ Search Operations ============
    
    /**
     * Find product by name
     * O(n) - Must traverse all nodes as name is not the key
     */
    public Product findProductByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return findProductByNameHelper(productTree.getRoot(), name.toLowerCase().trim());
    }
    
    private Product findProductByNameHelper(BSTNode<Product> node, String name) {
        if (node == null) {
            return null;
        }
        
        if (node.data.getName().toLowerCase().contains(name)) {
            return node.data;
        }
        
        Product leftResult = findProductByNameHelper(node.left, name);
        if (leftResult != null) {
            return leftResult;
        }
        
        return findProductByNameHelper(node.right, name);
    }
    
    /**
     * Find products by price range
     * Phase 2 Advanced Query: Uses BST in-order traversal
     */
    public void findProductsInPriceRange(double minPrice, double maxPrice) {
        if (productTree.isEmpty()) {
            System.out.println("No products in the system");
            return;
        }
        
        if (minPrice > maxPrice) {
            System.out.println("Error: Min price cannot be greater than max price");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("  PRODUCTS IN PRICE RANGE $" + minPrice + " - $" + maxPrice);
        System.out.println("========================================");
        
        LinkedList<Product> result = new LinkedList<>();
        collectProductsInPriceRange(productTree.getRoot(), minPrice, maxPrice, result);
        
        if (result.empty()) {
            System.out.println("No products found in this price range");
        } else {
            int count = 0;
            result.findFirst();
            while (true) {
                Product p = result.retrieve();
                count++;
                System.out.println(count + ". " + p.getName() + 
                                 " | ID: " + p.getProductId() +
                                 " | Price: $" + p.getPrice() +
                                 " | Stock: " + p.getStock() +
                                 " | Rating: " + p.getAverageRating() + "/5");
                if (result.last()) break;
                result.findNext();
            }
            System.out.println("----------------------------------------");
            System.out.println("Total products found: " + count);
        }
        System.out.println("========================================\n");
    }
    
    private void collectProductsInPriceRange(BSTNode<Product> node, double minPrice, 
                                            double maxPrice, LinkedList<Product> result) {
        if (node == null) {
            return;
        }
        
        collectProductsInPriceRange(node.left, minPrice, maxPrice, result);
        
        if (node.data.getPrice() >= minPrice && node.data.getPrice() <= maxPrice) {
            result.addLast(node.data);
        }
        
        collectProductsInPriceRange(node.right, minPrice, maxPrice, result);
    }
    
    // ============ Stock Management ============
    
    /**
     * Get out of stock products
     * O(n) - Must check all products
     */
    public LinkedList<Product> getOutOfStockProducts() {
        LinkedList<Product> outOfStock = new LinkedList<>();
        collectOutOfStock(productTree.getRoot(), outOfStock);
        return outOfStock;
    }
    
    private void collectOutOfStock(BSTNode<Product> node, LinkedList<Product> list) {
        if (node == null) {
            return;
        }
        collectOutOfStock(node.left, list);
        if (node.data.isOutOfStock()) {
            list.addLast(node.data);
        }
        collectOutOfStock(node.right, list);
    }
    
    public void displayOutOfStockProducts() {
        LinkedList<Product> outOfStock = getOutOfStockProducts();
        
        if (outOfStock.empty()) {
            System.out.println("✓ All products are in stock");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("      OUT OF STOCK PRODUCTS");
        System.out.println("========================================");
        int count = 0;
        outOfStock.findFirst();
        while (true) {
            count++;
            Product p = outOfStock.retrieve();
            System.out.println(count + ". " + p.getName() + " (ID: " + p.getProductId() + ")");
            if (outOfStock.last()) break;
            outOfStock.findNext();
        }
        System.out.println("----------------------------------------");
        System.out.println("Total out of stock: " + count);
        System.out.println("========================================\n");
    }
    
    /**
     * Update stock for a product
     */
    public boolean updateStock(int productId, int newStock) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("✗ Product not found: " + productId);
            return false;
        }
        
        product.updateStock(newStock);
        System.out.println("✓ Stock updated for " + product.getName() + ": " + newStock);
        return true;
    }
    
    // ============ Top Products Query ============
    
    /**
     * Get top N products by rating
     * Phase 2 Advanced Query 3
     */
    public LinkedList<Product> getTopProductsByRating(int n) {
        if (productTree.isEmpty()) {
            return new LinkedList<>();
        }
        
        // Collect all products
        LinkedList<Product> allProducts = new LinkedList<>();
        collectAllProducts(productTree.getRoot(), allProducts);
        
        if (allProducts.empty()) {
            return new LinkedList<>();
        }
        
        // Convert to array for sorting
        int size = 0;
        allProducts.findFirst();
        while (true) {
            size++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        Product[] productArray = new Product[size];
        double[] ratings = new double[size];
        
        int index = 0;
        allProducts.findFirst();
        while (true) {
            Product product = allProducts.retrieve();
            productArray[index] = product;
            ratings[index] = product.getAverageRating();
            index++;
            if (allProducts.last()) break;
            allProducts.findNext();
        }
        
        // Selection sort to find top N
        for (int i = 0; i < productArray.length && i < n; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < productArray.length; j++) {
                if (ratings[j] > ratings[maxIndex]) {
                    maxIndex = j;
                }
            }
            // Swap
            if (maxIndex != i) {
                Product tempProd = productArray[i];
                double tempRating = ratings[i];
                productArray[i] = productArray[maxIndex];
                ratings[i] = ratings[maxIndex];
                productArray[maxIndex] = tempProd;
                ratings[maxIndex] = tempRating;
            }
        }
        
        // Return top N
        LinkedList<Product> topN = new LinkedList<>();
        int limit = Math.min(n, productArray.length);
        for (int i = 0; i < limit; i++) {
            if (ratings[i] > 0) {
                topN.insert(productArray[i]);
            }
        }
        
        return topN;
    }
    
    private void collectAllProducts(BSTNode<Product> node, LinkedList<Product> list) {
        if (node == null) {
            return;
        }
        collectAllProducts(node.left, list);
        list.addLast(node.data);
        collectAllProducts(node.right, list);
    }
    
    /**
     * Display top 3 products by rating
     */
    public void displayTop3Products() {
        LinkedList<Product> top3 = getTopProductsByRating(3);
        
        if (top3.empty()) {
            System.out.println("No products with reviews available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("     TOP 3 PRODUCTS BY RATING");
        System.out.println("========================================");
        int rank = 1;
        top3.findFirst();
        while (true) {
            Product p = top3.retrieve();
            System.out.println("#" + rank + " - " + p.getName() + 
                             " (ID: " + p.getProductId() + ")");
            System.out.println("    Rating: " + p.getAverageRating() + "/5.0");
            System.out.println("    Price: $" + p.getPrice());
            System.out.println("    Stock: " + p.getStock());
            System.out.println("    ----------------------------------------");
            rank++;
            if (top3.last()) break;
            top3.findNext();
        }
        System.out.println("========================================\n");
    }
    
    // ============ Statistics ============
    
    public int getTotalProducts() {
        return productTree.countNodes();
    }
    
    public void displayStatistics() {
        if (productTree.isEmpty()) {
            System.out.println("No product statistics available");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("       PRODUCT STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Products: " + getTotalProducts());
        
        int outOfStock = getOutOfStockProducts().size();
        int inStock = getTotalProducts() - outOfStock;
        System.out.println("In Stock: " + inStock);
        System.out.println("Out of Stock: " + outOfStock);
        
        double avgPrice = calculateAveragePrice(productTree.getRoot(), 0, 0)[0];
        System.out.println("Average Price: $" + avgPrice);
        
        System.out.println("========================================\n");
    }
    
    private double[] calculateAveragePrice(BSTNode<Product> node, double sum, int count) {
        if (node == null) {
            return new double[]{sum, count};
        }
        
        sum += node.data.getPrice();
        count++;
        
        double[] left = calculateAveragePrice(node.left, sum, count);
        return calculateAveragePrice(node.right, left[0], (int)left[1]);
    }
    
    // ============ File Operations ============
    
    /**
     * Load products from CSV file
     * Phase 2: Insert into BST - O(n log n)
     */
    public void loadProductsFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            System.out.println("Loading products from: " + filename);
            
            // Skip header if exists
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine().trim();
                if (!firstLine.matches("^\\d+.*")) {
                    // It's a header, already skipped
                } else {
                    // It's data, process it
                    processProductLine(firstLine);
                }
            }
            
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    if (processProductLine(line)) {
                        count++;
                    }
                }
            }
            
            scanner.close();
            System.out.println("✓ Loaded " + count + " products successfully\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("✗ Error: File not found - " + filename);
        } catch (Exception e) {
            System.out.println("✗ Error loading products: " + e.getMessage());
        }
    }
    
    private boolean processProductLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int stock = Integer.parseInt(parts[3].trim());
                
                Product product = new Product(id, name, price, stock);
                return addProduct(product);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error parsing product data: " + line);
        }
        return false;
    }
}
