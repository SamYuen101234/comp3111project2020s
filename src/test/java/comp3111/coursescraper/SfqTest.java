package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class SfqTest extends ApplicationTest{

	private Scene s;
	
	/*@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}*/
	private Controller controller;

	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
   		controller = loader.getController();
   		Course course = new Course();
		Section section = new Section();
		Slot slot = new Slot();
		LocalTime start = LocalTime.parse("03:00PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		LocalTime end = LocalTime.parse("03:50PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		slot.setDay(1);
		slot.setStart(start);
		slot.setEnd(end);
		section.setSectionID("LA8 (8888)");
		section.addSlot(slot);
		course.setTitle("COMP 3111 - Software Engineering (4 units)");
		course.addSection(section);
		List_row e = new List_row(course, section);
		controller.enrollments.add(e);

		Course course1 = new Course();
		Section section1 = new Section();
		Slot slot1 = new Slot();
		LocalTime start1 = LocalTime.parse("04:00PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		LocalTime end1 = LocalTime.parse("04:50PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		slot1.setDay(2);
		slot1.setStart(start1);
		slot1.setEnd(end1);
		section1.setSectionID("L1 (1023)");
		section1.addSlot(slot1);
		course1.setTitle("ACCT 2010 - Principles of Accounting I (3 units)");
		course1.addSection(section1);
		List_row e1 = new List_row(course1, section1);
		controller.enrollments.add(e1);
		controller.removeFromTimetable(e1);
		
		clickOn("#tabSfq");
		//clickOn("#buttonRefresh");
		controller.findSfqEnrollCourse();
	}
	
	@Test
	public void testEnrolledSFQ() {
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"SFQ ratings of enrolled course(s):\nCOMP3111: 66.8%\nThe course ACCT2010 does not appear in the provided URL\n");
		
	}
	@Test
	public void testInvalidInstructorUrl() {
		clickOn("#tabSfq");
		TextField t = (TextField)s.lookup("#textfieldSfqUrl");
		t.setText("http://sfq.ust.hk/results-wrong/eng-f19.html");
		clickOn("#buttonInstructorSfq");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"The inputted URL is not valid");
	}
	
	
	
	@Test
	public void testInvalidEnrolledUrl() {
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		clickOn("#tabFilter");
		clickOn("#Tuesday");
		clickOn("#tabList");
		// enroll???
		
		clickOn("#tabSfq");
		TextField t = (TextField)s.lookup("#textfieldSfqUrl");
		t.setText("http://sfq.ust.hk/results-wrong/eng-f19.html");
		clickOn("#buttonSfqEnrollCourse");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"The inputted URL is not valid");
		//assertEquals(t2.getText(),"There are no enrolled course");
	}
	
	@Test
	public void testEnrollCourseSfq() {
		TextField t = (TextField)s.lookup("#textfieldSfqUrl");
		t.setText("/Usersâ�©/â�¨xieyulong/â�¨Desktop/sfq.html");
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		clickOn("#tabSfq");
		clickOn("#buttonSfqEnrollCourse");
		
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertFalse(t2.getText().isEmpty());
	}
	
	@Test 
	public void testNoEnrolledCourse() {
		controller.enrollments.clear();
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		clickOn("#tabSfq");
		clickOn("#buttonSfqEnrollCourse");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"There are no enrolled course");
		
	}

}
