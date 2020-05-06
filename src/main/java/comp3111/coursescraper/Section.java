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
	
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
		instructor = new HashSet<String>();
	}
	
	public String toString() {
		String result = this.sectionID + "\n";
		for(int i = 0; i < numSlots; ++i) {
			result += slots[i];
		}
		return result;
	}
	
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	@Override
	public Section clone() {
		Section s = new Section();
		s.sectionID = this.sectionID;
		s.slots = this.slots;
		s.numSlots = this.numSlots;
		s.instructor = this.instructor;
		return s;
	}
	
	public void setSectionID(String ID) {
		this.sectionID = ID;
	}
	
	public String getSectionID() {
		return this.sectionID;
	}
	
	public int getNumSlots() {
		return this.numSlots;
	}
	
	public Set<String> getAllInstructor() {
		Set<String> result = new HashSet<String>();
		for(int i = 0; i < numSlots; ++i) {
			result.addAll(slots[i].getAllInstructor());
		}
		return result;
	}
	
	public Set<String> getInstructorConstraint(LocalTime time) {
		Set<String> result = new HashSet<String>();
		for(int i = 0; i < numSlots; ++i) {
			if(slots[i].getStart() != null && slots[i].getDay() == 1 && time.isAfter(slots[i].getStart()) && time.isBefore(slots[i].getEnd())){
				result.addAll(slots[i].getAllInstructor());
			}
		}
		return result;
	}
	
	public void removeSlot(int i) {
		--this.numSlots;
		this.slots = ArrayUtils.remove(this.slots, i);
	}
	
	public void addInstructor(Set<String> i) {
		this.instructor.addAll(i);
	}
	
	public Set<String> getSectionInstructor() {
		return this.instructor;
	}
	
}
