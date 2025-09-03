import java.util.concurrent.*;
class Main {
    public static void main(String[] args) {
        DiningPhilosophers dp = new DiningPhilosophers();
        int n = 5; // 5 philosophers
   
        for (int i = 1; i <= n; i++) {
            final int philosopher = i;
            new Thread(() -> {
                try {
                    dp.wantsToEat(
                        philosopher,
                        () -> System.out.println("Philosopher " + philosopher + " picks left fork"),
                        () -> System.out.println("Philosopher " + philosopher + " picks right fork"),
                        () -> System.out.println("Philosopher " + philosopher + " eats"),
                        () -> System.out.println("Philosopher " + philosopher + " puts left fork"),
                        () -> System.out.println("Philosopher " + philosopher + " puts right fork")
                    );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

class DiningPhilosophers {
    private final Semaphore maxPhilo = new Semaphore(4);
    public DiningPhilosophers() {
        
    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        maxPhilo.acquire();
        synchronized(this){
            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putLeftFork.run();
            putRightFork.run();
            philoAteCount++;
            System.out.println("No of Philosopers ate: "+ philoAteCount);
        }
        maxPhilo.release();
    }
}
