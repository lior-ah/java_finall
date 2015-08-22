package finallPro.model;

public class Customer {
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

}
