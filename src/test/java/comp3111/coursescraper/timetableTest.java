package comp3111.coursescraper;

import comp3111.coursescraper.Controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import org.testfx.framework.junit.ApplicationTest;
import comp3111.coursescraper.Scraper;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class timetableTest extends ApplicationTest {
	private Scene s;
	private Controller controller = new Controller();

//	@Before
//	public void setUp() throws Exception {
//
//		baseURL = new String("https://w5.ab.ust.hk/wcq/cgi-bin/");
//		term = new String("1910");
//		subject = new String("MATH");
//	}

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
	}

	@Test
	public void testDefaultTimetable() {	
		TabPane tp = (TabPane)s.lookup("#tabPane");
		Tab tabTimetable = tp.getTabs().get(4);
		AnchorPane ap = (AnchorPane)tabTimetable.getContent();
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		clickOn("#tabTimetable");
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		assertEquals(ap.getChildren().size(), 37);
		assertFalse(t.getText().contains("COMP 1022"));
		clickOn("#tabMain");
		assertEquals(ap.getChildren().size(), 29);
	}

	@Test
	public void testEnrolledTimetable() {	
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
		course.setTitle("COMP 3111");
		course.addSection(section);
		List_row r = new List_row(course, section);
		controller.enrollments.add(r);
		TabPane tp = (TabPane)s.lookup("#tabPane");
		Tab tabTimetable = tp.getTabs().get(4);
		AnchorPane ap = (AnchorPane)tabTimetable.getContent();
		clickOn("#tabMain");
		clickOn("#tabTimetable");
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		assertEquals(ap.getChildren().size(), 30);
		boolean temp = false;
		for(Node n: ap.getChildren()) {
			if(n.getId() != null && n.getId().replaceFirst(".$", "").equals(r.getCourse_code() + r.getSection().split(" ")[0])) {
				temp = true;
				break;
			}
		}
		assertTrue(temp);
		clickOn("#tabMain");
		assertEquals(ap.getChildren().size(), 30);
	}
}