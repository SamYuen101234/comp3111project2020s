//package comp3111.coursescraper;
//
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//import java.util.List;
//
//public class TestBySam {
//	Course course = new Course();
//	Scraper scraper = new Scraper();
//	@Before
//	public void setUp() throws Exception{
//		course.setTitle("COMP3111");
//		course.setNumSlots(10);
//		course.setDescription("Good grade");
//	
//	}
//
//	@Test
//	public void testCourseTitle() {
//		assertEquals("COMP3111", course.getTitle());
//	}
//	@Test
//	public void testNumSlots() {
//		assertEquals(10, course.getNumSlots());
//	}
//	@Test
//	public void testDescription() {
//		assertEquals("Good grade", course.getDescription());
//	}
//	@Test
//	public void testGetSlot() {
//		assertNull(course.getSlot(5));
//	}
//	@Test
//	public void testnullGetSlot() {
//		assertNull(course.getSlot(0));
//	}
//	@Test
//	public void testScaper() {
//		List<Course> testScaper = scraper.scrape("We", "19/20", "lab");
//	}
//	
//
//}
