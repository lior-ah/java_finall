package finallPro.model;

import java.util.logging.Logger;

public class Resturant {
	private static Logger theLogger = Logger.getLogger("myLogger");
	private String name;
	private int numOfSeats;
	private int maxCustomersPerDay;
	
	public Resturant(String name, int numOfSeats, int maxCustomersPerDay) {
		super();
		this.name = name;
		this.numOfSeats = numOfSeats;
		this.maxCustomersPerDay = maxCustomersPerDay;
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

}
