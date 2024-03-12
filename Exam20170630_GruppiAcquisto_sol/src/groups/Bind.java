package groups;

public class Bind {
	protected String groupName;
	protected String supplierName;
	protected int price;
	protected GroupHandling grouph;
	protected int voto = 0;
	
	public Bind(String groupName, String supplierName, int price, GroupHandling grouph) {
		this.groupName = groupName;
		this.supplierName = supplierName;
		this.price = price;
		this.grouph = grouph;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public GroupHandling getGrouph() {
		return grouph;
	}

	public void setGrouph(GroupHandling grouph) {
		this.grouph = grouph;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}
	
}
