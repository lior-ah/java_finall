package finallPro.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Waiter {
	private static Logger theLogger = Logger.getLogger("myLogger");
	private String name;
	private int maxWaitersInShift;
	private int maxCustomersCuncurrentPerWaiter;
	private int maxCustomersPerShift;
	
	public Waiter(String name, int maxWaitersInShift, int maxCustomersCuncurrentPerWaiter, int maxCustomersPerShift) {
		super();
		this.name = name;
		this.maxWaitersInShift = maxWaitersInShift;
		this.maxCustomersCuncurrentPerWaiter = maxCustomersCuncurrentPerWaiter;
		this.maxCustomersPerShift = maxCustomersPerShift;
		
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
	
	
}