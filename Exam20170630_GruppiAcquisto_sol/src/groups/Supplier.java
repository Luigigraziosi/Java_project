package groups;

import java.util.Map;
import java.util.TreeMap;

public class Supplier {
	protected Map<String, Product> products = new TreeMap<>();
	protected String name;
	protected GroupHandling group;
	protected int voto = 0;
	protected int nofferte = 0;
	
	public Supplier (String name, GroupHandling group ) {
		this.name = name;
		this.group = group;
	}
	
	public Map<String, Product> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GroupHandling getGroup() {
		return group;
	}

	public void setGroup(GroupHandling group) {
		this.group = group;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public void addProduct(String productTypeName) {
		this.products.put(productTypeName, this.group.products.get(productTypeName));
	}

	public int getNofferte() {
		return nofferte;
	}

	public void setNofferte(int nofferte) {
		this.nofferte = nofferte;
	}
	
	
}
