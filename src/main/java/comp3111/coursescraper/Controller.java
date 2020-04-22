package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;

import java.util.Random;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;

import java.lang.Object;


public class Controller {
	List<String> subjects;
	List<Course> courses;
	List<Course> enrollments;

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;
    
    @FXML
    private Button allSubjectSearch;

    @FXML
    private Button allCoursesSearch;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    @FXML
    private Button buttonPrintAllSubjectCourses;
    
    private Scraper scraper = new Scraper();
    
    
    
    
    
    
    
    @FXML
    void printAllSubjectCourses() {
    	scraper.printCourses(courses);
    }
    
    @FXML
    private Button selectALL;
    
    @FXML
    private CheckBox AM;
    
    @FXML
    private CheckBox PM;
    
    @FXML
    private CheckBox Monday;
    
    @FXML
    private CheckBox Tuesday;
    
    @FXML
    private CheckBox Wednesday;
    
    @FXML
    private CheckBox Thursday;
    
    @FXML
    private CheckBox Friday;
    
    @FXML
    private CheckBox Saturday;
    
    @FXML
    private CheckBox CommonCore;
    
    @FXML
    private CheckBox NoExclusion;
    
    @FXML
    private CheckBox With_Labs_Tutorial;
    
    Vector<CheckBox> getAllCheckBox(){
    	Vector<CheckBox> CheckBoxes = new Vector<CheckBox>();
    	CheckBoxes.add(AM);
    	CheckBoxes.add(PM);
    	CheckBoxes.add(Monday);
    	CheckBoxes.add(Tuesday);
    	CheckBoxes.add(Wednesday);
    	CheckBoxes.add(Thursday);
    	CheckBoxes.add(Friday);
    	CheckBoxes.add(Saturday);
    	CheckBoxes.add(CommonCore);
    	CheckBoxes.add(NoExclusion);
    	CheckBoxes.add(With_Labs_Tutorial);
    	return CheckBoxes;
    }
    
    
    
