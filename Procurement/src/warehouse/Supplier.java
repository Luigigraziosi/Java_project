package warehouse;

import java.util.stream.Collectors;
import java.util.*;

public class Supplier {
	protected String name;
	protected String codice;
	protected Warehouse magazzino;
	protected Map<String,Product> products = new TreeMap<>();
	protected Map<String,Order> orders = new TreeMap<>();

	
	public Supplier(String name, String codice, Warehouse magazzino) {
		this.name = name;
		this.codice = codice;
		this.magazzino = magazzino;
	}
	
	public String getCodice(){
		// TODO: completare!
		return this.codice;
	}

	public String getNome(){
		// TODO: completare!
		return this.name;
	}
	
	public void newSupply(Product product){
		// TODO: completare!
		this.products.put(product.code, product);
		product.suppliers.put(this.codice, this);
	}
	
	public List<Product> supplies(){
		// TODO: completare!
		return this.products.values().stream()
				.sorted(Comparator.comparing(products ->  products.description))
				.collect(Collectors.toList());
	}
}
