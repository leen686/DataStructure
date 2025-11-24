# E-Commerce System Phase 2 - Technical Report
## Binary Search Tree Implementation

---

## 📋 Table of Contents
1. [System Overview](#system-overview)
2. [Classes and Methods](#classes-and-methods)
3. [Time Complexity Analysis](#time-complexity-analysis)
4. [Big-O Comparison: Phase 1 vs Phase 2](#big-o-comparison)
5. [Data Structures](#data-structures)
6. [Implementation Details](#implementation-details)

---

## 1. System Overview

### Architecture
The E-Commerce System Phase 2 uses Binary Search Trees (BST) to replace the linear data structures from Phase 1, providing logarithmic search and insertion times.

### Core Components:
- **BST<K, V>**: Generic Binary Search Tree implementation
- **Product Management**: Product catalog with reviews
- **Customer Management**: Customer database with order history
- **Order Management**: Order processing and tracking

---

## 2. Classes and Methods

### 2.1 BST Class
**Purpose**: Generic Binary Search Tree for key-value storage

**Methods**:
```java
public void insert(K key, V value)           // O(log n) - Insert or update
public V search(K key)                       // O(log n) - Search by key
public LinkedList<V> inOrderTraversal()      // O(n) - Get sorted values
public LinkedList<V> getAllValues()          // O(n) - Get all values
public boolean isEmpty()                     // O(1) - Check if empty
```

### 2.2 Product Class
**Purpose**: Represents a product with reviews

**Attributes**:
- String productId (Primary Key)
- String name
- String category
- double price
- int stockQuantity
- LinkedList<Review> reviews

**Methods**:
```java
public void addReview(Review review)         // O(1) - Add review
public double getAverageRating()             // O(n) - Calculate average rating
public int getReviewCount()                  // O(n) - Count reviews
public int compareTo(Product other)          // O(1) - Compare by ID
```

### 2.3 Customer Class
**Purpose**: Represents a customer with order history

**Attributes**:
- String customerId (Primary Key)
- String name
- String email
- String phone
- LinkedList<Order> orderHistory

**Methods**:
```java
public void addOrder(Order order)            // O(1) - Add order to history
public int compareTo(Customer other)         // O(1) - Compare by ID
```

### 2.4 Order Class
**Purpose**: Represents an order with items

**Attributes**:
- String orderId (Primary Key)
- String customerId
- String orderDate
- String status
- LinkedList<OrderItem> items
- double totalAmount

**Methods**:
```java
public void addItem(OrderItem item)          // O(1) - Add item to order
public int compareTo(Order other)            // O(1) - Compare by ID
```

### 2.5 ECommerceSystemPhase2 Class
**Purpose**: Main system class with all required operations

**Core Data Structures**:
```java
private BST<String, Product> productTree;    // Products keyed by productId
private BST<String, Customer> customerTree;  // Customers keyed by customerId  
private BST<String, Order> orderTree;        // Orders keyed by orderId
```

**Required Methods**:

1. **Product Operations**:
```java
public void insertProduct(Product product)                      // O(log n)
public Product searchProduct(String productId)                  // O(log n)
public LinkedList<Product> findProductsByPriceRange(
    double minPrice, double maxPrice)                           // O(n)
```

2. **Customer Operations**:
```java
public void insertCustomer(Customer customer)                   // O(log n)
public Customer searchCustomer(String customerId)               // O(log n)
public LinkedList<Order> getCustomerOrderHistory(
    String customerId)                                          // O(log n) + O(m)
```

3. **Order Operations**:
```java
public void insertOrder(Order order)                            // O(log n)
public LinkedList<Order> findOrdersBetweenDates(
    String startDate, String endDate)                           // O(n)
```

4. **Advanced Queries**:
```java
public LinkedList<Product> getTop3MostReviewedProducts()        // O(n)
public LinkedList<Product> getTop3HighestRatedProducts()        // O(n)
public LinkedList<Customer> listCustomersAlphabetically()       // O(n log n)
public LinkedList<Customer> findCustomersWhoReviewedProduct(
    String productId)                                           // O(log n) + O(r)
```

---

## 3. Time Complexity Analysis

### 3.1 BST Operations

| Operation | Best Case | Average Case | Worst Case | Notes |
|-----------|-----------|--------------|------------|-------|
| Insert | O(log n) | O(log n) | O(n) | Worst case when tree is skewed |
| Search | O(log n) | O(log n) | O(n) | Worst case when tree is skewed |
| In-order Traversal | O(n) | O(n) | O(n) | Must visit all nodes |
| Delete | O(log n) | O(log n) | O(n) | Not implemented |

### 3.2 System Operations

#### Product Operations:

**1. Insert Product**
- **Time Complexity**: O(log n)
- **Analysis**: Direct BST insertion
- **Space Complexity**: O(1)

**2. Search Product by ID**
- **Time Complexity**: O(log n)
- **Analysis**: Direct BST search
- **Space Complexity**: O(1)

**3. Range Query by Price**
- **Time Complexity**: O(n)
- **Analysis**: 
  - O(n) to traverse all products
  - O(1) for each price comparison
- **Space Complexity**: O(k) where k is number of results

#### Customer Operations:

**4. Insert Customer**
- **Time Complexity**: O(log n)
- **Analysis**: Direct BST insertion
- **Space Complexity**: O(1)

**5. Search Customer**
- **Time Complexity**: O(log n)
- **Analysis**: Direct BST search
- **Space Complexity**: O(1)

**6. Customer Order History**
- **Time Complexity**: O(log n) + O(m)
- **Analysis**:
  - O(log n) to find customer
  - O(m) to return m orders
- **Space Complexity**: O(1) (returns reference)

#### Order Operations:

**7. Insert Order**
- **Time Complexity**: O(log n)
- **Analysis**: 
  - O(log n) for BST insertion
  - O(log n) to find customer
  - O(1) to add to customer history
- **Space Complexity**: O(1)

**8. Find Orders Between Dates**
- **Time Complexity**: O(n)
- **Analysis**:
  - O(n) in-order traversal
  - O(1) for each date comparison
- **Space Complexity**: O(k) where k is number of results

#### Advanced Queries:

**9. Top 3 Most Reviewed Products**
- **Time Complexity**: O(n)
- **Analysis**:
  - O(n) to traverse all products
  - O(1) to maintain top 3 array
- **Space Complexity**: O(1) (fixed array of 3)

**10. Top 3 Highest Rated Products**
- **Time Complexity**: O(n)
- **Analysis**:
  - O(n) to traverse all products
  - O(1) to maintain top 3 array
- **Space Complexity**: O(1) (fixed array of 3)

**11. List Customers Alphabetically**
- **Time Complexity**: O(n log n)
- **Analysis**:
  - O(n) to get all customers
  - O(n log n) for bubble sort by name
  - Could be optimized to O(n) using BST on name
- **Space Complexity**: O(n) for array

**12. Customers Who Reviewed Product**
- **Time Complexity**: O(log n) + O(r × log m)
- **Analysis**:
  - O(log n) to find product
  - O(r) to traverse r reviews
  - O(log m) to find each customer
- **Space Complexity**: O(r) for result list

---

## 4. Big-O Comparison: Phase 1 vs Phase 2

### 4.1 Search Operations

| Operation | Phase 1 (LinkedList) | Phase 2 (BST) | Improvement |
|-----------|----------------------|---------------|-------------|
| Search Product | O(n) | O(log n) | ✅ Exponential |
| Search Customer | O(n) | O(log n) | ✅ Exponential |
| Search Order | O(n) | O(log n) | ✅ Exponential |

**Example**: For 1000 products
- Phase 1: 1000 comparisons (worst case)
- Phase 2: ~10 comparisons (log₂1000 ≈ 10)
- **Improvement**: 100x faster

### 4.2 Insert Operations

| Operation | Phase 1 (LinkedList) | Phase 2 (BST) | Improvement |
|-----------|----------------------|---------------|-------------|
| Insert Product | O(1) or O(n)* | O(log n) | ⚖️ Depends |
| Insert Customer | O(1) or O(n)* | O(log n) | ⚖️ Depends |
| Insert Order | O(1) or O(n)* | O(log n) | ⚖️ Depends |

*O(1) for append, O(n) for sorted insert

**Analysis**:
- If Phase 1 uses append: Phase 1 is faster for insertion
- If Phase 1 maintains sorted order: Phase 2 is much faster
- BST provides consistent O(log n) performance

### 4.3 Range Queries

| Operation | Phase 1 | Phase 2 | Improvement |
|-----------|---------|---------|-------------|
| Price Range Query | O(n) | O(n) | ⚖️ Same |
| Date Range Query | O(n) | O(n) | ⚖️ Same |

**Note**: Both require traversing all elements. BST advantage is in sorted traversal.

### 4.4 Advanced Queries

| Operation | Phase 1 | Phase 2 | Notes |
|-----------|---------|---------|-------|
| Top K Products | O(n) | O(n) | Same, but BST gives sorted data |
| Alphabetical List | O(n log n) | O(n log n) | Same sorting complexity |
| Customer by Review | O(n × m) | O(r × log m) | ✅ Much better with BST |

### 4.5 Overall Performance Summary

**Phase 1 Advantages**:
- Simpler implementation
- O(1) append operations
- Less memory overhead per node

**Phase 2 Advantages**:
- ✅ O(log n) search instead of O(n)
- ✅ O(log n) insert/update with positioning
- ✅ In-order traversal gives sorted data
- ✅ Better for large datasets (>100 items)
- ✅ Efficient for frequent searches

**Break-even Point**: 
- For datasets < 50 items: Phase 1 might be sufficient
- For datasets > 100 items: Phase 2 shows significant improvement
- For datasets > 1000 items: Phase 2 is essential

### 4.6 Real-World Performance Comparison

**Scenario: E-commerce with 10,000 products**

| Operation | Phase 1 Time | Phase 2 Time | Speedup |
|-----------|--------------|--------------|---------|
| Search by ID | 10,000 ops | 13 ops | 769x |
| 100 Sequential Searches | 1,000,000 ops | 1,300 ops | 769x |
| Insert 1000 Products | 1,000 ops | 13,000 ops | 0.08x |
| Range Query | 10,000 ops | 10,000 ops | 1x |

**Conclusion**: Phase 2 is dramatically better for search-heavy workloads, which is typical for e-commerce systems.

---

## 5. Data Structures

### 5.1 BST Structure

```
         P003
        /     \
      P001    P005
        \     /  \
       P002 P004 P006
```

**Properties**:
- Left subtree has keys < parent key
- Right subtree has keys > parent key
- In-order traversal gives sorted sequence

### 5.2 Memory Layout

**Per BST Node**:
- Key: 8 bytes (String reference)
- Value: 8 bytes (Object reference)
- Left pointer: 8 bytes
- Right pointer: 8 bytes
- **Total**: 32 bytes per node

**Comparison with LinkedList Node**:
- LinkedList Node: 16 bytes (data + next)
- BST Node: 32 bytes (2x memory)

**Trade-off**: 2x memory for exponentially faster searches

---

## 6. Implementation Details

### 6.1 Key Design Decisions

**1. Generic BST Implementation**
```java
class BST<K extends Comparable<K>, V>
```
- Allows reuse for Product, Customer, and Order trees
- Type-safe with compile-time checking
- Requires Comparable interface for sorting

**2. Comparable Implementation**
```java
class Product implements Comparable<Product> {
    public int compareTo(Product other) {
        return this.productId.compareTo(other.productId);
    }
}
```
- Products compared by productId
- Customers compared by customerId
- Orders compared by orderId

**3. In-Order Traversal for Sorted Data**
```java
private void inOrderRec(BSTNode<K, V> node, LinkedList<V> result) {
    if (node != null) {
        inOrderRec(node.left, result);   // Left subtree
        result.insert(node.value);        // Current node
        inOrderRec(node.right, result);   // Right subtree
    }
}
```
- Produces sorted sequence
- Used for range queries
- Recursive implementation

**4. Update on Duplicate Insert**
```java
if (cmp == 0) {
    node.value = value;  // Update existing
}
```
- Prevents duplicate keys
- Allows product updates

### 6.2 Algorithm Implementations

**Top K Products Algorithm**:
```
1. Initialize array of size K
2. For each product in tree:
   a. Get review count or rating
   b. Compare with array
   c. If larger than smallest in array:
      - Shift array
      - Insert product
3. Return array as LinkedList
```
- Time: O(n) for n products
- Space: O(K) = O(1) for fixed K

**Alphabetical Sort Algorithm**:
```
1. Get all customers from BST (in-order by ID)
2. Convert to array
3. Bubble sort by name
4. Convert back to LinkedList
```
- Time: O(n log n) for sorting
- Could be optimized with separate BST on name

### 6.3 Error Handling

- Null checks before operations
- Empty list returns for no results
- Update capability for duplicate inserts

### 6.4 Testing Approach

**Test Data**:
- 6 Products with different prices
- 4 Customers with different names
- 4 Orders with different dates
- Multiple reviews per product

**Test Coverage**:
✅ Insert operations
✅ Search operations  
✅ Range queries
✅ Order history
✅ Top K queries
✅ Alphabetical sorting
✅ Review lookups

---

## 7. Conclusion

### Achievements:
✅ Implemented all required functions
✅ Used BST for O(log n) operations
✅ Maintained Phase 1 functionality
✅ Comprehensive testing
✅ Detailed documentation

### Future Improvements:
- AVL Tree for guaranteed O(log n) (balanced tree)
- Separate BST for name-based sorting
- Persistent storage
- More efficient sorting algorithms

---

**Report Prepared**: November 2024  
**Phase**: 2 of 2  
**Implementation**: Complete with BST
