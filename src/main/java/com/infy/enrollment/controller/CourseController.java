package com.infy.enrollment.controller;
import com.infy.enrollment.entity.Course;
import com.infy.enrollment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable ("id") int id){
        return courseService.getCourseById(id);
    }
    @GetMapping()
    public List<Course> getAllCourse(){
        return courseService.getAllCourses();
    }

   @PostMapping("/addCourse")
    public  Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
   }
   @DeleteMapping("/{id}")
        public void deleteCourse(@PathVariable ("id")int id ){
            courseService.deleteCourse(id);
       }
   @PostMapping  ("assign-teacher") 
   public Course assignTeacher(@RequestParam Integer courseId,@RequestParam Integer teacherId) {
        return courseService.assginTeacher(courseId,teacherId);
   }
    @PostMapping  ("/{courseId}/unassign-teacher")
    public Course unassignTeacher(@PathVariable Integer courseId) {
        return courseService.unassginTeacher(courseId);
    }


}
