package journals;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Journals {
	Map<String, Journal> journals = new TreeMap<>();
	Map<String,Author> authors = new TreeMap<>();
	
	//R1 
	/**
	 * inserts a new journal with name and impact factor. 
	 * 
	 * @param name	name of the journal
	 * @param impactFactor relative impact factor
	 * @return  the number of characters of the name
	 * @throws JException if the journal (represented by its name) already exists
	 */
	public int addJournal (String name, double impactFactor) throws JException {
		if(this.journals.containsKey(name)) {
			throw new JException();
		}
		this.journals.put(name, new Journal(name, impactFactor, this));
		return name.length();
	}

	/**
	 * retrieves the impact factor of the journal indicated by the name
	 * 
	 * @param name the journal name
	 * @return the journal IF
	 * @throws JException if the journal does not exist
	 */
	public double getImpactFactor (String name) throws JException {
		if(!this.journals.containsKey(name)) {
			throw new JException();
		}		
		
		return this.journals.get(name).impactFactor;
	}

	/**
	 * groups journal names by increasing impact factors. 
	 * Journal names are listed in alphabetical order
	 * 
	 * @return the map of IF to journal
	 */
	public SortedMap<Double, List<String>> groupJournalsByImpactFactor () {
		return this.journals.values().stream()
				.collect(Collectors.groupingBy(j -> j.getImpactFactor(),
						TreeMap::new,
						Collectors.mapping(j -> j.getName(), Collectors.toList())));
	}

	//R2
	/**
	 * adds authors. 
	 * Duplicated authors are ignored.
	 * 
	 * @param authorNames names of authors to be added
	 * @return the number of authors entered so far
	 */
	public int registerAuthors (String... authorNames) {
		int counter = 0;
		for(String author : authorNames) {
			if(!this.authors.containsKey(author)) {
				this.authors.put(author, new Author(author, 0, this));
				counter++;
			}
		}
		return this.authors.size();
	}
	
	/**
	 * adds a paper to a journal. 
	 * The journal is indicated by its name; 
	 * the paper has a title that must be unique in the specified journal,
	 * the paper can have one or more authors.
	 * 
	 * @param journalName
	 * @param paperTitle
	 * @param authorNames
	 * @return the journal name followed by ":" and the paper title.
	 * @throws JException if the journal does not exist or the title is not unique within the journal or not all authors have been registered
	 */
	public String addPaper (String journalName, String paperTitle, String... authorNames) throws JException {
		Map<String,Author> authorsN = new TreeMap<>();
		int counter = 0;
		if(!this.journals.containsKey(journalName)) {
			throw new JException();			
		}
		if(this.journals.get(journalName).papers.containsKey(paperTitle)) {
			throw new JException();			
		}
		for(String author : authorNames) {
			if (!this.authors.containsKey(author)) {
				throw new JException();			
			}
			else {
				authorsN.put(author, this.authors.get(author));
				counter++;
			}
		}
		if (counter == authorNames.length) {
			this.journals.get(journalName).papers.put(paperTitle, 
							new Paper(journalName, paperTitle, authorsN, this.journals.get(journalName)));
			for(String author : authorNames) {
				this.authors.get(author).papers.put(paperTitle, this.journals.get(journalName).papers.get(paperTitle));
				this.authors.get(author).impactFactor += this.getImpactFactor(journalName);
			}
		}
		
		return "" + journalName + ":" + paperTitle ;
	}
	
	/**
	 * gives the number of papers for each journal. 
	 * Journals are sorted alphabetically. 
	 * Journals without papers are ignored.
	 * 
	 * @return the map journal to count of papers
	 */
	public SortedMap<String, Integer> giveNumberOfPapersByJournal () { //serve toMap
		return this.journals.values().stream()
				.filter(j -> j.papers.size() > 0)
				.collect(Collectors.toMap(j -> j.name,
						j -> j.papers.size(),
						(a,b) -> a,
						TreeMap:: new));
	}
	
	//R3
	/**
	 * gives the impact factor for the author indicated.
	 * The impact factor of an author is obtained by adding 
	 * the impact factors of his/her papers. 
	 * The impact factor of a paper is equal to that of the 
	 * journal containing the paper.
	 * If the author has no papers the result is 0.0.
	 *
	 * @param authorName
	 * @return author impact factor
	 * @throws JException if the author has not been registered
	 */
	public double getAuthorImpactFactor (String authorName) throws JException {
		if(!this.authors.containsKey(authorName)) {
			throw new JException();
		}
		return this.authors.get(authorName).impactFactor;
	}
	
	/**
	 * groups authors (in alphabetical order) by increasing impact factors.
	 * Authors without papers are ignored.
	 * 
	 * @return the map IF to author list
	 */
	
	public SortedMap<Double, List<String>> getImpactFactorsByAuthors () {
		return this.authors.values().stream()
				.filter(a -> a.impactFactor > 0)
				.collect(Collectors.groupingBy(a -> a.impactFactor,
						TreeMap::new,
						Collectors.mapping(a->a.name, Collectors.toList())));
    }
	
	//R4 
	/**
	 * gives the number of papers by author; 
	 * authors are sorted alphabetically. 
	 * Authors without papers are ignored.
	 * 
	 * @return
	 */
	public SortedMap<String, Integer> getNumberOfPapersByAuthor() {
		return this.authors.values().stream()
				.filter(a -> a.papers.size() > 0)
				.collect(Collectors.toMap(a->a.name,
						a-> a.papers.size(), 
						(a,b) -> a, 
						TreeMap::new));
	}
	
	/**
	 * gives the name of the journal having the largest number of papers.
	 * If the largest number of papers is common to two or more journals 
	 * the result is the name of the journal which is the first in 
	 * alphabetical order.
	 * 
	 * @return journal with more papers
	 */
	public String getJournalWithTheLargestNumberOfPapers() {
		return "" + this.journals.keySet().stream()
				.max(Comparator.comparing(j -> this.journals.get(j).papers.size()))
				.orElse(null) + ":" + this.journals.get(this.journals.keySet().stream()
				.max(Comparator.comparing(j -> this.journals.get(j).papers.size()))
				.orElse(null)).papers.size();
	}

//	public String getJournalWithTheLargestNumberOfPapers() {
//		return this.journals.keySet().stream()
//				.max(Comparator.comparing(j -> this.journals.get(j).papers.size()))
//				.orElse(null);
//	}

}

