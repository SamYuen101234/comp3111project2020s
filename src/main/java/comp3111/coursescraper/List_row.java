package comp3111.coursescraper;

/** A class for convenient representation of the section in list (task 3)
 * 
 * @author Yuen Zhikun (zyuen)
 *
 */


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
	
	/** Create a empty List_row (a row on the list table), the default constructor.
	*/

	public List_row() {
		this.course_code = null;
		this.course_name = null;
		this.section = null;
		this.instructor = null;
		this.slots = null;
		this.numSlots = -1;
	}
	
	
	/** Create a List_row (a row on the list table).
	 * @param courses a course
	 * @param section a section within that course
	*/

	
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
	
	/** Change the time slots in a section to string
	 * 	@return a String of the all slots of that section
	 */
	public String toString() {
		String result = this.section + "\n";
		for(int i = 0; i < numSlots; ++i) {
			result += slots[i];
		}
		return result;
	}
	/** Get the course code
	 * 
	 * @return a string for course code e.g. COMP3111
	 */
	public String getCourse_code() {
		return this.course_code;
	}
	
	/** set the course code
	 * 
	 * @param a string of the course code e.g. COMP3111
	 */
	
	
	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	
	/** get the section with its id
	 * 
	 * @return a string of the section id e.g. COMP3111
	 */
	
	public String getSection() {
		return this.section;
	}
	/** set the section with its section id
	 * 
	 * @param a string of the section id section
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/** return the course name
	 * 
	 * @return a string of the course_name e.g. Software Engineer
	 */
	public String getCourse_name() {
		return this.course_name;
	}
	/** Set the course name
	 * 
	 * @param a string of course_name
	 */
	
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	/** get the name of all instructor of the section
	 * 
	 * @return a string of all the instructor
	 */
	
	public String getInstructor() {
		return this.instructor;
	}
	/** set the name of the instructor
	 * 
	 * @param string of all the instructor
	 */
	public void set_Instructor(String instructor) {
		this.instructor = instructor;
	}
	/** get the enrollment status of the section
	 * 
	 * @return boolean of a section enrolled or not
	 */
	public boolean getSelect() {
		return select;
	}
	/** set the section as enrolled
	 * 
	 * @param a boolean value, true is enroll, false is drop
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}
	/** get the course title 
	 * 
	 * @return string of the course title
	 */
	public String getTitle() {
		return this.course_title;
	}
	/** get the number of slot
	 * 
	 * @return int of the number of slot
	 */
	public int getNumSlot() {
		return this.numSlots;
	}
	/** get the whole slot[]
	 * 
	 * @return a array of slot with many single slot in the array
	 */
	public Slot[] getSlot() {
		return this.slots;
	}
	/** get the section type e.g. L, LAB, T
	 * 
	 * @return a string of the section type, e.g. L, LAB, T
	 */
	public String getSectionType() {
		return this.section_type;
	}
	/** get the slot through its index
	 * 
	 * @param i the index of slots
	 * @return a slot if the slots is empty, return null
	 */
	
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}


	
	
}
