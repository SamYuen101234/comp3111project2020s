package comp3111.coursescraper;

import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.concurrent.Task;

import java.util.Random;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
	List<Course> courses = new Vector<Course>();
	List<List_row> enrollments = new Vector<>();
	
	@FXML
	private TabPane tabPane;

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
    private Button buttonRefresh;

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
    
    /**
     * Method to set the textAreaConsole with the scraped information.
     * Invalid sections and slots will also be removed after printing the information.
     */
    @FXML
    void printAllSubjectCourses() {
    	textAreaConsole.setText(scraper.printCourses(courses, true));
    	courses = scraper.removeInvalid(courses);
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
			    			}
			    		}else if(CheckBox_name.contentEquals("PM")) {
			    			if(temp_slot.getEndHour() > 12) {
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
		    		/*if(fulfil == true) {
		    			if(temp_course.getNumSections() == 0)
		    				temp_course.addSection(courses.get(i).getSection(j));
		    			else {
			    			for (int m = 0; m < temp_course.getNumSections(); ++m) {
			    				if(temp_course.getSection(m).getSectionID().contentEquals(courses.get(i).getSection(j).getSectionID()))
			    					break;
			    				if(m == temp_course.getNumSections()-1)
			    					temp_course.addSection(courses.get(i).getSection(j));
			    			}
		    			}
		    			fulfil = false;
		    		}*/
  		    	}
      		}
    		/*temp_course.setTitle(courses.get(i).getTitle());
    		temp_course.setDescription(courses.get(i).getDescription());
    		temp_course.setExclusion(courses.get(i).getExclusion());*/
       		for(int l = 0; l < fulfils.length; ++l) {
    			if(fulfils[l] == false)
    				break;
    			if(l == fulfils.length-1)
    				Filtered.add(courses.get(i));
    		}
    	}
    	if(Checked.size() > 0)
    	{
    		String filter_console = scraper.printCourses(Filtered, false);
    		textAreaConsole.setText(filter_console);
    	}else {
    		String search_console = scraper.printCourses(courses, false);
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
    public class List_rowComparator implements Comparator<List_row> {
        @Override
        public int compare(List_row o1, List_row o2) {
            return o1.getCourse_code().compareTo(o2.getCourse_code());
        }
    }
    
 
    String print() {
    	String result = "";
    	Collections.sort(enrollments, new List_rowComparator());
    	for(int i = 0; i < enrollments.size(); ++i) {
    		if(i == 0) {
     			result += enrollments.get(i).getTitle() + "\n";
     			result += enrollments.get(i).getSection() + "\n";
     			for(int j = 0; j < enrollments.get(i).getNumSlot(); ++j) {
     				//result += enrollments.get(i).getSlot()[i] + "\n";
     				result += enrollments.get(i).getSlot()[j];
     			}
     		}else {
     			if(enrollments.get(i).getCourse_code().contentEquals(enrollments.get(i-1).getCourse_code())) {
    				result += enrollments.get(i).getSection() + "\n";
    				for(int j = 0; j < enrollments.get(i).getNumSlot(); ++j) {
        				result += enrollments.get(i).getSlot()[j];
        			}
    			}else {
    				result += "\n";
    				result += enrollments.get(i).getTitle() + "\n";
        			result += enrollments.get(i).getSection() + "\n";
        			for(int j = 0; j < enrollments.get(i).getNumSlot(); ++j) {
        				result += enrollments.get(i).getSlot()[j];
        			}
    			}
    		}
    	}
    	//System.out.println(result);
    	//System.out.println("");
    	return result;
    }    
    //Task 3 List
    @SuppressWarnings("unchecked")
    void List_View(List<Course> filtered) {
    	List_table.getItems().clear();
    	List_table.setEditable(true);
    	List<List_row> list_rows = new Vector<>();
    	for(int i = 0; i < filtered.size(); ++i) {
    		Course course = filtered.get(i);
    		for(int j = 0; j < filtered.get(i).getNumSections(); ++j) {
    			Section section = course.getSection(j);
    			List_row list_row  = new List_row(course, section);
    			String course_code = list_row.getCourse_code();
    			String sectionid = list_row.getSection();
    			for(int k = 0; k < enrollments.size(); ++k) {
        			if(course_code.contentEquals(enrollments.get(k).getCourse_code()) && 
        					sectionid.contentEquals(enrollments.get(k).getSection())) {
        				list_row.setSelect(true);
        			}
    			}
    			list_rows.add(list_row);
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
    			List_row temp = param.getValue();
    			SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(temp.getSelect());
    			//System.out.println(temp.getSelect());
    			booleanProp.addListener(new ChangeListener<Boolean>() {
    				@Override
    				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
    					String course_code_temp = temp.getCourse_code();
       					String section_type_temp = temp.getSectionType();
    					String sectionid_temp = temp.getSection();
    					for(int i = 0; i < enrollments.size(); ++i) {
    						String course_code = enrollments.get(i).getCourse_code();
    						String section_type = enrollments.get(i).getSectionType();
    						String sectionid = enrollments.get(i).getSection();
    						if(course_code_temp.contentEquals(course_code) && section_type_temp.contentEquals(section_type)
    								&&  !sectionid_temp.contentEquals(sectionid)) {
    							return;
    						}
    						/*if(newValue == false) {
    							continue;
    						}
    						else {
	    						for(int j = 0; j < temp.getNumSlot(); ++j){
	    							LocalTime start_temp = temp.getSlot(j).getStart();
	    							LocalTime end_temp = temp.getSlot(j).getEnd();
		    						for(int k = 0; k < enrollments.get(i).getNumSlot(); ++k) {
		    							LocalTime start = enrollments.get(i).getSlot(k).getStart();
		    							LocalTime end = enrollments.get(i).getSlot(k).getEnd();
		    							if((start_temp.isBefore(start) && end_temp.isBefore(end)) || 
		    									(start_temp.isAfter(start) && end_temp.isAfter(end))) {
		    								continue;
		    							}
		    							return;
		    						}
	    						}
    						}*/
    					}
    					
    					if(oldValue == false && newValue == true) {
    						
    						temp.setSelect(newValue);
    						enrollments.add(temp); 
//    						addToTimetable(temp);
    						String result = print();
    						textAreaConsole.clear();
    						textAreaConsole.setText(result);
    						
    					}else if(oldValue == true && newValue == false) {
    						String temp_code = temp.getCourse_code();
    						String temp_sectionid = temp.getSection();
    						for(int i = 0; i < enrollments.size(); ++i) {
    							String code = enrollments.get(i).getCourse_code();
    							String section = enrollments.get(i).getSection();
    							if(temp_code.contentEquals(code) && temp_sectionid.contentEquals(section)) {
//    								removeFromTimetable(enrollments.get(i));
    								enrollments.remove(i);
    							}
    						}
    						temp.setSelect(newValue);
    						textAreaConsole.clear();
    						if(enrollments.size()>0) {
    							String result = print();
    							textAreaConsole.setText(result);
    							
    						}else {
    							String filter_console = scraper.printCourses(filtered, false);
    							textAreaConsole.setText(filter_console);
    						}
    					}
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
    
    /**
     * Method to search all subjects obtained from the user-inputed URL in the "Main" tab.
     * This method will be invoked when "All Subject Search" button in the "All Subject Search" tab is clicked.
     */
    @FXML
    void allSubjectSearch() {
    	// Scrape all subjects from given URL and term
    	subjects = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());

    	// Record and display the total no. of subjects
    	// Display wrong web-page in console if return value is null
    	if (subjects == null) {
    		textAreaConsole.setText("The inputted URL is not valid");
    	} else {
    		textAreaConsole.setText("Total Number of Categories/Code Prefix: " + subjects.size());
    	}
    }

    /**
     * Method to search all courses obtained from the user-inputed URL in the "Main" tab.
     * This method will be invoked when "All Courses Search" button in the "All Subject Search" tab is clicked.
     */
    @FXML
    void allCoursesSearch() {
    	// Clean up console 
    	textAreaConsole.clear();
    	
    	// Scrape all subjects from given URL and term
    	subjects = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());
    	
    	// Create a new list if there wasn't any. Otherwise clear the current courses list
    	// Display wrong web-page in console if return value is null
    	if (subjects == null) {
    		textAreaConsole.setText("The inputted URL is not valid");
    	} else{
    		if(courses==null) {
    			courses = new Vector<Course>();
    		}
    		else {
    			courses.clear();
    		}
    	
    		// Scrape all courses in each subject in subjects
    		//List<Course> courseOfSubject = new Vector<Course>();
    		
    		new Thread(){
				public void run() {
					Scraper scraper2 = new Scraper();
					//HashSet<String> sub = new HashSet<String>();
					List<Course> courseOfSubject = new Vector<Course>();
					for (int i=0;i<subjects.size();++i) {		
						try {
		    				Thread.sleep(100); 
		    			} catch(InterruptedException ex) {
		    				Thread.currentThread().interrupt();
		    			}
						
		    			courseOfSubject = scraper2.scrape(textfieldURL.getText(), textfieldTerm.getText(),subjects.get(i));
		    			try {
		    				Thread.sleep(100); 
		    			} catch(InterruptedException ex) {
		    				Thread.currentThread().interrupt();
		    			}
		    		
		    			// Append all courses
		    			for(Course c:courseOfSubject) {
		    				courses.add(c);
		    			}
		    			// Print "SUBJECT is done" on console
		    			System.out.println("SUBJECT is done");
		    		
		    			// Update progress bar by 1/(total no. of subjects)
		    			
		    			progressbar.setProgress((float)(1.0/subjects.size()*(i+1)));
		    			try {
		    				Thread.sleep(1); 
		    			} catch(InterruptedException ex) {
		    				Thread.currentThread().interrupt();
		    			}
		    			// Print total no. of courses in console (size of allCourses list)
		    			textAreaConsole.setText("Total Number of Courses fetched: " + courses.size() + "\n"); 						
		    		}	
					
				}
				
			}.start();
    	}
    	// Change "Main" tab text input in "Subject" to "(All Subjects)" and enable the show all courses button
		textfieldSubject.setText("(All Subjects)");
		buttonPrintAllSubjectCourses.setDisable(false);

		// Enables the "Find SFQ with my enrolled courses" button
		buttonSfqEnrollCourse.setDisable(false);
    }

    /**
     * Method to show the SFQ rating(s) of the all instructors obtained from the user-inputed URL.
     * This method will be invoked when "List instructors' average SFQ" button in the "SFQ" tab is clicked.
     */
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
    		textAreaConsole.setText("SFQ ratings of instructors:\n");
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

    /**
     * Method to show the SFQ rating(s) of the enrolled course(s) obtained from the user-inputed URL.
     * This method will be invoked when "Find SFQ with my enrolled courses" button in the "SFQ" tab is clicked.
     */
    @FXML
    void findSfqEnrollCourse() {
    	// Clean up console 
    	textAreaConsole.clear();
    	
    	// Retrieve enrolled course
    	List<String> enrolled = new Vector<String>(enrollments.size());
    	for(int i=0;i<enrollments.size();++i) {
    		enrolled.add(enrollments.get(i).getCourse_code());
    	}
    	
    	// if the enrolled course list is empty, tell the user that there is no enrolled course, and do nothing else
    	//if(Empty.isEmpty()){
    	if(enrolled.isEmpty()) {
    		textAreaConsole.setText("There are no enrolled course");
    	}
    	else {
    		// Change the enrolled course list to HashSet
        	HashSet<String> enrolledH = new HashSet<String>(enrolled.size());
        	for(String s:enrolled) {
        		enrolledH.add(s);
        	}
        	
        	// Scrape enrolled course list together with their average SFQ from the URL
        	HashMap<String,Float> enrolledSfq = scraper.scrapeCoursesSFQ(textfieldSfqUrl.getText(),enrolledH);
        	
        	// Display wrong web-page in console if return value is null
        	if(enrolledSfq==null) {
        		textAreaConsole.setText("The inputted URL is not valid");
        	}
        	else {
        		// Display result in console
        		textAreaConsole.setText("SFQ ratings of enrolled course(s):\n");
        		for(String s:enrolled) {
        			if(!enrolledSfq.containsKey(s)) {
        				textAreaConsole.setText(textAreaConsole.getText() + "The course " + s + " does not appear in the provided URL\n");
        			}
        			else if(enrolledSfq.get(s)==null) {
        				textAreaConsole.setText(textAreaConsole.getText() + s + ": (had no SFQ rating available)\n");
        			}
        			else {
        				textAreaConsole.setText(textAreaConsole.getText() + s + ": " + (float)Math.round(enrolledSfq.get(s)*10)/10 + "%\n");
        			}
        		}
        	}
    	}
    }

    /**
     * Method to search all the courses from the given input in textfield.
     * This method will be invoked when search button in the main tab is clicked.
     */
    @FXML
    void search() {
    	textAreaConsole.clear();
    	courses = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	courses = scraper.removeInvalid(courses);
    	textAreaConsole.setText(scraper.printCourses(courses, true));
    	
    	buttonPrintAllSubjectCourses.setDisable(true);
		buttonSfqEnrollCourse.setDisable(false);
    }
    
    @FXML
    void refreshTimetable() {
    	textAreaConsole.clear();
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	List<Node> tempArray = new ArrayList<Node>();
    	for(Node n: ap.getChildren()) {
    		if(n.getId() != null && n.getId().substring(0, 9).equals("slotLabel")) {
    			tempArray.add(n);
    		}
    	}
    	for(Node n: tempArray) {
    		ap.getChildren().remove(n);
    	}
    	
		String text = "Please refer to the following text timetable in case the text is overlapped due to time clash:\n";
		if(enrollments == null || enrollments.size() == 0) {
			if(courses != null && courses.size() > 0) {
				int count = 0;
    			for(Course c: courses) {
    				text += "\n" + c.getTitle() + "\n";
    				for(int i = 0; count < 5 && i < c.getNumSections(); ++i, ++count) {
    					List_row r = new List_row(c, c.getSection(i));
    					addToTimetable(r);
    					text += c.getSection(i);
    				}
    				if(count >= 5) break;
    			}
    			textAreaConsole.setText(text);
    		}
			else textAreaConsole.setText("Please do enrollment or search to before refreshing timetable.");
		}
		else {
			String temp = " ";
			for(List_row r: enrollments) {
				if(!r.getCourse_code().equals(temp)) {
					addToTimetable(r);
					text += "\n" + r.getCourse_code() + "\n";
					temp = r.getCourse_code();
				}
				text += r;
			}
			textAreaConsole.setText(text);
		}
    }
    
    void addToTimetable(List_row e) {
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Random r = new Random();
		String tempString = e.getCourse_code() + " " + e.getSection().split(" ")[0];
		String tempColor = e.getSection().split(" ")[1];
		int c1 = r.nextInt(25) * 10 + Character.getNumericValue(tempColor.charAt(1));
		int c2 = r.nextInt(25) * 10 + Character.getNumericValue(tempColor.charAt(2));
		int c3 = r.nextInt(2) * 100 + Character.getNumericValue(tempColor.charAt(4)) * 10 + Character.getNumericValue(tempColor.charAt(3));
		Background tempBackground = new Background(new BackgroundFill(Color.rgb(c1, c2, c3, 0.3), CornerRadii.EMPTY, Insets.EMPTY));
		
		//Create labels
		for(int i = 0; i < e.getNumSlot(); ++i) {
			Slot t = e.getSlot(i);
			Label l = new Label(tempString);
			l.setId("slotLabel" + e.getCourse_code() + e.getSection().split(" ")[0] + i);
			l.setTextFill(Color.rgb(0, 0, 0, 1));
			l.setFont(new Font("Times New Roman Bold", 10));
			l.setBackground(tempBackground);
			l.setLayoutX(102.0 + t.getDay() * 100.0); //left of label [102.0, 602.0] -> [Mo, Sa]
			l.setLayoutY(35.5 + (t.getStartHour() - 9 + t.getStartMinute()/60.0) * 21.0);  //top of label [25.0, 277.0] -> [0900, 2100]
			l.setMinWidth(100.0); //width of label should be 100.0
	    	l.setMaxWidth(100.0);
	    	l.setMinHeight(t.getDuration() * 21.0); //height of label should be 21.0/60.0 * minutes
	    	l.setMaxHeight(t.getDuration() * 21.0);
	    	ap.getChildren().addAll(l);
		}
    }
    
    void removeFromTimetable(List_row e) {
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	List<Node> temp = new ArrayList<Node>();
    	for(Node n: ap.getChildren()) {
    		if(n.getId() != null && n.getId().replaceFirst(".$", "").equals(e.getCourse_code() + e.getSection().split(" ")[0])) {
    			temp.add(n);
    		}
    	}
    	for(Node n: temp) {
    		ap.getChildren().remove(n);
    	}
    }

}
