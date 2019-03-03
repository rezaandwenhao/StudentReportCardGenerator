
public class Test {
    private String id;
    private String course_id;
    private double weight;
    
    public Test(String id, String course_id, double weight) {
	this.id = id;
	this.course_id = course_id;
	this.weight = weight;
    }
    
    /**
     * @return the id
     */
    public String getId() {
	return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
	this.id = id;
    }
    /**
     * @return the course_id
     */
    public String getCourse_id() {
	return course_id;
    }
    /**
     * @param course_id the course_id to set
     */
    public void setCourse_id(String course_id) {
	this.course_id = course_id;
    }
    /**
     * @return the weight
     */
    public double getWeight() {
	return weight;
    }
    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
	this.weight = weight;
    }
}
