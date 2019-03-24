import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCardMainTest {

    @BeforeEach
    void setUp() throws Exception {
	ReportCardMain.parseCsv("courses.csv");
	ReportCardMain.parseCsv("marks.csv");
	ReportCardMain.parseCsv("students.csv");
	ReportCardMain.parseCsv("tests.csv");
    }

    @Test
    void setEachStudentCoursesTest() {
	DecimalFormat df2 = new DecimalFormat("#.00");
	Map<String, Student> studentIdToStudentMap = new HashMap<>();
	Map<String, List<String>> studentIdToTestIdMap = new HashMap<>();
	Map<String, String> testIdToCourseIdMap = new HashMap<>();
	Map<String, List<TEst>> courseIdToTestIdsMap = new HashMap<>();
	Map<String, Double> testIdToWeightMap = new HashMap<>();
	Map<String, Course> courseIdToCourseMap = new HashMap<>();
	Map<Entry<String, String>, Double> twoIdsToMarkMap = new HashMap<>();
	ReportCardMain.createMaps(studentIdToStudentMap, studentIdToTestIdMap, testIdToCourseIdMap,
		courseIdToTestIdsMap, testIdToWeightMap, courseIdToCourseMap, twoIdsToMarkMap);

	// test the method setEachStudentCourses. Insertions on the name of the course
	// to see if the course is generated correctly
	ReportCardMain.setEachStudentCourses(studentIdToTestIdMap, testIdToCourseIdMap, studentIdToStudentMap,
		courseIdToCourseMap);
	String firstStudent1stCourse = ReportCardMain.students.get(0).getCourses().get(0).getName();
	String firstStudent2ndCourse = ReportCardMain.students.get(0).getCourses().get(1).getName();
	String thirdStudent3rdCourse = ReportCardMain.students.get(2).getCourses().get(2).getName();
	Assert.assertEquals("Biology", firstStudent1stCourse);
	Assert.assertEquals("History", firstStudent2ndCourse);
	Assert.assertEquals("Math", thirdStudent3rdCourse);

	// test the method calculateGrades. Insertions on both course grade and final
	// average grade
	ReportCardMain.calculateGrades(courseIdToTestIdsMap, testIdToWeightMap, twoIdsToMarkMap);
	Double firstStuHisGrade = ReportCardMain.students.get(0).getFinalGrades().get(1);
	Assert.assertEquals("51.80", df2.format(firstStuHisGrade));
	Double thirdStuMathGrade = ReportCardMain.students.get(2).getFinalGrades().get(2);
	Assert.assertEquals("74.20", df2.format(thirdStuMathGrade));
	Double secondStuTotAvg = ReportCardMain.students.get(1).getTotalAvg();
	Assert.assertEquals("62.15", df2.format(secondStuTotAvg));
    }
}
