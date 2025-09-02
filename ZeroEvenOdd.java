import java.util.concurrent.*;

class Main {
    public static void main(String[] args)throws Exception {
       ZeroEvenOdd z = new ZeroEvenOdd(5);
       IntConsumer ic = new IntConsumer();
       Thread t1 = new Thread(()->{
           try{
               z.zero(ic);
           }catch(Exception e){}
       });
       Thread t2 = new Thread(()->{
           try{
               z.even(ic);
           }catch(Exception e){}
       });
       Thread t3 = new Thread(()->{
           try{
               z.odd(ic);
           }catch(Exception e){}
       });
       t1.start();
       t2.start();
       t3.start();
       Thread.sleep(3000);
    }
}
class IntConsumer{
    public void accept(int x){
        System.out.print(x);
    }
}
class ZeroEvenOdd {
    private int n;
    private final Semaphore zeroSem; 
    private final Semaphore oddSem; 
    private final Semaphore evenSem; 
    public ZeroEvenOdd(int n) {
        this.n = n;
        this.zeroSem = new Semaphore(1);
        this.oddSem = new Semaphore(0);
        this.evenSem = new Semaphore(0);
    }

    
    public void zero(IntConsumer printNumber) {
        try{
            for(int i=1;i<=n;i++){
                zeroSem.acquire();
                printNumber.accept(0);
                if(i%2==0){
                    evenSem.release();
                }
                else if(i%2!=0){
                    oddSem.release();
                }
            }
         }catch(Exception e){}  
    }     

    public void even(IntConsumer printNumber){
        try{
        for(int i=2;i<=n;i+=2){
            evenSem.acquire();
            printNumber.accept(i);
            zeroSem.release();
        }
        }catch(Exception e){}
    }

    public void odd(IntConsumer printNumber) {
        try{
        for(int i=1;i<=n;i+=2){
            oddSem.acquire();
            printNumber.accept(i);
            zeroSem.release();
        }
        }catch(Exception e){}
    }
}
