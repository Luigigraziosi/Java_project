package operations;

import java.util.*;

public class Order {
	protected Customer customer;
	public Map<String, Integer> people = new TreeMap<>();
	
	
	public Order(Customer customer, Map<String, Integer> people) {
		this.customer = customer;
		this.people = people;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Map<String, Integer> getPeople() {
		return people;
	}
	public void setPeople(Map<String, Integer> people) {
		this.people = people;
	}
	


}
