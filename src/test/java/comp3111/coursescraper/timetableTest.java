package comp3111.coursescraper;

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
import javafx.scene.Scene;

public class timetableTest extends ApplicationTest {
	String baseURL;
	String term;
	String subject;
	Scraper scraper = new Scraper();
	private Scene s;

	@Before
	public void setUp() throws Exception {

		baseURL = new String("https://w5.ab.ust.hk/wcq/cgi-bin/");
		term = new String("1910");
		subject = new String("MATH");
	}

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
	public void testAllCoursesSearch() {	
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		sleep(5000);
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		Button b = (Button)s.lookup("#buttonSearchCourses");
		assertEquals(t.getText(), "");
	}
}