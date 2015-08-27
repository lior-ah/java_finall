package finallPro.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Resturant implements Runnable{
	private static Logger theLogger = Logger.getLogger("myLogger");
	private String name;
	private int numOfSeats;
	private int maxCustomersPerDay;
	private int maxWaitersInShift;
	private int maxCustomersCuncurrentPerWaiter;
	private int maxCustomersPerShift;
	private boolean isOpen = true;
	private Vector<Waiter> allWaiters = new Vector<Waiter>();
	private Queue<Waiter> waitingWaiters = new LinkedList<Waiter>();
	private Vector<Customer> allCustomers = new Vector<Customer>();
	private Queue<Customer> waitingCustomers = new LinkedList<Customer>();
	
	public Resturant(String name, int numOfSeats, int maxCustomersPerDay) {
		super();
		this.name = name;
		this.numOfSeats = numOfSeats;
		this.maxCustomersPerDay = maxCustomersPerDay;
		
		FileHandler restHandler;
		try {
			restHandler = new FileHandler("Resturant_" + name + ".txt");
			restHandler.setFormatter(new MyFormatter());
			theLogger.addHandler(restHandler);
			theLogger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumOfSeats() {
		return numOfSeats;
	}

	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}

	public int getMaxCustomersPerDay() {
		return maxCustomersPerDay;
	}

	public void setMaxCustomersPerDay(int maxCustomersPerDay) {
		this.maxCustomersPerDay = maxCustomersPerDay;
	}
	
	public int getMaxWaitersInShift() {
		return maxWaitersInShift;
	}

	public void setMaxWaitersInShift(int maxWaitersInShift) {
		this.maxWaitersInShift = maxWaitersInShift;
	}

	public int getMaxCustomersCuncurrentPerWaiter() {
		return maxCustomersCuncurrentPerWaiter;
	}

	public void setMaxCustomersCuncurrentPerWaiter(int maxCustomersCuncurrentPerWaiter) {
		this.maxCustomersCuncurrentPerWaiter = maxCustomersCuncurrentPerWaiter;
	}

	public int getMaxCustomersPerShift() {
		return maxCustomersPerShift;
	}

	public void setMaxCustomersPerShift(int maxCustomersPerShift) {
		this.maxCustomersPerShift = maxCustomersPerShift;
	}

	public void addWaiter(Waiter newWaiter) {
		allWaiters.add(newWaiter);
		newWaiter.start();
	}
	
	public void addCustomer(Customer newCustomer) {
		allCustomers.add(newCustomer);
		newCustomer.start();
	}

	public void closeAirport() {
		isOpen = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}

	public synchronized void addWaitingWaiters(Waiter w) {
		waitingWaiters.add(w);

		System.out.println("After adding waiter #" + w.getId()
				+ " there are " + waitingWaiters.size()
				+ " waiters waiting");

		synchronized (/*dummyWaiter*/this) {
			if (waitingWaiters.size() == 1) {
				/*dummyWaiter.*/notify(); // to let know there is an waiter
										// waiting
			}
		}
	}
	
	public synchronized void addWaitingAirplane(Customer c) {
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

	public synchronized void notifyWaiter() {
		Waiter firstWaiter = waitingWaiters.poll();
		if (firstWaiter != null) {

			System.out.println("Resturant is notifying waiter #"
					+ firstWaiter.getId());
			synchronized (firstWaiter) {
				firstWaiter.notifyAll();
			}
		}
			try {

				System.out.println("Resturant waits that  waiter #"
						+ firstWaiter.getId()
						+ " will announce it is finished");

				wait(); // wait till the waiter finishes

				System.out.println("Resturant was announced that  waiter #"
						+ firstWaiter.getId() + " is finished");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public synchronized void notifyCustomer() {
		Customer firstCustomer = waitingCustomers.poll();
		if (firstCustomer != null) {

			System.out.println("Resturant is notifying customer #"
					+ firstCustomer.getId());
			synchronized (firstCustomer) {
				firstCustomer.notifyAll();
			}
		}
			try {

				System.out.println("Resturant waits that  customer #"
						+ firstCustomer.getId()
						+ " will announce it is finished");

				wait(); // wait till the customer finishes

				System.out.println("Resturant was announced that  customer #"
						+ firstCustomer.getId() + " is finished");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public void run() {
		System.out.println("In Resturant::run");
		while (isOpen) {
			if (!waitingWaiters.isEmpty()) {
				notifyWaiter();
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Resturant has no witers waiting");
						/*dummyWaiter.*/wait(); // wait till there is an waiter
											// waiting
						System.out
								.println("Resturant recieved a message there is an waiter waiting");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (!waitingCustomers.isEmpty()) {
				notifyCustomer();
			} else {
				synchronized (/*dummyWaiter*/this) {
					try {
						System.out.println("Resturant has no customers waiting");
						/*dummyWaiter.*/wait(); // wait till there is an customer
											// waiting
						System.out
								.println("Resturant recieved a message there is an customer waiting");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Resturant is closing");
	}

}
