package finallPro.model;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Waiter extends Thread{
	private static Logger theLogger = Logger.getLogger("myLogger");
	private Resturant theResturant;
	private String name;
	private Vector<Customer> allCustomers = new Vector<Customer>();
	
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

	private void bringCheck() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Waiter " + name + ": Going to bring the check");
		Thread.sleep((int) (Math.floor(Math.random() * 10000) + 1000));
	}

	private void bringFood() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Waiter " + name + ": Going to bring the food");
		Thread.sleep((int) (Math.floor(Math.random() * 10000) + 1000));
	}

	private void takeOrder() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Waiter " + name + ": Going to take the order");
		Thread.sleep((int) (Math.floor(Math.random() * 10000) + 1000));
	}

	private void getMenu() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Waiter " + name + ": Bring menu to customer");
		Thread.sleep((int) (Math.floor(Math.random() * 10000) + 1000));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			getMenu();
			takeOrder();
			bringFood();
			bringCheck();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}