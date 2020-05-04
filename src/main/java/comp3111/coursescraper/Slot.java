package comp3111.coursescraper;

import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class Slot {
	private static final int DEFAULT_MAX_INSTRUCTOR = 30;
	
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private Set<String> instructor;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}
	
	public Slot() {
		instructor = new HashSet<String>();
	}

	@Override
	public Slot clone() {
		Slot s = new Slot();
		s.day = this.day;
		s.start = this.start;
		s.end = this.end;
		s.venue = this.venue;
		s.instructor = this.instructor;
		return s;
	}
	public String toString() {
		if(start == null) return "";
		else if(venue == null) return DAYS[day] + start.toString() + "-" + end.toString() + "   TBA\n";
		else return DAYS[day] + start.toString() + "-" + end.toString() + "   " + venue + "\n";
	}
	public int getStartHour() {
		return start.getHour();
	}
	public int getStartMinute() {
		return start.getMinute();
	}
	public int getEndHour() {
		return end.getHour();
	}
	public int getEndMinute() {
		return end.getMinute();
	}
	public double getDuration() {
		return end.getHour() - start.getHour() + (end.getMinute() - start.getMinute())/60.0;
	}
	/**
	 * @return the start
	 */
	public LocalTime getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(LocalTime start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public LocalTime getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	public void addInstructor(String name) {
		this.instructor.add(name);
	}
	
	public Set<String> getAllInstructor() {
		return this.instructor;
	}

}
