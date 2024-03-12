package warehouse;

public class Order {
	protected String code;
	protected Product product;
	protected Supplier supplier;
	protected Warehouse magazzino;
	protected int quantity;
	protected boolean delivered = false;
		
	public Order(String code, Product product, Supplier supplier, Warehouse magazzino, int quantity) {
		this.code = code;
		this.product = product;
		this.supplier = supplier;
		this.magazzino = magazzino;
		this.quantity = quantity;
		this.supplier.orders.put(code, this);
	}
	
	public int getquantity() {
		return this.quantity;
	}

	public String getCode(){
		// TODO: Completare!
		return this.code;
	}
	
	public boolean delivered(){
		// TODO: Completare!
		return delivered;
	}

	public void setDelivered() throws MultipleDelivery {
		// TODO: Completare!
		if(this.delivered) {
			throw new MultipleDelivery();
		}
		this.delivered = true;
		this.product.quantity += this.quantity;
	}
	
	public String toString(){
		// TODO: Completare!
		return "Order " + this.code +" for " + this.quantity+" of "+ this.product.code +
				" : " + this.product.getDescription() + " from " +this.supplier.name;
	}
}

