package datastructureproject;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;

    // Inner Node class
    private class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T val) {
            data = val;
            next = null;
        }
    }

    // Constructor
    public LinkedList() {
        head = current = null;
    }

    // Check if list is empty
    public boolean empty() {
        return head == null;
    }

    // Check if current is at last node
    public boolean last() {
        return current != null && current.next == null;
    }

    // Check if list is full (always false for linked list)
    public boolean full() {
        return false;
    }

    // Move current to first node
    public void findFirst() {
        current = head;
    }

    // Move current to next node
    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    // Retrieve data from current node
    public T retrieve() {
        if (current == null) {
            return null;
        }
        return current.data;
    }

    // Update data in current node
    public void update(T val) {
        if (current != null) {
            current.data = val;
        }
    }

    // Insert data at beginning of list
    public void insert(T val) {
        Node<T> tmp = new Node<>(val);
        if (empty()) {
            head = current = tmp;
        } else {
            tmp.next = head;
            head = tmp;
        }
    }

    // Add data at end of list
    public void addLast(T val) {
        Node<T> tmp = new Node<>(val);
        if (empty()) {
            head = current = tmp;
        } else {
            Node<T> p = head;
            while (p.next != null) {
                p = p.next;
            }
            p.next = tmp;
            current = tmp;
        }
    }

    // Remove current node
    public void remove() {
        if (current == head) {
            head = head.next;
            if (head == null) {
                current = null;
            } else {
                current = head;
            }
        } else {
            Node<T> p = head;
            while (p.next != current) {
                p = p.next;
            }
            p.next = current.next;
            if (current.next == null) {
                current = head;
            } else {
                current = current.next;
            }
        }
    }

    // Remove first node
    public void removeFirst() {
        if (!empty()) {
            head = head.next;
            current = head;
        }
    }

    // Remove last node
    public void removeLast() {
        if (empty()) {
            return;
        }
        if (head.next == null) {
            head = current = null;
            return;
        }
        Node<T> p = head;
        while (p.next.next != null) {
            p = p.next;
        }
        p.next = null;
        current = head;
    }

    // Get size of list
    public int size() {
        int count = 0;
        Node<T> p = head;
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }

    // Search for element
    public boolean search(T val) {
        Node<T> p = head;
        while (p != null) {
            if (p.data.equals(val)) {
                current = p;
                return true;
            }
            p = p.next;
        }
        return false;
    }

    // Display all elements
    public void display() {
        if (empty()) {
            System.out.println("List is empty");
            return;
        }
        Node<T> p = head;
        System.out.print("[");
        while (p != null) {
            System.out.print(p.data);
            if (p.next != null) {
                System.out.print(", ");
            }
            p = p.next;
        }
        System.out.println("]");
    }

    // Clear all elements
    public void clear() {
        head = current = null;
    }

    // Get element at specific index
    public T get(int index) {
        if (index < 0 || empty()) {
            return null;
        }
        Node<T> p = head;
        int count = 0;
        while (p != null && count < index) {
            p = p.next;
            count++;
        }
        return (p != null) ? p.data : null;
    }

    // Check if element exists
    public boolean contains(T val) {
        Node<T> p = head;
        while (p != null) {
            if (p.data.equals(val)) {
                return true;
            }
            p = p.next;
        }
        return false;
    }

    // Convert to array (for easier processing)
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (empty()) {
            return (T[]) new Object[0];
        }
        T[] arr = (T[]) new Object[size()];
        Node<T> p = head;
        int i = 0;
        while (p != null) {
            arr[i++] = p.data;
            p = p.next;
        }
        return arr;
    }

    @Override
    public String toString() {
        if (empty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node<T> p = head;
        while (p != null) {
            sb.append(p.data);
            if (p.next != null) {
                sb.append(", ");
            }
            p = p.next;
        }
        sb.append("]");
        return sb.toString();
    }
}