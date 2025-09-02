import java.util.concurrent.*;

class Main {
    public static void main(String[] args)throws Exception {
       PrintSequence p = new PrintSequence(5);    
       Thread t1 = new Thread(()->{
           try{
               p.printA();
           }catch(Exception e){}
       });
       Thread t2 = new Thread(()->{
           try{
               p.printB();
           }catch(Exception e){}
       });
       Thread t3 = new Thread(()->{
           try{
               p.printC();
           }catch(Exception e){}
       });
       t1.start();
       t2.start();
       t3.start();
       Thread.sleep(3000);
    }
}
class PrintSequence{
    private final int  n;
    private Semaphore semA = new Semaphore(1);
    private Semaphore semB = new Semaphore(0);
    private Semaphore semC = new Semaphore(0);
    
    PrintSequence(int n){
        this.n = n;
    }
    
    public void printA(){
    try{
       for(int i=0;i<n;i++){
               semA.acquire();
               System.out.print("A,");
               semB.release();
       }
    }catch(Exception e){}
}
    
    public void printB(){
    try{
       for(int i=0;i<n;i++){
               semB.acquire();
               System.out.print("B,");
               semC.release();
        }
    }catch(Exception e){}
  }
  
   public void printC(){
    try{
       for(int i=0;i<n;i++){
               semC.acquire();
               System.out.print("C ");
               semA.release();
        }
    }catch(Exception e){}
  }
}
