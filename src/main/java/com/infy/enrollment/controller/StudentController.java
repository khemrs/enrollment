package com.infy.enrollment.controller;

import com.infy.enrollment.entity.Student;
import com.infy.enrollment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment/api/student")


public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id")int id ){
        return studentService.getStudentById(id);

    }
    @GetMapping()
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();

    }
    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);

    }
    @PutMapping("/updateStudent")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);

    }
    @DeleteMapping("/{id}")
    public void  deleteStudent(@PathVariable ("id")int id){
         studentService.deleteStudent(id);;

    }
    @PostMapping("/enroll")
    public Student enrollStudent(@RequestParam Integer studentId,@RequestParam Integer courseId){
        return studentService.enrollInCourse(studentId,courseId);

    }
    @PostMapping("/unenroll")
    public Student unenrollStudent(@RequestParam Integer studentId,@RequestParam Integer courseId){
        return studentService.unenrollFromCourse(studentId,courseId);

    }















}
