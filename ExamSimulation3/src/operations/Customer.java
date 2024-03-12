package operations;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Customer {
	protected String name;
	protected int sconto = 0;
	public Map<String, Integer> orders = new TreeMap<>();
	protected Operations operations;
	protected int maxspesa = 0;

	
	
	public Customer(String name, int sconto, Map<String, Integer> orders, Operations operations) {
		this.name = name;
		this.sconto = sconto;
		this.orders = orders;
		this.operations = operations;

	}
	public Customer(String name, int sconto,Operations operations) {
		this.name = name;
		this.sconto = sconto;
		this.operations = operations;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSconto() {
		return sconto;
	}
	public void setSconto(int sconto) {
		this.sconto = sconto;
	}
	
	}
