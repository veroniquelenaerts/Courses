package courses_ir;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logicalcollections.LogicalSet;


/**
 * @invar | getUsername() != null
 * @invar | getCourses() != null
 * @invar | The association between students and courses is consistent.
 * 		| getCourses().stream.allMatch(c -> c.getStudents().contains(this))
 */
public class Student {
	
	public Set<Object> getPeerGroup(Object thisObject) {
		return LogicalSet.matching(m -> 
			m.contains(thisObject) && 
			m.allMatch(object -> 
				object instanceof Course ?
						((Course)object).name != null &&
						((Course)object).students != null 
				:
					
					((Student)object).username != null &&
					((Student)object).courses != null) &&
			m.allMatch(object -> 
				object instanceof Course ?
						((Course)object).students.values().stream().allMatch(s ->
							m.contains(s) &&
							s.courses.values().contains((Course)object)
						)	
					:
						((Student)object).courses.values().stream().allMatch(c ->
						m.contains(c) &&
						c.students.values().contains((Student)object)
					)
			)
		);
	}
	
	/**
	 * @invar |getPeerGroup() != null
	 */
	String username;
	Map<String, Course> courses = new HashMap<String, Course>();
	
	public String getUsername() { return username;}
	
	public Course getCourse(String name) {
		return courses.get(name);
//		for (Course course : courses)
//			if (course.name.equals(name))
//				return course;
//		return null;
	}
	
	public Set<Course> getCourses() {
		return Set.copyOf(courses.values());
	}
	
	public Student(String username) {
		this.username = username;
	}
	/**
	 * @post | result == Course.getPeerGroup(this).stream().filter(object ->
	 * 		|	object instanceof Student &&
	 * 		|	((Student)object).getCourses().equals(this.getCourses())
	 * 		| ).count()
	 */
	public int getNbStudentWithSameProgram() {
	//	Set<Student> students = new HashSet<Student>();
		int size = O;
		for (Course course : this.courses.values()) {
			for (Student student : course.getStudents())
				if (student.courses.equals(courses))
					size++;
			break;}
		
		return size;
	}

}
