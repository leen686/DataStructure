package datastructureproject;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;

    private class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T val) {
            data = val;
            next = null;
        }
    }

    public LinkedList() {
        head = current = null;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current != null && current.next == null;
    }

    public boolean full() {
        return false;
    }

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

    public void update(T val) {
        if (current != null) {
            current.data = val;
        }
    }

    public void insert(T val) {
        Node<T> tmp = new Node<>(val);
        if (empty()) {
            head = current = tmp;
        } else {
            tmp.next = head;
            head = tmp;
        }
    }

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

    public void removeFirst() {
        if (!empty()) {
            head = head.next;
            current = head;
        }
    }

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

    public int size() {
        int count = 0;
        Node<T> p = head;
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }

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

    public void clear() {
        head = current = null;
    }
}

