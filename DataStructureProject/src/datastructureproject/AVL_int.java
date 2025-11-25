package datastructureproject;

class AVLNode<T> {
    public int key;
    public T data;
    public AVLNode<T> left, right;
    public int height;
    
    public AVLNode(int key, T data) {
        this.key = key;
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

public class AVL_int<T> {
    private AVLNode<T> root;
    private AVLNode<T> current;
    
    public AVL_int() {
        root = null;
        current = null;
    }
    
    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }
    
    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }
    
    private void updateHeight(AVLNode<T> node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }
    
    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    // ============ BASIC OPERATIONS ============
    public boolean isEmpty() {
        return root == null;
    }
    
    public T getCurrent() {
        return current == null ? null : current.data;
    }
    
    public AVLNode<T> getRoot() {
        return root;
    }
    
    // ============ SEARCH OPERATIONS ============
    public boolean search(int key) {
        if (root == null) return false;
        
        AVLNode<T> temp = root;
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
        return search(key) ? current.data : null;
    }
    
    // ============ INSERT OPERATION ============
    public boolean add(int key, T value) {
        root = insert(root, key, value);
        return search(key);
    }
    
    private AVLNode<T> insert(AVLNode<T> node, int key, T value) {
        if (node == null) {
            return new AVLNode<T>(key, value);
        }
        
        if (key < node.key) {
            node.left = insert(node.left, key, value);
        } else if (key > node.key) {
            node.right = insert(node.right, key, value);
        } else {
            current = node;
            return node;
        }
        
        return balanceNode(node);
    }
    
    // ============ DELETE OPERATION ============
    public boolean delete(int key) {
        int initialSize = countNodes();
        root = deleteNode(root, key);
        return countNodes() < initialSize;
    }
    
    private AVLNode<T> deleteNode(AVLNode<T> node, int key) {
        if (node == null) return null;
        
        if (key < node.key) {
            node.left = deleteNode(node.left, key);
        } else if (key > node.key) {
            node.right = deleteNode(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode<T> successor = findMinNode(node.right);
                node.key = successor.key;
                node.data = successor.data;
                node.right = deleteNode(node.right, successor.key);
            }
        }
        
        if (node == null) return null;
        
        return balanceNode(node);
    }
    
    private AVLNode<T> findMinNode(AVLNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // ============ BALANCING OPERATIONS ============
    private AVLNode<T> balanceNode(AVLNode<T> node) {
        updateHeight(node);
        int balance = getBalance(node);
        
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    // ============ UPDATE OPERATION ============
    public boolean update(int key, T newValue) {
        if (search(key)) {
            current.data = newValue;
            return true;
        }
        return false;
    }
    
    // ============ TRAVERSAL OPERATIONS ============
    public void displayInOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        inOrderTraversal(root);
    }
    
    private void inOrderTraversal(AVLNode<T> node) {
        if (node == null) return;
        inOrderTraversal(node.left);
        System.out.println("Key: " + node.key + " | Data: " + node.data + " | Height: " + node.height);
        inOrderTraversal(node.right);
    }
    
    public void displayPreOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        preOrderTraversal(root);
    }
    
    private void preOrderTraversal(AVLNode<T> node) {
        if (node == null) return;
        System.out.println("Key: " + node.key + " | Data: " + node.data + " | Height: " + node.height);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }
    
    // ============ TREE STATISTICS ============
    public int countNodes() {
        return countNodesHelper(root);
    }
    
    private int countNodesHelper(AVLNode<T> node) {
        return node == null ? 0 : 1 + countNodesHelper(node.left) + countNodesHelper(node.right);
    }
    
    public int getHeight() {
        return height(root);
    }
    
    public int findMin() {
        if (root == null) throw new RuntimeException("Tree is empty");
        return findMinNode(root).key;
    }
    
    public int findMax() {
        if (root == null) throw new RuntimeException("Tree is empty");
        AVLNode<T> temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.key;
    }
    
    // ============ RANGE QUERY OPERATIONS ============
    public LinkedList<T> getRange(int minKey, int maxKey) {
        LinkedList<T> result = new LinkedList<>();
        collectRange(root, minKey, maxKey, result);
        return result;
    }
    
    private void collectRange(AVLNode<T> node, int min, int max, LinkedList<T> result) {
        if (node == null) return;
        
        if (node.key > min) {
            collectRange(node.left, min, max, result);
        }
        
        if (node.key >= min && node.key <= max) {
            result.addLast(node.data);
        }
        
        if (node.key < max) {
            collectRange(node.right, min, max, result);
        }
    }

    int size() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}