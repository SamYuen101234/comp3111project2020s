package comp3111.coursescraper;

import java.net.URLEncoder;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.DomText;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;



/**
 * WebScraper provide a sample code that scrape web content. After it is constructed, you can call the method scrape with a keyword, 
 * the client will go to the default url and parse the page by looking at the HTML DOM.  
 * <br>
 * In this particular sample code, it access to HKUST class schedule and quota page (COMP). 
 * <br>
 * https://w5.ab.ust.hk/wcq/cgi-bin/1830/subject/COMP
 *  <br>
 * where 1830 means the third spring term of the academic year 2018-19 and COMP is the course code begins with COMP.
 * <br>
 * Assume you are working on Chrome, paste the url into your browser and press F12 to load the source code of the HTML. You might be freak
 * out if you have never seen a HTML source code before. Keep calm and move on. Press Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your
 * mouse cursor around, different part of the HTML code and the corresponding the HTML objects will be highlighted. Explore your HTML page from
 * body &rarr; div id="classes" &rarr; div class="course" &rarr;. You might see something like this:
 * <br>
 * <pre>
 * {@code
 * <div class="course">
 * <div class="courseanchor" style="position: relative; float: left; visibility: hidden; top: -164px;"><a name="COMP1001">&nbsp;</a></div>
 * <div class="courseinfo">
 * <div class="popup attrword"><span class="crseattrword">[3Y10]</span><div class="popupdetail">CC for 3Y 2010 &amp; 2011 cohorts</div></div><div class="popup attrword"><span class="crseattrword">[3Y12]</span><div class="popupdetail">CC for 3Y 2012 cohort</div></div><div class="popup attrword"><span class="crseattrword">[4Y]</span><div class="popupdetail">CC for 4Y 2012 and after</div></div><div class="popup attrword"><span class="crseattrword">[DELI]</span><div class="popupdetail">Mode of Delivery</div></div>	
 *    <div class="courseattr popup">
 * 	    <span style="font-size: 12px; color: #688; font-weight: bold;">COURSE INFO</span>
 * 	    <div class="popupdetail">
 * 	    <table width="400">
 *         <tbody>
 *             <tr><th>ATTRIBUTES</th><td>Common Core (S&amp;T) for 2010 &amp; 2011 3Y programs<br>Common Core (S&amp;T) for 2012 3Y programs<br>Common Core (S&amp;T) for 4Y programs<br>[BLD] Blended learning</td></tr><tr><th>EXCLUSION</th><td>ISOM 2010, any COMP courses of 2000-level or above</td></tr><tr><th>DESCRIPTION</th><td>This course is an introduction to computers and computing tools. It introduces the organization and basic working mechanism of a computer system, including the development of the trend of modern computer system. It covers the fundamentals of computer hardware design and software application development. The course emphasizes the application of the state-of-the-art software tools to solve problems and present solutions via a range of skills related to multimedia and internet computing tools such as internet, e-mail, WWW, webpage design, computer animation, spread sheet charts/figures, presentations with graphics and animations, etc. The course also covers business, accessibility, and relevant security issues in the use of computers and Internet.</td>
 *             </tr>	
 *          </tbody>
 *      </table>
 * 	    </div>
 *    </div>
 * </div>
 *  <h2>COMP 1001 - Exploring Multimedia and Internet Computing (3 units)</h2>
 *  <table class="sections" width="1012">
 *   <tbody>
 *    <tr>
 *        <th width="85">Section</th><th width="190" style="text-align: left">Date &amp; Time</th><th width="160" style="text-align: left">Room</th><th width="190" style="text-align: left">Instructor</th><th width="45">Quota</th><th width="45">Enrol</th><th width="45">Avail</th><th width="45">Wait</th><th width="81">Remarks</th>
 *    </tr>
 *    <tr class="newsect secteven">
 *        <td align="center">L1 (1765)</td>
 *        <td>We 02:00PM - 03:50PM</td><td>Rm 5620, Lift 31-32 (70)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td></tr><tr class="newsect sectodd">
 *        <td align="center">LA1 (1766)</td>
 *        <td>Tu 09:00AM - 10:50AM</td><td>Rm 4210, Lift 19 (67)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td>
 *    </tr>
 *   </tbody>
 *  </table>
 * </div>
 *}
 *</pre>
 * <br>
 * The code 
 * <pre>
 * {@code
 * List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
 * }
 * </pre>
 * extracts all result-row and stores the corresponding HTML elements to a list called items. Later in the loop it extracts the anchor tag 
 * &lsaquo; a &rsaquo; to retrieve the display text (by .asText()) and the link (by .getHrefAttribute()).   
 * 
 *
 */
