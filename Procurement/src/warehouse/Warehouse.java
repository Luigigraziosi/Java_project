package warehouse;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Warehouse {
	protected int ordn = 0;
	protected Map<String,Product> products = new TreeMap<>();
	protected Map<String,Supplier> suppliers = new TreeMap<>();
	protected Map<String,Order> orders = new TreeMap<>();


	public Product newProduct(String code, String description){
		// TODO: completare
		this.products.put(code, new Product(code,description,this));
		return this.products.get(code);
	}
	
	public Collection<Product> products(){
		// TODO: completare
		return this.products.values();
	}

	public Product findProduct(String code){
		// TODO: completare
		return this.products.get(code);
	}

	public Supplier newSupplier(String code, String name){
		// TODO: completare
		this.suppliers.put(code, new Supplier(name, code, this));
		return this.suppliers.get(code);
	}
	
	public Supplier findSupplier(String code){
		// TODO: completare
		return this.suppliers.get(code);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		// TODO: completare
		if(!prod.suppliers.containsValue(supp)) {
			throw new InvalidSupplier();
		}
		ordn +=1;
		String ordN = "ORD" + String.valueOf(ordn);
		this.orders.put(ordN, new Order(ordN, prod, supp, this, quantity));
		return this.orders.get(ordN);
	}

	public Order findOrder(String code){
		// TODO: completare
		return this.orders.get(code);
	}
	
	public List<Order> pendingOrders(){
		// TODO: completare
		return this.orders.values().stream()
				.filter(o -> !o.delivered())
				.sorted(Comparator.comparing(o -> o.product.getCode()))
				.collect(Collectors.toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
	    return this.orders.values().stream()
	    		.collect(Collectors.groupingBy(o -> o.product.getCode()));
	}
	
	public Map<String,Long> orderNBySupplier(){
		
	    return this.orders.values().stream()
	    		.filter(o -> o.delivered())
	    		.collect(Collectors.groupingBy(o-> o.supplier.name,
	    				TreeMap::new,
	    				Collectors.counting()));
	}
	
	public List<String> countDeliveredByProduct(){
	    return this.orders.values().stream()
	    		.filter(o -> o.delivered())
	    		.collect(Collectors.groupingBy(o -> o.product.code,
	    				TreeMap::new,
	    				Collectors.counting()))
	    		.entrySet().stream()
	    		.map(entry -> entry.getKey() + " - " + entry.getValue())
	    		.collect(Collectors.toList());
	}
}
