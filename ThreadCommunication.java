import java.util.ArrayList;

class ProducerConsumer {
    final int CAPACITY = 2;
    ArrayList<Integer> buffer = new ArrayList<>();
    int value = 1;

    public void produce() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (buffer.size() == CAPACITY) {
                    wait(); // wait until space available
                }

                buffer.add(value++);
                System.out.println("Produced: " + buffer);

                notify(); // wake up consumer
                Thread.sleep(1000);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (buffer.isEmpty()) {
                    wait(); // wait until item available
                }

                buffer.remove(0);
                System.out.println("Consumed: " + buffer);

                notify(); // wake up producer
                Thread.sleep(1000);
            }
        }
    }
}
public class ThreadCommunication {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Thread producer = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();
    }
}
