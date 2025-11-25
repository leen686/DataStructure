package datastructureproject;

class BSTNode<T> {
    public int key;
    public T data;
    public BSTNode<T> left, right;
    
    public BSTNode(int key, T data) {
        this.key = key;
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BST_int<T> {
    private BSTNode<T> root;
    private BSTNode<T> current;
    
    public BST_int() {
        root = null;
        current = null;
    }
    
    // ============ Basic Operations ============
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public T getCurrent() {
        if (current == null) {
            return null;
        }
        return current.data;
    }
    
    public BSTNode<T> getRoot() {
        return root;
    }
    
    // ============ Search Operations ============
    
    public boolean search(int key) {
        if (root == null) {
            return false;
        }
        
        BSTNode<T> temp = root;
        while (temp != null) {
            current = temp;
            if (key == temp.key) {
                return true;
            } else if (key < temp.key) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return false;
    }
    
    public T getData(int key) {
        if (search(key)) {
            return current.data;
        }
        return null;
    }
    
    // ============ Insert Operation ============
    
    public boolean add(int key, T value) {
        if (root == null) {
            root = new BSTNode<T>(key, value);
            current = root;
            return true;
        }
        
        BSTNode<T> parent = null;
        BSTNode<T> temp = root;
        
        while (temp != null) {
            parent = temp;
            if (key == temp.key) {
                current = temp;
                return false; // Key already exists
            } else if (key < temp.key) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        
        BSTNode<T> newNode = new BSTNode<T>(key, value);
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        current = newNode;
        return true;
    }
    
    // ============ Update Operation ============
    
    public boolean update(int key, T newValue) {
        if (search(key)) {
            current.data = newValue;
            return true;
        }
        return false;
    }
    
    // ============ Delete Operation ============
    
    public boolean delete(int key) {
        if (root == null) {
            return false;
        }
        
        BSTNode<T> parent = null;
        BSTNode<T> temp = root;
        
        // Search for the node
        while (temp != null && temp.key != key) {
            parent = temp;
            if (key < temp.key) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        
        if (temp == null) {
            return false; // Key not found
        }
        
        // Case 1: Node with two children
        if (temp.left != null && temp.right != null) {
            BSTNode<T> successorParent = temp;
            BSTNode<T> successor = temp.right;
            
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            
            temp.key = successor.key;
            temp.data = successor.data;
            
            key = successor.key;
            parent = successorParent;
            temp = successor;
        }
        
        // Case 2 & 3: Node with one or no children
        BSTNode<T> child;
        if (temp.left != null) {
            child = temp.left;
        } else {
            child = temp.right;
        }
        
        if (parent == null) {
            root = child;
        } else if (temp == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        
        current = root;
        return true;
    }
    
    // ============ Traversal Operations ============
    
    public void displayInOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        inOrderTraversal(root);
    }
    
    private void inOrderTraversal(BSTNode<T> node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left);
        System.out.println("Key: " + node.key + " | Data: " + node.data);
        inOrderTraversal(node.right);
    }
    
    public void displayPreOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        preOrderTraversal(root);
    }
    
    private void preOrderTraversal(BSTNode<T> node) {
        if (node == null) {
            return;
        }
        System.out.println("Key: " + node.key + " | Data: " + node.data);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }
    
    public void displayPostOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        postOrderTraversal(root);
    }
    
    private void postOrderTraversal(BSTNode<T> node) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.println("Key: " + node.key + " | Data: " + node.data);
    }
    
    // ============ Tree Statistics ============
    
    public int countNodes() {
        return countNodesHelper(root);
    }
    
    private int countNodesHelper(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodesHelper(node.left) + countNodesHelper(node.right);
    }
    
    public int getHeight() {
        return getHeightHelper(root);
    }
    
    private int getHeightHelper(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeightHelper(node.left);
        int rightHeight = getHeightHelper(node.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    // ============ Advanced Operations ============
    
    public int findMin() {
        if (root == null) {
            throw new RuntimeException("Tree is empty");
        }
        BSTNode<T> temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.key;
    }
    
    public int findMax() {
        if (root == null) {
            throw new RuntimeException("Tree is empty");
        }
        BSTNode<T> temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.key;
    }
}
