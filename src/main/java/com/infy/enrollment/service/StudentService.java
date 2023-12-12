package com.infy.enrollment.service;

import com.infy.enrollment.entity.Course;
import com.infy.enrollment.entity.Student;
import com.infy.enrollment.repository.CourseRepository;
import com.infy.enrollment.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    public Student getStudentById(int id ){
        Optional<Student>optionalStudent=studentRepository.findById(id);
        if (optionalStudent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found");
        }
        return optionalStudent.get();
    }

    public List<Student>getAllStudents(){
        return studentRepository.findAll();
    }
    public Student addStudent (Student student ){
        if(student.getEmail()==null|| student.getName()==null||student.getLastName()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student missing email, name or lastname. ");
        }
//        if (!EmailValidator.getInstance().isValid(student.getEmail())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid email.")
//        }
        student.setEmail(student.getEmail().toLowerCase());
        Optional<Student>optionalStudentFromDB= studentRepository.findByEmail(student.getEmail()).stream().findFirst();
        if (optionalStudentFromDB.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student with email"+student.getEmail()+"already exists");
        }
        student.setCourses(new HashSet<>());
        return studentRepository.save(student);
    }
    public Student updateStudent (Student student){
        if (student.getId()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student to be updated missing id");
        }
        Optional<Student>optionalStudent=studentRepository.findById(student.getId());
        if (optionalStudent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student with id "+student.getId()+"not found ");

        }
        Student studentFromDb =optionalStudent.get();
        if(student.getEmail()!=null){
            studentFromDb.setEmail(student.getEmail().toLowerCase());
        }
        if (student.getName()!=null){
            studentFromDb.setName(student.getName());
        }
        if (student.getLastName()!=null){
            studentFromDb.setLastName(student.getLastName());
        }
        return studentRepository.save(studentFromDb);
    }

    public void deleteStudent(Integer id ){
        Optional<Student>optionalStudent=studentRepository.findById(id);
            if (optionalStudent.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student to be deleted not found ");
            }
            studentRepository.deleteById(id);

        }
        public Student enrollInCourse(Integer studentId, Integer courseId ){
        if(studentId==null ||courseId==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Missing student or course id");
        }
        Optional<Student>optionalStudent=studentRepository.findById(studentId);
        if(optionalStudent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found");

        }
        Optional<Course>optionalCourse=courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found");
        }
        optionalStudent.get().enrollToCourse(optionalCourse.get());
        return studentRepository.save(optionalStudent.get());
        }
        public Student unenrollFromCourse (Integer sId , Integer cId){

            if(sId==null || cId==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Missing student or course id");
            }
            Optional<Student>optionalStudent=studentRepository.findById(sId);
            if(optionalStudent.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found");

            }
            Optional<Course>optionalCourse=courseRepository.findById(cId);
            if (optionalCourse.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found");
            }
            optionalStudent.get().getCourses().remove(optionalCourse.get());
            optionalCourse.get().getStudents().remove(optionalCourse.get());
            return studentRepository.save(optionalStudent.get());
        }
        }




