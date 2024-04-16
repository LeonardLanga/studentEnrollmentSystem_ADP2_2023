
package za.ac.cput.studentenrolmentsystem;

import java.io.Serializable;

/**
 *
 * @author Leonard
 */
public class Course implements Serializable
{
    private String courseCode; 
    private String courseTitle;
    private String courseDescription;

    public Course() 
    {
    }

    public Course(String courseCode, String courseTitle, String courseDescription) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
    }
    
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override
    public String toString() {
        return "Course{ " + "courseCode : " + courseCode + ", courseTitle : " + courseTitle + ", courseDescription : " + courseDescription + '}';
    }
   
    
    
    
}
