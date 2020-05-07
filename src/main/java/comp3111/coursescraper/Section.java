package comp3111.coursescraper;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class Section {
private static final int DEFAULT_MAX_SLOT = 10;

	private String sectionID;
	private Slot [] slots;
	private int numSlots;
	private Set<String> instructor;
	
	/**
	 * Default constructor for Section,
	 * sections array of size DEFAULT_MAX_SLOT will be created, 
	 * * instructor set will be initialized,
	 * numSlots will be initialized to 0,
	 */
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
		instructor = new HashSet<String>();
	}
	
	/**
	 * Convert the section to string output
	 */
	public String toString() {
		String result = this.sectionID + "\n";
		for(int i = 0; i < numSlots; ++i) {
			result += slots[i];
		}
		return result;
	}
	
	
	/**
	 * Method to add slot
	 * @param s the slot to be added
	 */
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	/**
	 * Method to get slot with index i
	 * @param i index of slot to get
	 * @return the slot with index i
	 */
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	/**
	 * Method to duplicate the current section
	 */
	@Override
	public Section clone() {
		Section s = new Section();
		s.sectionID = this.sectionID;
		s.slots = this.slots;
		s.numSlots = this.numSlots;
		s.instructor = this.instructor;
		return s;
	}
	
	/**
	 * Method to set sectionID
	 * @param ID sectionID
	 */
	public void setSectionID(String ID) {
		this.sectionID = ID;
	}
	
	/**
	 * Method to get SectionID
	 * @return sectionID
	 */
	public String getSectionID() {
		return this.sectionID;
	}
	
	/**
	 * Method to get number of slots
	 * @return numSlots
	 */
	public int getNumSlots() {
		return this.numSlots;
	}
	
	/**
	 * Method to get all instructors from all slots teaching this section
	 * @return set of instructor
	 */
	public Set<String> getAllInstructor() {
		Set<String> result = new HashSet<String>();
		for(int i = 0; i < numSlots; ++i) {
			result.addAll(slots[i].getAllInstructor());
		}
		return result;
	}
	
	/**
	 * Method to get all instructors from all slots teaching this section at specific time
	 * @param time the time constraint for the instructor
	 * @return set of instructor fulfil the requirement
	 */
	public Set<String> getInstructorConstraint(LocalTime time) {
		Set<String> result = new HashSet<String>();
		for(int i = 0; i < numSlots; ++i) {
			if(slots[i].getStart() != null && slots[i].getDay() == 1 && time.isAfter(slots[i].getStart()) && time.isBefore(slots[i].getEnd())){
				result.addAll(slots[i].getAllInstructor());
			}
		}
		return result;
	}
	
	/**
	 * Method to remove slot from this section
	 * @param i the index of the slot to remove
	 */
	public void removeSlot(int i) {
		--this.numSlots;
		this.slots = ArrayUtils.remove(this.slots, i);
	}
	
	/**
	 * Method to add instructors to this section
	 * @param i set of instructors to add
	 */
	public void addInstructor(Set<String> i) {
		this.instructor.addAll(i);
	}
	
	/**
	 * Method to get instructors of this section
	 * @return set of all instructors teaching this section
	 */
	public Set<String> getSectionInstructor() {
		return this.instructor;
	}
	
}
