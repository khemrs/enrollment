package com.infy.enrollment.controller;
import com.infy.enrollment.entity.Teacher;
import com.infy.enrollment.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment/api/teachers")
public class TeacherController {
@Autowired

    private TeacherService teacherService;
    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable("id")int id ){
        return teacherService.getTeacherById(id);
    }

    @GetMapping()
    public List<Teacher>getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @PostMapping("/addTeacher")
    public Teacher addTeacher(@RequestBody Teacher teacher){
        return teacherService.addTeacher(teacher);

    }
    @PutMapping("/updateTeacher")
    public Teacher updateTeacher(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);

    }


    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable ("id")int id){
        teacherService.deleteTeacher(id);

    }
}