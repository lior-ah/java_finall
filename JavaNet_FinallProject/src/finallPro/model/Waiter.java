package finallPro.model;

public class Waiter {
	private int maxWaitersInShift;
	private int maxCustomersCuncurrentPerWaiter;
	private int maxCustomersPerShift;
	
	public Waiter(int maxWaitersInShift, int maxCustomersCuncurrentPerWaiter, int maxCustomersPerShift) {
		super();
		this.maxWaitersInShift = maxWaitersInShift;
		this.maxCustomersCuncurrentPerWaiter = maxCustomersCuncurrentPerWaiter;
		this.maxCustomersPerShift = maxCustomersPerShift;
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