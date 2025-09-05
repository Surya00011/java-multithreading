
import java.util.concurrent.Semaphore;

public class BoundedBlockingQueue<E> {
    private final int MAX_BUFFER_SIZE;
    private Node<E> front;
    private Node<E> rear;
    private int size;
    private final Semaphore enqueueSemaphore;
    private final Semaphore dequeueSemaphore;

    public BoundedBlockingQueue(int maxBufferCapacity) {
        MAX_BUFFER_SIZE  = maxBufferCapacity;
        enqueueSemaphore = new Semaphore(maxBufferCapacity);
        dequeueSemaphore = new Semaphore(0);
        size = 0;
    }

    public BoundedBlockingQueue() {
        final int DEFAULT_BUFFER_SIZE = 5;
        MAX_BUFFER_SIZE = DEFAULT_BUFFER_SIZE;
        enqueueSemaphore = new Semaphore(DEFAULT_BUFFER_SIZE);
        dequeueSemaphore = new Semaphore(0);
        size = 0;
    }

    private static class Node<T> {
        T element;
        Node<T> next;
        Node(T element) {
            this.element = element;
            this.next = null;
        }
    }

    public void enqueue(E element) {
        try {
            enqueueSemaphore.acquire();
            synchronized (this) {
                Node<E> newNode = new Node<>(element);
                if (rear == null && front == null) {
                    rear = newNode;
                    front = newNode;
                } else {
                    rear.next = newNode;
                    rear = newNode;
                }
                size++;
                System.out.println(Thread.currentThread().getName() +
                        " produced " + element + " | Queue: " + this);
            }
            dequeueSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public E dequeue() {
        try {
            dequeueSemaphore.acquire();
            E dequeued;
            synchronized (this) {
                dequeued = front.element;
                front = front.next;
                size--;
                if (front == null) {
                    rear = null;
                }
                System.out.println(Thread.currentThread().getName() +
                        " consumed " + dequeued + " | Queue: " + this);
            }
            enqueueSemaphore.release();
            return dequeued;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized E peekFront() {
        if (front == null) return null;
        return front.element;
    }

    public synchronized E peekRear() {
        if (rear == null) return null;
        return rear.element;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    public synchronized boolean isFull() {
        return size == MAX_BUFFER_SIZE;
    }

    public synchronized int getSize() {
        return size;
    }

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = front;
        while (curr != null) {
            sb.append(curr.element);
            if (curr.next != null) sb.append(", ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
