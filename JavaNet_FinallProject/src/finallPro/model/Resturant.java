package finallPro.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Resturant {
	private static Logger theLogger = Logger.getLogger("myLogger");
	private String name;
	private int numOfSeats;
	private int maxCustomersPerDay;
	private int maxWaitersInShift;
	private int maxCustomersCuncurrentPerWaiter;
	private int maxCustomersPerShift;
	private boolean isOpen = true;
	private Vector<Waiter> workingWaiters = new Vector<Waiter>();
	private Queue<Waiter> waitingWaiters = new LinkedList<Waiter>();
	private Vector<Customer> diningCustomers = new Vector<Customer>();
	private Queue<Customer> waitingCustomers = new LinkedList<Customer>();
	
	public Resturant(String name, int numOfSeats, int maxCustomersPerDay, int maxWaitersInShift, int maxCustomersCuncurrentPerWaiter, int maxCustomersPerShift) {
		super();
		this.name = name;
		this.numOfSeats = numOfSeats;
		this.maxCustomersPerDay = maxCustomersPerDay;
		this.maxWaitersInShift = maxWaitersInShift;
		this.maxCustomersCuncurrentPerWaiter = maxCustomersCuncurrentPerWaiter;
		this.maxCustomersPerShift = maxCustomersPerShift;
		
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
	
	public void customerDone(Customer c){
		diningCustomers.remove(c);
	}

	public void addWaiter(Waiter newWaiter) {
		if(workingWaiters.size() < maxWaitersInShift){
			workingWaiters.add(newWaiter);
			newWaiter.start();
		}
		else waitingWaiters.add(newWaiter);
	}
	
	public void addCustomer(Customer newCustomer) {
		Waiter tmp = workingWaiters.get(0);
		if(diningCustomers.size() < numOfSeats){
			for(int i = 0; i < workingWaiters.size(); i++)
				if(tmp.getServedCustomerNum() > workingWaiters.get(i).getServedCustomerNum())
					tmp = workingWaiters.get(i);
			diningCustomers.add(newCustomer);
			tmp.addCustomer(newCustomer);
			newCustomer.setTheWaiter(tmp);
		}
		else waitingCustomers.add(newCustomer);
	}

	public void closeResturant() {
		isOpen = false;
		synchronized (/*dummyWaiter*/this) {
			/*dummyWaiter.*/notifyAll();
		}
	}

	public synchronized void notifyWaiter() {
		Waiter firstWaiter = waitingWaiters.poll();
		if (firstWaiter != null) {
			workingWaiters.add(firstWaiter);
			firstWaiter.start();
		}
	}
	
	public synchronized void notifyCustomer() {
		Waiter tmp = workingWaiters.get(0);
		if(diningCustomers.size() < numOfSeats){
			for(int i = 0; i < workingWaiters.size(); i++)
				if(tmp.getServedCustomerNum() > workingWaiters.get(i).getServedCustomerNum())
					tmp = workingWaiters.get(i);
			Customer firstCustomer = waitingCustomers.poll();
			diningCustomers.add(firstCustomer);
			tmp.addCustomer(firstCustomer);
		}
	}

	public void open() {
		System.out.println("Resturant: " + name + " is open");
		while (isOpen) {
			if (!waitingWaiters.isEmpty())
				if(workingWaiters.size() < maxWaitersInShift)
					notifyWaiter();
			if (!waitingCustomers.isEmpty())
				notifyCustomer();
			for(int i = 0; i < workingWaiters.size(); i++)
				if(workingWaiters.get(i).getServedCustomerNum() == maxCustomersPerShift){
					workingWaiters.get(i).endShift();
					int temp = i;
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								workingWaiters.get(temp).join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							workingWaiters.remove(temp);
						}
					});
				}
		}

		System.out.println(Calendar.getInstance().getTimeInMillis()
				+ " Resturant " + name + " is closing");
	}

}
