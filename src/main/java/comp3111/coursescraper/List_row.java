package comp3111.coursescraper;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;

public class List_row {
	private String course_code;
	private String section;
	private String course_name;
	private String instructor;
	private Slot[] slot;
	//private int day; // Monday = 0, Tuesday = 1; ... Saturday = 5
	//private LocalTime start;
	//private LocalTime end;
	private boolean select;
	
	public List_row() {
		this.course_code = null;
		this.course_name = null;
		//this.day = -1;
		//this.start = null;
		//this.end = null;
		this.select = false;
		
	}
	
	public List_row(Course courses, Section section, Slot slot) {
		String course_title = courses.getTitle();
		String temp = new String(); int counter = 0;
		for(char ch: course_title.toCharArray()) {
			if(ch == ' ' && counter < 2) {
				++counter;
				continue;
			}else if(ch == '-')
				continue;
			else if(ch == '(')
				break;
			if(counter == 2) {
				this.course_code = temp;
				ch = '\0';
				temp = "";
				++counter;
			}
			temp += ch;
		}
		this.course_name = temp;
		this.section = section.getSectionID();
		Set<String> instructors = slot.getAllInstructor();
		this.instructor = instructors.toString();
		//this.day = slot.getDay();
		//this.start = slot.getStart();
		//this.end = slot.getEnd();
		//this.select = false;
	}
	
	@Override
	public List_row clone() {
		List_row new_class = new List_row();
		new_class.course_code = this.course_code;
		new_class.course_name = this.course_name;
		//new_class.day = this.day;
		//new_class.end = this.end;
		//new_class.instructor = this.instructor;
		//new_class.section = this.section;
		//new_class.start = this.start;
		new_class.select = this.select;
		return new_class;
	}
	
	public String getCourse_code() {
		return this.course_code;
	}
	
	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	
	public String getSection() {
		return this.section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public String getCourse_name() {
		return this.course_name;
	}
	
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	public void set_Instructor(String instructor) {
		this.instructor = instructor;
	}
	
	/*public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public LocalTime getStart() {
		return start;
	}
	
	public void setStart(LocalTime start) {
		this.start = start;
	}
	
	public int getStartHour() {
		return start.getHour();
	}
	
	public int getStartMinute() {
		return start.getMinute();
	}
	
	public LocalTime getEnd() {
		return end;
	}
	
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	
	public int getEndHour() {
		return end.getHour();
	}
	public int getEndMinute() {
		return end.getMinute();
	}*/
	
	public boolean getSelect() {
		return select;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	
	
}
