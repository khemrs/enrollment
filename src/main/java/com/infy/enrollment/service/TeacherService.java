package com.infy.enrollment.service;

import com.infy.enrollment.entity.Course;
import com.infy.enrollment.entity.Teacher;
import com.infy.enrollment.repository.CourseRepository;
import com.infy.enrollment.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Teacher getTeacherById(Integer id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "teacher not found");

        }
        return optionalTeacher.get();

    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher addTeacher(Teacher teacher) {
        if (teacher.getEmail() == null || teacher.getName() == null || teacher.getLastName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing  email,name or lastName");

        }
        Optional<Teacher> optionalTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (optionalTeacher.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher already exists.");

        }
        teacher.setEmail(teacher.getEmail().toLowerCase());
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Teacher teacher) {
        if (teacher.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher to be updated id missing ");
        }
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacher.getId());
        if (optionalTeacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found.");

        }
        Teacher teacherFromDb = optionalTeacher.get();
        if (teacher.getEmail() != null) {
            teacherFromDb.setEmail(teacher.getEmail().toLowerCase());
        }
        if (teacher.getName() != null) {
            teacherFromDb.setName(teacher.getName());
        }
        if (teacher.getLastName() != null) {
            teacherFromDb.setLastName(teacher.getLastName());
        }
        return teacherRepository.save(teacherFromDb);
    }

    public void deleteTeacher(Integer id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher to delete not found.");
        }
            if (optionalTeacher.get().getCourse() != null) {
                Course course =
                        optionalTeacher.get().getCourse();
                course.removeTeacher(optionalTeacher.get());
                teacherRepository.deleteById(id);
                return;
            }
            teacherRepository.deleteById(id);

        }
    }

