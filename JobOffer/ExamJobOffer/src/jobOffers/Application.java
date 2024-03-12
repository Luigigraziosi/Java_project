package jobOffers;


public class Application {
	protected String name;
	protected Candidate candidatename;
	protected Position position;
	protected JobOffers joboffers;

	
	
	public Application( Candidate candidatename, Position position, JobOffers joboffers) {
		this.candidatename = candidatename;
		this.position = position;
		this.joboffers = joboffers;
		this.name = "" + candidatename + ":"+ position;
	}
	
	

}
