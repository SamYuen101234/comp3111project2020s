package comp3111.coursescraper;

import org.apache.commons.lang3.ArrayUtils;

public class Course {
	private static final int DEFAULT_MAX_SECTION = 80;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private Section [] sections;
	private int numSections;
	private boolean isCC;
	private boolean hasLabsOrTutorial;
	
	/**
	 * Default constructor for Course, 
	 * sections array of size DEFAULT_MAX_SECTION will be created, 
	 * numSections will be initialized to 0,
	 * hasLabsOrTutorial will be initialized to false.
	 */
	public Course() {
		sections = new Section[DEFAULT_MAX_SECTION];
		for (int i = 0; i < DEFAULT_MAX_SECTION; i++) sections[i] = null;
		numSections = 0;
		hasLabsOrTutorial = false;
	}
	
	/**
	 * Method to add a section
	 * @param s The section to be added to course
	 */
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;
		sections[numSections++] = s.clone();
	}
	
	/**
	 * Method to get section of index i
	 * @param i The index of section to return
	 * @return section of index i
	 */
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}

	/**
	 * Method to get title of the course
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method to set title of the course
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

//	/**
//	 * 
//	 * @return the description
//	 */
//	public String getDescription() {
//		return description;
//	}

//	/**
//	 * @param description the description to set
//	 */
//	public void setDescription(String description) {
//		this.description = description;
//	}

	/**
	 * Method to get exclusion
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * Method to set exclusion
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * Method to get numSections
	 * @return the numSections
	 */
	public int getNumSections() {
		return numSections;
	}

	/**
	 * Method to set numSections
	 * @param numSections the numSections to set
	 */
	public void setNumSections(int numSections) {
		this.numSections = numSections;
	}
	
	/**
	 * Method to set isCC
	 * @param bool True if the course is a CC, otherwise false
	 */
	public void isCC(boolean bool) {
		this.isCC = bool;
	}
	
	/**
	 * Method to get isCC
	 * @return isCC True is the course is a CC, otherwise false
	 */
	public boolean checkCC() {
		return this.isCC;
	}
	
	/**
	 * Method to get hasLabsOrTurotial
	 * @return hasLabsOrTutorial True is the course has labs or tutorials, otherwise false
	 */
	public boolean hasLabsOrTutorial() {
		return this.hasLabsOrTutorial;
	}
	
	/**
	 * Method to set hasLabsOrTurotial to true
	 */
	public void setLabsOrTutorial() {
		this.hasLabsOrTutorial = true;
	}
	
	/**
	 * Method to remove section of index i from the course
	 * @param i Index of the section to be removed
	 */
	public void removeSection(int i) {
		--this.numSections;
		this.sections = ArrayUtils.remove(this.sections, i);
	}
}
