package finallPro.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Waiter extends Thread{
	private static Logger theLogger = Logger.getLogger("myLogger");
	private Resturant theResturant;
	private String name;
	private boolean isInShift = true;
	private Vector<Customer> allCustomers = new Vector<Customer>();
	private Queue<Customer> waitingCustomers = new LinkedList<Customer>();
	
	public Waiter(String name, Resturant theResturant) {
		super();
		this.name = name;
		this.theResturant = theResturant;
		
		FileHandler waiterHandler;
		try {
			waiterHandler = new FileHandler("Waiter_" + name + ".txt");
			waiterHandler.setFilter(new WaiterFilter(this.name));
			waiterHandler.setFormatter(new MyFormatter());
			theLogger.addHandler(waiterHandler);
			theLogger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void customerDone(Customer c){
		allCustomers.remove(c);
		theResturant.customerDone(c);
	}
	
	public int getServedCustomerNum(){
		return allCustomers.size();
	}
	
	public String getTheName() {
		return name;
	}

	public void setTheName(String name) {
		this.name = name;
	}

	public Resturant getTheResturant() {
		return theResturant;
	}

	public void setTheResturant(Resturant theResturant) {
		this.theResturant = theResturant;
	}

	public void addCustomer(Customer newCustomer) {
		allCustomers.add(newCustomer);
		newCustomer.start();
	}
	
	public void endShift() {
		isInShift = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}

	public synchronized void addWaitingCustomer(Customer c) {
		waitingCustomers.add(c);

		System.out.println("After adding customer #" + c.getId()
				+ " there are " + waitingCustomers.size()
				+ " customers waiting");

		synchronized (/*dummyWaiter*/this) {
			if (waitingCustomers.size() == 1) {
				/*dummyWaiter.*/notify(); // to let know there is an customer
										// waiting
			}
		}
	}

	public synchronized void notifyCustomer() {
		Customer firstCustomer = waitingCustomers.poll();
		if (firstCustomer != null) {

			System.out.println("Waiter is notifying customer #"
					+ firstCustomer.getId() + " at stage " + firstCustomer.getStage());
			synchronized (firstCustomer) {
				firstCustomer.notifyAll();
			}
		}
			try {

				System.out.println("Waiter waits that customer #"
						+ firstCustomer.getId()
						+ " will announce it is finished");

				wait(); // wait till the customer finishes

				System.out.println("Waiter was announced that customer #"
						+ firstCustomer.getId() + " is finished");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		//}
	}

	public void run() {
		System.out.println("Waiter #" + getId() + " " + name + " Strated its shift");
		while (isInShift) {
			if (!waitingCustomers.isEmpty()) {
				notifyCustomer();
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Waiter has no customers waiting");
						/*dummyWaiter.*/wait(); // wait till there is an customer
											// waiting
						System.out
								.println("Waiter recieved a message there is an customer waiting");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Shift ended for Waiter #" + getId() + this.name);
	}
}