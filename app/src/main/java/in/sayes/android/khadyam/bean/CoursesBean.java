package in.sayes.android.khadyam.bean;

import java.util.ArrayList;

/**
 * @author sourav
 *
 */
public class CoursesBean {

	private String courseID;
	private String courseName;
	private String courseDescription;
	private String courseFees;
	private String courseDuration;
    private String courseThumbImage;
	private ArrayList<CourseDetails> courseDetails;


public static class CourseDetails {
		private String title;
		private ArrayList<RecipeBean> items;

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public ArrayList<RecipeBean> getItems() {
			return items;
		}
		public void setItems(ArrayList<RecipeBean> recipes) {
			this.items = recipes;
		}
	
	
}




	public String getCourseName() {
		return courseName;
	}




	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}




	public String getCourseDescription() {
		return courseDescription;
	}




	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}




	public String getCourseFees() {
		return courseFees;
	}




	public void setCourseFees(String courseFees) {
		this.courseFees = courseFees;
	}




	public String getCourseDuration() {
		return courseDuration;
	}




	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}




	public ArrayList<CourseDetails> getCourseDetails() {
		return courseDetails;
	}




	public String getCourseID() {
		return courseID;
	}




	public void setCourseID(String string) {
		this.courseID = string;
	}




	public void setCourseDetails(ArrayList<CourseDetails> courseDetails) {
		this.courseDetails = courseDetails;
	}


    public String getCourseThumbImage() {
        return courseThumbImage;
    }

    public void setCourseThumbImage(String courseThumbImage) {
        this.courseThumbImage = courseThumbImage;
    }
}