    @FXML
    void checkboxfilter(){
    	textAreaConsole.clear();
    	Vector<CheckBox> CheckBoxes = getAllCheckBox();
    	Vector<CheckBox> Checked = new Vector<CheckBox>();
    	List<Course> Filtered = new Vector<Course>();
    	
    	for(int i =0; i < CheckBoxes.size(); ++i) {
    		if(CheckBoxes.get(i).isSelected()) {
    			Checked.add(CheckBoxes.get(i));
    		}
    	}
    	
    	if(Checked.size() != CheckBoxes.size()) {
    		selectALL.setText("Select All");
    	}else
    		selectALL.setText("De-select All");
    	
    	if(courses == null)
    		return;
    	
    	for (int i = 0; i < courses.size(); ++i) {
    		Course temp_course = new Course();
    		boolean[] fulfils = new boolean[Checked.size()];
    		for(int l = 0; l < Checked.size(); ++l){
    			fulfils[l] = false;
    			String CheckBox_name = Checked.get(l).getText();
  		    	for(int j = 0; j < courses.get(i).getNumSections(); ++j) {
		    		Section temp_section = new Section();
		    		for(int k = 0; k < courses.get(i).getSection(j).getNumSlots(); ++k) {
		    			Slot temp_slot = courses.get(i).getSection(j).getSlot(k);
			    		if(CheckBox_name.contentEquals("AM")) {
			    			if(temp_slot.getStartHour() < 12) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("PM")) {
			    			if(temp_slot.getStartHour() >= 12) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Monday")) {
			    			if(temp_slot.getDay() == 0) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Tuesday")) {
			    			if(temp_slot.getDay() == 1) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Wednesday")) {
			    			if(temp_slot.getDay() == 2) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Thursday")) {
			    			if(temp_slot.getDay() == 3) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Friday")) {
			    			if(temp_slot.getDay() == 4) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Saturday")) {
			    			if(temp_slot.getDay() == 5) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Common Core")) {
			    			if(courses.get(i).checkCC()) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("No Exclusion")) {
			    			if(courses.get(i).getExclusion() == "null") {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("With Labs or Tutorial")) {
			    			String temp_T = courses.get(i).getSection(j).getSectionID().substring(0, 1);
			    			String temp_LA = courses.get(i).getSection(j).getSectionID().substring(0, 2);
			    			if(temp_T.contentEquals("T") || temp_T.contentEquals("LA")) {
			    				fulfils[l] = true;
			    				temp_section.addSlot(temp_slot);
			    			}
			    		}
		    		}
		    		if(fulfils[l] == true) {
		    			temp_section.setSectionID(courses.get(i).getSection(j).getSectionID());
		    			temp_course.addSection(temp_section);
		    		}
  		    	}
      		}
    		temp_course.setTitle(courses.get(i).getTitle());
    		temp_course.setDescription(courses.get(i).getDescription());
    		temp_course.setExclusion(courses.get(i).getExclusion());
       		for(int l = 0; l < fulfils.length; ++l) {
    			if(fulfils[l] == false)
    				break;
    			if(l == fulfils.length-1)
    			Filtered.add(temp_course);
    		}
    	}
    	if(Checked.size() > 0)
    	{
    		String filter_console = scraper.printCourses(Filtered);
    		textAreaConsole.setText(filter_console);
    	}else {
    		String search_console = scraper.printCourses(courses);
    		textAreaConsole.setText(search_console);
    	}
    	
    	
    	    	
    }
    
    
    
    @FXML
    void PressSelectAll() {
    	Vector<CheckBox> CheckBoxes = getAllCheckBox();
    	Vector<CheckBox> Checked = new Vector<CheckBox>();
    	for(int i =0; i < CheckBoxes.size(); ++i) {
    		if(CheckBoxes.get(i).isSelected()) {
    			Checked.add(CheckBoxes.get(i));
    		}
    	}
    		if(selectALL.getText().contentEquals("Select All"))
    		{
    	    	AM.setSelected(true);
    	    	PM.setSelected(true);
    	    	Monday.setSelected(true);
    	    	Tuesday.setSelected(true);
    	    	Wednesday.setSelected(true);
    	    	Thursday.setSelected(true);
    	    	Friday.setSelected(true);
    	    	Saturday.setSelected(true);
    	    	CommonCore.setSelected(true);
    	    	NoExclusion.setSelected(true);
    	    	With_Labs_Tutorial.setSelected(true);
    	    	selectALL.setText("De-select All");
    		}else {
    	    	AM.setSelected(false);
    	    	PM.setSelected(false);
    	    	Monday.setSelected(false);
    	    	Tuesday.setSelected(false);
    	    	Wednesday.setSelected(false);
    	    	Thursday.setSelected(false);
    	    	Friday.setSelected(false);
    	    	Saturday.setSelected(false);
    	    	CommonCore.setSelected(false);
    	    	NoExclusion.setSelected(false);
    	    	With_Labs_Tutorial.setSelected(false);
    	    	selectALL.setText("Select All");
    		}
    		checkboxfilter();
    }
    
    @FXML
    void allSubjectSearch() {
    	// Scrape all subjects from given URL and term
    	subjects = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());

    	// Record and display the total no. of subjects
    	textAreaConsole.setText("Total Number of Categories/Code Prefix: " + subjects.size());
    }

    @FXML
    void allCoursesSearch() {
    	// Scrape all subjects from given URL and term
    	subjects = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());
    	
    	// Create a new list if there wasn't any. Otherwise clear the current courses list
    	if(courses==null) {
    		courses = new Vector<Course>();
    	}
    	else {
    		courses.clear();
    	}
    	
