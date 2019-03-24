import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

public class ReportCardMain {

    static List<Course> courses = new ArrayList<>();
    static List<Student> students = new ArrayList<>();
    static List<TEst> tests = new ArrayList<>();
    static List<Mark> marks = new ArrayList<>();

    public static void main(String[] args) {
	// parse each input file to fill the courses, students, tests, marks lists
	parseCsv("courses.csv");
	parseCsv("marks.csv");
	parseCsv("students.csv");
	parseCsv("tests.csv");

	// Using hashmap so that data entry doesn't need to be in order according to id.
	Map<String, Student> studentIdToStudentMap = new HashMap<>();
	Map<String, List<String>> studentIdToTestIdMap = new HashMap<>();
	Map<String, String> testIdToCourseIdMap = new HashMap<>();
	Map<String, List<TEst>> courseIdToTestIdsMap = new HashMap<>();
	Map<String, Double> testIdToWeightMap = new HashMap<>();
	Map<String, Course> courseIdToCourseMap = new HashMap<>();
	Map<Entry<String, String>, Double> twoIdsToMarkMap = new HashMap<>();
	createMaps(studentIdToStudentMap, studentIdToTestIdMap, testIdToCourseIdMap, courseIdToTestIdsMap,
		testIdToWeightMap, courseIdToCourseMap, twoIdsToMarkMap);

	setEachStudentCourses(studentIdToTestIdMap, testIdToCourseIdMap, studentIdToStudentMap, courseIdToCourseMap);
	calculateGrades(courseIdToTestIdsMap, testIdToWeightMap, twoIdsToMarkMap);
	generateOutput();
    }

    /**
     * Create all the needed maps
     * 
     * @param studentIdToStudentMap, studentIdToTestIdMap, testIdToCourseIdMap,
     *        courseIdToTestIdsMap, testIdToWeightMap, courseIdToCourseMap,
     *        twoIdsToMarkMap
     */
    static void createMaps(Map<String, Student> studentIdToStudentMap,
	    Map<String, List<String>> studentIdToTestIdMap, Map<String, String> testIdToCourseIdMap,
	    Map<String, List<TEst>> courseIdToTestIdsMap, Map<String, Double> testIdToWeightMap,
	    Map<String, Course> courseIdToCourseMap, Map<Entry<String, String>, Double> twoIdsToMarkMap) {
	// Student Id to student Mapping by looping students array list
	for (Student s : students) {
	    studentIdToStudentMap.put(s.getStudentId(), s);
	}
	// Student Id to test ids list mapping by looping marks array list
	// Also create a map where key is an entry (test_id, student id pair), the value
	// is the mark corresponding to the given student id and test id
	for (Mark mark : marks) {
	    if (studentIdToTestIdMap.containsKey(mark.getStudent_id())) {
		studentIdToTestIdMap.get(mark.getStudent_id()).add(mark.getTest_id());
	    } else {
		List<String> newTestIdsList = new ArrayList<String>();
		newTestIdsList.add(mark.getTest_id());
		studentIdToTestIdMap.put(mark.getStudent_id(), newTestIdsList);
	    }
	    Map.Entry<String, String> en = new AbstractMap.SimpleEntry<String, String>(mark.getTest_id(),
		    mark.getStudent_id());
	    twoIdsToMarkMap.put(en, mark.getMark());
	}
	// Course id to test ids list mapping, test id to weight mapping, test id to
	// course id mapping
	// by looping tests array list
	for (TEst t : tests) {
	    testIdToCourseIdMap.put(t.getId(), t.getCourse_id());
	    if (!courseIdToTestIdsMap.containsKey(t.getCourse_id()))
		courseIdToTestIdsMap.put(t.getCourse_id(), new ArrayList<>());
	    courseIdToTestIdsMap.get(t.getCourse_id()).add(t);
	    testIdToWeightMap.put(t.getId(), t.getWeight());

	}
	// Course id to Course mapping by looping courses array list
	for (Course c : courses) {
	    courseIdToCourseMap.put(c.getId(), c);
	}
    }

