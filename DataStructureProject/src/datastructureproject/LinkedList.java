package datastructureproject;

/**
 * Generic LinkedList Implementation
 */
class Node<T> {
    public T data;
    public Node<T> next;
    
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;
    private int size;
    
    public LinkedList() {
        head = current = null;
        size = 0;
    }
    
    // ============ Basic Operations ============
    
    public boolean empty() {
        return head == null;
    }
    
    public boolean full() {
        return false;  // Linked list is never full
    }
    
    public int size() {
        return size;
    }
    
    public boolean last() {
        return current != null && current.next == null;
    }
    
    // ============ Navigation ============
    
    public void findFirst() {
        current = head;
    }
    
    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }
    
    public T retrieve() {
        if (current == null) {
            return null;
        }
        return current.data;
    }
    
    // ============ Insertion ============
    
    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        if (empty()) {
            head = current = newNode;
        } else {
            newNode.next = current.next;
            current.next = newNode;
            current = newNode;
        }
        size++;
    }
    
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        if (current == null) {
            current = head;
        }
        size++;
    }
    
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (empty()) {
            head = current = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }
    
    // ============ Deletion ============
    
    public void remove() {
        if (current == null || head == null) {
            return;
        }
        
        if (current == head) {
            head = head.next;
            current = head;
        } else {
            Node<T> temp = head;
            while (temp.next != current) {
                temp = temp.next;
            }
            temp.next = current.next;
            current = temp;
        }
        size--;
    }
    
    public void removeFirst() {
        if (head != null) {
            head = head.next;
            if (current == null || head == null) {
                current = head;
            }
            size--;
        }
    }
    
    public void removeLast() {
        if (head == null) {
            return;
        }
        
        if (head.next == null) {
            head = current = null;
            size--;
            return;
        }
        
        Node<T> temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        temp.next = null;
        current = temp;
        size--;
    }
    
    // ============ Update ============
    
    public void update(T data) {
        if (current != null) {
            current.data = data;
        }
    }
    
    // ============ Search ============
    
    public boolean search(T data) {
        if (empty()) {
            return false;
        }
        
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data.equals(data)) {
                current = temp;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    public int indexOf(T data) {
        if (empty()) {
            return -1;
        }
        
        Node<T> temp = head;
        int index = 0;
        while (temp != null) {
            if (temp.data.equals(data)) {
                return index;
            }
            temp = temp.next;
            index++;
        }
        return -1;
    }
    
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.data;
    }
    
    // ============ Display ============
    
    public void display() {
        if (empty()) {
            System.out.println("List is empty");
            return;
        }
        
        Node<T> temp = head;
        while (temp != null) {
            System.out.print(temp.data);
            if (temp.next != null) {
                System.out.print(" -> ");
            }
            temp = temp.next;
        }
        System.out.println();
    }
    
    // ============ Utility ============
    
    public void clear() {
        head = current = null;
        size = 0;
    }
    
    public boolean contains(T data) {
        return search(data);
    }
    
    public T getFirst() {
        return head != null ? head.data : null;
    }
    
    public T getLast() {
        if (head == null) {
            return null;
        }
        
        Node<T> temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        return temp.data;
    }
    
    // ============ Conversion ============
    
    public T[] toArray(T[] array) {
        if (size == 0) {
            return array;
        }
        
        Node<T> temp = head;
        int index = 0;
        while (temp != null && index < array.length) {
            array[index++] = temp.data;
            temp = temp.next;
        }
        return array;
    }
    
    // ============ Advanced Operations ============
    
    public void reverse() {
        if (head == null || head.next == null) {
            return;
        }
        
        Node<T> prev = null;
        Node<T> curr = head;
        Node<T> next = null;
        
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        head = prev;
        current = head;
    }
    
    public LinkedList<T> copy() {
        LinkedList<T> newList = new LinkedList<>();
        if (empty()) {
            return newList;
        }
        
        Node<T> temp = head;
        while (temp != null) {
            newList.addLast(temp.data);
            temp = temp.next;
        }
        return newList;
    }
    
    // ============ String Representation ============
    
    @Override
    public String toString() {
        if (empty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node<T> temp = head;
        while (temp != null) {
            sb.append(temp.data);
            if (temp.next != null) {
                sb.append(", ");
            }
            temp = temp.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
