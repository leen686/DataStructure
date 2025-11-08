# 📁 CSC 212 Project - File Structure

## 🗂️ Package Structure
```
pkg212project/
├── LinkedList.java          ✅ DONE
├── Customer.java            ✅ DONE
├── Order.java               ✅ DONE
├── InventorySystem.java     ✅ DONE (partial - needs Product & Review)
├── Product.java             ⏳ TODO (by teammates)
├── Review.java              ⏳ TODO (by teammates)
└── Main.java                ⏳ TODO (testing)
```

---

## 📋 File Details

### ✅ **1. LinkedList.java** 
**Status:** Complete ✓  
**Location:** Base data structure  
**Dependencies:** None  
**Contains:**
- Generic LinkedList implementation
- All required methods (empty, findFirst, findNext, retrieve, etc.)
- Additional helper methods (size, search, display)

**Used by:**
- Customer (for orders list)
- Order (for products list)
- InventorySystem (for all collections)
- Product (for reviews list)

---

### ✅ **2. Customer.java**
**Status:** Complete ✓  
**Location:** Entity class  
**Dependencies:** LinkedList, InventorySystem (for some methods)  
**Attributes:**
- `int customerId`
- `String name`
- `String email`
- `LinkedList<Integer> orders` - List of order IDs

**Key Methods:**
- `placeOrder(int orderId)` - Add order to customer's list
- `viewOrderHistory(InventorySystem system)` - Display all orders
- `calculateTotalSpending(InventorySystem system)` - Calculate total
- `findOrdersByStatus(...)` - Filter orders by status
- `displaySummary()` - Show customer info
- `displayDetailedInfo(...)` - Show full details with spending

**University Requirements Covered:**
✓ customerId, name, email, orders list  
✓ Place a new order for a specific customer  
✓ View order history

---

### ✅ **3. Order.java**
**Status:** Complete ✓  
**Location:** Entity class  
**Dependencies:** LinkedList, LocalDate  
**Attributes:**
- `int orderId`
- `int customerId` - Customer reference
- `LinkedList<Integer> products` - List of product IDs
- `double totalPrice`
- `LocalDate orderDate`
- `String status` - (pending/shipped/delivered/canceled)

**Key Methods:**
- `addProduct(int productId)` - Add product to order
- `removeProduct(int productId)` - Remove product
- `containsProduct(int productId)` - Check if product exists
- `cancelOrder()` - Cancel the order
- `setStatus(String status)` - Update order status
- `isBetweenDates(LocalDate, LocalDate)` - For date queries
- `displayOrderDetails()` - Show full order info
- `displayBriefInfo()` - Show summary

**University Requirements Covered:**
✓ orderId, customer reference, list of products, total price, order date, status  
✓ Create/cancel order  
✓ Update order status  
✓ Search order by ID (in InventorySystem)

---

### ✅ **4. InventorySystem.java**
**Status:** Partial - Complete for Customer & Order ✓  
**Location:** Main system class  
**Dependencies:** LinkedList, Customer, Order, Product, Review, LocalDate  
**Attributes:**
- `LinkedList<Customer> customers`
- `LinkedList<Order> orders`
- `LinkedList<Product> products` ⏳ needs Product class
- `LinkedList<Review> reviews` ⏳ needs Review class

**Customer Operations:**
- `registerCustomer(Customer)` - Register new customer
- `findCustomer(int customerId)` - Linear search for customer
- `removeCustomer(int customerId)` - Remove customer
- `updateCustomer(int, String, String)` - Update customer info
- `displayAllCustomers()` - Show all customers

**Order Operations:**
- `createOrder(Order)` - Create new order
- `findOrder(int orderId)` - Linear search for order
- `cancelOrder(int orderId)` - Cancel order
- `removeOrder(int orderId)` - Remove order
- `updateOrderStatus(int, String)` - Update status
- `findOrdersBetweenDates(LocalDate, LocalDate)` - Query by date range
- `displayOrdersBetweenDates(...)` - Show orders in date range
- `displayAllOrders()` - Show all orders
- `displayOrdersByStatus(String)` - Filter by status

**Statistics:**
- `getTotalCustomers()` - Count customers
- `getTotalOrders()` - Count orders
- `calculateTotalRevenue()` - Sum all order prices
- `displaySystemStatistics()` - Show system stats

**University Requirements Covered:**
✓ Register new customer  
✓ Place a new order for a specific customer (via createOrder)  
✓ View order history (via Customer.viewOrderHistory)  
✓ Create/cancel order  
✓ Update order status  
✓ Search order by ID  
✓ All Orders between two dates

---

### ⏳ **5. Product.java** (TODO by teammates)
**Status:** Not yet implemented  
**Expected Attributes:**
- `int productId`
- `String name`
- `double price`
- `int stock`
- `LinkedList<Review> reviews`

**Expected Methods:**
- Add/remove/update products
- Search by ID or name (linear)
- Track out-of-stock products
- Get average rating

---

