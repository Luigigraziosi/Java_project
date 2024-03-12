package operations;


import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.*;

public class Operations {
	protected Map<String,Product> products = new TreeMap<>();
	protected Map<String,Customer> customers = new TreeMap<>();
//R1
	
	/**
	* adds a new product type with name, number of products and price. 
	* 
	* @param productType name of the product type (unique labels that contain no spaces);
	* @param n number of products belonging to the product type;
	* @param price the product price, which is the same for a single product item.
	* @return the overall price of all the products of the same product type.
	* @throws OException if the product type is duplicated.
	*/
	public int addProductType(String productType, int n, int price) throws OException {
		if(products.containsKey(productType)) {
			throw new OException(productType);
		}
		
		products.put(productType,new Product(productType,n, price, this));
		return n*price;
	}

	/**
	* gives the number of products belonging to the given productType.
	* 
	* @param productType name of the product type.
	* @return the number of products belonging to the product type.
	* @throws OException if the productType does not exist.
	*/
	public int getNumberOfProducts(String productType) throws OException {
		if(!products.containsKey(productType)) {
			throw new OException(productType);
		}
		return products.get(productType).getN();
	}

	/**
	* groups the product types by increasing price. 
	* 
	* @param productType name of the product type.
	* @return the sorted map of products grouped by price. The product types with same price are listed in alphabetical order.
	*/
	public SortedMap<Integer, List<String>> groupingProductTypesByPrices() {
		return this.products.values().stream()
		.collect(Collectors.groupingBy(p-> p.getPrice(), 
				TreeMap::new, 
				Collectors.mapping(p->p.productType, Collectors.toList())));		
		}

//R2
	
	/**
	* adds a discount to a customer (the same customer can receive more than one discount).
	* 
	* @param customer name of the customer;
	* @param discount amount of the discount.
	* @return the total discount received by the customer.
	*/
	public int addDiscount(String customer, int discount) {
		if(customers.containsKey(customer)){
			this.customers.get(customer).sconto += discount;
		}
		else {
			customers.put(customer, new Customer(customer,discount,this));
		}
		return this.customers.get(customer).sconto ;
	}
	
	/**
	* places customers' orders. 
	* If not all the number of products requested are available the result is 0; the customer doesn't buy anything.
	* If the order is placed:
	*  - reduces the number of available products by the number of products requested by the customer order;
	*  - reduces the available discount by the requested discount.
	*  
	* @param customer the name of the customer.
	* @param ptpn the products with the relative quantity (example: "tableX:2 loungeChair:1").
	* @param discount the discount requested.
	* @return the total order price (the sum of the prices of the products minus the discount (if greater than 0)).
	* @throws OException if the requested discount exceeds the available discount for the customer.
	*/
	public int customerOrder(String customer, String ptpn, int discount) throws OException {
		String[] ordine = ptpn.split(" ");
		int price = 0;
		int scontototale = customers.get(customer).getSconto();
		int prodotti = 0;
		for(String o: ordine) {
			String[] cose = o.split(":");
			if(Integer.valueOf(cose[1]) <= products.get(cose[0]).n) {
				prodotti++;
			}
			else return 0;
		}
		if(prodotti == ordine.length) {
			for(String o: ordine) {
				String[] cose = o.split(":");
				customers.get(customer).orders.put(cose[0], null);
				products.get(cose[0]).n -= Integer.parseInt(cose[1]);
				price += (Integer.parseInt(cose[1])*products.get(cose[0]).getPrice());
			}
		}
		if( scontototale >= discount ) {
			customers.get(customer).sconto = scontototale-discount;
		}
		else throw new OException(ptpn);
		if(this.customers.get(customer).maxspesa < price-discount ) {
			this.customers.get(customer).maxspesa = price-discount;
		}
		return price-discount;
	}

	/**
	* gets the available discount for the customer as the difference between the total discount received by the
	* customer and the sum of the discounts already used.
	*  
	* @param customer the name of the customer.
	* @return the available discount for the customer (0 if there is no discount that can be used).
	*/
	public int getDiscountAvailable(String customer) {
		return this.customers.get(customer).getSconto();
	}

//R3
	/**
	* allows a customer to give a score in the range from 4 to 10 to the product type.
	* 
	* @param customer the name of the customer.
	* @param productType the name of the product type.
	* @param score the score assigned by the customer to the product type.
	* @return the overall number of product types scores given by the customer.
	* @throws OException if the customer has not purchased the indicated product type, has already given a score to the product type, or the score is out of range.
	*/
	public int evalByCustomer(String customer, String productType, int score) throws OException {
		if (!this.customers.get(customer).orders.containsKey(productType)) {
			throw new OException("Non è presente il prodotto");
		}
		if (score < 4 || score > 10) {
			throw new OException("Score out of range");
		}
		if (this.customers.get(customer).orders.get(productType) != null) {
			throw new OException("Gia valutato");
		}
		this.customers.get(customer).orders.put(productType, score);
		
		return (int) this.customers.get(customer).orders.values().stream()
				.filter(o -> o != null)
				.count();
		}

	/**
	* gets the score assigned by the customer to the indicated type of product.
	* 
	* @param customer the name of the customer.
	* @param productType the name of the product type.
	* @return the score assigned by the customer to the indicated type of product.
	* @throws OException if the customer has not assigned a score to that product type.
	*/
	public int getScoreFromProductType(String customer, String productType) throws OException {
		if (this.customers.get(customer).orders.get(productType) == null) {
			throw new OException("Gia valutato");
		}
		return this.customers.get(customer).orders.get(productType);
	}

	/**
	* groups customers by increasing scores relating to the type of product indicated.
	* 
	* @param productType the name of the product type.
	* @return the the sorted map of customers grouped by scores and listed in alphabetical order.
	*/
	public SortedMap<Integer, List<String>> groupingCustomersByScores(String productType) {
		return this.customers.keySet().stream()
				.filter(c -> customers.get(c).orders.get(productType) != null)
				.sorted(Comparator.naturalOrder())
				.collect(Collectors.groupingBy(c -> customers.get(c).orders.get(productType),
						TreeMap::new,Collectors.toList()));
	}

//R4
	
	/**
	* groups the customers by increasing number of products purchased. 
	* Customers with no orders are not reported.
	* 
	* @return the the sorted map of customers grouped by number of products purchased and listed in alphabetical order.
	*/
	public SortedMap<Integer, List<String>> groupingCustomersByNumberOfProductsPurchased() {
		return this.customers.keySet().stream()
	            .filter(c -> this.customers.get(c).orders.size() > 0)
				.collect(Collectors.groupingBy(c -> this.customers.get(c).orders.size(),
						TreeMap::new,
						Collectors.mapping(c->c, Collectors.toList())));
	}
	
	/**
	* provides the largest expense in an individual order for each customer.
	* Customers with no orders are not reported.
	* 
	* @return the the sorted map of customers and their largest expenses in increasing order.
	*/
	public SortedMap<String, Integer> largestExpenseForCustomer() {
		return this.customers.keySet().stream()
				.filter(c -> this.customers.get(c).maxspesa != 0)
				.collect(Collectors.toMap(c -> c, 
						c-> this.customers.get(c).maxspesa,
						(v1, v2) -> v1, 
						 TreeMap::new));
				
	}

}
