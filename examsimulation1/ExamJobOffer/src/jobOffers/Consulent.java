package jobOffers;

import java.util.LinkedList;
import java.util.List;

public class Consulent {
	protected String name;
	protected List<String> skill = new LinkedList<>();
	protected JobOffers joboffers;

	public Consulent(String name, JobOffers joboffers, String... skill) {
		this.joboffers = joboffers;
		this.name = name;
		for(String s: skill) {
			this.skill.add(s);
		}
	}
	
	

}
