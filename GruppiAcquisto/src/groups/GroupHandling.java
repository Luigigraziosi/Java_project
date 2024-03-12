package groups;
import java.util.stream.Collectors;
import java.util.*;


public class GroupHandling {
	protected Map<String, Product> products = new TreeMap<>();
	protected Map<String, Supplier> suppliers = new TreeMap<>();
	protected Map<String, Group> groups = new TreeMap<>();
	protected Map<String, Client> clients = new TreeMap<>();



	
//R1	
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if(this.products.containsKey(productTypeName)) {
			throw new GroupHandlingException("Product just Initializiated");
		}
		this.products.put(productTypeName, new Product(productTypeName,this));
		for (String s : supplierNames) {
			if(!this.suppliers.containsKey(s)) {
				this.suppliers.put(s, new Supplier(s,this));
			}
			this.suppliers.get(s).addProduct(productTypeName);
		}
		this.products.get(productTypeName).addSuplliers(supplierNames);;
	}
	
	public List<String> getProductTypes (String supplierName) {
		return this.suppliers.get(supplierName).products.keySet().stream().collect(Collectors.toList());
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if(!this.products.containsKey(productName)) {
			throw new GroupHandlingException("Product not Initializiated");
		}
		if(this.groups.containsKey(name)) {
			throw new GroupHandlingException("Group just Initializiated");
		}
		this.groups.put(name,new Group(name, productName, this));
		
		for (String s : customerNames) {
			if(!this.clients.containsKey(s)) {
				this.clients.put(s, new Client(s,this));
			}
			this.clients.get(s).addGroups(name);
			this.clients.get(s).addProduct(productName);
		}
		this.groups.get(name).addClient(customerNames);
	}
	
	public List<String> getGroups (String customerName) {
        return this.clients.get(customerName).groups.keySet().stream().collect(Collectors.toList());
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		for (String s : supplierNames) {
			if(!this.suppliers.containsKey(s)) {
				throw new GroupHandlingException("Suppliers not Initializiated");
			}
			if(!this.suppliers.get(s).products.containsKey(this.groups.get(groupName).nameTypeProduct)) {
				throw new GroupHandlingException("Suppliers have not group's product.");
			}
		}
		this.groups.get(groupName).addSuplliers(supplierNames);
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		if(!this.groups.get(groupName).suppliers.containsKey(supplierName)) {
			throw new GroupHandlingException("Suppliers is not in group's suppliers");
		}
		this.groups.get(groupName).binds.add(new Bind(groupName, supplierName, price, this));
		this.suppliers.get(supplierName).nofferte++;
	}
	
	public String getBids (String groupName) {
        return this.groups.get(groupName).binds.stream()
        		.sorted(Comparator.comparing(Bind::getPrice).thenComparing(Comparator.comparing(Bind::getSupplierName)))
        		.map(b -> ""+ b.supplierName +":"+ b.price)
        		.collect(Collectors.joining(","));
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		if(!this.groups.get(groupName).clients.containsKey(customerName)) {
			throw new GroupHandlingException("Client is not in group's clients");
		}
		if(this.groups.get(groupName).binds.stream()
				.filter(b -> b.supplierName ==supplierName)
				.count() == 0) {
			throw new GroupHandlingException("Supplier doesn't do a bind");
		}
		 this.groups.get(groupName).binds.stream()
         		.filter(b -> b.supplierName.equals(supplierName))
         		.forEach(b -> {
	             b.voto++;
	             this.suppliers.get(supplierName).voto++;});		
	}
	
	public String  getVotes (String groupName) {
        return this.suppliers.values().stream()
        		.filter(s -> s.voto != 0)
        		.map(s -> "" + s.name + ":" + s.voto)
        		.collect(Collectors.joining(","));
	}
	
	public String getWinningBid (String groupName) {
        return this.groups.get(groupName).binds.stream()
        		.sorted(Comparator.comparing(b -> b.price))
        		.collect(Collectors.maxBy(Comparator.comparing(b -> b.voto)))
        		.map(b -> ""+ b.supplierName +":"+ b.voto)
        		.orElse(null);
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
		return this.groups.values().stream()
	            .flatMap(g -> g.binds.stream())
	            .collect(Collectors.groupingBy(
	                    b -> b.grouph.groups.get(b.groupName).nameTypeProduct,
	                    TreeMap::new,
	                    Collectors.mapping(Bind::getPrice, Collectors.maxBy(Comparator.naturalOrder()))))
	            .entrySet().stream()
	            .filter(entry -> entry.getValue().isPresent())
	            .collect(Collectors.toMap(
	                    Map.Entry::getKey,
	                    entry -> entry.getValue().get(),
	                    (v1, v2) -> v2, // Seleziona l'ultimo valore in caso di duplicati
	                    TreeMap::new));  
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
        return this.suppliers.values().stream()
	            .filter(s -> s.nofferte>0)
        		.collect(Collectors.groupingBy(s ->s.nofferte,
        				() -> new TreeMap<>(Collections.reverseOrder()),
        				Collectors.mapping(Supplier::getName, Collectors.toList())));
        }
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
		 return this.clients.values().stream()
		            .flatMap(c -> c.groups.values().stream())
		            .flatMap(g -> g.binds.stream())
		            .collect(Collectors.toMap(
		                    bind -> bind.grouph.groups.get(bind.groupName).nameTypeProduct,
		                    bind -> 1L,
		                    Long::sum,
		                    TreeMap::new
		            ));		
		 }
}