    	// Scrape all courses in each subject in subjects
    	List<Course> courseOfSubject = new Vector<Course>();
    	for (int i=0;i<subjects.size();++i) {
    		if(!subjects.get(i).equals("MGMT")) {
    			courseOfSubject = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),subjects.get(i));
    		}
    		
    		// Append all courses
    		for(Course c:courseOfSubject) {
    			courses.add(c);
    		}
    		// Print "SUBJECT is done" on console
    		System.out.println("SUBJECT is done");
    		
    		// Update progress bar by 1/(total no. of subjects)
    		progressbar.setProgress((float)(1.0/subjects.size()*(i+1)));
    	}
    	// Print total no. of courses in console (size of allCourses list)
    	textAreaConsole.setText("Total Number of Courses fetched: " + courses.size() + "\n");    	
    	
    	// Call "Select all" function in "Filter" tab
    	
    	
    	
    	// Change "Main" tab text input in "Subject" to "(All Subjects)" and enable the show all courses button
    	textfieldSubject.setText("(All Subjects)");
    	
    	buttonPrintAllSubjectCourses.setDisable(false);

    	// Enables the "Find SFQ with my enrolled courses" button
    	buttonSfqEnrollCourse.setDisable(false);
    }


    @FXML
    void findInstructorSfq() {
    	// Clean up console 
    	textAreaConsole.clear();
    	
    	// Scrape instructors list together with their average SFQ from the URL
    	HashMap<String,Vector<Float>> instructors = scraper.scrapeInstructorSFQ(textfieldSfqUrl.getText());
    	
    	// Display wrong web-page in console if return value is null
    	if (instructors == null) {
    		textAreaConsole.setText("The inputted URL is not valid");
    	}
    	else {
    		// Sort instructors according alphabetical order in the format of "LAST_NAME, FIRST_NAME"
    		TreeMap<String,Vector<Float>> sortInstructors = new TreeMap<>(instructors);
    	
    		// Display result in console
    		for (Map.Entry<String,Vector<Float>> entry: sortInstructors.entrySet()) {
    			// Calculate the average SFQ rating
    			if(entry.getValue().get(0)==null) {
    				textAreaConsole.setText(textAreaConsole.getText() + entry.getKey() + ": (had no SFQ rating available)\n");
    			} else {
    				entry.getValue().set(0, entry.getValue().get(0)/entry.getValue().get(1));
        			String instructor = entry.getKey() + ": " + String.valueOf((float)Math.round(entry.getValue().get(0)*10)/10 + "%\n");
        			textAreaConsole.setText(textAreaConsole.getText() + instructor);
    			}
    		}
    	}
    }

    @FXML
    void findSfqEnrollCourse() {
    	// Clean up console 
    	textAreaConsole.clear();
    	
    	// Retrieve enrolled course
    	Vector<Course> enrolled = new Vector<Course>();
    	Vector<Course> testEmpty = new Vector<Course>();
    	Course c1 = new Course();Course c2 = new Course();Course c3 = new Course();Course c4 = new Course();Course c5 = new Course();Course c6 = new Course();
    	c1.setTitle("COMP 1029C");c2.setTitle("ELEC 1100");c3.setTitle("MECH 4720");c4.setTitle("ELEC 1200");c5.setTitle("COMP 3111H");c6.setTitle("IEDA 6100A");
    	enrolled.add(c1);enrolled.add(c2);enrolled.add(c3);enrolled.add(c4);enrolled.add(c5);enrolled.add(c6);
    	
    	// if the enrolled course list is empty, tell the user that there is no enrolled course, and do nothing else
    	//if(Empty.isEmpty()){
    	if(enrolled.isEmpty()) {
    		textAreaConsole.setText("There are no enrolled course");
    	}
    	else {
    		// Change the enrolled course list to HashSet
        	HashSet<String> enrolledC = new HashSet<String>();
        	for(Course c: enrolled) {
        		enrolledC.add(c.getTitle());
        	}
        	
        	// Scrape enrolled course list together with their average SFQ from the URL
        	HashMap<String,Float> enrolledSfq = scraper.scrapeCoursesSFQ(textfieldSfqUrl.getText(),enrolledC);
        	
        	// Display wrong web-page in console if return value is null
        	if(enrolledSfq==null) {
        		textAreaConsole.setText("The inputted URL is not valid");
        	}
        	else {
        		// Sort the enrolled course
        		TreeMap<String,Float> sortenrolled = new TreeMap<>(enrolledSfq);
        		// Display result in console
        		for (Map.Entry<String,Float> entry: sortenrolled.entrySet()) {
        			if(entry.getValue()==null) {
        				textAreaConsole.setText(textAreaConsole.getText() + entry.getKey() + ": (had no SFQ rating available)\n");
        			} else {
        				textAreaConsole.setText(textAreaConsole.getText() + entry.getKey() + ": " + (float)Math.round(entry.getValue()*10)/10 + "%\n");
        			}
        			
        		}
        	}
    	}
    }

    @FXML
    void search() {
    	textAreaConsole.clear();
    	courses = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	textAreaConsole.setText(scraper.printCourses(courses));
    	
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;

    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    }
    
   
}
