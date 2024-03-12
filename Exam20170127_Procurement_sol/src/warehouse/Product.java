package warehouse;

import java.util.*;
import java.util.stream.Collectors;

public class Product {
	protected String code;
	protected String description;
	protected int quantity;
	protected Map<String, Supplier> suppliers = new TreeMap<>();
	protected Warehouse magazzino;

	
	public Product(String code, String description, Warehouse magazzino) {
		this.code = code;
		this.description = description;
		this.magazzino = magazzino;
	}

	public String getCode(){
		// TODO: completare!
		return this.code;
	}

	public String getDescription(){
		// TODO: completare!
		return this.description;
	}
	
	public void setQuantity(int quantity){
		// TODO: completare!
		this.quantity = quantity;
	}

	public void decreaseQuantity(){
		// TODO: completare!
		this.quantity -=1;
	}

	public int getQuantity(){
		// TODO: completare!
		return this.quantity;
	}

	public List<Supplier> suppliers(){
		// TODO: completare!
		return this.suppliers.values().stream()
				.sorted(Comparator.comparing(s -> s.getNome()))
				.collect(Collectors.toList());
	}

	public List<Order> pendingOrders(){
		// TODO: completare
		return this.magazzino.orders.values().stream()
				.filter(o -> !o.delivered())
				.filter(o -> o.product == this)
				.sorted(Comparator.comparing(Order::getquantity).reversed())
				.toList();
	}
}
