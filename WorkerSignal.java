import java.util.concurrent.Semaphore;

class Worker {
    private Semaphore startSignal;

    Worker(Semaphore signal) {
        this.startSignal = signal;
    }

    public void doWork() {
        try{
            System.out.println("Worker waiting for raw materials to start Work");
            startSignal.acquire();
            for(int i=1;i<=5;i++){
                System.out.println("Preparing material "+ i);
                Thread.sleep(1000);
            }
        }catch(Exception e){}
        finally{
            System.out.println("Work Done");
            startSignal.release();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Semaphore signal = new Semaphore(0);
        Worker worker = new Worker(signal);

        Thread t = new Thread(worker::doWork);
        t.start();
        System.out.println("Out-Sourcing Raw materials");
        Thread.sleep(3000);
        signal.release();
    }
}
