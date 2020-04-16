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


public class Controller {

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
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    private Scraper scraper = new Scraper();
    
    @FXML
    void allSubjectSearch() {
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }

    @FXML
    void search() {
    	textAreaConsole.clear();
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	Set<String> allInstructor = new HashSet<String>();
    	Set<String> unavailableInstructor = new HashSet<String>();
    	LocalTime time = LocalTime.parse("03:10PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    	if(v == null) textAreaConsole.setText("404 Not Found: Invalid base URL or term or subject");
    	else {
    		int noOfSection = 0;
	    	for (Course c : v) {
	    		String SID = "";
	    		String newline = c.getTitle() + "\n";
	    		for (int i = 0; i < c.getNumSlots(); i++) {
	    			Slot t = c.getSlot(i);
	    			newline += t + "\n";
	    			if(SID != t.getSectionID()) {
	    				++noOfSection;
	    				SID = t.getSectionID();
	    			}
	    			allInstructor.addAll(t.getAllInstructor());
	    			if(t.getStart() != null && time.isAfter(t.getStart()) && time.isBefore(t.getEnd())) unavailableInstructor.addAll(t.getAllInstructor());
	    		}
	    		
	    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
	    	}
	    	String additionalInfo = "";
	    	additionalInfo += "Total number of different sections: " + Integer.toString(noOfSection) + "\n";
	    	additionalInfo += "Total number of course: " + Integer.toString(v.size()) + "\n";
	    	allInstructor.remove("TBA");
	    	allInstructor.removeAll(unavailableInstructor);
	    	List<String> availableInstructor = new ArrayList<String>(allInstructor);
	    	Collections.sort(availableInstructor);
	    	additionalInfo += "Instructors who has teaching assignment this term but does not need to teach at Tu3:10pm:\n";
	    	for(int i = 0; i < availableInstructor.size(); ++i) {
	    		additionalInfo += availableInstructor.get(i) + "\n";
	    	}
	    	
	    	
	    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + additionalInfo);
    	}
    	
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
