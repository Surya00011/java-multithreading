import java.util.concurrent.Semaphore;

class Printer {
    private Semaphore mutex = new Semaphore(1);

    public void printNumbers(int threadId) {
        try{
            System.out.println("Thread "+threadId+" is waiting in queue");
            mutex.acquire();
            System.out.println("Thread "+threadId+" acquired permit to enter critical section");
            for(int i=1;i<=5;i++){
                System.out.println("Thread "+threadId+" printed "+i);
                Thread.sleep(1000);
            }
        }catch(Exception e){}
        finally{
            System.out.println("Thread "+threadId+" left critical section");
            mutex.release();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();

        for (int i = 1; i <= 3; i++) {
            int id = i;
            new Thread(() -> printer.printNumbers(id)).start();
        }
    }
}
