import java.util.LinkedList;

public class GenericQueue<T> {
    private LinkedList<T> list;

    public GenericQueue() {
        list = new LinkedList<>();
    }

    public void enqueue(T item) {
        list.addFirst(item);
    }

    public T dequeue() {
        return list.removeLast();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}

