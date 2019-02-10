package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 3;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}
	
	// Starting test cases for homework 2
	@Test
	void testUpdateEvent_OverMaxString_BadCase() {
		int eventID = 1;
		String newName = "New Event Name Char Over 20";
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, newName);
		});	
	}
	
	@Test
	void testUpdateEvent_EmptyString_GoodCase() throws StudyUpException {
		int eventID = 1;
		String newName = "";
		eventServiceImpl.updateEventName(eventID, newName);
		assertEquals(newName, DataStorage.eventData.get(eventID).getName());
	}
	
	// Can string be set to null -- check if this 
	// test case should throw an error
	@Test
	void testUpdateEvent_NullString_BadCase() throws StudyUpException {
		int eventID = 1;
		String newName = null;
		//Assertions.assertThrows(StudyUpException.class, () -> {
		eventServiceImpl.updateEventName(eventID, newName);			
		assertTrue(DataStorage.eventData.get(eventID).getName() == null);
		//});
	}
	
	@Test
	void testUpdateEvent_ChangeIDAgain_GoodCase() throws StudyUpException {
		int eventID = 2;
		Event event1 = new Event();
		event1.setEventID(eventID);
		event1.setName("Original");
		String newName = "New Event Name";
		
		DataStorage.eventData.put(event1.getEventID(), event1);
		
		eventServiceImpl.updateEventName(eventID, newName);
		assertEquals(newName, DataStorage.eventData.get(eventID).getName());
		
		event1.setEventID(5);
		DataStorage.eventData.put(event1.getEventID(), event1);
		assertEquals(newName, DataStorage.eventData.get(5).getName());
	}
	
	@Test
	void testUpdateEvent_CreateNewEvent_GoodCase() throws StudyUpException {
		int newEventID = 5;
		
		Event event2 = new Event();
		event2.setEventID(newEventID);
		event2.setDate(new Date());
		event2.setName("Event 2");
		Location location = new Location(100, 20);
		event2.setLocation(location);
		
		DataStorage.eventData.put(event2.getEventID(), event2);
		
		eventServiceImpl.updateEventName(newEventID, "Updated Event 2");
		assertEquals("Updated Event 2", DataStorage.eventData.get(newEventID).getName());
	}
	
	@Test
	void testUpdateEvent_OverwriteEmptyName_GoodCase() throws StudyUpException {
		int event1ID = 2;
		
		Event event1 = new Event();
		event1.setEventID(event1ID);
		
		DataStorage.eventData.put(event1.getEventID(), event1);
		eventServiceImpl.updateEventName(event1ID, "No original name");
		assertEquals("No original name", DataStorage.eventData.get(event1ID).getName());
	}
	
	@Test
	void testAddStudent_AddMultStudents_BadCase() throws StudyUpException {
		int newEventID = 11;
		
		Event event2 = new Event();
		event2.setEventID(newEventID);
		event2.setDate(new Date());
		event2.setName("Event 2");
		Location location = new Location(0, 0);
		event2.setLocation(location);
		List<Student> eventStudents2 = new ArrayList<>();
		event2.setStudents(eventStudents2);
		
		Student student1 = new Student();
		student1.setFirstName("Tom");
		student1.setLastName("Hank");
		student1.setEmail(null);
		student1.setId(0);
		
		Student student2 = new Student();
		student2.setFirstName("Jane");
		student2.setLastName("Jones");
		student2.setEmail("JaneJones@email.com");
		student2.setId(6);
		
		Student student3 = new Student();

		DataStorage.eventData.put(event2.getEventID(), event2);
		
		eventServiceImpl.addStudentToEvent(student1, newEventID);		
		eventServiceImpl.addStudentToEvent(student2, newEventID);		
		eventServiceImpl.addStudentToEvent(student3, newEventID);		
	

		assertTrue(DataStorage.eventData.get(newEventID).getStudents().size() <= 2);
	}
	
	@Test
	void testAddStudent_NullStudentList_GoodCase() throws StudyUpException {
		int newEventID = 9;
		
		Event event2 = new Event();
		event2.setEventID(newEventID);
		event2.setDate(new Date());
		event2.setName("Event 2");
		Location location = new Location(11, -19);
		event2.setLocation(location);
		
		Student student = new Student();
		student.setFirstName("Jack");
		student.setLastName("Johnson");
		
		DataStorage.eventData.put(event2.getEventID(), event2);
		
		eventServiceImpl.addStudentToEvent(student, newEventID);
		assertEquals(event2.getStudents(), DataStorage.eventData.get(newEventID).getStudents());
	}
	
	@Test
	void testAddStudent_NullStudentFirstName_GoodCase() throws StudyUpException {
		int newEventID = 7;
		
		Event event2 = new Event();
		event2.setEventID(newEventID);
		
		Student student = new Student();
		student.setFirstName(null);
		student.setLastName("Johnson");
		
		DataStorage.eventData.put(event2.getEventID(), event2);
		
		eventServiceImpl.addStudentToEvent(student, newEventID);
		assertEquals(null, DataStorage.eventData.get(newEventID).getStudents().get(0).getFirstName());
	}
	
	@Test
	void testAddStudent_WrongEvent_BadCase() {
		Student student = new Student();
		
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student, 2);
		});
	}
	
	// Confirm how to set Date to future date
	@Test 
	void testActiveData_TwoActiveEvents_GoodCase() {
		Event event1 = new Event();
		event1.setEventID(10);
		Date date1 = new Date(190, 1, 1);
		event1.setDate(date1);

		Event event2 = new Event();
		event2.setEventID(11);
		Date date2 = new Date(230, 2, 3);
		event2.setDate(date2);
		
		DataStorage.eventData.put(event1.getEventID(), event1);
		DataStorage.eventData.put(event2.getEventID(), event2);

		// assertEquals expect 3 -- the event created in @BeforeAll
		// and the two events created just now
		assertEquals(3, eventServiceImpl.getActiveEvents().size());
	}

	@Test 
	void testActiveData_OnePastEvent_BadCase() {
		Event event1 = new Event();
		event1.setEventID(10);
		Date date1 = new Date(130, 1, 1);
		event1.setDate(date1);

		Event event2 = new Event();
		event2.setEventID(11);
		Date date2 = new Date(200, 2, 3);
		event2.setDate(date2);
		
		Event event3 = new Event();
		event3.setEventID(12);
		Date date3 = new Date(97, 1, 1);
		event3.setDate(date3);
		
		DataStorage.eventData.put(event1.getEventID(), event1);
		DataStorage.eventData.put(event2.getEventID(), event2);
		DataStorage.eventData.put(event3.getEventID(), event3);

		// assertEquals expect 3 -- the event created in @BeforeAll
		// and the two events created just now
		assertEquals(3, eventServiceImpl.getActiveEvents().size());
	}
	
	// How to set Date to a past date?	
	@Test
	void testPastEvent_GoodCase() {
		Event event1 = new Event();
		event1.setEventID(9);
		Date date1 = new Date(63, 1, 1);
		event1.setDate(date1);
		
		DataStorage.eventData.put(event1.getEventID(), event1);
		
		assertEquals(1, eventServiceImpl.getPastEvents().size());
	}
	
	@Test
	void testDeleteEvent_GoodCase() {
		Event event1 = new Event();
		event1.setEventID(99);
		
		DataStorage.eventData.put(event1.getEventID(), event1);
		
		assertEquals(event1, eventServiceImpl.deleteEvent(99));
	}
	
	@Test
	void testDeleteEvent_WrongEvent_BadCase() {
		int eventID = 88;
		
		assertEquals(null, eventServiceImpl.deleteEvent(eventID));
	}
}

