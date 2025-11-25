package datastructureproject;

class AVLStringNode<T> {
    public String key;
    public T data;
    public AVLStringNode<T> left, right;
    public int height;
    
    public AVLStringNode(String key, T data) {
        this.key = key.toLowerCase();
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

public class AVLString<T> {
    private AVLStringNode<T> root;
    private AVLStringNode<T> current;
    
    public AVLString() {
        root = null;
        current = null;
    }
    
    private int height(AVLStringNode<T> node) {
        return node == null ? 0 : node.height;
    }
    
    private int getBalance(AVLStringNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }
    
    private void updateHeight(AVLStringNode<T> node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }
    
    private AVLStringNode<T> rotateRight(AVLStringNode<T> y) {
        AVLStringNode<T> x = y.left;
        AVLStringNode<T> T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    private AVLStringNode<T> rotateLeft(AVLStringNode<T> x) {
        AVLStringNode<T> y = x.right;
        AVLStringNode<T> T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public T getCurrent() {
        return current == null ? null : current.data;
    }
    
    public AVLStringNode<T> getRoot() {
        return root;
    }
    
    public boolean search(String key) {
        if (root == null) return false;
        
        AVLStringNode<T> temp = root;
        String searchKey = key.toLowerCase();
        
        while (temp != null) {
            current = temp;
            int comparison = searchKey.compareTo(temp.key);
            
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return false;
    }
    
    public T getData(String key) {
        if (search(key)) {
            return current.data;
        }
        return null;
    }
    
    public boolean add(String key, T value) {
        root = insert(root, key, value);
        return search(key);
    }
    
    private AVLStringNode<T> insert(AVLStringNode<T> node, String key, T value) {
        if (node == null) {
            return new AVLStringNode<T>(key, value);
        }
        
        String insertKey = key.toLowerCase();
        int comparison = insertKey.compareTo(node.key);
        
        if (comparison < 0) {
            node.left = insert(node.left, key, value);
        } else if (comparison > 0) {
            node.right = insert(node.right, key, value);
        } else {
            current = node;
            return node;
        }
        
        return balanceNode(node);
    }
    
    public boolean delete(String key) {
        int initialSize = countNodes();
        root = deleteNode(root, key);
        return countNodes() < initialSize;
    }
    
    private AVLStringNode<T> deleteNode(AVLStringNode<T> node, String key) {
        if (node == null) return null;
        
        String deleteKey = key.toLowerCase();
        int comparison = deleteKey.compareTo(node.key);
        
        if (comparison < 0) {
            node.left = deleteNode(node.left, key);
        } else if (comparison > 0) {
            node.right = deleteNode(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLStringNode<T> successor = findMinNode(node.right);
                node.key = successor.key;
                node.data = successor.data;
                node.right = deleteNode(node.right, successor.key);
            }
        }
        
        if (node == null) return null;
        
        return balanceNode(node);
    }
    
    private AVLStringNode<T> findMinNode(AVLStringNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    private AVLStringNode<T> balanceNode(AVLStringNode<T> node) {
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
    
    public void displayInOrder() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        inOrderTraversal(root);
    }
    
    private void inOrderTraversal(AVLStringNode<T> node) {
        if (node == null) return;
        inOrderTraversal(node.left);
        System.out.println("Key: " + node.key + " | Data: " + node.data + " | Height: " + node.height);
        inOrderTraversal(node.right);
    }
    
    public int countNodes() {
        return countNodesHelper(root);
    }
    
    private int countNodesHelper(AVLStringNode<T> node) {
        return node == null ? 0 : 1 + countNodesHelper(node.left) + countNodesHelper(node.right);
    }
    
    public int getHeight() {
        return height(root);
    }
    
    public LinkedList<T> getRange(String minKey, String maxKey) {
        LinkedList<T> result = new LinkedList<>();
        collectRange(root, minKey.toLowerCase(), maxKey.toLowerCase(), result);
        return result;
    }
    
    private void collectRange(AVLStringNode<T> node, String min, String max, LinkedList<T> result) {
        if (node == null) return;
        
        if (node.key.compareTo(min) > 0) {
            collectRange(node.left, min, max, result);
        }
        
        if (node.key.compareTo(min) >= 0 && node.key.compareTo(max) <= 0) {
            result.addLast(node.data);
        }
        
        if (node.key.compareTo(max) < 0) {
            collectRange(node.right, min, max, result);
        }
    }
}