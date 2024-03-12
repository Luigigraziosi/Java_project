package groups;

import java.util.Map;
import java.util.TreeMap;

public class Product {
	protected String name;
	protected Map<String, Supplier> suppliers = new TreeMap<>();
	protected GroupHandling group;
	
	public Product(String name, GroupHandling group) {
		this.name = name;
		this.group = group;
	}
	
	public void addSuplliers(String... supplierNames) {
		for (String s: supplierNames) {
			this.suppliers.put(s, this.group.suppliers.get(s));
		}
	}
}
