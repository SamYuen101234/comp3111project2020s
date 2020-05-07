package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.List;

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

public class ScapeTest extends ApplicationTest{
	Scraper scraper = new Scraper();
	private Scene s;

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
	public void testScraper() {
		Scraper scraper = new Scraper();
		List<Course> v = scraper.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910", "COMP");
		assertEquals(v.get(0).getTitle(), "COMP 1001 - Exploring Multimedia and Internet Computing (3 units)");
	}

	/*@Test
	public void testInvalidURL() {	
		TextField subject = (TextField)s.lookup("#textfieldSubject");
		subject.setText("UST");
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		Button b = (Button)s.lookup("#buttonSearchCourses");
		assertEquals(t.getText(), "404 Not Found: Invalid base URL or term or subject");
	}*/

}
