package student.studentIdentity.Service;

import student.studentIdentity.entity.Student;

public interface StudentService {
    Student createStudent(Student student);
    Student updateStudentMarks(Long id, Student updatedStudent);
}
