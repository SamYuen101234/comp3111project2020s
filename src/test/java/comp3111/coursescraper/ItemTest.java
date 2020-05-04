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
	
	
}
