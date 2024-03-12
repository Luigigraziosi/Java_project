package journals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Paper {
	protected String journalName;
	protected String paperTitle;
	protected Map<String,Author> authors = new TreeMap<>();
	protected Journal journal;
	
	
	public Paper(String journalName, String paperTitle, Map<String,Author> authors, Journal journal) {
		super();
		this.journalName = journalName;
		this.paperTitle = paperTitle;
		this.authors = authors;
		this.journal = journal;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getPaperTitle() {
		return paperTitle;
	}
	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}
	
}
