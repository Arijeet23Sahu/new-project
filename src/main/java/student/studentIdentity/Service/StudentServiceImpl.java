package student.studentIdentity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.studentIdentity.Exception.StudentNotFoundException;
import student.studentIdentity.entity.Student;
import student.studentIdentity.repository.StudentRepository;

import java.time.LocalDate;
import java.time.Period;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private  StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        if (!isAgeValid(student.getDob()) || !areMarksValid(student)) {
            throw new IllegalArgumentException("Invalid age or marks");
        }

        calculateAndSetStudentMetrics(student);

        return studentRepository.save(student);
    }


        @Override
        public Student updateStudentMarks(Long id, Student updatedStudent) {
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (!studentOptional.isPresent()){
                throw new StudentNotFoundException(id);
            }

            Student student = studentOptional.get();
            student.setMarks1(updatedStudent.getMarks1());
            student.setMarks2(updatedStudent.getMarks2());
            student.setMarks3(updatedStudent.getMarks3());

            if (!areMarksValid(student)) {
                throw new IllegalArgumentException("Invalid marks");
            }

            calculateAndSetStudentMetrics(student);

            return studentRepository.save(student);
        }





    private boolean isAgeValid(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        return age > 15 && age <= 20;
    }

    private boolean areMarksValid(Student student) {
        return (student.getMarks1() == null || (student.getMarks1() >= 0 && student.getMarks1() <= 100))
                && (student.getMarks2() == null || (student.getMarks2() >= 0 && student.getMarks2() <= 100))
                && (student.getMarks3() == null || (student.getMarks3() >= 0 && student.getMarks3() <= 100));
    }

    private void calculateAndSetStudentMetrics(Student student) {
        int totalMarks = (student.getMarks1() != null ? student.getMarks1() : 0)
                + (student.getMarks2() != null ? student.getMarks2() : 0)
                + (student.getMarks3() != null ? student.getMarks3() : 0);

        double averageMarks = totalMarks / 3.0;
        String result = (student.getMarks1() >= 35 && student.getMarks2() >= 35 && student.getMarks3() >= 35)
                ? "Pass" : "Fail";

        student.setTotal(totalMarks);
        student.setAverage(averageMarks);
        student.setResult(result);
    }
}
