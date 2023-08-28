package student.studentIdentity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.studentIdentity.Exception.StudentNotFoundException;
import student.studentIdentity.Service.StudentService;
import student.studentIdentity.entity.Student;


import javax.validation.Valid;


@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody @Valid Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.ok("Student created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/update-marks")
    public ResponseEntity<?> updateStudentMarks(@PathVariable Long id, @RequestBody @Valid Student updatedStudent) {
        try {
            Student updated = studentService.updateStudentMarks(id, updatedStudent);
            return ResponseEntity.ok("Student marks updated successfully.");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.notFound().build();

        }


    }
}

