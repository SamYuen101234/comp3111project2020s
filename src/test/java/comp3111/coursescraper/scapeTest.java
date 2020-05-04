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

public class scapeTest extends ApplicationTest{

	@Test
	public void testScraper() {
		Scraper scraper = new Scraper();
		List<Course> v = scraper.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910", "COMP");
		assertEquals(v.get(0).getTitle(), "COMP 1001 - Exploring Multimedia and Internet Computing (3 units)");
	}

}
