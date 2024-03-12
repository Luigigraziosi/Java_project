package journals;

import java.util.Map;
import java.util.TreeMap;

public class Author {
	protected String name;
	protected double impactFactor = 0;
	protected Map<String, Paper> papers = new TreeMap<>();
	protected Map<String, Paper> journals = new TreeMap<>();

	
	public Author(String name) {
		super();
		this.name = name;
	}
	
	public Author(String name, double impactFactor) {
		super();
		this.name = name;
		this.impactFactor = impactFactor;
	}
	
	public Author(String name, double impactFactor, Paper paper) {
		super();
		this.name = name;
		this.impactFactor = impactFactor;
	}
	
	public Author(String name, double impactFactor, Journals Journal) {
		super();
		this.name = name;
		this.impactFactor = impactFactor;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
