package finallPro.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
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

}
