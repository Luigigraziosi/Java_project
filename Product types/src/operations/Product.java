package operations;

public class Product {
	protected String productType;
	protected int n;
	protected int price;
	protected Operations operations;
	
	public Product(String productType, int n, int price, Operations operations) {
		this.productType = productType;
		this.n = n;
		this.price = price;
		this.operations = operations;
	}
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


}
