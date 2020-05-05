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
	public void testMaxSection() {
		Course c = new Course();
		Section s = new Section();
		for(int i = 0; i < 101; ++i) {
			c.addSection(s);
		}
		assertEquals(c.getNumSections(), 100);
	}
	
	@Test
	public void testGetNullSection() {
		Course c = new Course();
		Section s = c.getSection(-1);
		assertNull(s);
	}
	
	@Test
	public void testGetSection() {
		Course c = new Course();
		Section s = new Section();
		s.setSectionID("L1");
		c.addSection(s);
		assertEquals(c.getSection(0).getSectionID(), "L1");
	}
	
	@Test
	public void testHasLabOrTutorial() {
		Course c = new Course();
		c.setLabsOrTutorial();
		assertTrue(c.hasLabsOrTutorial());
	}
	
	@Test
	public void testMaxSlot() {
		Section s = new Section();
		Slot t = new Slot();
		for(int i = 0; i < 11; ++i) {
			s.addSlot(t);
		}
		assertEquals(s.getNumSlots(), 10);
	}
	
	@Test
	public void testGetNullSlot() {
		Section s = new Section();
		Slot t = s.getSlot(-1);
		assertNull(t);
	}
	
	@Test
	public void testGetEndMinute() {
		LocalTime time = LocalTime.parse("03:10PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		Slot t = new Slot();
		t.setEnd(time);
		assertEquals(t.getEndMinute(), 10);
	}
	
	@Test
	public void testListRowtoString() {
		Course c = new Course();
		c.setTitle("COMP 3111 - Software Engineering (4 units)");
		Section s = new Section();
		s.setSectionID("LA1");
		Slot t = new Slot();
		t.addInstructor("Kenneth");
		s.addSlot(t);
		List_row r = new List_row(c, s);
		assertEquals(r.toString(), "LA1\n");
	}
	
	@Test
	public void testListRowSetSelect() {
		List_row r = new List_row();
		r.setSelect(true);
		assertTrue(r.getSelect());
	}
	
	@Test
	public void testListRowGetSlot() {
		List_row r = new List_row();
		assertNull(r.getSlot());
		assertNull(r.getSlot(-1));
	}
	
	@Test
	public void testListRowGetSectionType() {
		Course c = new Course();
		c.setTitle("COMP 3111 - Software Engineering (4 units)");
		Section s = new Section();
		s.setSectionID("L1");
		Slot t = new Slot();
		t.addInstructor("Kenneth");
		s.addSlot(t);
		List_row r = new List_row(c, s);
		assertEquals(r.getSectionType(), "L");
	}
	
}
