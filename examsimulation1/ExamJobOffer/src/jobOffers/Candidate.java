package jobOffers;

import java.util.Map;
import java.util.TreeMap;

public class Candidate {
	protected String name;
	protected Map<String, Integer> skill = new TreeMap<>();
;
	protected JobOffers joboffers;

	
	public Candidate(String name) {
		this.name = name;
	}
	public Candidate(String name, JobOffers joboffers, String... skills) {
		this.name = name;
		this.joboffers = joboffers;
		for (String s : skills) {
			this.skill.put(s,0);
		}
	}
	
}
