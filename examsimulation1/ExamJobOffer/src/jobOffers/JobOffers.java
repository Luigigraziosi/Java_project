package jobOffers; 
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JobOffers  {
	List<String> skill = new LinkedList<>();
	Map<String,Position> positions = new TreeMap<>();
	Map<String,Candidate> candidates = new TreeMap<>();
	Map<String,Application> applications = new TreeMap<>();
	Map<String,Consulent> consulents = new TreeMap<>();


//R1
	public int addSkills (String... skills) {
		for (String s : skills) {
			if(!skill.contains(s)) {
				skill.add(s);
			}
		}
		return this.skill.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		if(positions.containsKey(position)) {
			throw new JOException("Position is just initializiated");
		}
		for (String s : skillLevels) {
			
			String[] sk = s.split(":");
			
			if(!skill.contains(sk[0])) {
				throw new JOException("Skill:" + sk[0] + " is not initializiated");
			}
			if(!sk[1].matches("[4-8]")) {
				throw new JOException("Level:" + sk[1] + " is not in the rage 4-8");
			}			
		}
		
		this.positions.put(position, new Position(position,this,skillLevels));

		return this.positions.get(position).getAverageLevel(skillLevels);
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		if(this.candidates.containsKey(name)) {
			throw new JOException("Candidates:" + name + " is just initializiated");
		}
		for (String s : skills) {	
			if(!skill.contains(s)) {
				throw new JOException("Skill:" + s + " is not initializiated");
			}
		}
		this.candidates.put(name, new Candidate(name, this, skills));
		return skills.length;
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		if (!this.candidates.containsKey(candidate)) {
			throw new JOException("Candidates:" + candidate + " is not initializiated");
		}
		for (String s : positions) {	
			if(!this.positions.containsKey(s)) {
				throw new JOException("Position: " + s + " is not initializiated");
			}
		}
		Candidate c = this.candidates.get(candidate);
		for(String p: positions) {
			if(!c.skill.keySet().containsAll(this.positions.get(p).skill.keySet())) {
				throw new JOException("Candidate has not regular for " + p );
			}
			this.applications.put("" + candidate + ":"+ p, new Application(this.candidates.get(candidate),this.positions.get(p),this));
		}
		
		return this.applications.keySet().stream()
				.filter(a -> this.applications.get(a).candidatename == this.candidates.get(candidate))
				.collect(Collectors.toList());
	} 
	
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
	    return this.applications.values().stream()
	            .collect(Collectors.groupingBy(a -> a.position.name,
	                    TreeMap::new,
	                    Collectors.mapping(a -> a.candidatename.name, Collectors.toList())));
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if(this.consulents.containsKey(name)) {
			throw new JOException("Consulent " + name + " is just initializiated");
		}
		if(!this.skill.containsAll(Stream.of(skills).collect(Collectors.toList()))) {
			throw new JOException("Skills is not initializiated");
		}
		this.consulents.put(name, new Consulent(name,this,skills));		
		return skills.length;
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		if(!this.consulents.containsKey(consultant)) {
			throw new JOException("Consulent " + consultant + " is not initializiated");
		}
		if(!this.candidates.containsKey(candidate)) {
			throw new JOException("Consulent " + candidate + " is not initializiated");
		}
		
		if(!this.consulents.get(consultant).skill.containsAll(this.candidates.get(candidate).skill.keySet())) {
			throw new JOException("Skills is not initializiated");
		}
		if( Stream.of(skillRatings)
		.collect(Collectors.mapping(
				s -> { String[] sk = ((String) s).split(":");
				int level = Integer.parseInt(sk[1]);
				return level;			
				}, Collectors.toList()))
		.stream()
		.filter(l -> l>4)
		.filter(l -> l >= 10)
		.anyMatch(l -> true)) {
			throw new JOException("Level of some skills out of range");
		}
		int averagelevel = 0;
		for (String s: skillRatings) {
			String[] sk = ((String) s).split(":");
			this.candidates.get(candidate).skill.put(sk[0],Integer.parseInt(sk[1]));
			averagelevel += Integer.parseInt(sk[1]);
		}
		
		return averagelevel/skillRatings.length;
		
	}
//R4
	public List<String> discardApplications() {	
		
		return this.applications.values().stream()
			    .filter(application ->
			        application.position.skill.keySet().stream()
			            .allMatch(skill ->
			                application.position.skill.get(skill) > application.candidatename.skill.get(skill)))
			    .map(application -> application.candidatename.name + ":" + application.position.name)
			    .sorted()
			    .collect(Collectors.toList());
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		return null;
	}
	

	
}

		
