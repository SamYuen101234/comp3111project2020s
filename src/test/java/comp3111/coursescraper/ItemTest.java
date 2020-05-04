package comp3111.coursescraper;


import org.junit.Test;

import comp3111.coursescraper.Course;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class ItemTest {

	@Test
	public void testSetTitle() {
		Course i = new Course();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
	
	@Test
	public void testSetVenue() {
		Slot s = new Slot();
		s.setVenue("Principal Office");
		assertEquals(s.getVenue(), "Principal Office");
	}

	@Test
	public void testSetStart() {
		Slot s = new Slot();
		LocalTime time = LocalTime.parse("03:10PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		s.setStart(time);
		assertEquals(s.getStart(), time);
	}
	
	@Test
	public void testScraper() {
		Scraper scraper = new Scraper();
		List<Course> v = scraper.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910", "COMP");
		assertEquals(v.get(0).getTitle(), "COMP 1001 - Exploring Multimedia and Internet Computing (3 units)");
	}
}
