package journals;

import java.util.Map;
import java.util.TreeMap;

public class Journal {
	protected String name;
	protected double impactFactor;
	protected Journals giornale;
	protected Map<String, Paper> papers = new TreeMap<>();
	
	public Journal(String name, double impactFactor, Journals giornale) {
		this.name = name;
		this.impactFactor = impactFactor;
		this.giornale = giornale;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getImpactFactor() {
		return impactFactor;
	}
	public void setImpactFactor(double impactFactor) {
		this.impactFactor = impactFactor;
	}
	
	
}
