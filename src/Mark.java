
public class Mark {
    private String test_id;
    private String student_id;
    private double mark;

    public Mark(String test_id, String student_id, double mark) {
	this.test_id = test_id;
	this.student_id = student_id;
	this.mark = mark;
    }

    /**
     * @return the test_id
     */
    public String getTest_id() {
	return test_id;
    }

    /**
     * @param test_id the test_id to set
     */
    public void setTest_id(String test_id) {
	this.test_id = test_id;
    }

    /**
     * @return the student_id
     */
    public String getStudent_id() {
	return student_id;
    }

    /**
     * @param student_id the student_id to set
     */
    public void setStudent_id(String student_id) {
	this.student_id = student_id;
    }

    /**
     * @return the mark
     */
    public double getMark() {
	return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(double mark) {
	this.mark = mark;
    }
}
