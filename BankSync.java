import java.util.*;
class BankAccount {
	private int  balance =1000;
	public synchronized void withDraw(int amount) {
		if(balance>=amount) {
			balance-=amount;
			System.out.println("Amount withDrawed by "+Thread.currentThread().getName());
			System.out.println(Thread.currentThread().getName()+" deducted "+ amount);
			System.out.println(Thread.currentThread().getName()+" your Balance is "+ balance);
		} else {
		    System.out.println("Tried by "+Thread.currentThread().getName());
			System.out.println("Insufficient balance "+"Rs: "+balance+"available");
		}
	}
}
public class BankSync
{
	public static void main(String[] args) throws InterruptedException{
		BankAccount sharedAccount = new BankAccount();
		Thread user1 = new Thread(()-> {
			sharedAccount.withDraw(700);
		});
		Thread user2 = new Thread(()-> {
			sharedAccount.withDraw(700);
		});
		user1.setName("User 1");
		user2.setName("User 2");
		user1.start();
		//user1.join();
		user2.start();
	}
}
