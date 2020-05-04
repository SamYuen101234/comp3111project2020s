package comp3111.coursescraper;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class FilterTest extends ApplicationTest {
	
	private Scene s;
	private List_row list_row = new List_row();
	
	@Before
	public void setUp() throws Exception{
		list_row.set_Instructor("SAM");
		list_row.setCourse_code("COMP3111");
		list_row.setCourse_name("Software Engineer");
		list_row.setSection("L1");
		list_row.setSelect(false);
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
	public void testSelectAllButton() {
		clickOn("#tabFilter");
		clickOn("#selectALL");
		Button b = (Button)s.lookup("#selectALL");
		sleep(500);
		assertTrue(b.getText().contentEquals("De-select All"));
		clickOn("#selectALL");
		b = (Button)s.lookup("#selectALL");
		assertTrue(b.getText().contentEquals("Select All"));
		sleep(500);
	}
	@Test
	public void testCheckBox() {
		clickOn("#tabAllSubject");
		clickOn("#allCoursesSearch");
		sleep(1000);
		clickOn("#tabFilter");
		clickOn("#AM");
		sleep(100);
		CheckBox temp = (CheckBox)s.lookup("#AM");
		assertTrue(temp.isSelected());
		clickOn("#PM");
		sleep(100);
		temp = (CheckBox)s.lookup("#PM");
		assertTrue(temp.isSelected());
		clickOn("#PM");
		sleep(100);
		temp = (CheckBox)s.lookup("#PM");
		assertFalse(temp.isSelected());
		clickOn("#Monday");
		sleep(100);
		temp = (CheckBox)s.lookup("#Monday");
		assertTrue(temp.isSelected());
		clickOn("#AM");
		sleep(100);
		temp = (CheckBox)s.lookup("#AM");
		assertFalse(temp.isSelected());
		clickOn("#NoExclusion");
		sleep(100);
		temp = (CheckBox)s.lookup("#NoExclusion");
		assertTrue(temp.isSelected());
		clickOn("#With_Labs_Tutorial");
		sleep(100);
		temp = (CheckBox)s.lookup("#With_Labs_Tutorial");
		assertTrue(temp.isSelected());
		clickOn("#Wednesday");
		sleep(100);
		temp = (CheckBox)s.lookup("#Wednesday");
		assertTrue(temp.isSelected());
		clickOn("#Thursday");
		sleep(100);
		temp = (CheckBox)s.lookup("#Thursday");
		assertTrue(temp.isSelected());
		clickOn("#Friday");
		sleep(100);
		temp = (CheckBox)s.lookup("#Friday");
		assertTrue(temp.isSelected());
		clickOn("#Saturday");
		sleep(100);
		temp = (CheckBox)s.lookup("#Saturday");
		assertTrue(temp.isSelected());
		clickOn("#CommonCore");
		sleep(100);
		temp = (CheckBox)s.lookup("#CommonCore");
		assertTrue(temp.isSelected());
	}
	
	@Test
	public void testListTable() {
		clickOn("#tabAllSubject");
		clickOn("#allCoursesSearch");
		sleep(1000);
		clickOn("#tabFilter");
		clickOn("#Monday");
		sleep(100);
		CheckBox temp = (CheckBox)s.lookup("#Monday");
		assertTrue(temp.isSelected());
		clickOn("#tabList");
	}
	
	@Test
	public void testList_row() {
		assertEquals("COMP3111", list_row.getCourse_code());
		assertEquals("Software Engineer", list_row.getCourse_name());
		assertEquals("L1", list_row.getSection());
		assertEquals("SAM", list_row.getInstructor());

	}
	
	

}
