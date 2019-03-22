
public class Course {
    private String name;
    private String id;
    private String teacher;

    public Course(String id, String name, String teacher) {
	this.name = name;
	this.setId(id);
	this.teacher = teacher;
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
     * @return the teacher
     */
    public String getTeacher() {
	return teacher;
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(String teacher) {
	this.teacher = teacher;
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
}
