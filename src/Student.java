import java.util.HashMap;
import java.util.List;

public class Student {
    private String name;
    /* assign id as string, so as other classes' ids. The purpose is for that letters can be used as id
     * When needed to be ordered, it will be ordered in ASCII order
     */
    private String id;  
    private double totalAvg;
    private List<Course> courses;
    private List<Double> finalGrades;
    
    public Student (String id, String name) {
	this.name = name;
	this.id = id;
    }
    
    public Student() {
	
    }
    
    public HashMap<String, Student> getStudentIdToStudentMapping(List<Student> students) {
	HashMap<String, Student> map = new HashMap<>();
	for (Student s : students) {
	    map.put(s.getStudentId(), s);
	}
	return map;
    }
    /**
     * @return the name
     */
    public String getName() {
	return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }
    /**
     * @return the studentId
     */
    public String getStudentId() {
	return id;
    }
    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
	this.id = studentId;
    }
    /**
     * @return the totalAvg
     */
    public double getTotalAvg() {
	return totalAvg;
    }
    /**
     * @param totalAvg the totalAvg to set
     */
    public void setTotalAvg(double totalAvg) {
	this.totalAvg = totalAvg;
    }
    /**
     * @return the courses
     */
    public List<Course> getCourses() {
	return courses;
    }
    /**
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
	this.courses = courses;
    }

    /**
     * @return the finalGrades
     */
    public List<Double> getFinalGrades() {
	return finalGrades;
    }

    /**
     * @param finalGrades the finalGrades to set
     */
    public void setFinalGrades(List<Double> finalGrades) {
	this.finalGrades = finalGrades;
    }
}
