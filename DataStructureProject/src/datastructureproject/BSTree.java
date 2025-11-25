package datastructureproject;

// BST Node class
class TreeNode<K extends Comparable<K>, T> {
    public K key;
    public T data;
    public TreeNode<K, T> left, right;

    public TreeNode(K key, T data) {
        this.key = key;
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

// Binary Search Tree implementation
public class BSTree<K extends Comparable<K>, T> {
    private TreeNode<K, T> root, current;
    
    public BSTree() {
        this.root = null;
        this.current = null;
    }
    
    public TreeNode<K, T> getRoot() {
        return root;
    }
    
    public boolean empty() {
        return root == null;
    }
    
    public boolean full() {
        return false;
    }
    
    public T retrieve() {
        return current.data;
    }
    
    // Find element by key
    public boolean find(K key) {
        TreeNode<K, T> p = root;
        while (p != null) {
            if (key.compareTo(p.key) == 0) {
                current = p;
                return true;
            } else if (key.compareTo(p.key) < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        return false;
    }
    
    // Insert new element
    public boolean insert(K key, T val) {
        if (root == null) {
            current = root = new TreeNode<K, T>(key, val);
            return true;
        }
        
        TreeNode<K, T> p = root;
        TreeNode<K, T> q = null;
        
        while (p != null) {
            int res = key.compareTo(p.key);
            if (res == 0) {
                // Key already exists, update data
                p.data = val;
                current = p;
                return false;
            } else {
                q = p;
                if (res < 0) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }
        }
        
        TreeNode<K, T> newNode = new TreeNode<K, T>(key, val);
        if (key.compareTo(q.key) < 0) {
            q.left = newNode;
        } else {
            q.right = newNode;
        }
        current = newNode;
        return true;
    }
    
    // Remove element by key
    public boolean remove(K key) {
        K searchKey = key;
        TreeNode<K, T> p = root;
        TreeNode<K, T> q = null; // Parent of p
        
        while (p != null) {
            int res = searchKey.compareTo(p.key);
            if (res < 0) {
                q = p;
                p = p.left;
            } else if (res > 0) {
                q = p;
                p = p.right;
            } else {
                // Found the key
                
                // Case 3: Two children
                if ((p.left != null) && (p.right != null)) {
                    // Find min in right subtree
                    TreeNode<K, T> min = p.right;
                    q = p;
                    while (min.left != null) {
                        q = min;
                        min = min.left;
                    }
                    p.key = min.key;
                    p.data = min.data;
                    searchKey = min.key;
                    p = min;
                    // Fall back to case 1 or 2
                }
                
                // Case 1 or 2: One or no children
                if (p.left != null) {
                    p = p.left;
                } else {
                    p = p.right;
                }
                
                if (q == null) {
                    // No parent, root must change
                    root = p;
                } else {
                    if (searchKey.compareTo(q.key) < 0) {
                        q.left = p;
                    } else {
                        q.right = p;
                    }
                }
                current = root;
                return true;
            }
        }
        
        return false; // Not found
    }
    
    // Get all elements using in-order traversal
    public LinkedList<T> getAllElements() {
        LinkedList<T> list = new LinkedList<>();
        inOrderTraversal(root, list);
        return list;
    }
    
    private void inOrderTraversal(TreeNode<K, T> node, LinkedList<T> list) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, list);
        list.addLast(node.data);
        inOrderTraversal(node.right, list);
    }
    
    // Display tree in-order
    public void displayInOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
        } else {
            displayInOrder(root);
        }
    }
    
    private void displayInOrder(TreeNode<K, T> node) {
        if (node == null) {
            return;
        }
        displayInOrder(node.left);
        System.out.print("Key: " + node.key);
        System.out.println(", Data: " + node.data);
        displayInOrder(node.right);
    }
    
    // Count nodes
    public int size() {
        return countNodes(root);
    }
    
    private int countNodes(TreeNode<K, T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}
