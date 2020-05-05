package comp3111.coursescraper;

import comp3111.coursescraper.Controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import org.testfx.framework.junit.ApplicationTest;
import comp3111.coursescraper.Scraper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DefaultTimetableTest extends ApplicationTest {
	private Scene s;
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

		clickOn("#tabMain");
		controller.search();
		clickOn("#tabTimetable");
		controller.refreshTimetable();
	}

	@Test
	public void testDefaultTimetable() {
		TabPane tp = (TabPane)s.lookup("#tabPane");
		Tab tabTimetable = tp.getTabs().get(4);
		AnchorPane ap = (AnchorPane)tabTimetable.getContent();
		assertEquals(ap.getChildren().size(), 38);
		boolean temp = false;
		for(Node n: ap.getChildren()) {
			if(n.getId() != null && n.getId().replaceFirst(".$", "").equals("slotLabelCOMP1001L1")) {
				temp = true;
				break;
			}
		}
		assertTrue(temp);
		TextArea console = (TextArea)s.lookup("#textAreaConsole");
		System.out.println("hahaha");
		System.out.println(console.getText());
		assertTrue(console.getText().contains("COMP 1021"));
		assertFalse(console.getText().contains("COMP 1022"));
	}
}