public class Scraper {
	private WebClient client;

	/**
	 * Default Constructor 
	 */
	public Scraper() {
		client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}
	
	String printCourses(List<Course> courses) {
		
    	Set<String> allInstructor = new HashSet<String>();
    	Set<String> unavailableInstructor = new HashSet<String>();
    	LocalTime time = LocalTime.parse("03:10PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    	if(courses == null) return "404 Not Found: Invalid base URL or term or subject";
    	else {
    		
    		String result = "";
    		int noOfSection = 0;
	    	for (Course c : courses) {
	    		//System.out.println(c.getTitle());
	    		noOfSection += c.getNumSections();
	    		String SID = "";
	    		result += c.getTitle() + "\n";
	    		for (int i = 0; i < c.getNumSections(); i++) {
	    			//System.out.println(c.getSection(i).getSectionID());
	    			Section s = c.getSection(i);
	    			result += s;
	    			allInstructor.addAll(s.getAllInstructor());
	    			//System.out.println(c.getSection(i).getAllInstructor().size());
	    			unavailableInstructor.addAll(s.getInstructorConstraint(time));
	    		}
	    		result += "\n";
	    		
	    	}
	    	String additionalInfo = "";
	    	additionalInfo += "Total number of different sections: " + Integer.toString(noOfSection) + "\n";
	    	additionalInfo += "Total number of course: " + Integer.toString(courses.size()) + "\n";
	    	allInstructor.remove("TBA");
	    	allInstructor.removeAll(unavailableInstructor);
	    	List<String> availableInstructor = new ArrayList<String>(allInstructor);
	    	Collections.sort(availableInstructor);
	    	additionalInfo += "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm:\n";
	    	for(int i = 0; i < availableInstructor.size(); ++i) {
	    		additionalInfo += availableInstructor.get(i) + "\n";
	    	}
	    	
	    	return result + "\n" + additionalInfo;
	    	
    	}
    	
    }

	private void addSection(HtmlElement e, Course c, boolean secondRow) {
		
		//Times
		LocalTime minStart = LocalTime.parse("09:00AM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		LocalTime maxEnd = LocalTime.parse("10:00PM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		String times[] =  e.getChildNodes().get(secondRow ? 0 : 3).asText().split("\n");
		//Handle time with dates
		if(times.length != 1) times = times[1].split(" ");
		else times = times[0].split(" ");
		
		//Venue
		String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();
		int index_temp = venue.indexOf("(");
		if(index_temp >=0) venue = venue.substring(0, index_temp-1);
		
		//Instructor
		DomNodeList<DomNode> temp = e.getChildNodes().get(secondRow ? 2 : 5).getChildNodes();
		
		//Section
		Section s = new Section();
		if(!secondRow) {
			String sectionID = e.getChildNodes().get(1).asText();
			if(sectionID.substring(0, 1).equals("T") || sectionID.substring(0, 2).equals("LA")) {
				c.setLabsOrTutorial();
			}
			else if(!sectionID.substring(0, 1).equals("L") && !sectionID.substring(0, 2).equals("LX")) return;
			s.setSectionID(sectionID);
		}
		else s = c.getSection(c.getNumSections()-1);
		
		//Add to course list
		if (!times[0].equals("TBA")) {
			LocalTime start = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("hh:mma", Locale.US));
			LocalTime end = LocalTime.parse(times[3], DateTimeFormatter.ofPattern("hh:mma", Locale.US));
			if(!start.isBefore(minStart) && !end.isAfter(maxEnd)) {
				for (int j = 0; j < times[0].length(); j+=2) {
					String code = times[0].substring(j , j + 2);
					if (Slot.DAYS_MAP.get(code) == null)
						break;
					Slot t = new Slot();
					t.setDay(Slot.DAYS_MAP.get(code));
					t.setStart(start);
					t.setEnd(end);
					t.setVenue(venue);
					for(int i = 0; i < temp.getLength(); i += 2) {
						t.addInstructor(temp.get(i).getTextContent());
					}
					s.addSlot(t);
				}
			}
		}
		c.addSection(s);
	}
	
	public List<String> scrapeSubject(String baseurl, String term){
		HtmlPage mainPage;
		try {
			mainPage = client.getPage(baseurl + "/" + term + "/");
		} catch(Exception e) {
			return null;
		}

		List<?> subjectHTML = (List<?>) mainPage.getByXPath("//div[@class='depts']/a");

		Vector<String> subjects = new Vector<String>();

		for (HtmlElement e: (List<HtmlElement>) subjectHTML) {
			String subj = new String();

			subj = e.asText();

			subjects.add(subj);
		}

		return subjects;

	}


	public List<Course> scrape(String baseurl, String term, String sub) {
		
		HtmlPage page;

		try {
			
			page = client.getPage(baseurl + "/" + term + "/subject/" + sub);
			
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
			
		List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
		
		Vector<Course> result = new Vector<Course>();

		for (int i = 0; i < items.size(); i++) {
			Course c = new Course();
			HtmlElement htmlItem = (HtmlElement) items.get(i);
			
			HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
			c.setTitle(title.asText());
			
			List<?> popupdetailslist = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
			HtmlElement exclusion = null;
			c.isCC(false);
			for ( HtmlElement e : (List<HtmlElement>)popupdetailslist) {
				HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
				HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
				if (d.asText().contains("Common Core")) c.isCC(true);
				if (t.asText().equals("EXCLUSION")) {
					exclusion = d;
				}
			}
			c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
			
			List<?> sections = (List<?>) htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
			for ( HtmlElement e: (List<HtmlElement>)sections) {
				addSection(e, c, false);
				e = (HtmlElement)e.getNextSibling();
				if (e != null && !e.getAttribute("class").contains("newsect"))
					addSection(e, c, true);
			}
			result.add(c);
		}
		client.close();
		return result;
	}
	
	private List<String> getSFQSubject(HtmlElement subjectTable){
		List<?> link = (List<?>) subjectTable.getByXPath(".//a");
		Vector<String> subjects = new Vector<String>();
		
		for(HtmlAnchor a: (List<HtmlAnchor>)link){
			String l = a.getHrefAttribute();
			if (!l.equals("#notes")) {
				subjects.add(l.substring(1));
			}
		}
		
		return subjects;
	}
	
	private HtmlElement getNextTable(HtmlElement curr) {
		HtmlElement target = (HtmlElement) curr.getNextElementSibling();
		while(!target.getClass().getName().equals("com.gargoylesoftware.htmlunit.html.HtmlTable")) {
			target = (HtmlElement) curr.getNextElementSibling();
			curr = target;
		}
		return target;
	}
	
	private List<HtmlElement> getAllSubjectsSFQTable(String baseurl){
		try {
			HtmlPage page = client.getPage(baseurl);
			
			// Get the table containing all subjects' ID
			HtmlElement dummy = (HtmlElement) page.getFirstByXPath("//table[@*]");
			HtmlElement subjectsList = getNextTable(dummy);

			// Get all subjects' ID
			List<String> subjects = getSFQSubject(subjectsList);
			
			// Find the table for each subject in subjects
			Vector<HtmlElement> allSubjects = new Vector<HtmlElement>();
			for(String s: subjects) {
				HtmlElement sub = (HtmlElement) page.getFirstByXPath("//b[@id='" + s + "']");
				HtmlElement subTable = getNextTable(sub);
				
				allSubjects.add(subTable);
			}
			
			return allSubjects;
			
		} catch (Exception e) {
			return null;
		}		
	}
	
	public HashMap<String,Float> scrapeCoursesSFQ(String baseurl, HashSet<String> enrolled) {
		List<HtmlElement> allSubjects = getAllSubjectsSFQTable(baseurl);
		if(allSubjects==null)
			return null;
		
		HashMap<String,Float> input = new HashMap<String,Float>();
		Float sfqTotal;
		Integer number;
		
		for(HtmlElement e: allSubjects) {
			HtmlElement tr = e.getFirstByXPath(".//tr");
			
			// Find the row that would contain a course and its section (1st requirement: every row start with <tr> only)
			while(!tr.asText().equals("HtmlTableRow[<tr>]") && tr.getAttribute("style").equals("")) {
				// (2nd requirement: the first column should have colspan=3 to contain the course title)
				HtmlElement td = tr.getFirstByXPath(".//td[@colspan='3']");
				if(td != null) {
					String course = td.getChildNodes().get(0).asText();
					course = course.replaceAll("\\s+","");
					// (3rd requirement: second column should contain the course title, and it should match the enrolled course)
					if(enrolled.contains(course)) {
						sfqTotal = 0F; number = 0;
						// (4th requirement: any row below should not contain colspan=3)
						HtmlElement tr3 = (HtmlElement) tr.getNextElementSibling();
						while(tr3.getFirstByXPath(".//td[@colspan='3']")==null) {
							// (5th requirement: the first column should contain only whitespace)
							td = tr3.getFirstByXPath(".//td");
							if(td.getChildNodes().get(0).asText().equals(" ")) {
								// (6th requirement: the second column should contain the course section)
								HtmlElement td2 = (HtmlElement) td.getNextElementSibling();
								if(!td2.getChildNodes().get(0).asText().equals(" ")) {
									td = (HtmlElement) td2.getNextElementSibling();
									td2 = (HtmlElement) td.getNextElementSibling();
									String sfqString = td2.getChildNodes().get(0).asText().substring(0,4);
									if(sfqString.equals("-(-)")){
										// if sfq is initially null, ignore it
										if(sfqTotal!=null) {
											// if it is the first sfq entry, set sfq to null
											if(sfqTotal==0F) {
												sfqTotal = null;
											}
										}
									}
									else {
										// if sfq is not null, increment the section by 1, and add up the sfq score
										++number;
										if(sfqTotal==null) {
											sfqTotal = Float.valueOf(sfqString);
										}
										else {
											sfqTotal += Float.valueOf(sfqString);
										}
									} // end if									
								} // end if
							} // end if 
							tr = (HtmlElement) tr3.getNextElementSibling();
							tr3 = tr;
						} // end while
						Float average;
						if(sfqTotal==null) {
							average = null;
						}
						else {
							average = sfqTotal/number;
						}
						input.put(course, average);
						tr = (HtmlElement) tr3.getPreviousElementSibling();						
					} // end if				
				} // end if	
				HtmlElement tr2 = (HtmlElement) tr.getNextElementSibling();
				tr = tr2;
			} // end while
		} // end for
		
		
		return input;
	}
	
	
	public HashMap<String,Vector<Float>> scrapeInstructorSFQ(String baseurl){
		List<HtmlElement> allSubjects = getAllSubjectsSFQTable(baseurl);
		
		if(allSubjects==null)
			return null;
		HashMap<String,Vector<Float>> input = new HashMap<String,Vector<Float>>();
		
		// Find instructors in each subjects and their corresponding SFQ, and put into the HashMap input
		for(HtmlElement e: allSubjects) {
			HtmlElement tr = e.getFirstByXPath(".//tr");
			
			// Find the row that would contain instructor name (1st requirement: every row start with <tr> only)
			while(!tr.asText().equals("HtmlTableRow[<tr>]") && tr.getAttribute("style").equals("")) {
				HtmlElement td = tr.getFirstByXPath(".//td");
				// (2nd requirement: the row should contain <td>)
				if(td != null) {
					// (3rd requirement: first column should have only white space)
					if(td.getChildNodes().get(0).asText().equals(" ")) {
						HtmlElement td2 = (HtmlElement) td.getNextElementSibling();
						// (4th requirement: second column should have only white space)
						if(td2.getChildNodes().get(0).asText().equals(" ")) {
							td = (HtmlElement) td2.getNextElementSibling();
							String te = td.getChildNodes().get(0).asText();
							te = te.substring(1,te.length()-1);
							// (5th requirement: third column should contain instructor's name)
							if(!te.isEmpty()) {
								// Get the instructor SFQ rating
								td2 = (HtmlElement) td.getNextElementSibling();
								td = (HtmlElement) td2.getNextElementSibling();
								// Extract only the value not in bracket, and change to float
								String temp = td.getChildNodes().get(0).asText().substring(0,4);
								Float sfq;
								if(temp.equals("-(-)")) {
									sfq = null;
								}
								else {
									sfq = Float.valueOf(temp);
								}
								
								Vector<Float> value = input.get(te);
								if(value==null) { // instructor not appeared before
									// Initialize the vector with [SFQ rating, no. of sections teach]
									Vector<Float> sfqAndCourseNo = new Vector<Float>(2);
									sfqAndCourseNo.add(sfq);
									if(sfq==null) {
										sfqAndCourseNo.add(0F);
									}
									else {
										sfqAndCourseNo.add(1F);
									}
									// Put the entry into the HashMap
									input.put(te,sfqAndCourseNo);									
								}
								else { // instructor appeared before
									if(sfq!=null) {
										// Add up the SFQ
										if(value.get(0)==null) {
											value.set(0, sfq);
										}
										else {
											value.set(0, value.get(0)+sfq);
										}
										// Increment the number of courses teaches by 1
										value.set(1, value.get(1)+1);	
									}
								} // end if
							} // end if 							
						} // end if
					} // end if						
				} // end if 
				HtmlElement tr2 = (HtmlElement) tr.getNextElementSibling();
				tr = tr2;
			} // end while
		} // end for
		return input;
	} // end function
	
	

}
