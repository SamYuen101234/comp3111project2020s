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

import java.util.Comparator;
import java.util.List;
public class FilterListTest extends ApplicationTest {
	private Scene s;
	private List_row section1 = new List_row();
	private List_row section2 = new List_row();
	private List_row section3 = new List_row();
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
	}
	
	@Before
	public void setUp() throws Exception{
		section1.set_Instructor("SAM");
		section1.setCourse_code("COMP3111");
		section1.setCourse_name("Software Engineer");
		section1.setSection("L1");
		controller.enrollments.add(section1);
		section2.set_Instructor("SAM");
		section2.setCourse_code("COMP3311");
		section2.setCourse_name("Database");
		section2.setSection("L1");
		controller.enrollments.add(section2);
		section3.set_Instructor("SAM");
		section3.setCourse_code("COMP3311");
		section3.setCourse_name("Database");
		section3.setSection("T1");
		controller.enrollments.add(section3);
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
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
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
		clickOn("#tabMain");
		clickOn("#buttonSearchCourses");
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
		assertEquals("COMP3111", section1.getCourse_code());
		assertEquals("Software Engineer", section1.getCourse_name());
		assertEquals("L1", section1.getSection());
		assertEquals("SAM", section1.getInstructor());
	}
	@Test
	public void testSelfPrint() {
		String enrolledResult = controller.print();
		System.out.println(enrolledResult);
	}
	@Test
	public void testComparator() {
		Comparator<List_row> a;
		Controller.List_rowComparator temp = controller.new List_rowComparator();
		temp.compare(section1, section2);
	}
	

}
