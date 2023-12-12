package com.infy.enrollment.service;
import com.infy.enrollment.entity.Course;
import com.infy.enrollment.entity.Teacher;
import com.infy.enrollment.repository.CourseRepository;
import com.infy.enrollment.repository.StudentRepository;
import com.infy.enrollment.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    public Course getCourseById(int  id) {
        Optional<Course>optionalCourse= courseRepository.findById(id);
        if(optionalCourse.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course Not found");
        }
        return optionalCourse.get();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course addCourse(Course course ) {
       if (course.getTitle()==null){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Missing title");
       }
       Optional<Course>optionalCourse=courseRepository.findByTitleIgnoreCaseContaining(course.getTitle());
       if (optionalCourse.isPresent()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Course already exists");
       }
       course.setTeacher(null);
       return courseRepository.save(course);

}
    public void deleteCourse(Integer id  ) {

        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course to deleted not found");
        }
        optionalCourse.get().getStudents().stream().forEach(student -> student.removeCourse(optionalCourse.get()));
        if (optionalCourse.get().getTeacher() != null) {
            optionalCourse.get().removeTeacher(optionalCourse.get().getTeacher());
        }
        courseRepository.deleteById(id);
    }
    public Course assginTeacher(Integer courseId, Integer teacherId){
        if (courseId==null||teacherId==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Course id or teacher id missing.");

        }
        Optional<Course>optionalCourse =courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found.");

        }
        Optional<Teacher>optionalTeacher=teacherRepository.findById(teacherId);
        if (optionalTeacher.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Teacher not found");
        }
        optionalCourse.get().setTeacher(optionalTeacher.get());
        return courseRepository.save(optionalCourse.get());
    }

    public Course unassginTeacher(Integer courseId){
        if (courseId==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Course id missing.");

        }
        Optional<Course>optionalCourse =courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found.");

        }
        Teacher teacher =optionalCourse.get().getTeacher();
        if (teacher==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not assigned to any teacher");
        }
        optionalCourse.get().setTeacher(null);
        teacher.setCourse(null);
        return courseRepository.save(optionalCourse.get());
    }

}



