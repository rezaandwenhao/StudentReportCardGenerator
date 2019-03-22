import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class ReportCardMain {

    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Test> tests = new ArrayList<>();
    private static List<Mark> marks = new ArrayList<>();

    public static void main(String[] args) {
	parseCsv("courses.csv");
	parseCsv("marks.csv");
	parseCsv("students.csv");
	parseCsv("tests.csv");

//	Student s = new Student();

//	HashMap<String, Student> studentIdToStudentMap = s.getStudentIdToStudentMapping(students);
	HashMap<String, Student> studentIdToStudentMap = new HashMap<>();
	for (Student s : students) {
	    studentIdToStudentMap.put(s.getStudentId(), s);
	}

	/*
	 * for(Course course: courses) System.out.println(course.getName());
	 */

	/*
	 * Create a hashmap by looping the marks, take student id as key and tests as
	 * list of tests that he takes as value
	 */
	/* Even the student id is not in order, it will still work */
	HashMap<String, List<String>> studentIdToTestIdMap = new HashMap<>();
	for (Mark mark : marks) {
	    if (studentIdToTestIdMap.containsKey(mark.getStudent_id())) {
		studentIdToTestIdMap.get(mark.getStudent_id()).add(mark.getTest_id());
	    } else {
		List<String> newTestIdsList = new ArrayList<String>();
		newTestIdsList.add(mark.getTest_id());
		studentIdToTestIdMap.put(mark.getStudent_id(), newTestIdsList);
	    }
	}
	/*
	 * for (String str : studentIdToTestIdMap.keySet()) {
	 * System.out.println(studentIdToTestIdMap.get(str)); }
	 */
	/*
	 * Create a hashmap by looping the tests, take test id as key and course id as
	 * value
	 */
	HashMap<String, String> testIdToCourseIdMap = new HashMap<>();
	for (Test test : tests) {
	    testIdToCourseIdMap.put(test.getId(), test.getCourse_id());
	}

	// Figure out each student's course list
	for (Entry<String, List<String>> entry : studentIdToTestIdMap.entrySet()) {
	    // each entry represents one student
	    List<Course> myCourses = new ArrayList<>();
	    HashSet<String> courseIds = new HashSet<>();
	    for (String testId : entry.getValue()) { // one student's testId loop
		String courseId = testIdToCourseIdMap.get(testId); // get the corresponding courseId
		// generate a set of course ids for one student
		if (!courseIds.contains(courseId)) {
		    courseIds.add(courseId);
		}
	    }
	    // transfer each distinct course id to Course and add to one student's course
	    // list
	    courseIds.forEach(courseId -> {
		myCourses.add(getCourseWithCourseId(courseId));
	    });
	    // sorted student's course order in regarding to course id
	    Collections.sort(myCourses, (a, b) -> a.getId().compareTo(b.getId()));
	    studentIdToStudentMap.get(entry.getKey()).setCourses(myCourses);
	    /*
	     * for (Course course : myCourses) System.out.println(course.getName());
	     */ // test to print out each student's course name
	}

	Map<String, List<Test>> courseIdToTestIdsMap = new HashMap<>();
	Map<String, Double> testIdToWeightMap = new HashMap<>();

	for (Test t : tests) {
	    if (!courseIdToTestIdsMap.containsKey(t.getCourse_id()))
		courseIdToTestIdsMap.put(t.getCourse_id(), new ArrayList<>());
	    courseIdToTestIdsMap.get(t.getCourse_id()).add(t);
	    testIdToWeightMap.put(t.getId(), t.getWeight());
	}

	for (Student student : students) {
	    List<Double> finalGrades = new ArrayList<>();
	    for (Course course : student.getCourses()) {
		double finalGrade = 0;
		String courseId = course.getId();
		List<Test> relatedTests = courseIdToTestIdsMap.get(courseId);
		for (Test eachTest : relatedTests) {
		    finalGrade += testIdToWeightMap.get(eachTest.getId()) / 100
			    * getMarkWith2Ids(student.getStudentId(), eachTest.getId());
		}
		finalGrades.add(finalGrade); // when printing, needs to truncate to 2 decimals
	    }
	    student.setFinalGrades(finalGrades);
	    double totalGrade = 0;
	    for (double grade : finalGrades) {
		totalGrade += grade;
	    }
	    double totalAvg = totalGrade / finalGrades.size();
	    student.setTotalAvg(totalAvg);
	}

    }

    private static Course getCourseWithCourseId(String courseId) {
	for (Course course : courses) {
	    if (course.getId().equals(courseId)) {
		return course;
	    }
	}
	return null;
    }

    private static double getMarkWith2Ids(String studentId, String testId) {
	for (Mark mark : marks) {
	    if (mark.getStudent_id().equals(studentId) && mark.getTest_id().equals(testId)) {
		return mark.getMark();
	    }
	}
	return 0;
    }

    private static void parseCsv(String filename) {
	try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	    String line;
	    br.readLine();
	    while ((line = br.readLine()) != null) {
		String[] values = line.split(",");
		if (filename.equals("courses.csv")) {
		    courses.add(new Course(values[0], values[1], values[2]));
		} else if (filename.equals("marks.csv")) {
		    marks.add(new Mark(values[0], values[1], Double.valueOf(values[2])));
		} else if (filename.equals("students.csv")) {
		    students.add(new Student(values[0], values[1]));
		} else if (filename.equals("tests.csv")) {
		    tests.add(new Test(values[0], values[1], Double.valueOf(values[2])));
		} else {
		    throw new IllegalArgumentException("Filename is not valid");
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
