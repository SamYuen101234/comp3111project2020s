package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import comp3111.coursescraper.Scraper;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SearchAllTest extends FxTest {
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
		assertArrayEquals(subjectsList,scraper.scrapeSubject(baseURL,term).toArray());
	}

	@Test
	public void testButton() {
		//FXMLLoader loader = new FXMLLoader();
    	//loader.setLocation(getClass().getResource("/ui.fxml"));
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		clickOn("#allCoursesSearch");
		Button bu = (Button)s.lookup("#allCoursesSearch");
		//sleep(1000);
		assertFalse(bu.isDisabled());
	}

}