### ⏳ **6. Review.java** (TODO by teammates)
**Status:** Not yet implemented  
**Expected Attributes:**
- `int rating` (1-5)
- `String comment`
- `int customerId`
- `int productId`

**Expected Methods:**
- Add/edit review
- Get average rating for product

---

## 🔗 Relationships Between Classes

```
InventorySystem (Main Controller)
    ├── manages → LinkedList<Customer>
    │                   └── has → LinkedList<Integer> orders
    │
    ├── manages → LinkedList<Order>
    │                   └── has → LinkedList<Integer> products
    │
    ├── manages → LinkedList<Product>
    │                   └── has → LinkedList<Review>
    │
    └── manages → LinkedList<Review>
```

---

## 📊 Data Flow Example

### Example: Creating an Order
```java
// 1. System has customers and products
InventorySystem system = new InventorySystem();

// 2. Register customer
Customer customer = new Customer(1, "Sara", "sara@email.com");
system.registerCustomer(customer);

// 3. Create order
Order order = new Order(101, 1, "10;20;30", 500.0, 
                        LocalDate.now(), "pending");
system.createOrder(order);  
// This also calls: customer.placeOrder(101)

// 4. Customer views history
customer.viewOrderHistory(system);

// 5. Update order status
system.updateOrderStatus(101, "shipped");

// 6. Cancel order if needed
system.cancelOrder(101);
```

---

## ✅ What's Complete (Your Part)

| Component | Status | Files |
|-----------|--------|-------|
| LinkedList | ✅ Complete | LinkedList.java |
| Customer | ✅ Complete | Customer.java |
| Order | ✅ Complete | Order.java |
| InventorySystem (C&O) | ✅ Complete | InventorySystem.java |

---

## ⏳ What's Needed (Teammates' Part)

| Component | Status | Files |
|-----------|--------|-------|
| Product | ⏳ TODO | Product.java |
| Review | ⏳ TODO | Review.java |
| InventorySystem (P&R) | ⏳ TODO | Add to InventorySystem.java |
| Main/Testing | ⏳ TODO | Main.java |
| CSV Reading | ⏳ TODO | DataLoader.java (optional) |

---

## 📝 Notes for Integration

### When teammates finish Product & Review:

1. **Add to InventorySystem:**
   ```java
   // Product methods needed
   public boolean addProduct(Product p) { ... }
   public Product findProduct(int id) { ... }
   public Product findProductByName(String name) { ... }
   public LinkedList<Product> getOutOfStockProducts() { ... }
   
   // Review methods needed
   public boolean addReview(Review r) { ... }
   public LinkedList<Review> getCustomerReviews(int customerId) { ... }
   public LinkedList<Product> getTop3Products() { ... }
   public LinkedList<Product> getCommonHighRatedProducts(int c1, int c2) { ... }
   ```

2. **CSV Reading:**
   - Read customers → create Customer objects → registerCustomer()
   - Read orders → create Order objects → createOrder()
   - Read products → create Product objects → addProduct()
   - Read reviews → create Review objects → addReview()

3. **Testing:**
   - Test each operation
   - Test query requirements
   - Verify time complexity

---

## 🎯 University Requirements Checklist

### Data Structures
- ✅ LinkedList (custom implementation)
- ✅ Used in all collections

### Classes & Attributes
- ✅ Customer: customerId, name, email, orders list
- ✅ Order: orderId, customer reference, products list, totalPrice, orderDate, status
- ⏳ Product: productId, name, price, stock, reviews list
- ⏳ Review: rating, comment

### Operations
- ✅ Register new customer
- ✅ Place a new order for a specific customer
- ✅ View order history
- ✅ Create/cancel order
- ✅ Update order status
- ✅ Search order by ID (linear)
- ⏳ Add/remove/update products
- ⏳ Search product by ID or name (linear)
- ⏳ Track out-of-stock products
- ⏳ Add/edit review
- ⏳ Get average rating

### Queries
- ✅ All Orders between two dates
- ⏳ Extract reviews from specific customer
- ⏳ Suggest "top 3 products" by average rating
- ⏳ Common products reviewed by two customers with rating > 4

### Documentation
- ⏳ Complete class diagram
- ⏳ Time complexity analysis
- ⏳ Space complexity analysis
- ⏳ Written report

---

## 🚀 Next Steps

1. ✅ Copy LinkedList.java
2. ✅ Copy Customer.java
3. ✅ Copy Order.java
4. ✅ Copy InventorySystem.java (Customer & Order methods)
5. ⏳ Wait for teammates to finish Product.java
6. ⏳ Wait for teammates to finish Review.java
7. ⏳ Integrate all parts
8. ⏳ Test thoroughly
9. ⏳ Write report with complexity analysis
10. ⏳ Create complete class diagram
11. ⏳ Submit before November 9th

---

## 💡 Tips

- Keep package name: `pkg212project`
- All files in same folder
- Test each class independently first
- Use linear search (O(n)) as required
- Document time complexity for each method
- NO Java Collections allowed - use custom LinkedList only
