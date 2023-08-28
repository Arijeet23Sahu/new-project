package student.studentIdentity.Exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Student not found with ID: " + id);
    }
}
