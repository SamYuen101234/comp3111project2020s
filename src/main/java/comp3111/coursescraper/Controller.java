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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.cell.CheckBoxTableCell;

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
import javafx.util.Callback;
import java.lang.Object;


public class Controller {
	List<String> subjects;
	List<Course> courses;
	private List<List_row> enrollments = new Vector<>();

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
    
    @FXML
    private TableView<List_row> List_table = new TableView<>();
    
    public static ObservableList<List_row> toObservableList = FXCollections.observableArrayList();
    
    
    @FXML
    private TableColumn<List_row, String> course_code = new TableColumn<>("Course Code");
    
    @FXML
    private TableColumn<List_row, String> section = new TableColumn<>("Section");

    @FXML
    private TableColumn<List_row, String> course_name = new TableColumn<>("Course Name");

    @FXML
    private TableColumn<List_row, String> instructor = new TableColumn<>("Instructor");

    @FXML
    private TableColumn<List_row, Boolean> enroll = new TableColumn<>("Enroll");
    
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
		    		//Section temp_section = new Section();
		    		boolean fulfil = false;
		    		for(int k = 0; k < courses.get(i).getSection(j).getNumSlots(); ++k) {
		    			Slot temp_slot = courses.get(i).getSection(j).getSlot(k);
			    		if(CheckBox_name.contentEquals("AM")) {
			    			if(temp_slot.getStartHour() < 12) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("PM")) {
			    			if(temp_slot.getStartHour() >= 12) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Monday")) {
			    			if(temp_slot.getDay() == 0) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Tuesday")) {
			    			if(temp_slot.getDay() == 1) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Wednesday")) {
			    			if(temp_slot.getDay() == 2) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Thursday")) {
			    			if(temp_slot.getDay() == 3) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Friday")) {
			    			if(temp_slot.getDay() == 4) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Saturday")) {
			    			if(temp_slot.getDay() == 5) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("Common Core")) {
			    			if(courses.get(i).checkCC()) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("No Exclusion")) {
			    			if(courses.get(i).getExclusion() == "null") {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}else if(CheckBox_name.contentEquals("With Labs or Tutorial")) {
			    			String temp_T = courses.get(i).getSection(j).getSectionID().substring(0, 1);
			    			String temp_LA = courses.get(i).getSection(j).getSectionID().substring(0, 2);
			    			if(temp_T.contentEquals("T") || temp_LA.contentEquals("LA")) {
			    				fulfil = true;
			    				fulfils[l] = true;
			    				//temp_section.addSlot(temp_slot);
			    			}
			    		}
		    		}
		    		if(fulfil == true) {
		    			if(temp_course.getNumSections() == 0)
		    				temp_course.addSection(courses.get(i).getSection(j));
		    			else {
			    			for (int m = 0; m < temp_course.getNumSections(); ++m) {
			    				System.out.println("hkust");
			    				if(temp_course.getSection(m).getSectionID().contentEquals(courses.get(i).getSection(j).getSectionID()))
			    					break;
			    				if(m == temp_course.getNumSections()-1)
			    					temp_course.addSection(courses.get(i).getSection(j));
			    			}
		    			}
		    			fulfil = false;
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
    		for(int i = 0; i < Filtered.size(); ++i) {
    			for(int j = 0; j < Filtered.get(i).getNumSections(); ++j) {
    				System.out.println(Filtered.get(i).getSection(j).getSectionID());
    				for(int k = 0; k < Filtered.get(i).getSection(j).getNumSlots(); ++k) {
    					System.out.println(Filtered.get(i).getSection(j).getSlot(k));
    				}
    			}
    		}
    	}else {
    		String search_console = scraper.printCourses(courses);
    		textAreaConsole.setText(search_console);
    	}
    	List_View(Filtered);
    	
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
    
    //Task 3 List
    @SuppressWarnings("unchecked")
    void List_View(List<Course> courses) {
    	List_table.getItems().clear();
    	List_table.setEditable(true);
    	enroll.setEditable(true);
    	List<List_row> list_rows = new Vector<>();
    	for(int i = 0; i < courses.size(); ++i) {
    		Course course = courses.get(i);
    		for(int j = 0; j < courses.get(i).getNumSections(); ++j) {
    			Section section = course.getSection(j);
    			for(int k = 0; k <courses.get(i).getSection(j).getNumSlots(); ++k) {
    				Slot slot = section.getSlot(k);
    				List_row list_row  = new List_row(course, section, slot);
    				list_rows.add(list_row);
    			}
    		}
    	}
    	List_table.setItems(toObservableList);
    	course_code.setCellValueFactory(new PropertyValueFactory<>("course_code"));
    	section.setCellValueFactory(new PropertyValueFactory<>("section"));
    	course_name.setCellValueFactory(new PropertyValueFactory<>("course_name"));
    	instructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));
    	enroll.setCellValueFactory(new PropertyValueFactory<>("select"));
       	enroll.setCellValueFactory(new Callback<CellDataFeatures<List_row,Boolean>, ObservableValue<Boolean>>(){
    		@Override
    		public ObservableValue<Boolean> call(CellDataFeatures<List_row, Boolean> param) {
    			List_row temp = (List_row)param.getValue().clone();
    			SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(temp.getSelect());
    			booleanProp.addListener(new ChangeListener<Boolean>() {
    				@Override
    				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
    					temp.setSelect(newValue);
    					System.out.println("Course code:"+temp.getCourse_code());
    					System.out.println(temp.getSection());
    					System.out.println("oldvalue:" + oldValue);
    					System.out.println("newvalue:" + newValue);
    					
    					//enrollments = toObservableList;
    					if(oldValue == false && newValue == true) {
    						enrollments.add(temp);
    						//Course enrolled_course = new Course();
    						//enrolled_course.setTitle(temp.getCourse_code() + temp.getCourse_name());
    						//Section enrolled_section = new Section();
    						//enrolled_section.setSectionID(temp.getSection());
    						
    					}
    					else if(oldValue == true && newValue == false)
    						enrollments.remove(temp);
    					
    					
    				}
    			});
    			return booleanProp;
    		}
    	});
    	enroll.setCellFactory(new Callback<TableColumn<List_row, Boolean>,TableCell<List_row, Boolean>>(){
    			@Override
    		    public TableCell<List_row, Boolean> call(TableColumn<List_row, Boolean>p){
    				CheckBoxTableCell<List_row, Boolean> cell = new CheckBoxTableCell<List_row, Boolean>();
    				cell.setAlignment(Pos.CENTER);
    				return cell;
    		}
    	});
    	toObservableList.addAll(list_rows);
    	List_table.getColumns().setAll(course_code, section, course_name, instructor, enroll);
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
