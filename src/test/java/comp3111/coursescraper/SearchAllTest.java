package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.Before;

import org.testfx.framework.junit.ApplicationTest;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import comp3111.coursescraper.Scraper;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SearchAllTest extends ApplicationTest {
	String[] subjectsList;
	String baseURL;
	String term;
	Scraper scraper = new Scraper();
	private Scene s;

	@Before
	public void setUp() throws Exception {
		subjectsList = new String[] {"AESF","CENG","CIVL","COMP","ECON",
				"ELEC","EMBA","ENGG","ENTR","ENVR","EVNG","EVSM","GFIN",
				"IEDA","IMBA","ISOM","LIFS","MATH","MECH","MGCS","MGMT",
				"PDEV","SBMT","SCIE","SHSS","SOSC","TEMG"};

		baseURL = new String("https://w5.ab.ust.hk/wcq/cgi-bin/");
		term = new String("1920");
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
	public void testScrapeSubjects() {
		try {
			assertArrayEquals(subjectsList,scraper.scrapeSubject(baseURL,term).toArray());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t.getText(),"Total Number of Categories/Code Prefix: 75");
	}

	/*@Test
	public void testAllCoursesSearch() {
		// Test number of courses correct		
		clickOn("#tabAllSubject");
		clickOn("#allCoursesSearch");
		clickOn("#allCoursesSearch");
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		sleep(60000);
		//assertEquals(t.getText(),"Total Number of Courses fetched: 1137\n");
		
		// Test courses detail print
		clickOn("#tabMain");
		clickOn("#buttonPrintAllSubjectCourses");
		sleep(1000);
		Boolean t1 = t.getText().length() > 200000;
		assertTrue(t1);
		
		// Test Sfq enroll course button enabled
		clickOn("#tabSfq");
		Button bu = (Button)s.lookup("#buttonSfqEnrollCourse");
		sleep(1000);
		assertFalse(bu.isDisabled());
	}*/
	
	@Test
	public void testPrintAll() {
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
		Button bu = (Button)s.lookup("#buttonPrintAllSubjectCourses");
		sleep(1000);
		bu.setDisable(false);
		clickOn("#buttonPrintAllSubjectCourses");
		sleep(1000);
		TextArea t = (TextArea)s.lookup("#textAreaConsole");
		System.out.println(t.getText());
		Boolean t1 = t.getText().length() > 2000;
		assertTrue(t1);
		
	}
	
	@Test
	public void testInvalidSubjectUrl() {
		clickOn("#tabMain");
		TextField t = (TextField)s.lookup("#textfieldURL");
		t.setText("https://w5.ab-wrong.ust.hk/wcq/cgi-bin/");
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		sleep(1000);
		assertEquals(t2.getText(),"The inputted URL is not valid");
			
	}
	
	@Test
	public void testInvalidCourseUrl() {
		clickOn("#tabMain");
		TextField t = (TextField)s.lookup("#textfieldURL");
		t.setText("https://w5.ab-wrong.ust.hk/wcq/cgi-bin/");
		clickOn("#tabAllSubject");
		TextArea t2 = (TextArea)s.lookup("#textAreaConsole");
		
		clickOn("#allCoursesSearch");
		sleep(1000);
		assertEquals(t2.getText(),"The inputted URL is not valid");
		
	}
}