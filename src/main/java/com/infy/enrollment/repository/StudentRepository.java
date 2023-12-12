package com.infy.enrollment.repository;

import com.infy.enrollment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findByNameContaining(String name);
    List<Student> findByEmail(String email);
}

