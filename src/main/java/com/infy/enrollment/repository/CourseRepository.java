package com.infy.enrollment.repository;


import com.infy.enrollment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

   Optional<Course> findByTeacherId(Integer id);
   Optional <Course>  findByTitleIgnoreCaseContaining(String title);

}