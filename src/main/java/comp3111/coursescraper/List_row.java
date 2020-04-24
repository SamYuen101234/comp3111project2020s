package comp3111.coursescraper;

public class List_row {
	private String course_code;
	private String section;
	private String course_name;
	private String course_title;
	private String instructor;
	private Slot[] slots;
	private int numSlots;
	private String section_type;
	private boolean select = false;
	
	public List_row() {
		this.course_code = null;
		this.course_name = null;
		this.section = null;
		this.instructor = null;
		this.slots = null;
		this.numSlots = -1;
	}
	
	public List_row(Course courses, Section section) {
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
		this.course_title = courses.getTitle();
		this.section = section.getSectionID();
		this.instructor = section.getAllInstructor().toString();
		this.numSlots = section.getNumSlots();
		slots = new Slot[this.numSlots];
		for(int i = 0; i < this.numSlots; ++i) {
			slots[i] = section.getSlot(i).clone();
		}
		String first2char = this.section.substring(0, 2);
		String firstchar = this.section.substring(0, 1);
		if(first2char.contentEquals("LA"))
			this.section_type = first2char;
		else
			this.section_type = firstchar;
	}
	
	/*@Override
	public List_row clone() {
		List_row new_class = new List_row();
		new_class.course_code = this.course_code;
		new_class.course_name = this.course_name;
		new_class.instructor = this.instructor;
		new_class.section = this.section;
		new_class.select = this.select;
		new_class.course_title = this.course_title;
		new_class.numSlots = this.numSlots;
		new_class.slots = new Slot[new_class.numSlots];
		for(int i = 0; i < slots.length; ++i) {
			new_class.slots[i] = this.slots[i].clone();
		}
		return new_class;
	}*/
	
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
	
		
	public boolean getSelect() {
		return select;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	public String getTitle() {
		return this.course_title;
	}
	
	public int getNumSlot() {
		return this.numSlots;
	}
	
	public Slot[] getSlot() {
		return this.slots;
	}
	
	public String getSectionType() {
		return this.section_type;
	}

	
	
}
