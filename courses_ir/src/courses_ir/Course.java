package courses_ir;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logicalcollections.LogicalSet;

/**
 * @invar | getName() != null
 * @invar | getStudents() != null
 * @invar The association between courses and students is consistent.
 * 		| getStudents().stream().allMatch(s -> s.getCourses().contains(this))
 */

public class Course {
	
	public static Set<Object> getPeerGroup(Object thisObject) {
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
	 * @invar | getPeerGroup() != null
	 */
	
	String name;
	Map<String, Student> students = new HashMap<String, Student>();
	
	public String getName() {return name;}
	
	public Set<Student> getStudents(){
		return Set.copyOf(students.values());
	}
	
	public Student getStudent(String username) {
//		for (Student student : students) {
//			if (student.username.equals(username))
//				return student;
//		}
//		return null;
		return students.get(username);
	}
	
	public Course(String name) {
		this.name = name;
	}
	
	public void enroll(Student student) {
//		students.add(student);
//		student.courses.add(this);
		students.put(student.username, student);
		student.courses.put(this.name,this);
	}

}
