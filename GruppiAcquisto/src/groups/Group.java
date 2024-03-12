package groups;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Group {
	protected Map<String, Client> clients = new TreeMap<>();
	protected Map<String, Supplier> suppliers = new TreeMap<>();

	protected String name;
	protected String nameTypeProduct;
	protected GroupHandling grouph;
	protected List<Bind> binds = new LinkedList<>();

	
	public Group(Map<String, Client> clients, String name, String nameTypeProduct, GroupHandling grouph) {
		super();
		this.clients = clients;
		this.name = name;
		this.nameTypeProduct = nameTypeProduct;
		this.grouph = grouph;
	}
	public Group(String name, String nameTypeProduct, GroupHandling grouph) {
		super();
		this.name = name;
		this.nameTypeProduct = nameTypeProduct;
		this.grouph = grouph;
	}
	
	public void addClient(String... customerNames) {
		for (String s: customerNames) {
			this.clients.put(s, this.grouph.clients.get(s));
		}
	}
	public void addSuplliers(String... supplierNames) {
		for (String s: supplierNames) {
			this.suppliers.put(s, this.grouph.suppliers.get(s));
		}
	}
	
}
