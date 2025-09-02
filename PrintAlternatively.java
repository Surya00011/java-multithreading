import java.util.concurrent.*;

class Main {
    public static void main(String[] args)throws Exception {
       PingPong p = new PingPong(3);    
       Thread t1 = new Thread(()->{
           try{
               p.printPing();
           }catch(Exception e){}
       });
       Thread t2 = new Thread(()->{
           try{
               p.printPong();
           }catch(Exception e){}
       });
       t1.start();
       t2.start();
       Thread.sleep(3000);
    }
}
class PingPong{
    private final int  n;
    private Semaphore ping = new Semaphore(1);
    private Semaphore pong = new Semaphore(0);
    
    PingPong(int n){
        this.n = n;
    }
    
    public void printPing(){
    try{
       for(int i=0;i<n;i++){
               ping.acquire();
               System.out.println("Ping");
               pong.release();
       }
    }catch(Exception e){}
   }
    
  public void printPong(){
    try{
       for(int i=0;i<n;i++){
               pong.acquire();
               System.out.println("Pong");
               ping.release();
        }
    }catch(Exception e){}
 }
}
