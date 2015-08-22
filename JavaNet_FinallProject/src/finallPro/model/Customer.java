package finallPro.model;

import java.util.concurrent.ThreadFactory;

public class Customer implements ThreadFactory{
	private String name;
	private String whileWaiting;
	
	public Customer(String name, String whileWaiting) {
		super();
		this.name = name;
		this.whileWaiting = whileWaiting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWhileWaiting() {
		return whileWaiting;
	}

	public void setWhileWaiting(String whileWaiting) {
		this.whileWaiting = whileWaiting;
	}

	@Override
	public Thread newThread(Runnable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
