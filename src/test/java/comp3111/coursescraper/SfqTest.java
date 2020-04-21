package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class SfqTest extends ApplicationTest{

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
	public void testInvalidInstructorUrl() {
		clickOn("#tabSfq");
		TextField t = (TextField)s.lookup("#textfieldSfqUrl");
		t.setText("http://sfq.ust.hk/results-wrong/eng-f19.html");
		clickOn("#buttonInstructorSfq");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"The inputted URL is not valid\n");
	}
	
	@Test
	public void testInvalidEnrolledUrl() {
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		clickOn("#tabSfq");
		TextField t = (TextField)s.lookup("#textfieldSfqUrl");
		t.setText("http://sfq.ust.hk/results-wrong/eng-f19.html");
		clickOn("#buttonSfqEnrollCourse");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"The inputted URL is not valid\n");
	}
	
	@Test
	public void testEnrollCourseSfq() {
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		clickOn("#tabSfq");
		clickOn("#buttonSfqEnrollCourse");
		
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertFalse(t2.getText().isEmpty());
	}

}
