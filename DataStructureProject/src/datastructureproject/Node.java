
package datastructureproject;
public class Node<T> {
    T data;        
    Node<T> next;  

    // constructor
    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    // getters and setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
