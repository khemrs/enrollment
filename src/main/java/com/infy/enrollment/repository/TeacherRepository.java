package com.infy.enrollment.repository;

import com.infy.enrollment.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository <Teacher,Integer> {
    Optional<Teacher>findByEmail(String email);
    Optional<Teacher> findByIdAndEmail(Integer id , String email);

}
