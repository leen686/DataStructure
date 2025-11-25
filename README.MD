========================================
   BIG-O COMPLEXITY ANALYSIS
   Phase 1 vs Phase 2 Comparison
========================================

1. DATA STRUCTURE COMPARISON
========================================

Phase 1: ArrayList-based Implementation
- Storage: ArrayList for Products, Customers, Orders
- Access: Sequential/Linear

Phase 2: BST-based Implementation  
- Storage: Binary Search Tree (BST_int)
- Access: Logarithmic (balanced tree)


2. OPERATION COMPLEXITY COMPARISON
========================================

OPERATION                 | PHASE 1 (ArrayList) | PHASE 2 (BST)
--------------------------+--------------------+---------------
Insert Customer/Order     |      O(1)*         |   O(log n)
Search by ID              |      O(n)          |   O(log n)
Update Customer/Order     |      O(n)          |   O(log n)
Delete Customer/Order     |      O(n)          |   O(log n)
Display All (sorted)      |   O(n log n)       |     O(n)

* ArrayList insert is O(1) amortized (may be O(n) during resizing)


3. DETAILED ANALYSIS
========================================

A) INSERT OPERATION
-------------------
Phase 1 (ArrayList):
- add(element) → O(1) amortized
- But requires search O(n) to check duplicates
- Overall: O(n)

Phase 2 (BST):
- Traverse tree to find position → O(log n)
- Insert new node → O(1)
- Overall: O(log n)

WINNER: Phase 2 (for large datasets)


B) SEARCH OPERATION
-------------------
Phase 1 (ArrayList):
- Linear search through all elements
- Best case: O(1) - found at first position
- Average case: O(n/2) ≈ O(n)
- Worst case: O(n) - found at last position or not found

Phase 2 (BST):
- Binary search property
- Best case: O(1) - root is target
- Average case: O(log n)
- Worst case: O(n) - skewed tree, O(log n) - balanced tree

WINNER: Phase 2 (significantly faster for large n)


C) UPDATE OPERATION
-------------------
Phase 1 (ArrayList):
- Find element: O(n)
- Update: O(1)
- Overall: O(n)

Phase 2 (BST):
- Find element: O(log n)
- Update: O(1)
- Overall: O(log n)

WINNER: Phase 2


D) DELETE OPERATION
-------------------
Phase 1 (ArrayList):
- Find element: O(n)
- Remove and shift elements: O(n)
- Overall: O(n)

Phase 2 (BST):
- Find element: O(log n)
- Restructure tree: O(log n)
- Overall: O(log n)

WINNER: Phase 2


E) DISPLAY ALL (In-Order)
--------------------------
Phase 1 (ArrayList):
- If unsorted: O(n)
- If needs sorting: O(n log n)

Phase 2 (BST):
- In-order traversal: O(n)
- Already sorted by key

WINNER: Phase 2 (natural sorting)


4. ADVANCED QUERIES ANALYSIS
========================================

A) Find Orders Between Two Dates
---------------------------------
Phase 1 (ArrayList):
- Iterate through all orders: O(n)
- Check each date: O(1)
- Overall: O(n)

Phase 2 (BST):
- In-order traversal: O(n)
- Check each date: O(1)
- Overall: O(n)

RESULT: Similar performance, but Phase 2 provides sorted output


B) List Customers Alphabetically
---------------------------------
Phase 1 (ArrayList):
- Collect all customers: O(n)
- Sort by name: O(n log n)
- Overall: O(n log n)

Phase 2 (BST):
- In-order traversal (by ID): O(n)
- Collect in list: O(n)
- Sort by name: O(n log n)
- Overall: O(n log n)

RESULT: Similar for this query (both need sorting by name)


C) Find Orders by Status
-------------------------
Phase 1 (ArrayList):
- Linear search: O(n)

Phase 2 (BST):
- In-order traversal: O(n)

RESULT: Similar, but could be optimized in Phase 2 with additional BST indexed by status


D) Customer Order History
--------------------------
Phase 1 (ArrayList):
- Iterate through all orders: O(n)
- Check customer ID for each: O(1)
- Overall: O(n)

Phase 2 (BST with LinkedList):
- Customer stores order IDs in LinkedList: O(k) where k = number of customer's orders
- Access each order from BST: O(k log n)
- Overall: O(k log n)

WINNER: Phase 2 (k << n for most cases)


5. SPACE COMPLEXITY
========================================

Phase 1 (ArrayList):
- Storage: O(n)
- No additional overhead

Phase 2 (BST):
- Storage: O(n)
- Additional overhead: 2 pointers per node (left, right)
- Space overhead: O(n) for pointers

RESULT: Phase 1 uses less memory, but difference is minimal


6. PERFORMANCE COMPARISON (Example)
========================================

For n = 10,000 customers:

Operation          | Phase 1      | Phase 2
-------------------+--------------+-----------
Search             | ~10,000 ops  | ~14 ops (log₂ 10000 ≈ 13.3)
Insert             | ~10,000 ops  | ~14 ops
Delete             | ~10,000 ops  | ~14 ops
Update             | ~10,000 ops  | ~14 ops

SPEED IMPROVEMENT: ~714x faster for search operations!


7. WORST CASE SCENARIOS
========================================

Phase 1 (ArrayList):
- Always O(n) for search operations
- Predictable performance

Phase 2 (BST):
- Balanced tree: O(log n)
- Skewed tree: O(n) - degrades to linked list
- Depends on insertion order

SOLUTION for Phase 2: Use AVL Tree (self-balancing) to guarantee O(log n)


8. RECOMMENDATIONS
========================================

Use Phase 1 (ArrayList) when:
- Dataset is small (n < 100)
- Simple sequential access is needed
- Memory is extremely limited
- Insertion order matters

Use Phase 2 (BST) when:
- Dataset is large (n > 1000)
- Frequent search operations
- Need sorted traversal
- Performance is critical

Upgrade to AVL Tree (Bonus) when:
- Need guaranteed O(log n) performance
- Insertion order may cause skewed tree
- Critical production system


9. CONCLUSION
========================================

Phase 2 (BST) provides SIGNIFICANT performance improvements over Phase 1 (ArrayList) for:
✓ Search operations: O(n) → O(log n)
✓ Insert operations: O(n) → O(log n)
✓ Delete operations: O(n) → O(log n)
✓ Update operations: O(n) → O(log n)
✓ Natural sorted traversal: O(n)

For a system with 10,000 records:
- Phase 1: ~10,000 operations for search
- Phase 2: ~14 operations for search
- IMPROVEMENT: ~714x faster

Trade-offs:
- Slightly more memory overhead
- Requires balanced tree for optimal performance
- More complex implementation

OVERALL: Phase 2 is the clear winner for production systems with large datasets.


10. IMPLEMENTATION NOTES
========================================

Phase 2 Implementation Features:
1. BST_int<T> - Generic BST with integer keys
2. Efficient search operations O(log n)
3. In-order traversal for sorted display
4. Support for range queries
5. Customer-Order linking preserved from Phase 1
6. LinkedList used within entities (hybrid approach)

Potential Improvements:
1. Implement AVL Tree for guaranteed balance
2. Add secondary BST indexes (by name, email, date, etc.)
3. Implement caching for frequently accessed data
4. Add batch operations for better performance


========================================
   END OF BIG-O ANALYSIS
========================================
