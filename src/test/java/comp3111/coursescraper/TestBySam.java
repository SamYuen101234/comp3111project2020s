package comp3111.coursescraper;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class TestBySam {
	Course course = new Course();
	Scraper scraper = new Scraper();
	@Before
	public void setUp() throws Exception{
		course.setTitle("COMP3111");
		course.setNumSections(1);
		course.setDescription("Good grade");
	}
	@Test
	public void testCourseTitle() {
		assertEquals("COMP3111", course.getTitle());
	}
	@Test
	public void testDescription() {
		assertEquals("Good grade", course.getDescription());
	}
	@Test
	public void testScaper() {
		List<Course> testScaper = scraper.scrape("We", "19/20", "lab");
	}
	

}
