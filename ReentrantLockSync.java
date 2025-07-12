import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
	private int balance = 1000;
	private ReentrantLock lock = new ReentrantLock();

	public void withDraw(int amount) {
		lock.lock();
		try {
			if (balance >= amount) {
				balance -= amount;
				System.out.println("Amount withdrawn by " + Thread.currentThread().getName());
				System.out.println(Thread.currentThread().getName() + " deducted " + amount);
				System.out.println(Thread.currentThread().getName() + " your balance is " + balance);
			} else {
				System.out.println("Tried by " + Thread.currentThread().getName());
				System.out.println("Insufficient balance Rs: " + balance + " available");
			}
		} finally {
			lock.unlock();
		}
	}
}

public class ReentrantLockSync
{
	public static void main(String[] args) throws InterruptedException {
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
