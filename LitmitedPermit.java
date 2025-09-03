import java.util.concurrent.Semaphore;

class ParkingLotResource {
    private Semaphore parking = new Semaphore(2);

    public void park(int carId) {
        try{
            System.out.println("CarId "+carId+" is waiting in queue");
            parking.acquire();
            System.out.println("CarId"+carId+"is Parked");
            Thread.sleep(2000);
        }catch(Exception e){}
        finally{
            System.out.println("CarId "+carId+" leaving the parking lot");
            parking.release();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ParkingLotResource lot = new ParkingLotResource();

        for (int i = 1; i <= 5; i++) {
            int carId = i;
            new Thread(() -> lot.park(carId)).start();
        }
    }
}  
