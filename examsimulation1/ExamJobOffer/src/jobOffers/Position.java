package jobOffers;


import java.util.Map;
import java.util.TreeMap;

public class Position {
	protected String name;
	protected Map<String, Integer> skill = new TreeMap<>();
	protected JobOffers joboffers;
	
	public Position(String name) {
		super();
		this.name = name;
		this.skill = new TreeMap<>();

	}
	
	public  Position (String position, JobOffers joboffers, String... skillLevels) {
		this.name = position;
		for (String s : skillLevels) {
				String[] sk = s.split(":");
				this.skill.put(sk[0], Integer.parseInt(sk[1]));
		}
		this.joboffers = joboffers;
	}
	
	public int getAverageLevel(String... skillLevels) {
		int averagelevel = 0;
		for (String s : skillLevels) {
			String[] sk = s.split(":");
			averagelevel += Integer.parseInt(sk[1]);
		}
		return averagelevel/skillLevels.length;

	}
	
	

}
