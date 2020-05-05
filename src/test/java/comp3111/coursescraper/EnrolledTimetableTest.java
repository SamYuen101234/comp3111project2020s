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

public class EnrolledTimetableTest extends ApplicationTest {
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
		section1.setSectionID("L1 (1024)");
		section1.addSlot(slot1);
		course1.setTitle("COMP 2711 - Discrete Mathematical Tools for Computer Science (4 units)");
		course1.addSection(section1);
		List_row e1 = new List_row(course1, section1);
		controller.enrollments.add(e1);
		controller.removeFromTimetable(e1);
		
		clickOn("#tabTimetable");
		clickOn("#buttonRefresh");
		controller.refreshTimetable();
	}

	@Test
	public void testEnrolledTimetable() {
		TabPane tp = (TabPane)s.lookup("#tabPane");
		Tab tabTimetable = tp.getTabs().get(4);
		AnchorPane ap = (AnchorPane)tabTimetable.getContent();
		assertEquals(ap.getChildren().size(), 32);
		boolean temp = false;
		for(Node n: ap.getChildren()) {
			if(n.getId() != null && n.getId().replaceFirst(".$", "").equals("slotLabelCOMP3111LA8")) {
				temp = true;
				break;
			}
		}
		assertTrue(temp);
		clickOn("#tabMain");
		assertEquals(ap.getChildren().size(), 32);
		TextArea console = (TextArea)s.lookup("#textAreaConsole");
		System.out.println("hahaha");
		System.out.println(console.getText());
		assertTrue(console.getText().contains("COMP3111"));
		assertTrue(console.getText().contains("COMP2711"));
	}
}