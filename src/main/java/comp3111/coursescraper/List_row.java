package comp3111.coursescraper;

import java.util.List;
import java.util.Set;

public class List_row {
	private String course_code;
	private String section;
	private String course_name;
	private String instructor;
	
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
	}
	
	public String getCourse_code() {
		return this.course_code;
	}
	
	public String getSection() {
		return this.section;
	}
	
	public String getCourse_name() {
		return this.course_name;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	
}
