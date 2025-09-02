import java.util.concurrent.Semaphore;

class Worker implements Runnable {
    private Semaphore startSignal;
    private int id;

    Worker(Semaphore signal, int id) {
        this.startSignal = signal;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // worker waits here
            startSignal.acquire();

            // worker starts actual job here
            for (int i = 1; i <= 5; i++) {
                System.out.println("Worker " + id + " step " + i);
                Thread.sleep(1000);
            }
            System.out.println("Worker " + id + " finished work");
        } catch (Exception e) {}
        finally{
            startSignal.release();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Semaphore signal = new Semaphore(0);

        // create 3 workers
        for (int i = 1; i <= 3; i++) {
            new Thread(new Worker(signal, i)).start();
        }

        System.out.println("Main preparing resources...");
        Thread.sleep(3000);

        // signal all workers at once
        signal.release(2);
    }
}
