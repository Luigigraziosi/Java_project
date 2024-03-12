package groups;

import java.util.Map;
import java.util.TreeMap;

public class Client {
	protected Map<String, Group> groups = new TreeMap<>();
	protected Map<String, Product> products = new TreeMap<>();

	protected String name;
	protected String nameTypeProduct;
	protected GroupHandling grouph;
	
	public Client(Map<String, Group> groups, String name, String nameTypeProduct, GroupHandling grouph) {
		super();
		this.groups = groups;
		this.name = name;
		this.nameTypeProduct = nameTypeProduct;
		this.grouph = grouph;
	}
	
	public Client(String name, GroupHandling grouph) {
		super();
		this.name = name;
		this.grouph = grouph;
	}
	
	public void addGroups(String groupsName) {
		this.groups.put(groupsName, this.grouph.groups.get(groupsName));
	}
	public void addProduct(String productName) {
		this.products.put(productName, this.grouph.products.get(productName));
	}
}