    /**
     * For each student, loop all his/her course and calculate the final grade for
     * the course. When all final grades are set, calculate the total average grade
     * 
     * @param courseIdToTestIdsMap, testIdToWeightMap
     */
    static void calculateGrades(Map<String, List<TEst>> courseIdToTestIdsMap,
	    Map<String, Double> testIdToWeightMap, Map<Entry<String, String>, Double> twoIdsToMarkMap) {
	for (Student student : students) {
	    List<Double> finalGrades = new ArrayList<>();
	    // Since I am looping the courses under each student, the grades stored in the
	    // grades list
	    // will be in corresponding same index as the courses list
	    for (Course course : student.getCourses()) {
		double finalGrade = 0;
		String courseId = course.getId();
		List<TEst> relatedTests = courseIdToTestIdsMap.get(courseId);
		for (TEst eachTest : relatedTests) {
		    Map.Entry<String, String> en = new AbstractMap.SimpleEntry<String, String>(eachTest.getId(),
			    student.getStudentId());
		    // If a student has not completed the course, meaning he/she took some tests but
		    // not all, throw an exception. Count the mark as 0 for the test and continue
		    // the program
		    if (!twoIdsToMarkMap.containsKey(en)) {
			try {
			    throw new Exception("Student " + student.getName() + " does not have grade for test "
				    + eachTest.getId() + " of the Course: " + course.getName()
				    + ". The mark for the test is counted as 0 to calculate the grade of the course.");
			} catch (Exception e) {
			    // Now I print out in the console, it will be a good practice to print to log
			    System.out.println(e.getMessage());
			    finalGrade += 0;
			}
		    } else {
			finalGrade += testIdToWeightMap.get(eachTest.getId()) / 100 * twoIdsToMarkMap.get(en);
		    }
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

    /**
     * Figure out each student's course list
     * 
     * @param studentIdToTestIdMap, testIdToCourseIdMap, studentIdToStudentMap
     */
    static void setEachStudentCourses(Map<String, List<String>> studentIdToTestIdMap,
	    Map<String, String> testIdToCourseIdMap, Map<String, Student> studentIdToStudentMap,
	    Map<String, Course> courseIdToCourseMap) {
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
		myCourses.add(courseIdToCourseMap.get(courseId));
	    });
	    // sorted student's course order in regarding to course id
	    Collections.sort(myCourses, (a, b) -> a.getId().compareTo(b.getId()));
	    studentIdToStudentMap.get(entry.getKey()).setCourses(myCourses);
	}
    }

    /**
     * All information is ready, loop each student to output a txt file
     */
    private static void generateOutput() {
	DecimalFormat df2 = new DecimalFormat("#.00");
	try {
	    FileWriter fw = new FileWriter("output.txt", false);
	    StringBuilder sb = new StringBuilder();
	    for (Student student : students) {
		sb.append("Student Id: " + student.getStudentId() + ", ");
		sb.append("name: " + student.getName() + System.lineSeparator());
		sb.append("Total Average:	" + df2.format(student.getTotalAvg()) + "%");
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		int counter = 0;
		for (Course course : student.getCourses()) {
		    double courseGrade = student.getFinalGrades().get(counter);
		    sb.append("	Course: " + course.getName() + ", Teacher: " + course.getTeacher()
			    + System.lineSeparator());
		    sb.append("	Final Grade:	" + df2.format(courseGrade) + "%" + System.lineSeparator());
		    sb.append(System.lineSeparator());
		    counter++;
		}
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
	    }
	    fw.write(sb.toString());
	    fw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Giving the file name, parse the csv file to fill the corresponding array list
     * 
     * @param filename
     */
    static void parseCsv(String filename) {
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
		    tests.add(new TEst(values[0], values[1], Double.valueOf(values[2])));
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
